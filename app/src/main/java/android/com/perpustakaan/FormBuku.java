package android.com.perpustakaan;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.com.perpustakaan.Koneksi.id_kategori_buku;
import static android.com.perpustakaan.Koneksi.judul_buku;
import static android.com.perpustakaan.Koneksi.nama_kategori;

public class FormBuku extends AppCompatActivity implements View.OnClickListener {
    Button pilih,simpan,edit,hapus;
    Spinner spKategori;
    ImageView iv;
    Bitmap bitmap = null;
    Uri imageUri;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    EditText penulis, nama_buku, stok;
    //set-adapter-to-edit (array)
    private ArrayList<String>arrayKategori, arrayIndex;

    String id_kategori, filenameUrl;
    private int id_buku_edit;
    private int id_kategori_edit;
//    String judul,penulis,stok;
//    ArrayList<HashMap<String, String>> tampil = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_buku);

        penulis = (EditText)findViewById(R.id.penulis);
        nama_buku = (EditText)findViewById(R.id.namaBuku);
        stok = (EditText)findViewById(R.id.stok);
        spKategori = (Spinner)findViewById(R.id.spinnerKategori);
        pilih = findViewById(R.id.btn_pilih);
        simpan = findViewById(R.id.btn_simpan);
        edit = (Button)findViewById(R.id.btn_edit);
        hapus = (Button)findViewById(R.id.btn_hapus);
        iv = findViewById(R.id.iv_gambar);

        //set-adapter-to-edit (array)
        arrayKategori = new ArrayList<String>();
        arrayIndex = new ArrayList<String>();

        Intent intent = getIntent();
        id_buku_edit = intent.getIntExtra("id_edit_buku", 0);
        id_kategori_edit = intent.getIntExtra("id_kategori", 0);
        filenameUrl = intent.getStringExtra("filename");

        nama_buku.setText(intent.getStringExtra("judul_buku"));
        penulis.setText(intent.getStringExtra("penulis"));
        stok.setText(intent.getStringExtra("stok"));

        ambilDataKategori();

        if (id_buku_edit == 0) {
            simpan.setVisibility(View.VISIBLE);
            edit.setVisibility(View.INVISIBLE);
            hapus.setVisibility(View.INVISIBLE);
        } else {
//            editFunc();
            Glide.with(this)
                    .load("http://192.168.43.216/perpusbackend/gambar_buku/"+filenameUrl)
                    .into(iv);
            simpan.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            hapus.setVisibility(View.VISIBLE);
        }
        spKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = parent.getSelectedItemPosition();
                id_kategori = String.valueOf(arrayIndex.get(index));
//                Toast.makeText(getBaseContext(),
//                        "You have selected item : " + arrayIndex.get(index),
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(),
                        "Nothing" ,
                        Toast.LENGTH_SHORT).show();
            }
        });

        pilih.setOnClickListener(this);
        simpan.setOnClickListener(this);
        edit.setOnClickListener(this);
        hapus.setOnClickListener(this);
        perizinan();
//        editFunc();

//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, nomnal);
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spKategori.setAdapter(spinnerArrayAdapter);
    }

    private void ambilDataKategori() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.tampil_kategori,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("1")) {
                            try {
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");

                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
                                    arrayKategori.add(c.getString(nama_kategori));
                                    arrayIndex.add(c.getString(id_kategori_buku));
//                                    String id = c.getString(Koneksi.id_kategori_buku);
//                                    String nama = c.getString(Koneksi.nama_kategori);
//                                    HashMap<String, String> map = new HashMap<String, String>();
//                                    map.put(Koneksi.nama_kategori, nama);
//                                    arrayKategori.add(map);
                                }
                                spKategori.setAdapter(new ArrayAdapter<String>(FormBuku.this, android.R.layout.simple_spinner_dropdown_item, arrayKategori));

                                //set-adapter-to-edit
                                if (id_kategori_edit != 0) {
                                    for (int j = 0; j < arrayIndex.size(); j++) {
                                        if (id_kategori_edit == Integer.parseInt(arrayIndex.get(j))) {
                                            //Toast.makeText(getApplicationContext(), "HALO", Toast.LENGTH_SHORT).show();
                                            spKategori.setSelection(j,true); //cerpen
//                                            return;
                                        }
                                    }
                                    //Toast.makeText(getApplicationContext(), String.valueOf(arrayIndex.size()), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "Tidak ada", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //berfungsi memberi tahu jika ada kesalahan pada server atau koneksi aplikasi.
                        Toast.makeText(FormBuku.this, "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_hapus:
                hapus();
                break;
            case R.id.btn_edit:
                update();
                break;
            case R.id.btn_simpan:
                simpan();
                break;
            case R.id.btn_pilih:
                selectImage();
                break;
            default:
                break;
        }
    }

    private void simpan() {
        final String judul =  nama_buku.getText().toString();
        final String pnls = penulis.getText().toString();
        final String stk = stok.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Koneksi.simpan_buku, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("1")) {
                    startActivity(new Intent(FormBuku.this, Buku.class));
                }else{
                    Toast.makeText(FormBuku.this, "Gagal",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FormBuku.this,String.valueOf(error),Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Koneksi.cover_buku,getStringImage(bitmap));
                params.put(Koneksi.judul_buku,judul);
                params.put(Koneksi.penulis, pnls);
                params.put(Koneksi.stok, stk);
                params.put(Koneksi.id_kategori_buku, id_kategori);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void hapus() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.hapus_buku,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("1")) {
                            //Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FormBuku.this, Buku.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Koneksi.id_buku,String.valueOf(id_buku_edit));
                params.put(Koneksi.cover_buku, filenameUrl);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void update() {
        final String judul =  nama_buku.getText().toString();
        final String pnls = penulis.getText().toString();
        final String stk = stok.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.edit_buku,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("1")) {
                            //Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FormBuku.this, Buku.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Koneksi.cover_buku,getStringImage(bitmap));
                params.put(Koneksi.id_buku, String.valueOf(id_buku_edit));
                params.put(Koneksi.judul_buku,judul);
                params.put(Koneksi.penulis, pnls);
                params.put(Koneksi.stok, stk);
                params.put(Koneksi.id_kategori_buku, id_kategori);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void perizinan(){
        ActivityCompat.requestPermissions(FormBuku.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                99);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 99: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    perizinan();
                }
                return;
            }
        }
    }
    private void selectImage() {
        final CharSequence[] options = {"Ambil Foto", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(
                FormBuku.this);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Ambil Foto")) {
                    String fileName = "new-photo-name.jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                    imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, PICK_Camera_IMAGE);
                } else if (options[item].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_IMAGE);
                }
            }
        });
        builder.show();

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;
        String filePath = null;
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data.getData();
                }
                break;
            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectedImageUri = imageUri;
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if (selectedImageUri != null) {
            try {
                String filemanagerstring = selectedImageUri.getPath();
                String selectedImagePath = getPath(selectedImageUri);

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(FormBuku.this, "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    decodeFile(filePath);
                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(FormBuku.this, "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);
        final int REQUIRED_SIZE = 1024;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);
        iv.setImageBitmap(bitmap);

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}

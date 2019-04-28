package android.com.perpustakaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pinjam extends AppCompatActivity {

    Button tambah_buku,cari_buku;
    EditText ed_cari_buku;
    RecyclerView rv_pinjam_buku;
    ArrayList<HashMap<String, String>> tampil = new ArrayList<HashMap<String, String>>();
    //mana class adapter dibuat varibel
    AdapterPinjam adapter;
    //  ImageView imgBuku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinjam);

//        tambah_buku = (Button)findViewById(R.id.btn_tambah_buku);
//        tambah_buku.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Pinjam.this, FormBuku.class));
//            }
//        });

        rv_pinjam_buku = (RecyclerView)findViewById(R.id.rv_pinjam_buku);
        rv_pinjam_buku.setHasFixedSize(true);
        //menngatur rotasi pada tampilan data.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        // tipe oriention ada vertical dan horizontal
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_pinjam_buku.setLayoutManager(llm);

//        ed_cari_buku = (EditText)findViewById(R.id.ed_cari_buku);
//        cari_buku = (Button)findViewById(R.id.btn_cari_buku);
//        cari_buku.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cariBuku();
//            }
//        });


        tampil();
    }

    private void tampil() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.tampil_buku,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("1")) {
                            try {
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");
                                tampil.clear();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
                                    String id_kategori = c.getString(Koneksi.id_kategori_buku);
                                    String nama = c.getString(Koneksi.nama_kategori);
                                    String judul = c.getString(Koneksi.judul_buku);
                                    String id_buku = c.getString(Koneksi.id_buku);
                                    String cover = c.getString(Koneksi.cover_buku);
                                    String penulis = c.getString(Koneksi.penulis);
                                    String stk = c.getString(Koneksi.stok);
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put(Koneksi.id_kategori_buku, id_kategori);
                                    map.put(Koneksi.nama_kategori, nama);
                                    map.put(Koneksi.judul_buku, judul);
                                    map.put(Koneksi.id_buku, id_buku);
                                    map.put(Koneksi.cover_buku, cover);
                                    map.put(Koneksi.penulis, penulis);
                                    map.put(Koneksi.stok, stk);
                                    tampil.add(map);
                                }
                                adapter=new AdapterPinjam(Pinjam.this,tampil);
                                rv_pinjam_buku.setAdapter(adapter);
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
                        Toast.makeText(Pinjam.this, "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
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
}

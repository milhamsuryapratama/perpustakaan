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

public class Peminjaman extends AppCompatActivity {

    Button tambah_peminjaman, cari_pinjam;
    EditText ed_cari_pinjam;
    RecyclerView rv_peminjaman;
    ArrayList<HashMap<String, String>> tampil = new ArrayList<HashMap<String, String>>();
    //mana class adapter dibuat varibel
    AdapterPeminjaman adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);

        tambah_peminjaman = (Button)findViewById(R.id.btn_tambah_peminjaman);
        rv_peminjaman = (RecyclerView)findViewById(R.id.rv_peminjaman);
        rv_peminjaman.setHasFixedSize(true);
        //menngatur rotasi pada tampilan data.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        // tipe oriention ada vertical dan horizontal
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv_peminjaman.setLayoutManager(llm);
        ed_cari_pinjam = (EditText)findViewById(R.id.ed_cari_pinjam);
        cari_pinjam = (Button)findViewById(R.id.btn_cari_pinjam);
        cari_pinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cariPinjam();
            }
        });

        tambah_peminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Peminjaman.this, FormPinjam.class));
            }
        });

        tampil();
    }

    private void cariPinjam() {
        final String cr_pnjm = ed_cari_pinjam.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.cari_pinjam,
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
                                    String id_pinjam = c.getString(Koneksi.id_pinjam);
                                    String nama_peminjam = c.getString(Koneksi.nama_peminjam);
                                    String jumlah_pinjam = c.getString(Koneksi.jumlah_pinjam);
                                    String waktu_pinjam = c.getString(Koneksi.waktu_pinjam);
                                    String waktu_kembali = c.getString(Koneksi.waktu_kembali);
                                    String status = c.getString(Koneksi.status_pinjam);
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put(Koneksi.id_pinjam, id_pinjam);
                                    map.put(Koneksi.nama_peminjam, nama_peminjam
                                    );
                                    map.put(Koneksi.jumlah_pinjam, jumlah_pinjam);
                                    map.put(Koneksi.waktu_pinjam, waktu_pinjam);
                                    map.put(Koneksi.waktu_kembali, waktu_kembali);
                                    map.put(Koneksi.status_pinjam, status);
                                    tampil.add(map);
                                }
                                adapter=new AdapterPeminjaman(Peminjaman.this,tampil);
                                rv_peminjaman.setAdapter(adapter);
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
                        Toast.makeText(Peminjaman.this, "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Koneksi.nama_peminjam,cr_pnjm);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void tampil() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.tampil_pinjam,
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
                                    String id_pinjam = c.getString(Koneksi.id_pinjam);
                                    String nama_peminjam = c.getString(Koneksi.nama_peminjam);
                                    String jumlah_pinjam = c.getString(Koneksi.jumlah_pinjam);
                                    String waktu_pinjam = c.getString(Koneksi.waktu_pinjam);
                                    String waktu_kembali = c.getString(Koneksi.waktu_kembali);
                                    String status = c.getString(Koneksi.status_pinjam);
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put(Koneksi.id_pinjam, id_pinjam);
                                    map.put(Koneksi.nama_peminjam, nama_peminjam
                                    );
                                    map.put(Koneksi.jumlah_pinjam, jumlah_pinjam);
                                    map.put(Koneksi.waktu_pinjam, waktu_pinjam);
                                    map.put(Koneksi.waktu_kembali, waktu_kembali);
                                    map.put(Koneksi.status_pinjam, status);
                                    tampil.add(map);
                                }
                                adapter=new AdapterPeminjaman(Peminjaman.this,tampil);
                                rv_peminjaman.setAdapter(adapter);
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
                        Toast.makeText(Peminjaman.this, "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
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

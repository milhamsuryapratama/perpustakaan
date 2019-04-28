package android.com.perpustakaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

public class FormPinjam extends AppCompatActivity {

    EditText nama_peminjam,id_buku,jumlah_pinjam;
    Button simpan_pinjam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pinjam);

        nama_peminjam = (EditText) findViewById(R.id.ed_nama_peminjam);
        id_buku = (EditText) findViewById(R.id.ed_id_buku);
        jumlah_pinjam = (EditText) findViewById(R.id.ed_jumlah_pinjam);

        simpan_pinjam = (Button) findViewById(R.id.simpan_pinjam);

        simpan_pinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanPinjam();
            }
        });
    }

    private void simpanPinjam() {
        final String nm_peminjam = nama_peminjam.getText().toString();
        final String id_bk = id_buku.getText().toString();
        final String jml_pinjam = jumlah_pinjam.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.simpan_pinjam,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //untuk menerima respon ketika berhasil / tidak berhasil pada php.
                        if(response.contains("1")) {
                            //Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FormPinjam.this, Peminjaman.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //berfungsi memberi tahu jika ada kesalahan pada server atau koneksi aplikasi.
                        Toast.makeText(getApplicationContext(), "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //untuk mengirim request pada php
                params.put(Koneksi.nama_peminjam, nm_peminjam);
                params.put(Koneksi.id_buku, id_bk);
                params.put(Koneksi.jumlah_pinjam, jml_pinjam);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

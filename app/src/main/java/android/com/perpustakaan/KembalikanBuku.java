package android.com.perpustakaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class KembalikanBuku extends AppCompatActivity {

    Button kembalikan;
    TextView tx_status;
    private int id_pinjaman;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kembalikan_buku);

        kembalikan = (Button) findViewById(R.id.kembalikan);
        tx_status = (TextView)findViewById(R.id.tx_status);

        Intent intent = getIntent();
        id_pinjaman = intent.getIntExtra("id_pinjam", 0);
        status = intent.getStringExtra("status_pinjam");

        if (status.equalsIgnoreCase("Dipinjam")) {
            kembalikan.setVisibility(View.VISIBLE);
            tx_status.setVisibility(View.INVISIBLE);
        } else {
            kembalikan.setVisibility(View.INVISIBLE);
            tx_status.setVisibility(View.VISIBLE);
        }


        kembalikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus();
            }
        });
    }

    private void updateStatus() {
//        final String nm_kategori =  nama_kategori.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Koneksi.update_status_pinjam,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("1")) {
                            //Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(KembalikanBuku.this, Peminjaman.class));
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
                params.put(Koneksi.id_pinjam, String.valueOf(id_pinjaman));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

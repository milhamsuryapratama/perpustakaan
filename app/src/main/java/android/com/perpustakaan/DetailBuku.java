package android.com.perpustakaan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailBuku extends AppCompatActivity implements View.OnClickListener {

    TextView judul, kategori, penulis, stok;
    ImageView imgBuku;
    Button pinjam;

    String id_kategori_buk, filenameUrl;
    private int id_buku_edit;
    private int id_kategori_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku);

        judul = (TextView)findViewById(R.id.namaBuku);
        kategori = (TextView)findViewById(R.id.nama_kategori);
        penulis = (TextView)findViewById(R.id.penulis);
        stok = (TextView)findViewById(R.id.stok);
        imgBuku = (ImageView)findViewById(R.id.iv_gambar);
        pinjam = (Button)findViewById(R.id.btn_pinjam_buku);

        Intent intent = getIntent();
        id_buku_edit = intent.getIntExtra("id_edit_buku", 0);
        id_kategori_edit = intent.getIntExtra("id_kategori", 0);
        filenameUrl = intent.getStringExtra("filename");

        judul.setText(intent.getStringExtra("judul_buku"));
        penulis.setText(intent.getStringExtra("penulis"));
        stok.setText(intent.getStringExtra("stok"));
        kategori.setText(intent.getStringExtra("nama_kategori"));

        Glide.with(this)
                .load("http://192.168.43.216/perpusbackend/gambar_buku/"+filenameUrl)
                .into(imgBuku);

        pinjam.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pinjam_buku:
                pinjamButu();
        }
    }

    private void pinjamButu() {
        Toast.makeText(this, "Item ID : " + String.valueOf(id_buku_edit), Toast.LENGTH_SHORT).show();
    }
}

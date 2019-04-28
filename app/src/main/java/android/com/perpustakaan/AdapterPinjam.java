package android.com.perpustakaan;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterPinjam extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> data;
    ArrayList<HashMap<String, String>> tampil = new ArrayList<HashMap<String, String>>();

    public AdapterPinjam(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_pinjam_buku, null);

        AdapterPinjam.MyHolder holder = new AdapterPinjam.MyHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final AdapterPinjam.MyHolder myHolder = (AdapterPinjam.MyHolder) holder;

        HashMap<String, String> data_tampil = new HashMap<String, String>();
        data_tampil = data.get(position);
        Glide.with(context)
                .load("http://192.168.43.216/perpusbackend/gambar_buku/"+data_tampil.get(Koneksi.cover_buku))
                .into(myHolder.mImageView);
        myHolder.mTextView.setText(data_tampil.get(Koneksi.judul_buku));
        myHolder.mTextView1.setText(data_tampil.get(Koneksi.nama_kategori));
        myHolder.mTextView2.setText(data_tampil.get(Koneksi.id_buku));
        myHolder.mTextView3.setText(data_tampil.get(Koneksi.penulis));
        myHolder.mTextView4.setText(data_tampil.get(Koneksi.stok));
        myHolder.mTextView5.setText(data_tampil.get(Koneksi.id_kategori_buku));
        myHolder.mFilenaeUrl.setText(data_tampil.get(Koneksi.cover_buku));

        myHolder.linearPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Item is clicked : " + myHolder.mTextView1.getText().toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (view.getContext(), DetailBuku.class);
                intent.putExtra("id_edit_buku", Integer.valueOf(myHolder.mTextView2.getText().toString()));
                intent.putExtra("judul_buku", myHolder.mTextView.getText().toString());
                intent.putExtra("penulis", myHolder.mTextView3.getText().toString());
                intent.putExtra("stok", myHolder.mTextView4.getText().toString());
                intent.putExtra("id_kategori", Integer.valueOf(myHolder.mTextView5.getText().toString()));
                intent.putExtra("filename", myHolder.mFilenaeUrl.getText().toString());
                intent.putExtra("nama_kategori", myHolder.mTextView1.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHoolder extends RecyclerView.ViewHolder {

        public MyHoolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView mTextView,mTextView1,mTextView2,mTextView3,mTextView4,mTextView5,mFilenaeUrl;
        ImageView mImageView;
        LinearLayout linearPinjam;

        public MyHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.dt_nama_buku);
            mTextView1 = (TextView) itemView.findViewById(R.id.dt_nama_kategori);
            mImageView = (ImageView) itemView.findViewById(R.id.img_buku);
            mTextView2 = (TextView) itemView.findViewById(R.id.dt_id_buku);
            mTextView3 = (TextView) itemView.findViewById(R.id.dt_penulis);
            mTextView4 = (TextView) itemView.findViewById(R.id.dt_stok);
            mTextView5 = (TextView) itemView.findViewById(R.id.dt_id_kategori);
            mFilenaeUrl = (TextView) itemView.findViewById(R.id.dt_filename);
//
            linearPinjam = itemView.findViewById(R.id.linier_pinjam_buku);
        }
    }
}

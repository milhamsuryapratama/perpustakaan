package android.com.perpustakaan;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterPeminjaman extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> data;

    public AdapterPeminjaman(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_peminjaman, null);

        AdapterPeminjaman.MyHolder holder = new AdapterPeminjaman.MyHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final AdapterPeminjaman.MyHolder myHolder = (AdapterPeminjaman.MyHolder) holder;

        HashMap<String, String> data_tampil = new HashMap<String, String>();
        data_tampil = data.get(position);

        myHolder.mIdPeminjaman.setText(data_tampil.get(Koneksi.id_pinjam));
        myHolder.mNamaPeminjam.setText(data_tampil.get(Koneksi.nama_peminjam));
        myHolder.mJumlahPinjam.setText(data_tampil.get(Koneksi.jumlah_pinjam));
        myHolder.mWaktuPinjam.setText(data_tampil.get(Koneksi.waktu_pinjam));
        myHolder.mWaktuKembali.setText(data_tampil.get(Koneksi.waktu_kembali));
        myHolder.mStatusPinjam.setText(data_tampil.get(Koneksi.status_pinjam));

        myHolder.pmjmn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (view.getContext(), KembalikanBuku.class);
                intent.putExtra("id_pinjam", Integer.valueOf(myHolder.mIdPeminjaman.getText().toString()));
                intent.putExtra("status_pinjam", myHolder.mStatusPinjam.getText().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView mIdPeminjaman, mNamaPeminjam, mJumlahPinjam, mWaktuPinjam, mWaktuKembali, mStatusPinjam;
        LinearLayout pmjmn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mIdPeminjaman = (TextView) itemView.findViewById(R.id.dt_id_peminjaman);
            mNamaPeminjam = (TextView) itemView.findViewById(R.id.dt_nama_peminjam);
            mJumlahPinjam = (TextView) itemView.findViewById(R.id.dt_jumlah_pinjam);
            mWaktuPinjam = (TextView) itemView.findViewById(R.id.dt_waktu_pinjam);
            mWaktuKembali = (TextView) itemView.findViewById(R.id.dt_waktu_kembali);
            mStatusPinjam = (TextView) itemView.findViewById(R.id.dt_status_pinjam);

            pmjmn = itemView.findViewById(R.id.peminjaman);
        }
    }
}

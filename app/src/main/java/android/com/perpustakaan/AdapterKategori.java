package android.com.perpustakaan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterKategori extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    TextView nma;
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<HashMap<String, String>> data;
    ArrayList<HashMap<String, String>> tampil = new ArrayList<HashMap<String, String>>();
    //deklarasi pengambilan data
    public AdapterKategori(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    //pendeklarasian item layout
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_kategori, null);

        MyHolder holder = new MyHolder(v);

        return holder;
    }


    //Penampilan data recycleriew
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MyHolder myHolder = (MyHolder) holder;

        HashMap<String, String> data_tampil = new HashMap<String, String>();
        data_tampil = data.get(position);
        myHolder.mTextView.setText(data_tampil.get(Koneksi.nama_kategori));
        myHolder.mTextView1.setText(data_tampil.get(Koneksi.id_kategori_buku));
//        myHolder.mTextView2.setText(data_tampil.get(koneksi.key_alamat));

//        myHolder.lio.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return false;
//            }
//        });

        myHolder.lio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Item is clicked : " + myHolder.mTextView.getText().toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent (view.getContext(), FormKategori.class);
                intent.putExtra("id", Integer.valueOf(myHolder.mTextView1.getText().toString()));
                intent.putExtra("nama", myHolder.mTextView.getText().toString());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView mTextView,mTextView1;
        LinearLayout lio;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.dt_nama_kategori);
            mTextView1 = (TextView) itemView.findViewById(R.id.dt_id_kategori);
//            mTextView2 = (TextView) itemView.findViewById(R.id.tvx_alamat);
//
            lio = itemView.findViewById(R.id.kategori);
        }

    }
}

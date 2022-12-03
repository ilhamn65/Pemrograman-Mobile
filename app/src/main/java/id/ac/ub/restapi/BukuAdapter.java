package id.ac.ub.restapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.ViewHolder> {
    LayoutInflater inflatter;
    ArrayList<Buku> items;
    Context context;

    public BukuAdapter(Context context, ArrayList<Buku> items ) {
        this.context = context;
        this.inflatter = LayoutInflater.from(context);
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflatter.inflate(R.layout.row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Buku item = items.get(position);
        holder.tvJudul.setText(item.getJudul());
        holder.tvDeskripsi.setText(item.getDeskripsi());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul;
        TextView tvDeskripsi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsi);
        }
    }
}

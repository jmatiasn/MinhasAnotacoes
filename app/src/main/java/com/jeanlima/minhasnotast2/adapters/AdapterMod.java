package com.jeanlima.minhasnotast2.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.jeanlima.minhasnotast2.R;

import com.jeanlima.minhasnotast2.Modelo.Nota;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AdapterMod extends RecyclerView.Adapter<AdapterMod.MinhaViewHolder> {

    private List<Nota> mNotas;

    public AdapterMod(List<Nota> lista){
        mNotas = lista;
    }

    @NonNull
    @Override
    public MinhaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elemento_lista,parent,false);

        return new MinhaViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MinhaViewHolder holder, int position) {

        Nota nota = mNotas.get(position);
        holder.tvTituloNota.setText(nota.getTitulo());
        holder.tvDescricaoNota.setText(nota.getDescricao());

        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        holder.tvDataCriacaoNota.setText(dateFormat.format(nota.getDataCriacao()));
    }

    @Override
    public int getItemCount() {
        return mNotas.size();
    }

    public class MinhaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        CardView cvNota;
        TextView tvTituloNota;
        TextView tvDescricaoNota;
        TextView tvDataCriacaoNota;

        public MinhaViewHolder(View itemView){
            super(itemView);

            cvNota = itemView.findViewById(R.id.cvNota);
            tvTituloNota = itemView.findViewById(R.id.tvTituloNota);
            tvDescricaoNota = itemView.findViewById(R.id.tvDescricaoNota);
            tvDataCriacaoNota = itemView.findViewById(R.id.tvDataCriacaoNota);

            cvNota.setOnClickListener(this);
            cvNota.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.clicouNaNota(getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.pressionouNota(getLayoutPosition());
            return true;
        }
    }

    public interface AoClicarNoItem{
        public void clicouNaNota(int pos);
        public void pressionouNota(int pos);
    }

    public AoClicarNoItem listener;

    public void implementaAoClicarNoItem(AoClicarNoItem listener){
        this.listener = listener;
    }
}

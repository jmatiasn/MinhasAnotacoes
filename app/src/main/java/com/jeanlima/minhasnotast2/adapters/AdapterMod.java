package com.jeanlima.minhasnotast2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.jeanlima.minhasnotast2.R;

import com.jeanlima.minhasnotast2.Modelo.Nota;

import java.util.List;

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
        holder.tvDescricaoNota.setText(nota.getDescricao());

    }

    @Override
    public int getItemCount() {
        return mNotas.size();
    }

    public class MinhaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        CardView cvNota;
        TextView tvDescricaoNota;

        public MinhaViewHolder(View itemView){
            super(itemView);

            cvNota = itemView.findViewById(R.id.cvTarefa);
            tvDescricaoNota = itemView.findViewById(R.id.tvDescricaoTarefa);

            cvNota.setOnClickListener(this);
            cvNota.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.clicouNaTarefa(getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.pressionouTarefa(getLayoutPosition());
            return true;
        }
    }

    public interface AoClicarNoItem{
        public void clicouNaTarefa(int pos);
        public void pressionouTarefa(int pos);
    }

    public AoClicarNoItem listener;

    public void implementaAoClicarNoItem(AoClicarNoItem listener){
        this.listener = listener;
    }
}

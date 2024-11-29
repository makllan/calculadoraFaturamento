package com.arasaky.calculadora;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CustoFixoAdapter extends RecyclerView.Adapter<CustoFixoAdapter.ViewHolder> {
    private List<CustoFixo> custos;
    private OnRemoverCustoListener removerCustoListener;

    public interface OnRemoverCustoListener {
        void onRemover(int position);
    }

    public CustoFixoAdapter(List<CustoFixo> custos, OnRemoverCustoListener listener) {
        this.custos = custos;
        this.removerCustoListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_custo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CustoFixo custo = custos.get(position);
        holder.textDescricao.setText(custo.getDescricao());
        holder.textValor.setText(String.format("R$ %.2f", custo.getValor()));

        holder.btnRemover.setOnClickListener(v -> removerCustoListener.onRemover(position));
    }

    @Override
    public int getItemCount() {
        return custos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textDescricao, textValor;
        ImageButton btnRemover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textDescricao = itemView.findViewById(R.id.textDescricao);
            textValor = itemView.findViewById(R.id.textValor);
            btnRemover = itemView.findViewById(R.id.btnRemover);
        }
    }
}

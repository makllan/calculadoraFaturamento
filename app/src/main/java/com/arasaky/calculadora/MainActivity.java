package com.arasaky.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editPreco, editQuantidade, editCustoVariavel;
    private Button btnAdicionarCusto, btnCalcular;
    private RecyclerView recyclerCustosFixos;
    private TextView textResultado;

    private List<CustoFixo> custosFixos;
    private CustoFixoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPreco = findViewById(R.id.editPreco);
        editQuantidade = findViewById(R.id.editQuantidade);
        editCustoVariavel = findViewById(R.id.editCustoVariavel);
        btnAdicionarCusto = findViewById(R.id.btnAdicionarCusto);
        btnCalcular = findViewById(R.id.btnCalcular);
        recyclerCustosFixos = findViewById(R.id.recyclerCustosFixos);
        textResultado = findViewById(R.id.textResultado);

        custosFixos = new ArrayList<>();
        adapter = new CustoFixoAdapter(custosFixos, position -> {
            custosFixos.remove(position);
            adapter.notifyItemRemoved(position);
        });

        recyclerCustosFixos.setLayoutManager(new LinearLayoutManager(this));
        recyclerCustosFixos.setAdapter(adapter);

        btnAdicionarCusto.setOnClickListener(v -> mostrarDialogAdicionarCusto());
        btnCalcular.setOnClickListener(v -> calcularLucro());
    }

    private void mostrarDialogAdicionarCusto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Custo Fixo");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_adicionar_custo, null);
        builder.setView(dialogView);

        EditText editDescricaoCusto = dialogView.findViewById(R.id.editDescricaoCusto);
        EditText editValorCusto = dialogView.findViewById(R.id.editValorCusto);

        builder.setPositiveButton("Adicionar", (dialog, which) -> {
            String descricao = editDescricaoCusto.getText().toString();
            String valorTexto = editValorCusto.getText().toString();

            if (!descricao.isEmpty() && !valorTexto.isEmpty()) {
                double valor = Double.parseDouble(valorTexto);
                custosFixos.add(new CustoFixo(descricao, valor));
                adapter.notifyItemInserted(custosFixos.size() - 1);
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", null);

        builder.create().show();
    }

    private void calcularLucro() {
        try {
            double preco = Double.parseDouble(editPreco.getText().toString());
            int quantidade = Integer.parseInt(editQuantidade.getText().toString());
            double custoVariavel = Double.parseDouble(editCustoVariavel.getText().toString());

            double receitaTotal = preco * quantidade;
            double totalCustosVariaveis = custoVariavel * quantidade;

            double totalCustosFixos = 0;
            for (CustoFixo custo : custosFixos) {
                totalCustosFixos += custo.getValor();
            }

            double lucro = receitaTotal - (totalCustosVariaveis + totalCustosFixos);

            textResultado.setText(String.format(
                    "Receita Total: R$ %.2f\nCustos Fixos: R$ %.2f\nCustos Variáveis: R$ %.2f\nLucro Líquido: R$ %.2f",
                    receitaTotal, totalCustosFixos, totalCustosVariaveis, lucro));
        } catch (Exception e) {
            Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
        }
    }
}


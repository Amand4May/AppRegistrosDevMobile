package com.exemplo.registros;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

// PROVISÓRIO: mostra 3 itens falsos usando o layout item_registro.
public class ListaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        BarrasSistema.ajustar(this);
        String[] titulos = {"Inspeção da linha 3", "Checklist de EPI", "Troca de turno"};
        ListView lista = findViewById(R.id.listRegistros);
        lista.setEmptyView(findViewById(R.id.layoutVazio));
        lista.setAdapter(new ArrayAdapter<>(this, R.layout.item_registro, R.id.tvItemTitulo, titulos));
        ((TextView) findViewById(R.id.tvQuantidade)).setText(String.valueOf(titulos.length));

        lista.setOnItemClickListener((p, v, pos, id) ->
                startActivity(new Intent(this, DetalhesActivity.class)));
        findViewById(R.id.btnAdicionar).setOnClickListener(v ->
                startActivity(new Intent(this, FormularioActivity.class)));
        findViewById(R.id.btnVoltarLista).setOnClickListener(v -> finish());
    }
}

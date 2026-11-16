package com.exemplo.registros;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

// PROVISÓRIO: Editar abre o formulário; Excluir mostra o diálogo de confirmação.
public class DetalhesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        BarrasSistema.ajustar(this);
        findViewById(R.id.btnEditar).setOnClickListener(v ->
                startActivity(new Intent(this, FormularioActivity.class)));
        findViewById(R.id.btnExcluir).setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle(R.string.dialogo_excluir_titulo)
                        .setMessage(R.string.dialogo_excluir_texto)
                        .setPositiveButton(R.string.dialogo_sim, (d, w) -> {
                            Toast.makeText(this, R.string.msg_excluido, Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .setNegativeButton(R.string.dialogo_nao, null)
                        .show());
        findViewById(R.id.btnVoltarDetalhes).setOnClickListener(v -> finish());
    }
}

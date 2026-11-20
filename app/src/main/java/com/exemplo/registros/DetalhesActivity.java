package com.exemplo.registros;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetalhesActivity extends AppCompatActivity {
    private int registroId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        BarrasSistema.ajustar(this);
        registroId = getIntent().getIntExtra(RegistroRepositorio.EXTRA_ID, -1);

        findViewById(R.id.btnVoltarDetalhes).setOnClickListener(v -> finish());
        findViewById(R.id.btnEditar).setOnClickListener(v -> {
            Intent intent = new Intent(this, FormularioActivity.class);
            intent.putExtra(RegistroRepositorio.EXTRA_ID, registroId);
            startActivity(intent);
        });
        findViewById(R.id.btnExcluir).setOnClickListener(v -> confirmarExclusao());
    }

    // O formulário pode ter alterado o registro enquanto esta tela estava em segundo plano.
    @Override
    protected void onResume() {
        super.onResume();
        Registro registro = RegistroRepositorio.buscarPorId(registroId);
        if (registro == null) {
            encerrarComMensagem(R.string.detalhe_indisponivel);
            return;
        }
        mostrarTexto(R.id.tvDetalheTitulo, registro.getTitulo());
        mostrarTexto(R.id.tvDetalheCategoria, registro.getCategoria());
        mostrarTexto(R.id.tvDetalheSetor, registro.getSetor());
        mostrarTexto(R.id.tvDetalheResponsavel, registro.getResponsavel());
        mostrarTexto(R.id.tvDetalheDescricao, registro.getDescricao());
        mostrarTexto(R.id.tvDetalheData, registro.getDataFormatada());

        String prioridade = registro.getPrioridade();
        mostrarTexto(R.id.tvDetalhePrioridade, prioridade);
        int fundo = R.color.prioridade_media;
        int texto = R.color.texto_principal;
        if (Registro.PRIORIDADE_ALTA.equals(prioridade)) {
            fundo = R.color.prioridade_alta;
            texto = R.color.branco;
        } else if (Registro.PRIORIDADE_BAIXA.equals(prioridade)) {
            fundo = R.color.prioridade_baixa;
            texto = R.color.branco;
        }
        TextView badge = findViewById(R.id.tvDetalhePrioridade);
        badge.setBackgroundTintList(ColorStateList.valueOf(getColor(fundo)));
        badge.setTextColor(getColor(texto));
    }

    private void mostrarTexto(int id, String valor) {
        TextView campo = findViewById(id);
        campo.setText(valor == null || valor.trim().isEmpty()
                ? getString(R.string.detalhe_sem_valor) : valor);
    }

    private void confirmarExclusao() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialogo_excluir_titulo)
                .setMessage(R.string.dialogo_excluir_texto)
                .setNegativeButton(R.string.dialogo_nao, null)
                .setPositiveButton(R.string.dialogo_sim, (dialogo, botao) -> {
                    boolean removido = RegistroRepositorio.remover(registroId);
                    encerrarComMensagem(removido ? R.string.msg_excluido : R.string.detalhe_indisponivel);
                })
                .show();
    }

    private void encerrarComMensagem(int mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
        finish();
    }
}

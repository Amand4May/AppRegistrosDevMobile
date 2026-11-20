package com.exemplo.registros;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * Tela de listagem (visualização) dos registros — o "R" (Read) do CRUD.
 *
 * Mostra todos os registros guardados em memória no {@link RegistroRepositorio},
 * do mais recente para o mais antigo, e permite:
 *  - tocar em um registro para abrir a {@link DetalhesActivity} (onde é possível editar/excluir);
 *  - tocar em "Novo registro" para abrir a {@link FormularioActivity} em modo cadastro;
 *  - voltar para a tela anterior.
 *
 * Quando não há registros, o ListView exibe automaticamente o aviso de lista vazia
 * (layoutVazio), configurado com setEmptyView.
 */
public class ListaActivity extends AppCompatActivity {

    private ListView listRegistros;
    private TextView tvQuantidade;
    private RegistroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        BarrasSistema.ajustar(this);

        listRegistros = findViewById(R.id.listRegistros);
        tvQuantidade = findViewById(R.id.tvQuantidade);

        configurarLista();
        configurarBotoes();
        // Os dados são carregados no onResume, que sempre roda logo depois do onCreate.
    }

    /**
     * Ciclo de vida: o onResume roda toda vez que a tela volta ao primeiro plano
     * (ao abrir e ao retornar do Formulário ou dos Detalhes). Recarregar aqui garante
     * que a lista mostre na hora os registros criados, editados ou excluídos.
     */
    @Override
    protected void onResume() {
        super.onResume();
        carregarRegistros();
    }

    /** Liga o adapter ao ListView, define o aviso de lista vazia e o clique nos itens. */
    private void configurarLista() {
        adapter = new RegistroAdapter(this, null);
        listRegistros.setAdapter(adapter);
        listRegistros.setEmptyView(findViewById(R.id.layoutVazio));

        listRegistros.setOnItemClickListener((parent, view, posicao, id) ->
                abrirDetalhes(adapter.getItem(posicao)));
    }

    /** Configura os botões "Novo registro" e "Voltar". */
    private void configurarBotoes() {
        findViewById(R.id.btnAdicionar).setOnClickListener(v -> abrirFormularioNovo());
        findViewById(R.id.btnVoltarLista).setOnClickListener(v -> finish());
    }

    /** Busca os registros no repositório e atualiza a lista e o contador do cabeçalho. */
    private void carregarRegistros() {
        List<Registro> registros = RegistroRepositorio.listar();
        adapter.atualizar(registros);
        tvQuantidade.setText(String.valueOf(registros.size()));
    }

    /** Intent explícita para os Detalhes, levando o id do registro tocado. */
    private void abrirDetalhes(Registro registro) {
        Intent intent = new Intent(this, DetalhesActivity.class);
        intent.putExtra(RegistroRepositorio.EXTRA_ID, registro.getId());
        startActivity(intent);
    }

    /** Intent explícita para o Formulário sem id, ou seja, em modo de cadastro. */
    private void abrirFormularioNovo() {
        startActivity(new Intent(this, FormularioActivity.class));
    }
}

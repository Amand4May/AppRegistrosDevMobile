package com.exemplo.registros;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * Tela de cadastro e edição de registros (a mesma tela serve para os dois).
 * Sem EXTRA_ID na Intent -> cadastro. Com EXTRA_ID -> edição.
 */
public class FormularioActivity extends AppCompatActivity {

    private static final int ID_INEXISTENTE = -1;

    private TextView tvTituloFormulario;
    private EditText etTitulo, etSetor, etResponsavel, etDescricao;
    private Spinner spCategoria;
    private RadioGroup rgPrioridade;
    private ImageButton btnVoltarForm;
    private MaterialButton btnCancelar, btnSalvar;

    // Id do registro em edição; ID_INEXISTENTE quando estamos cadastrando
    private int idEmEdicao = ID_INEXISTENTE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        BarrasSistema.ajustar(this);

        vincularViews();
        carregarRegistroSeEdicao();
        configurarBotoes();
    }

    /** Liga as variáveis aos componentes do XML. */
    private void vincularViews() {
        tvTituloFormulario = findViewById(R.id.tvTituloFormulario);
        etTitulo = findViewById(R.id.etTitulo);
        spCategoria = findViewById(R.id.spCategoria);
        etSetor = findViewById(R.id.etSetor);
        etResponsavel = findViewById(R.id.etResponsavel);
        rgPrioridade = findViewById(R.id.rgPrioridade);
        etDescricao = findViewById(R.id.etDescricao);
        btnVoltarForm = findViewById(R.id.btnVoltarForm);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnSalvar = findViewById(R.id.btnSalvar);
    }

    /** Se veio um id na Intent, carrega o registro e preenche os campos (modo edição). */
    private void carregarRegistroSeEdicao() {
        idEmEdicao = getIntent().getIntExtra(RegistroRepositorio.EXTRA_ID, ID_INEXISTENTE);

        if (idEmEdicao == ID_INEXISTENTE) {
            tvTituloFormulario.setText(R.string.form_titulo_novo);
            return;
        }

        Registro registro = RegistroRepositorio.buscarPorId(idEmEdicao);
        if (registro == null) {
            Toast.makeText(this, R.string.form_msg_nao_encontrado, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvTituloFormulario.setText(R.string.form_titulo_editar);
        etTitulo.setText(registro.getTitulo());
        etSetor.setText(registro.getSetor());
        etResponsavel.setText(registro.getResponsavel());
        etDescricao.setText(registro.getDescricao());
        selecionarCategoria(registro.getCategoria());
        selecionarPrioridade(registro.getPrioridade());
    }

    /** Seleciona no Spinner o item igual à categoria do registro. */
    private void selecionarCategoria(String categoria) {
        String[] categorias = getResources().getStringArray(R.array.categorias);
        for (int i = 0; i < categorias.length; i++) {
            if (categorias[i].equals(categoria)) {
                spCategoria.setSelection(i);
                return;
            }
        }
    }

    /** Marca o RadioButton correspondente à prioridade do registro. */
    private void selecionarPrioridade(String prioridade) {
        if (Registro.PRIORIDADE_BAIXA.equals(prioridade)) {
            rgPrioridade.check(R.id.rbBaixa);
        } else if (Registro.PRIORIDADE_ALTA.equals(prioridade)) {
            rgPrioridade.check(R.id.rbAlta);
        } else {
            rgPrioridade.check(R.id.rbMedia);
        }
    }

    private void configurarBotoes() {
        btnVoltarForm.setOnClickListener(v -> finish());
        btnCancelar.setOnClickListener(v -> finish());
        btnSalvar.setOnClickListener(v -> salvar());
    }

    /** Valida o formulário e cria ou atualiza o registro. */
    private void salvar() {
        String titulo = textoDe(etTitulo);
        String setor = textoDe(etSetor);
        String responsavel = textoDe(etResponsavel);
        String descricao = textoDe(etDescricao);
        String categoria = spCategoria.getSelectedItem() == null
                ? "" : spCategoria.getSelectedItem().toString();
        String prioridade = lerPrioridade();

        if (titulo.isEmpty()) {
            etTitulo.setError(getString(R.string.form_erro_titulo));
            etTitulo.requestFocus();
            Snackbar.make(btnSalvar, R.string.form_erro_titulo, Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (idEmEdicao == ID_INEXISTENTE) {
            // CREATE
            RegistroRepositorio.adicionar(
                    new Registro(titulo, categoria, setor, responsavel, prioridade, descricao));
            Toast.makeText(this, R.string.form_msg_criado, Toast.LENGTH_SHORT).show();
        } else {
            // UPDATE
            boolean atualizou = RegistroRepositorio.atualizar(
                    idEmEdicao, titulo, categoria, setor, responsavel, prioridade, descricao);
            if (!atualizou) {
                Snackbar.make(btnSalvar, R.string.form_msg_nao_encontrado, Snackbar.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, R.string.form_msg_atualizado, Toast.LENGTH_SHORT).show();
        }

        finish(); // volta para a tela anterior
    }

    /** Converte o RadioButton marcado na prioridade em texto. */
    private String lerPrioridade() {
        int marcado = rgPrioridade.getCheckedRadioButtonId();
        if (marcado == R.id.rbBaixa) return Registro.PRIORIDADE_BAIXA;
        if (marcado == R.id.rbAlta) return Registro.PRIORIDADE_ALTA;
        return Registro.PRIORIDADE_MEDIA;
    }

    /** Texto do campo sem espaços nas pontas e nunca nulo. */
    private String textoDe(EditText campo) {
        return campo.getText() == null ? "" : campo.getText().toString().trim();
    }
}
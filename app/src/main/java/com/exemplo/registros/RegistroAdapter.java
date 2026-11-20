package com.exemplo.registros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter da lista de registros.
 *
 * Faz a ponte entre os objetos {@link Registro} (dados em memória) e o layout
 * item_registro.xml, que é o "cartão" exibido para cada registro no ListView
 * da {@link ListaActivity}.
 *
 * Para cada item mostra:
 *  - faixa colorida conforme a prioridade (verde = Baixa, amarelo = Média, vermelho = Alta);
 *  - título e categoria (badge);
 *  - descrição resumida (até 2 linhas; some quando estiver vazia);
 *  - setor/linha e data de criação no rodapé.
 *
 * Usa o padrão ViewHolder para reaproveitar as views ao rolar a lista,
 * evitando chamar findViewById várias vezes para o mesmo item.
 */
public class RegistroAdapter extends BaseAdapter {

    /** Separador entre o setor e a data no rodapé do item. Ex.: "Montagem · 16/11/2026" */
    static final String SEPARADOR_RODAPE = " · ";

    private final Context contexto;
    private final LayoutInflater inflater;

    /** Cópia local dos registros exibidos. É substituída por {@link #atualizar(List)}. */
    private final List<Registro> registros = new ArrayList<>();

    /**
     * @param contexto  Activity onde a lista é exibida.
     * @param registros registros iniciais (pode ser vazio ou null).
     */
    public RegistroAdapter(Context contexto, List<Registro> registros) {
        this.contexto = contexto;
        this.inflater = LayoutInflater.from(contexto);
        if (registros != null) {
            this.registros.addAll(registros);
        }
    }

    /**
     * Troca os registros exibidos e redesenha a lista.
     * Chamado pela ListaActivity sempre que a tela volta ao primeiro plano (onResume),
     * para refletir cadastros, edições e exclusões feitos nas outras telas.
     */
    public void atualizar(List<Registro> novosRegistros) {
        registros.clear();
        if (novosRegistros != null) {
            registros.addAll(novosRegistros);
        }
        notifyDataSetChanged();
    }

    // ---------- Métodos obrigatórios do BaseAdapter ----------

    @Override
    public int getCount() {
        return registros.size();
    }

    @Override
    public Registro getItem(int posicao) {
        return registros.get(posicao);
    }

    /** O id da linha é o próprio id do registro (único e estável). */
    @Override
    public long getItemId(int posicao) {
        return registros.get(posicao).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Só infla um layout novo quando não há view para reaproveitar
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_registro, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        preencher(holder, getItem(posicao));
        return convertView;
    }

    /** Copia os dados do registro para as views do item. */
    private void preencher(ViewHolder holder, Registro registro) {
        holder.viewPrioridade.setBackgroundColor(
                contexto.getColor(corDaPrioridade(registro.getPrioridade())));

        holder.tvTitulo.setText(registro.getTitulo());

        // Campos opcionais: escondidos quando vazios, para o cartão não ficar com buracos.
        // O VISIBLE no else é necessário porque a view pode ter sido reaproveitada
        // de um item que estava com o campo escondido.
        mostrarOuEsconder(holder.tvCategoria, registro.getCategoria());
        mostrarOuEsconder(holder.tvDescricao, registro.getDescricao());

        holder.tvSetor.setText(montarRodape(registro.getSetor(), registro.getDataCurta()));
    }

    /** Exibe o texto no TextView ou esconde o TextView se o texto estiver vazio. */
    private static void mostrarOuEsconder(TextView campo, String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            campo.setVisibility(View.GONE);
        } else {
            campo.setText(texto.trim());
            campo.setVisibility(View.VISIBLE);
        }
    }

    // ---------- Regras de exibição (estáticas para poderem ser reutilizadas e testadas) ----------

    /**
     * Devolve o recurso de cor correspondente à prioridade.
     * Prioridade desconhecida ou nula cai em Média (cor padrão do layout).
     */
    public static int corDaPrioridade(String prioridade) {
        if (Registro.PRIORIDADE_ALTA.equals(prioridade)) {
            return R.color.prioridade_alta;
        }
        if (Registro.PRIORIDADE_BAIXA.equals(prioridade)) {
            return R.color.prioridade_baixa;
        }
        return R.color.prioridade_media;
    }

    /**
     * Monta o rodapé do item: "setor · data".
     * Se o setor não foi informado, mostra apenas a data.
     */
    static String montarRodape(String setor, String data) {
        if (setor == null || setor.trim().isEmpty()) {
            return data;
        }
        return setor.trim() + SEPARADOR_RODAPE + data;
    }

    /** Guarda as referências das views de um item para não buscá-las de novo a cada rolagem. */
    private static class ViewHolder {
        final View viewPrioridade;
        final TextView tvTitulo;
        final TextView tvCategoria;
        final TextView tvDescricao;
        final TextView tvSetor;

        ViewHolder(View item) {
            viewPrioridade = item.findViewById(R.id.viewPrioridade);
            tvTitulo = item.findViewById(R.id.tvItemTitulo);
            tvCategoria = item.findViewById(R.id.tvItemCategoria);
            tvDescricao = item.findViewById(R.id.tvItemDescricao);
            tvSetor = item.findViewById(R.id.tvItemSetor);
        }
    }
}

package com.exemplo.registros;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Modelo de um registro da produção (registro, lista de controle ou anotação).
 */
public class Registro {

    public static final String PRIORIDADE_BAIXA = "Baixa";
    public static final String PRIORIDADE_MEDIA = "Média";
    public static final String PRIORIDADE_ALTA = "Alta";

    private int id;
    private String titulo;
    private String categoria;
    private String setor;
    private String responsavel;
    private String prioridade;
    private String descricao;
    private final Date dataCriacao;

    public Registro(String titulo, String categoria, String setor,
                    String responsavel, String prioridade, String descricao) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.setor = setor;
        this.responsavel = responsavel;
        this.prioridade = prioridade;
        this.descricao = descricao;
        this.dataCriacao = new Date();
    }

    /** Data já formatada para exibir na tela. Ex.: "16/11/2026 às 14:32" */
    public String getDataFormatada() {
        return new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", new Locale("pt", "BR"))
                .format(dataCriacao);
    }

    /** Data curta para o item da lista. Ex.: "16/11/2026" */
    public String getDataCurta() {
        return new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR")).format(dataCriacao);
    }

    // ---------- Getters e setters ----------
    public int getId() { return id; }
    void setId(int id) { this.id = id; } // só o repositório define o id

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Date getDataCriacao() { return dataCriacao; }
}

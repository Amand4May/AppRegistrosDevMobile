package com.exemplo.registros;

import java.util.ArrayList;
import java.util.List;

/**
 * Armazena os registros em memória (ArrayList), sem banco de dados nem internet.
 * Por ser estático, a mesma lista é compartilhada por todas as telas
 * enquanto o app estiver aberto.
 */
public class RegistroRepositorio {

    /** Chave usada em todas as Intents para passar o id do registro. */
    public static final String EXTRA_ID = "id_registro";

    private static final List<Registro> registros = new ArrayList<>();
    private static int proximoId = 1;

    private RegistroRepositorio() { }

    /** Cadastra um novo registro e devolve o id gerado. */
    public static int adicionar(Registro registro) {
        registro.setId(proximoId++);
        registros.add(registro);
        return registro.getId();
    }

    /** Devolve todos os registros (do mais recente para o mais antigo). */
    public static List<Registro> listar() {
        List<Registro> copia = new ArrayList<>(registros);
        java.util.Collections.reverse(copia);
        return copia;
    }

    /** Busca pelo id. Devolve null se não existir. */
    public static Registro buscarPorId(int id) {
        for (Registro r : registros) {
            if (r.getId() == id) return r;
        }
        return null;
    }

    /** Atualiza os dados de um registro existente. Devolve true se encontrou. */
    public static boolean atualizar(int id, String titulo, String categoria, String setor,
                                    String responsavel, String prioridade, String descricao) {
        Registro r = buscarPorId(id);
        if (r == null) return false;
        r.setTitulo(titulo);
        r.setCategoria(categoria);
        r.setSetor(setor);
        r.setResponsavel(responsavel);
        r.setPrioridade(prioridade);
        r.setDescricao(descricao);
        return true;
    }

    /** Remove pelo id. Devolve true se removeu. */
    public static boolean remover(int id) {
        Registro r = buscarPorId(id);
        return r != null && registros.remove(r);
    }

    /** Quantidade de registros cadastrados. */
    public static int total() {
        return registros.size();
    }
}

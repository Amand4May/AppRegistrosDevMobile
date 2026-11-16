package com.exemplo.registros;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/** Evita que o conteúdo fique embaixo do relógio/barra de navegação (Android 15+). */
public final class BarrasSistema {
    private BarrasSistema() { }

    public static void ajustar(Activity activity) {
        ViewGroup conteudo = activity.findViewById(android.R.id.content);
        ViewGroup raiz = (ViewGroup) conteudo.getChildAt(0);
        View cabecalho = raiz.getChildAt(0); // cabeçalho azul = 1º item da tela
        int topoOriginal = cabecalho.getPaddingTop();
        int baseOriginal = raiz.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(raiz, (v, insets) -> {
            Insets barras = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            cabecalho.setPadding(cabecalho.getPaddingLeft(), topoOriginal + barras.top,
                    cabecalho.getPaddingRight(), cabecalho.getPaddingBottom());
            v.setPadding(v.getPaddingLeft(), v.getPaddingTop(),
                    v.getPaddingRight(), baseOriginal + barras.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }
}
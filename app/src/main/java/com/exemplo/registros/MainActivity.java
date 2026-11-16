package com.exemplo.registros;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

// PROVISÓRIO: só para testar a navegação entre as telas. O grupo substitui pela versão final.
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BarrasSistema.ajustar(this);
        findViewById(R.id.btnNovoRegistro).setOnClickListener(v ->
                startActivity(new Intent(this, FormularioActivity.class)));
        findViewById(R.id.btnVerRegistros).setOnClickListener(v ->
                startActivity(new Intent(this, ListaActivity.class)));
    }
}

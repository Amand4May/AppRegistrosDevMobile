package com.exemplo.registros;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onResume() {
        super.onResume();
        TextView total = findViewById(R.id.tvTotalRegistros);
        total.setText(String.valueOf(RegistroRepositorio.total()));
    }
}
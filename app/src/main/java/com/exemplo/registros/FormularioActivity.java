package com.exemplo.registros;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// PROVISÓRIO: Salvar só mostra o Toast de feedback.
public class FormularioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        BarrasSistema.ajustar(this);
        findViewById(R.id.btnSalvar).setOnClickListener(v -> {
            Toast.makeText(this, R.string.msg_salvo, Toast.LENGTH_SHORT).show();
            finish();
        });
        findViewById(R.id.btnCancelar).setOnClickListener(v -> finish());
        findViewById(R.id.btnVoltarForm).setOnClickListener(v -> finish());
    }
}

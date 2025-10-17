package com.devst.app;

import android.content.Intent; // ✅ Importación necesaria
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PerfilActivity extends AppCompatActivity {

    private EditText edtNombre;
    private String emailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Configurar Toolbar con botón de "Atrás"
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Inicializar vistas
        edtNombre = findViewById(R.id.edtNombre);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        // Recibir email del HomeActivity
        emailUsuario = getIntent().getStringExtra("email_usuario");
        if (emailUsuario == null) emailUsuario = "";

        // Mostrar email en el título (opcional)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Perfil de " + emailUsuario);
        }

        // Guardar cambios y devolver resultado
        btnGuardar.setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Ingresa un nombre", Toast.LENGTH_SHORT).show();
                return;
            }

            // Devolver resultado a HomeActivity
            Intent result = new Intent();
            result.putExtra("nombre_editado", nombre);
            setResult(RESULT_OK, result);
            finish(); // Vuelve a HomeActivity
        });
    }

    // Botón de "Atrás" en la Toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
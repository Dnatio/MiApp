package com.devst.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    private String emailUsuario = "";
    private TextView tvBienvenida;

    // Variables para la linterna y cámara
    private Button btnLinterna;
    private CameraManager camara;
    private String CamaraID = null;
    private boolean luz = false;

    // Activity Result (para recibir datos de PerfilActivity)
    private final ActivityResultLauncher<Intent> editarPerfilLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String nombre = result.getData().getStringExtra("nombre_editado");
                    if (nombre != null) {
                        tvBienvenida.setText("Hola, " + nombre);
                    }
                }
            });

    private final ActivityResultLauncher<String> permisoCamaraLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    alternarLuz();
                } else {
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializar vistas
        tvBienvenida = findViewById(R.id.tvBienvenida);
        Button btnIrPerfil = findViewById(R.id.btnIrPerfil);
        Button btnAbrirWeb = findViewById(R.id.btnAbrirWeb);
        Button btnEnviarCorreo = findViewById(R.id.btnEnviarCorreo);
        Button btnCompartir = findViewById(R.id.btnCompartir);
        btnLinterna = findViewById(R.id.btnLinterna);
        Button btnCamara = findViewById(R.id.btnCamara);
        Button btnConfig = findViewById(R.id.btnConfig);

        // Recibir dato del Login
        emailUsuario = getIntent().getStringExtra("email_usuario");
        if (emailUsuario == null) emailUsuario = "";
        tvBienvenida.setText("Bienvenido: " + emailUsuario);

        // Evento: Ir a Perfil (con resultado)
        btnIrPerfil.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, PerfilActivity.class);
            i.putExtra("email_usuario", emailUsuario);
            editarPerfilLauncher.launch(i);
        });

        // Evento: Abrir sitio web
        btnAbrirWeb.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://www.santotomas.cl");
            Intent viewWeb = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(viewWeb);
        });

        // Evento: Enviar correo
        btnEnviarCorreo.setOnClickListener(v -> {
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setData(Uri.parse("mailto:"));
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailUsuario});
            email.putExtra(Intent.EXTRA_SUBJECT, "Prueba desde la app");
            email.putExtra(Intent.EXTRA_TEXT, "Hola, esto es un intento de correo.");
            startActivity(Intent.createChooser(email, "Enviar correo con:"));
        });

        // Evento: Compartir texto
        btnCompartir.setOnClickListener(v -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "Hola desde mi app Android 😎");
            startActivity(Intent.createChooser(share, "Compartir usando:"));
        });

        // Evento: Linterna
        btnLinterna.setOnClickListener(v -> {
            if (CamaraID == null) {
                Toast.makeText(this, "Este dispositivo no tiene flash", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean camGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED;
            if (camGranted) {
                alternarLuz();
            } else {
                permisoCamaraLauncher.launch(Manifest.permission.CAMERA);
            }
        });

        // Evento: Abrir cámara
        btnCamara.setOnClickListener(v ->
                startActivity(new Intent(this, CamaraActivity.class))
        );

        // Evento: Abrir Configuración
        btnConfig.setOnClickListener(v ->
                startActivity(new Intent(this, ConfigActivity.class))
        );

        // Configurar cámara para linterna
        camara = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            for (String id : camara.getCameraIdList()) {
                CameraCharacteristics cc = camara.getCameraCharacteristics(id);
                Boolean disponibleFlash = cc.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                Integer lensFacing = cc.get(CameraCharacteristics.LENS_FACING);
                if (Boolean.TRUE.equals(disponibleFlash)
                        && lensFacing != null
                        && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                    CamaraID = id;
                    break;
                }
            }
        } catch (CameraAccessException e) {
            Toast.makeText(this, "No se puede acceder a la cámara", Toast.LENGTH_SHORT).show();
        }
    }

    private void alternarLuz() {
        try {
            luz = !luz;
            camara.setTorchMode(CamaraID, luz);
            btnLinterna.setText(luz ? "Apagar linterna" : "Encender linterna");
        } catch (CameraAccessException e) {
            Toast.makeText(this, "Error al controlar la linterna", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (CamaraID != null && luz) {
            try {
                camara.setTorchMode(CamaraID, false);
                luz = false;
                if (btnLinterna != null) btnLinterna.setText("Encender linterna");
            } catch (CameraAccessException ignored) {
            }
        }
    }
}
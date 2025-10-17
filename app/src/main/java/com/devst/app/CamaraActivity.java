package com.devst.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CamaraActivity extends AppCompatActivity {

    private static final String TAG = "CamaraActivity";
    private static final String AUTHORITY = "com.devst.app.fileprovider";
    private ImageView imagenPrevia;
    private Uri urlImagen;

    private final ActivityResultLauncher<String> permisoCamaraLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    Log.d(TAG, "Permiso de cámara concedido");
                    tomarFoto();
                } else {
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<Uri> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
                if (result && urlImagen != null) {
                    imagenPrevia.setImageURI(urlImagen);
                    Toast.makeText(this, "Foto guardada", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Foto guardada en: " + urlImagen);
                } else {
                    Toast.makeText(this, "Captura cancelada", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Captura cancelada o URI nula");
                }
                Intent intent = new Intent(this, PhotoActivity.class);
                intent.putExtra("uri", urlImagen.toString());
                startActivity(intent);
                finish(); // Opcional: cierra CamaraActivity
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        // Configurar Toolbar con flecha de "Atrás"
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Button btnTomarFoto = findViewById(R.id.btnTomarFoto);
        imagenPrevia = findViewById(R.id.imgPreview);

        btnTomarFoto.setOnClickListener(v -> checkPermisoYTomar());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Vuelve a la actividad anterior (HomeActivity)
        return true;
    }

    private void checkPermisoYTomar() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "No hay app de cámara disponible", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "No se encontró ninguna app de cámara");
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permiso de cámara ya concedido");
            tomarFoto();
        } else {
            Log.d(TAG, "Solicitando permiso de cámara...");
            permisoCamaraLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void tomarFoto() {
        try {
            File archivoFoto = crearArchivoImagen();
            Log.d(TAG, "Archivo creado: " + archivoFoto.getAbsolutePath());

            urlImagen = FileProvider.getUriForFile(this, AUTHORITY, archivoFoto);
            Log.d(TAG, "URI generada: " + urlImagen);

            takePictureLauncher.launch(urlImagen);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al crear archivo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error al crear archivo", e);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error de configuración del FileProvider. Revisa file_paths.xml y AndroidManifest.xml", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Error de FileProvider", e);
        }
    }

    private File crearArchivoImagen() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombre = "IMG_" + timeStamp + "_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (directorio == null) {
            throw new IOException("No se pudo acceder al directorio de imágenes");
        }

        File archivo = File.createTempFile(nombre, ".jpg", directorio);
        Log.d(TAG, "Archivo temporal creado: " + archivo.getAbsolutePath());
        return archivo;
    }
}
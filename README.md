¡Hola! Este repositorio contiene la implementación del segundo prototipo de la aplicación Android, enfocada en la integración de eventos explícitos e implícitos (Intents) para mejorar la navegación y la interacción con otras aplicaciones del sistema.

<table style="width:100%; border-collapse: collapse; background-color:#1e1e1e; color:#ffffff; border-radius:10px;">
  <thead>
    <tr style="background-color:#2b2b2b;">
      <th style="padding:10px; text-align:left;">Característica</th>
      <th style="padding:10px; text-align:left;">Estado</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="padding:10px;">Lenguaje</td>
      <td style="padding:10px;">Java</td>
    </tr>
    <tr>
      <td style="padding:10px;">Intents Implícitos (5)</td>
      <td style="padding:10px;">✅ Implementados</td>
    </tr>
    <tr>
      <td style="padding:10px;">Intents Explícitos (3)</td>
      <td style="padding:10px;">✅ Implementados</td>
    </tr>
    <tr>
      <td style="padding:10px;">Validaciones</td>
      <td style="padding:10px;">✅ Implementadas (en <b>LoginActivity</b>)</td>
    </tr>
    <tr>
      <td style="padding:10px;">APK Debug</td>
      <td style="padding:10px;"><a href="https://github.com/Dnatio/MiApp/blob/main/app-debug.apk" style="color:#4aa3ff;">Enlace al APK</a></td>
    </tr>
  </tbody>
</table>


- Resumen del Proyecto y Versiones

La aplicación simula un sistema de gestión con login y varias funcionalidades, incluyendo gestión de perfil y opciones de cámara.

Objetivo Principal: Implementar los 8 Intents requeridos y funcionalidad de validaciones.

Lenguaje: Java

Android Studio Version: Android Studio Hedgehog | 2023.1.1

Android Gradle Plugin (AGP) Version: 8.2.0

Target SDK: 34

- Intents Implementados

<h2>A. Intents Implícitos (5/5)</h2>
<p>Los <b>Intents implícitos</b> abren componentes en otras aplicaciones del sistema (por ejemplo: navegador, cámara, compartir, etc.).</p>

<table style="width:100%; border-collapse: collapse; background-color:#1e1e1e; color:#ffffff;">
  <thead>
    <tr style="background-color:#2b2b2b;">
      <th style="padding:10px;">Nº</th>
      <th style="padding:10px;">Descripción / Acción</th>
      <th style="padding:10px;">Pantalla de Origen</th>
      <th style="padding:10px;">Pasos de Prueba</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="padding:10px;">1</td>
      <td style="padding:10px;">Ver una Página Web Específica</td>
      <td style="padding:10px;">MainActivity</td>
      <td style="padding:10px;">
        1. Ir a MainActivity.<br>
        2. Tocar el botón que dispara la acción de Web.<br>
        3. Verificar que se abre el navegador predeterminado mostrando un sitio web (ej: 
        <a href="https://www.santotomas.cl" style="color:#4aa3ff;">https://www.santotomas.cl</a>).
      </td>
    </tr>
    <tr>
      <td style="padding:10px;">2</td>
      <td style="padding:10px;">Enviar Correo Electrónico</td>
      <td style="padding:10px;">MainActivity</td>
      <td style="padding:10px;">
        1. Ir a MainActivity.<br>
        2. Tocar el botón que dispara la acción de Correo.<br>
        3. Verificar que se abre la app de correo predeterminada con el campo 
        <b>"Para:"</b> (<code>ejemplo@mail.com</code>) y el asunto precargados.
      </td>
    </tr>
    <tr>
      <td style="padding:10px;">3</td>
      <td style="padding:10px;">Tomar Fotografía con Cámara</td>
      <td style="padding:10px;">CamaraActivity</td>
      <td style="padding:10px;">
        1. Ir a MainActivity y tocar el botón "Cámara" (navegación explícita a CamaraActivity).<br>
        2. En CamaraActivity, tocar "Tomar Foto".<br>
        3. Aceptar permisos.<br>
        4. Verificar que se abre la cámara del sistema (<code>MediaStore.ACTION_IMAGE_CAPTURE</code>).
      </td>
    </tr>
    <tr>
      <td style="padding:10px;">4</td>
      <td style="padding:10px;">Compartir Texto con otras Apps</td>
      <td style="padding:10px;">HomeActivity</td>
      <td style="padding:10px;">
        1. Ir a HomeActivity (después de Login).<br>
        2. Tocar el botón "Compartir".<br>
        3. Verificar que se abre el menú de compartir de Android, permitiendo seleccionar una app (ej: WhatsApp, Gmail) con un texto precargado.
      </td>
    </tr>
    <tr>
      <td style="padding:10px;">5</td>
      <td style="padding:10px;">Mostrar Notificación de Linterna</td>
      <td style="padding:10px;">HomeActivity</td>
      <td style="padding:10px;">
        1. Ir a HomeActivity.<br>
        2. Tocar el botón "Encender Linterna".<br>
        3. Verificar que la linterna se enciende y el botón cambia a "Apagar Linterna". Esto demuestra la interacción con el hardware del sistema (flash/cámara).
      </td>
    </tr>
  </tbody>
</table>

<br>

<h2>B. Intents Explícitos (3/3)</h2>
<p>Los <b>Intents explícitos</b> navegan entre los componentes internos de nuestra propia aplicación.</p>

<table style="width:100%; border-collapse: collapse; background-color:#1e1e1e; color:#ffffff;">
  <thead>
    <tr style="background-color:#2b2b2b;">
      <th style="padding:10px;">Nº</th>
      <th style="padding:10px;">Origen → Destino</th>
      <th style="padding:10px;">Tipo de Uso</th>
      <th style="padding:10px;">Pasos de Prueba</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="padding:10px;">1</td>
      <td style="padding:10px;">LoginActivity → HomeActivity</td>
      <td style="padding:10px;">Navegación y paso de datos</td>
      <td style="padding:10px;">
        1. Ingresar credenciales correctas (ej: <code>ejemplo@gmail.cl / 123456</code>).<br>
        2. Tocar "Ingresar".<br>
        3. Verificar que se abre <b>HomeActivity</b> y se muestra el email en el mensaje de bienvenida.
      </td>
    </tr>
    <tr>
      <td style="padding:10px;">2</td>
      <td style="padding:10px;">HomeActivity → PerfilActivity</td>
      <td style="padding:10px;">Con Resultado (<code>ActivityResult</code>)</td>
      <td style="padding:10px;">
        1. En HomeActivity, tocar el botón "Editar Perfil".<br>
        2. En PerfilActivity, ingresar un nuevo nombre (ej: "Juan Pérez") y tocar "Guardar".<br>
        3. Verificar que HomeActivity se actualiza y muestra el nuevo nombre usando el resultado devuelto.
      </td>
    </tr>
    <tr>
      <td style="padding:10px;">3</td>
      <td style="padding:10px;">MainActivity → ConfigActivity</td>
      <td style="padding:10px;">Navegación Simple</td>
      <td style="padding:10px;">—</td>
    </tr>
  </tbody>
</table>

- Capturas de Pantalla 

Aquí se muestran las interfaces principales y el uso de los Intents implementados.

1- Login con Validaciones.

2- Home con Intents Implícitos.

3- Edición de Perfil (Retorna Resultado).


<img width="354" height="741" alt="image" src="https://github.com/user-attachments/assets/87224b3d-3ccf-4c2b-a7f8-64c3640aba11" />


<img width="351" height="742" alt="image" src="https://github.com/user-attachments/assets/4d63bb7c-aea3-4eb1-ad9a-96282e122304" />


<img width="355" height="750" alt="image" src="https://github.com/user-attachments/assets/1ed5d7e1-612c-4935-a088-c227a62f9198" />


<img width="349" height="739" alt="image" src="https://github.com/user-attachments/assets/f2892f11-9ccc-4521-8694-a7b888f39add" />


APK Debug

El archivo ejecutable para pruebas (debug) se puede descargar directamente desde este enlace.

 <tr>
      <td style="padding:10px;">APK Debug</td>
      <td style="padding:10px;"><a href="https://github.com/Dnatio/MiApp/blob/main/app-debug.apk" style="color:#4aa3ff;">Enlace al APK</a></td>
    </tr>




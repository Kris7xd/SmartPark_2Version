# SmartPark_2Version - Proyecto Android Studio (Jetpack Compose)

Aplicación móvil desarrollada en Kotlin utilizando Jetpack Compose para la gestión de parqueaderos. 
Este README describe la estructura del proyecto, los archivos importantes y algunas notas sobre su funcionamiento.


# SmartPark_2Version/
La aplicación SmartPark se organiza de la siguiente manera:

- Carpeta app/: Contiene todos los elementos principales de la aplicación.
   - manifests/: Guarda el archivo AndroidManifest.xml, donde se definen los componentes y permisos de la app.
   - kotlin+java/: Contiene el código fuente de la aplicación.
      - com.urbantechsolutions.smartpark_2version/: Paquete principal de la app.
         - ui/: Contiene todo lo relacionado con la interfaz de usuario.
            - screens/: Aquí se encuentran las diferentes pantallas de la aplicación:
               - LoginScreen.kt → Pantalla de inicio de sesión
               - RegisterScreen.kt → Pantalla de registro de usuarios
               - ProfileScreen.kt → Pantalla de perfil del usuario
               - MyReservationsScreen.kt → Pantalla de reservas del usuario
               - ParkingMapScreen.kt → Pantalla con el mapa de parqueaderos
               - ReservationScreen.kt → Pantalla para realizar reservas
            - theme/: Contiene la configuración visual y estilos de la app, incluyendo:
               - Color.kt → Definición de colores
               - Theme.kt → Tema general de la app
               - Type.kt → Tipografía y estilos de texto
         - MainActivity.kt → Actividad principal que se ejecuta al iniciar la app
   - res/: Carpeta de recursos, como imágenes, layouts, strings, íconos, etc.
- Gradle Scripts/: Contiene los scripts necesarios para la compilación, dependencias y configuración del proyecto.


# Archivos Importantes

# MainActivity.kt
Actividad principal que se encarga de cargar las pantallas de Jetpack Compose.
Actúa como punto de entrada de la aplicación.


# screens/
Contiene las pantallas de la aplicación:
LoginScreen.kt: Pantalla de inicio de sesión con campos para correo y contraseña.
RegisterScreen.kt: Pantalla de registro de nuevos usuarios (correo, nombre, apellido, tipo de vehículo, teléfono).
ProfileScreen.kt: Pantalla que muestra los datos del usuario registrado.
MyReservationsScreen.kt: Pantalla que lista las reservas realizadas por el usuario.
ParkingMapScreen.kt: Pantalla que muestra el mapa del parqueadero.
ReservationScreen.kt: Pantalla para realizar una reserva de parqueadero.


# theme/
Color.kt: Colores utilizados en la app.
Theme.kt: Temas de Jetpack Compose.
Type.kt: Tipografía y estilos de texto.


# res/
Contiene recursos gráficos, íconos, layouts y strings de la app.


# Dependencias y Herramientas
Lenguaje: Kotlin
UI: Jetpack Compose
IDE: Android Studio
API mínima: 36.1 (según el emulador que se ve en la captura)


# Funcionalidades principales
Inicio de sesión y registro de usuario.
Visualización y edición de perfil.
Consulta de reservas y mapa del parqueadero.
Registro de vehículos por categoría.
Gestión de reservas desde la aplicación.


# Notas
Las variables email, password e isLoading están gestionadas con mutableStateOf para Jetpack Compose.
La navegación entre pantallas se maneja mediante callbacks (onNavigateToRegister, onLoginSuccess, onBack).

## Cómo probar
1. Descargar el archivo `SmartPark.apk` desde:
   [GitHub](https://github.com/Kris7xd/SmartPark_2VersionAPK.git)
2. Instalarlo en un dispositivo Android o emulador.
3. Abrir la app y navegar entre las pantallas.

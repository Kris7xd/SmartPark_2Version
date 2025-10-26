# SmartPark_2Version - Proyecto Android Studio (Jetpack Compose)

Aplicación móvil desarrollada en Kotlin utilizando Jetpack Compose para la gestión de parqueaderos. 
Este README describe la estructura del proyecto, los archivos importantes y algunas notas sobre su funcionamiento.

# SmartPark_2Version/
│
├─ app/
│  ├─ manifests/                 # Contiene AndroidManifest.xml
│  ├─ kotlin+java/
│  │   └─ com.urbantechsolutions.smartpark_2version/
│  │       ├─ ui/
│  │       │   ├─ screens/       # Pantallas de la aplicación
│  │       │   │   ├─ LoginScreen.kt
│  │       │   │   ├─ RegisterScreen.kt
│  │       │   │   ├─ ProfileScreen.kt
│  │       │   │   ├─ MyReservationsScreen.kt
│  │       │   │   ├─ ParkingMapScreen.kt
│  │       │   │   └─ ReservationScreen.kt
│  │       │   └─ theme/         # Temas y estilos de la app
│  │       │       ├─ Color.kt
│  │       │       ├─ Theme.kt
│  │       │       └─ Type.kt
│  │       └─ MainActivity.kt     # Actividad principal de Android
│  │
│  └─ res/                        # Recursos de la app (imágenes, layouts, strings, etc.)
│
└─ Gradle Scripts                 # Scripts de compilación y dependencias

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

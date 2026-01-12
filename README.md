# 📱 Pokémon Practice App - Jetpack Compose

Una aplicación de Android moderna construida para practicar la integración de **Jetpack Compose**, **Retrofit** y la arquitectura **MVVM**. La app consume la [PokeAPI](https://pokeapi.co/) para mostrar una lista infinita de Pokémon con sus detalles.

## 🚀 Características

- **Scroll Infinito (Paginación Manual):** Carga dinámica de Pokémon a medida que el usuario hace scroll.
- **Arquitectura Limpia:** Separación de responsabilidades mediante Repository y ViewModel.
- **UI Moderna:** Construida íntegramente con Jetpack Compose y Material 3.
- **Consumo de API:** Integración con Retrofit para llamadas de red asíncronas.
- **Carga de Imágenes:** Uso de Coil para procesar imágenes desde la web de forma eficiente.

## 🛠️ Stack Tecnológico

- **Lenguaje:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Networking:** [Retrofit 2](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson)
- **Asincronía:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- **Arquitectura:** MVVM (Model-View-ViewModel)
- **Carga de Imágenes:** [Coil](https://coil-kt.github.io/coil/)

## 🏗️ Estructura del Proyecto

El proyecto sigue una estructura organizada por capas:

- `data`: Contiene los modelos de datos y la definición de la API de Retrofit.
- `repository`: Lógica de acceso a datos (abstracción de la fuente de datos).
- `domain`: Modelos de negocio (en este caso, la entidad `Pokemon`).
- `ui`: Componentes de UI de Compose y ViewModels.

## 📸 Screenshots

| Lista de Pokémon | Scroll Infinito |
| :---: | :---: |
| ![Preview 1]() 
<img width="1344" height="2992" alt="Screenshot_20260112_120857" src="https://github.com/user-attachments/assets/88eec45b-0d20-485f-a3ba-1c64a967d500" />

| ![Preview 2]() |

<img width="1344" height="2992" alt="Screenshot_20260112_120910" src="https://github.com/user-attachments/assets/3bbcdafc-937f-4faf-ae85-db2f5b7e3df7" />

> *(Sugerencia: Reemplaza estas imágenes con capturas reales de tu emulador)*

## ⚙️ Instalación y Configuración

1. Clona el repositorio:
2. Abre el proyecto en **Android Studio (Iguana o superior)**.
3. Asegúrate de tener instalado el JDK 17.
4. Sincroniza el proyecto con Gradle.
5. ¡Ejecuta la app en un emulador o dispositivo físico!

## 💡 Próximos Pasos (To-Do)

- [ ] Implementar búsqueda de Pokémon por nombre.
- [ ] Pantalla de detalles avanzada (Estadísticas, tipos, habilidades).
- [ ] Persistencia de datos local con **Room** (Caché offline).
- [ ] Migración de paginación manual a **Paging 3**.

---
Desarrollado con ❤️ por [Kno85]

   

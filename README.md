# 📱 Pokémon Practice App - Jetpack Compose

Una aplicación de Android moderna construida para practicar la integración de **Jetpack Compose**, **Retrofit** y la arquitectura **MVVM**. La app consume la [PokeAPI](https://pokeapi.co/) para mostrar una lista infinita de Pokémon con sus detalles.

## 🚀 Características

- **Scroll Infinito (Paginación Manual):** Carga dinámica de Pokémon a medida que el usuario hace scroll.
- **Búsqueda en Tiempo Real:** Filtro de Pokémon por nombre desde la lista principal.
- **Favoritos:** Posibilidad de marcar Pokémon como favoritos y consultarlos en una sección dedicada.
- **Persistencia Local:** Almacenamiento de favoritos y caché utilizando **Room Database**.
- **Arquitectura Limpia:** Separación de responsabilidades mediante Repository y ViewModel.
- **UI Moderna:** Construida íntegramente con Jetpack Compose y Material 3.
- **Consumo de API:** Integración con Retrofit para llamadas de red asíncronas.
- **Carga de Imágenes:** Uso de Coil para procesar imágenes de forma eficiente.

## 🛠️ Stack Tecnológico

- **Lenguaje:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Inyección de Dependencias:** [Koin](https://insert-koin.io/)
- **Base de Datos:** [Room](https://developer.android.com/training/data-storage/room)
- **Networking:** [Retrofit 2](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson)
- **Asincronía:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- **Arquitectura:** MVVM (Model-View-ViewModel)
- **Carga de Imágenes:** [Coil](https://coil-kt.github.io/coil/)

## 🏗️ Estructura del Proyecto

El proyecto sigue una estructura organizada por capas:

- `data`: Contiene los modelos de datos (DTOs), la definición de la API de Retrofit y la configuración de **Room** (Entities, DAO, Database).
- `repository`: Lógica de acceso a datos, gestionando la fuente de datos remota y local.
- `domain`: Modelos de negocio puros (entidades `Pokemon` y `PokemonDetail`).
- `ui`: Componentes de UI de Compose, Temas y ViewModels.

## 📸 Screenshots


<img width="300"  alt="Screenshot_20260406_193555" src="https://github.com/user-attachments/assets/0b6c02e6-7cc0-4563-a3c4-5af4f27479e4" />
<img width="300" alt="Screenshot_20260406_193036" src="https://github.com/user-attachments/assets/796b09fd-5dd0-4e8d-85b9-80930f5a778a" />
<img width="300"  alt="Screenshot_20260406_193628" src="https://github.com/user-attachments/assets/b9ce8fa7-ddce-48b0-b910-533add3cdea6" />


## ⚙️ Instalación y Configuración

1. Clona el repositorio.
2. Abre el proyecto en **Android Studio (Ladybug o superior)**.
3. Asegúrate de tener instalado el JDK 17 o superior.
4. Sincroniza el proyecto con Gradle.
5. ¡Ejecuta la app en un emulador o dispositivo físico!

## 💡 Próximos Pasos (To-Do)

- [x] Implementar búsqueda de Pokémon por nombre.
- [x] Pantalla de detalles avanzada (Estadísticas, tipos, habilidades).
- [x] Persistencia de datos local con **Room**.
- [ ] Migración de paginación manual a **Paging 3**.
- [ ] Implementar animaciones de transición entre pantallas.
- [ ] Soporte para modo oscuro (Dark Mode) optimizado.

---
Desarrollado con ❤️ por [Kno85]

   

# 🥊 Street Fighter: Piedra, Papel o Tijera

Aplicación de consola desarrollada en Java en la que el jugador se enfrenta a la CPU en combates
de piedra, papel o tijera con la ambientación de Street Fighter: barras de vida, rondas, K.O. y PERFECT.
Los jugadores, los luchadores y el historial de combates se guardan en ficheros XML, de modo que
los datos se conservan al cerrar el programa.

## 👥 Autores

- **Daniel Ruiz**
- **José Trujillo**
- **Iván Berral**
- **José Antonio Castillero**
- Módulo: **Acceso a datos** — **IES Francisco De Los Ríos**
 
---

## 📌 Funcionalidades principales

- Combates al mejor de 3 rondas contra la CPU, con barras de vida y marcador
- Gestión de **jugadores** (añadir, listar, editar y eliminar)
- Gestión de **luchadores** (añadir, listar, editar y eliminar)
- **Historial de combates** con el detalle de los turnos de cada uno
- **Ranking de jugadores** ordenado por victorias
- Persistencia en XML: todo se guarda automáticamente
- Validaciones de integridad: no se puede eliminar un jugador o un luchador que tenga combates en el historial, y no se admiten nombres repetidos
- Entrada de datos controlada: si el usuario escribe algo no válido, el programa lo vuelve a pedir en lugar de fallar
---

## 🎮 Cómo se juega

Al elegir **Nuevo combate**, se escoge el jugador y su luchador, y la CPU elige un rival al azar.

| Movimiento | Golpe | Gana a |
|---|---|---|
| Piedra | Shoryuken | Tijera |
| Papel | Hadouken | Piedra |
| Tijera | Tatsumaki | Papel |

**Reglas del combate**

- Cada luchador empieza cada ronda con **100 de vida**
- En cada turno el jugador elige su ataque y la CPU elige otro al azar
- Quien gana el turno quita entre **15 y 30** de vida al rival
- Si hay empate, los ataques chocan y **los dos pierden 5**
- La ronda termina cuando alguien se queda sin vida (**K.O.**)
- Si se gana la ronda sin recibir ningún daño, aparece **PERFECT!**
- Si los dos caen a la vez es **DOUBLE K.O.** y esa ronda no puntúa
- Gana el combate quien consiga **2 rondas**
  **Menú principal**

```text
1. Nuevo combate
2. Gestionar jugadores
3. Gestionar luchadores
4. Historial de combates
5. Ranking de jugadores
0. Salir
```
 
---

## 🛠️ Tecnologías utilizadas

- Java 25
- Maven
- JAXB (`javax.xml.bind` 2.3.x) para guardar y leer los ficheros XML
- JUnit 5 (dependencia de pruebas)
- Git y GitHub (trabajo en ramas y Pull Requests)
---

## 🧱 Arquitectura del proyecto

La aplicación separa la interfaz, la lógica y el acceso a datos en capas:

```text
src/main/java/
 ├── Main.java                  → crea los objetos y arranca el menú
 ├── controllers/               → lógica de la aplicación
 │   ├── MenuController.java        menú principal y navegación
 │   ├── JugadorController.java     gestión de jugadores
 │   ├── LuchadorController.java    gestión de luchadores
 │   └── CombateController.java     partidas y combates guardados
 ├── DAO/                       → acceso a los datos
 │   ├── JugadorDAO.java
 │   ├── LuchadorDAO.java
 │   └── CombateDAO.java
 ├── dataAccess/
 │   ├── XMLManager.java            utilidad genérica de lectura/escritura XML (JAXB)
 │   └── contenedores/              lectura y escritura de cada fichero XML
 │       ├── JugadoresXML.java
 │       ├── LuchadoresXML.java
 │       └── CombatesXML.java
 ├── model/                     → datos y reglas del juego
 │   ├── Jugador.java
 │   ├── Luchador.java
 │   ├── Combate.java               combate terminado (incluye sus turnos)
 │   ├── Turno.java
 │   ├── Partida.java               lógica de un combate en curso (no se guarda)
 │   ├── RankingJugador.java        fila del ranking (se calcula, no se guarda)
 │   └── enums/
 │       ├── Movimiento.java
 │       ├── Ganador.java
 │       └── ResultadoCombate.java
 └── view/
     └── ConsoleView.java       → toda la entrada y salida por consola
 
data/                           → ficheros XML con los datos
 ├── jugadores.xml
 ├── luchadores.xml
 └── combates.xml
```

**Cómo trabajan las capas**

```text
ConsoleView  ←→  Controller  ←→  DAO  ←→  XML (carpeta data)
(pantalla)       (decide)       (guarda/lee)
```

- **ConsoleView** solo imprime y lee con `Scanner`. No toma decisiones.
- **Controllers** aplican la lógica: validan datos, comprueban duplicados y llaman a los DAO.
- **DAO** buscan, añaden, actualizan y eliminan registros. Asignan los ids automáticamente (el siguiente al mayor existente).
- **Contenedores** leen y escriben cada fichero XML con JAXB.
- **MenuController** es el único que habla a la vez con la vista y con los controllers.
---

## 💾 Persistencia de datos

Los datos se guardan en la carpeta `data/`, un fichero XML por entidad:

| Fichero | Contenido |
|---|---|
| `jugadores.xml` | Los jugadores registrados |
| `luchadores.xml` | Los luchadores disponibles |
| `combates.xml` | El historial, con los turnos de cada combate dentro |

Un combate solo guarda los **ids** del jugador y de los luchadores, y lleva dentro la lista de sus turnos:

```xml
<combate>
    <idCombate>1</idCombate>
    <idJugador>1</idJugador>
    <idLuchadorJugador>1</idLuchadorJugador>
    <idLuchadorCpu>2</idLuchadorCpu>
    <rondasJugador>2</rondasJugador>
    <rondasCpu>0</rondasCpu>
    <resultado>VICTORIA</resultado>
    <fecha>2026-10-03T12:40:11.123</fecha>
    <turnos>
        <turno>
            <numeroRonda>1</numeroRonda>
            <numeroTurno>1</numeroTurno>
            <movimientoJugador>PAPEL</movimientoJugador>
            <movimientoCpu>PIEDRA</movimientoCpu>
            <ganador>JUGADOR</ganador>
            <danio>23</danio>
        </turno>
        ...
    </turnos>
</combate>
```

Los ficheros se crean automáticamente la primera vez que se guarda algo, incluida la carpeta `data/`.
Los ficheros de datos reales no se suben al repositorio (están en el `.gitignore`), así que cada
persona del grupo trabaja con sus propios datos de prueba.
 
---

## ▶️ Ejecución

**Requisitos:** JDK 25 (la versión que fija el `pom.xml`) e IntelliJ IDEA.

1. Clonar el repositorio
2. Abrirlo en IntelliJ como proyecto Maven (si aparece, pulsar *Load Maven Changes*)
3. Comprobar que el SDK del proyecto es la versión 25
4. Ejecutar la clase `Main`
   **Primer uso:** al arrancar por primera vez no hay datos. Antes de jugar hay que crear, desde el menú,
   **al menos un jugador** (opción 2) y **dos luchadores** (opción 3).

---

## 📋 Lista de comprobación técnica

| Requisito | Implementación |
|---|---|
| Arquitectura por capas | Vista (`ConsoleView`), controllers, DAO y modelo separados |
| Persistencia | Ficheros XML con JAXB, un fichero por entidad |
| DAO | Un DAO por entidad con altas, bajas, modificaciones y consultas |
| Colecciones | `List` en todo el proyecto |
| Enumerados | `Movimiento`, `Ganador` y `ResultadoCombate` |
| Relaciones | Un combate se relaciona con un jugador y dos luchadores mediante ids, y contiene sus turnos |
| Streams y lambdas | Ranking, filtrado de rivales, comprobaciones de integridad y `removeIf` en los DAO |
| Control de entrada | Validación y repetición de las preguntas en `ConsoleView` |
 
---

## 🚀 Posibles mejoras futuras

- Sistema de logros (por ejemplo, ganar una ronda sin recibir daño)
- Luchadores con atributos que influyan en el combate
- Modos de dificultad para la CPU
- Estadísticas detalladas por jugador
- Guardar los datos en base de datos en lugar de XML
---
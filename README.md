# Street Fighter: Piedra, Papel o Tijera

Videojuego de escritorio desarrollado en **Java** con persistencia de datos en **MySQL**. Es una versión de "Piedra, Papel o Tijera" ambientada en el universo de Street Fighter, donde los movimientos clásicos se sustituyen por técnicas de los luchadores.

## Integrantes

- Iván Berral
- Daniel Ruiz
- José Trujillo
- José Antonio Castillero

## Enfoque del proyecto

### Idea del juego

El jugador elige un personaje y se enfrenta a la CPU en combates por rondas. Cada ronda se elige un movimiento:

| Movimiento | Equivale a | Gana a |
|---|---|---|
| Hadouken | Piedra | Tatsumaki |
| Shoryuken | Papel | Hadouken |
| Tatsumaki | Tijera | Shoryuken |

Quien pierde la ronda pierde vida. El combate termina cuando uno de los dos llega a 0.

### Tecnologías

- Java 21
- Mysql
- GitHub

### Persistencia de datos

La base de datos guarda:

- Jugadores registrados
- Personajes disponibles
- Historial de partidas
- Ranking de mejores jugadores

### Arquitectura

Seguimos el patrón **MVC** con estos paquetes: `modelo`, `dao`, `logica`, `vista`, `controlador` y `util`.

### Plan de trabajo

1. Diseñar la base de datos y crear el script SQL
2. Programar la capa de acceso a datos (DAO)
3. Implementar la lógica del juego
4. Crear la interfaz gráfica (si se puede)
5. Integrar todo y probarlo
6. Pulido final

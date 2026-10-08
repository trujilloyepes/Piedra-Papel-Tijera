package view;

import model.Jugador;
import model.Luchador;
import model.Partida;
import model.Turno;
import model.enums.Ganador;
import model.enums.Movimiento;

import java.util.List;
import java.util.Scanner;

/**
 * Toda la entrada y salida por consola de la aplicación.
 * Solo imprime y lee: no toma decisiones ni conoce los DAO.
 */
public class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);


    /** Muestra el título del juego */
    public void mostrarTitulo() {
        System.out.println("=============================================");
        System.out.println("   STREET FIGHTER: PIEDRA, PAPEL O TIJERA");
        System.out.println("=============================================");
    }

    /** Muestra un mensaje normal */
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /** Muestra un mensaje de error o aviso */
    public void mostrarError(String mensaje) {
        System.out.println("[!] " + mensaje);
    }

    /**
     * Muestra el menú principal y pide una opción.
     * @return la opción elegida (0 = salir)
     */
    public int mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("1. Nuevo combate");
        System.out.println("2. Gestionar jugadores");
        System.out.println("3. Gestionar luchadores");
        System.out.println("4. Historial de combates");
        System.out.println("0. Salir");
        return pedirEntero("Opción: ", 0, 4);
    }


    /**
     * Pide un texto que no puede estar vacío.
     * @param mensaje Texto que se le muestra al usuario
     * @return el texto escrito, sin espacios al principio ni al final
     */
    public String pedirTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                mostrarError("No puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    /**
     * Pide un número entero dentro de un rango, y repite la pregunta si no es válido.
     * @param mensaje Texto que se le muestra al usuario
     * @param min Valor mínimo permitido
     * @param max Valor máximo permitido
     * @return el número elegido
     */
    public int pedirEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            try {
                int numero = Integer.parseInt(scanner.nextLine().trim());
                if (numero >= min && numero <= max) {
                    return numero;
                }
            } catch (NumberFormatException e) {

            }
            mostrarError("Introduce un número entre " + min + " y " + max + ".");
        }
    }

    /**
     * Pregunta si o no.
     * @return true si el usuario responde "s"
     */
    public boolean confirmar(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.equals("s")) return true;
            if (respuesta.equals("n")) return false;
            mostrarError("Responde con s o n.");
        }
    }

    /** Espera a que el usuario pulse Enter */
    public void esperarEnter() {
        System.out.print("Pulsa Enter para continuar...");
        scanner.nextLine();
    }
    // ---------- Gestión (jugadores y luchadores) ----------

    /**
     * Muestra el submenú de gestión y pide una opción.
     * @param titulo Nombre de lo que se gestiona (por ejemplo "JUGADORES")
     * @return 1 listar, 2 añadir, 3 editar, 4 eliminar, 0 volver
     */
    public int mostrarMenuGestion(String titulo) {
        System.out.println();
        System.out.println("--- " + titulo + " ---");
        System.out.println("1. Listar");
        System.out.println("2. Añadir");
        System.out.println("3. Editar");
        System.out.println("4. Eliminar");
        System.out.println("0. Volver");
        return pedirEntero("Opción: ", 0, 4);
    }

    /** Muestra la lista de jugadores */
    public void mostrarJugadores(List<Jugador> jugadores) {
        System.out.println();
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados.");
            return;
        }
        for (Jugador j : jugadores) {
            System.out.printf("%3d. %s%n", j.getIdJugador(), j.getNombre());
        }
    }

    /** Muestra la lista de luchadores */
    public void mostrarLuchadores(List<Luchador> luchadores) {
        System.out.println();
        if (luchadores.isEmpty()) {
            System.out.println("No hay luchadores registrados.");
            return;
        }
        for (Luchador l : luchadores) {
            System.out.printf("%3d. %-10s %-16s %-14s %s%n",
                    l.getIdLuchador(), l.getNombre(), l.getPais(), l.getEstilo(), l.getGolpeEspecial());
        }
    }

    /**
     * Pide un texto que se puede dejar vacío (al editar, vacío significa "no cambiar").
     * @return el texto escrito, o "" si el usuario pulsa Enter
     */
    public String pedirTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    // ---------- Combate ----------

    /** Muestra los jugadores y pide uno por id. @return el jugador, o null si cancela */
    public Jugador elegirJugador(List<Jugador> jugadores) {
        mostrarJugadores(jugadores);
        while (true) {
            int id = pedirEntero("Id del jugador (0 = cancelar): ", 0, 9999);
            if (id == 0) return null;
            for (Jugador j : jugadores) {
                if (j.getIdJugador() == id) return j;
            }
            mostrarError("No existe ningún jugador con ese id.");
        }
    }

    /** Muestra los luchadores y pide uno por id. @return el luchador, o null si cancela */
    public Luchador elegirLuchador(List<Luchador> luchadores) {
        mostrarLuchadores(luchadores);
        while (true) {
            int id = pedirEntero("Id de tu luchador (0 = cancelar): ", 0, 9999);
            if (id == 0) return null;
            for (Luchador l : luchadores) {
                if (l.getIdLuchador() == id) return l;
            }
            mostrarError("No existe ningún luchador con ese id.");
        }
    }

    /** Anuncia el comienzo de una ronda */
    public void mostrarRonda(int ronda) {
        System.out.println();
        System.out.println("*** ROUND " + ronda + " - FIGHT! ***");
    }

    /** Muestra las barras de vida de los dos luchadores */
    public void mostrarEstado(Partida p) {
        System.out.println();
        System.out.printf("%-10s %s %3d%n", p.getLuchadorJugador().getNombre(), barra(p.getVidaJugador()), p.getVidaJugador());
        System.out.printf("%-10s %s %3d%n", p.getLuchadorCpu().getNombre(), barra(p.getVidaCpu()), p.getVidaCpu());
    }

    /** Pide el ataque del jugador. @return el movimiento elegido */
    public Movimiento pedirMovimiento() {
        System.out.println();
        System.out.println("1. Piedra (Shoryuken)   2. Papel (Hadouken)   3. Tijera (Tatsumaki)");
        return Movimiento.values()[pedirEntero("Tu ataque: ", 1, 3) - 1];
    }

    /** Cuenta lo que ha pasado en un turno */
    public void mostrarTurno(Turno t, Partida p) {
        String jugador = p.getLuchadorJugador().getNombre();
        String cpu = p.getLuchadorCpu().getNombre();

        System.out.println();
        System.out.println(jugador + " usa " + t.getMovimientoJugador().getGolpe() + "!");
        System.out.println(cpu + " usa " + t.getMovimientoCpu().getGolpe() + "!");
        switch (t.getGanador()) {
            case JUGADOR -> System.out.println(">> ¡" + jugador + " acierta! -" + t.getDamage() + " a " + cpu);
            case CPU -> System.out.println(">> ¡" + cpu + " acierta! -" + t.getDamage() + " a " + jugador);
            case EMPATE -> System.out.println(">> ¡Los ataques chocan! Ambos pierden " + t.getDamage());
        }
    }

    /** Muestra el K.O. y quién gana la ronda */
    public void mostrarFinRonda(int ronda, Ganador ganador, boolean perfect, Partida p) {
        System.out.println();
        System.out.println("*** K.O. ***");
        switch (ganador) {
            case JUGADOR -> System.out.println(p.getLuchadorJugador().getNombre() + " gana el round " + ronda);
            case CPU -> System.out.println(p.getLuchadorCpu().getNombre() + " gana el round " + ronda);
            case EMPATE -> System.out.println("DOUBLE K.O. - Draw game (esta ronda no cuenta)");
        }
        if (perfect && ganador != Ganador.EMPATE) {
            System.out.println("*** PERFECT! ***");
        }
        System.out.println("Marcador: " + p.getRondasJugador() + " - " + p.getRondasCpu());
    }

    /** Muestra el resultado final del combate */
    public void mostrarFinCombate(boolean victoria, Partida p) {
        System.out.println();
        System.out.println("=============================================");
        if (victoria) {
            System.out.println("  " + p.getLuchadorJugador().getNombre() + " WINS!  ¡Has ganado el combate!");
        } else {
            System.out.println("  " + p.getLuchadorCpu().getNombre() + " WINS!  Has perdido... ¡revancha!");
        }
        System.out.println("=============================================");
    }

    /** Dibuja una barra de vida de 20 posiciones, por ejemplo [##########----------] */
    private String barra(int vida) {
        int total = 20;
        int llenos = (int) Math.ceil(vida * total / (double) Partida.VIDA_MAXIMA);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < total; i++) {
            sb.append(i < llenos ? '#' : '-');
        }
        return sb.append("]").toString();
    }
}
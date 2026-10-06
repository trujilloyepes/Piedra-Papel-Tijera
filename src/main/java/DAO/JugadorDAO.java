package DAO;

import dataAccess.contenedores.JugadoresXML;
import model.Jugador;

import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    private JugadoresXML jugadoresXML;

    public JugadorDAO(JugadoresXML jugadoresXML) {
        this.jugadoresXML = jugadoresXML;
    }

    /**
     * Guarda un nuevo jugador autoincrementando su ID.
     */
    public boolean guardarJugador(Jugador jugador) {
        List<Jugador> jugadores = jugadoresXML.cargarJugadores();
        if (jugadores == null) {
            jugadores = new ArrayList<>();
        }

        // Autoincremento de ID
        int nuevoId = 1;
        for (Jugador j : jugadores) {
            if (j.getIdJugador() >= nuevoId) {
                nuevoId = j.getIdJugador() + 1;
            }
        }
        jugador.setIdJugador(nuevoId);

        jugadores.add(jugador);
        return jugadoresXML.guardarJugadores(jugadores);
    }

    /**
     * Actualiza la información de un jugador existente.
     */
    public boolean actualizarJugador(Jugador jugadorActualizado) {
        List<Jugador> jugadores = jugadoresXML.cargarJugadores();
        if (jugadores == null) return false;

        boolean encontrado = false;
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getIdJugador() == jugadorActualizado.getIdJugador()) {
                jugadores.set(i, jugadorActualizado);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            return jugadoresXML.guardarJugadores(jugadores);
        }
        return false;
    }

    /**
     * Elimina un jugador según su ID.
     */
    public boolean eliminarJugador(int idJugador) {
        List<Jugador> jugadores = jugadoresXML.cargarJugadores();
        if (jugadores == null) return false;

        boolean borrado = jugadores.removeIf(j -> j.getIdJugador() == idJugador);
        if (borrado) {
            return jugadoresXML.guardarJugadores(jugadores);
        }
        return false;
    }

    /**
     * Busca un jugador por su ID.
     */
    public Jugador obtenerPorId(int idJugador) {
        List<Jugador> jugadores = jugadoresXML.cargarJugadores();
        if (jugadores != null) {
            for (Jugador j : jugadores) {
                if (j.getIdJugador() == idJugador) {
                    return j;
                }
            }
        }
        return null;
    }

    /**
     * Busca un jugador por su nombre.
     */
    public Jugador obtenerPorNombre(String nombre) {
        List<Jugador> jugadores = jugadoresXML.cargarJugadores();
        if (jugadores != null && nombre != null) {
            for (Jugador j : jugadores) {
                if (nombre.equalsIgnoreCase(j.getNombre())) {
                    return j;
                }
            }
        }
        return null;
    }

    /**
     * Devuelve todos los jugadores registrados.
     */
    public List<Jugador> obtenerTodos() {
        List<Jugador> jugadores = jugadoresXML.cargarJugadores();
        return jugadores != null ? jugadores : new ArrayList<>();
    }

    /**
     * Comprueba si existe un jugador por su ID.
     */
    public boolean existeJugador(int idJugador) {
        return obtenerPorId(idJugador) != null;
    }

    /**
     * Añade un logro a un jugador y guarda directamente los cambios.
     */
    public boolean desbloquearLogro(int idJugador, int idLogro) {
        Jugador jugador = obtenerPorId(idJugador);
        if (jugador != null) {
            jugador.addLogro(idLogro);
            return actualizarJugador(jugador);
        }
        return false;
    }
}

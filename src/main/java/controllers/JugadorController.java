package controllers;

import DAO.JugadorDAO;
import dataAccess.contenedores.JugadoresXML;
import model.Jugador;

import java.util.List;

public class JugadorController {

    private final JugadorDAO jugadorDAO;

    /**
     * Constructor por defecto.
     * Utiliza los contenedores XML del proyecto.
     */
    public JugadorController() {
        this.jugadorDAO = new JugadorDAO(new JugadoresXML());
    }

    /**
     * Constructor para poder inyectar los DAO.
     */
    public JugadorController(JugadorDAO jugadorDAO) {
        this.jugadorDAO = jugadorDAO;
    }

    /**
     * Crea y guarda un jugador.
     */
    public boolean crearJugador(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        if (jugadorDAO.obtenerPorNombre(nombre.trim()) != null) {
            return false;
        }

        Jugador jugador = new Jugador(nombre.trim());
        return jugadorDAO.guardarJugador(jugador);
    }

    /**
     * Guarda un jugador ya creado.
     */
    public boolean guardarJugador(Jugador jugador) {
        if (jugador == null ||
                jugador.getNombre() == null ||
                jugador.getNombre().trim().isEmpty()) {
            return false;
        }

        return jugadorDAO.guardarJugador(jugador);
    }

    /**
     * Busca un jugador por ID.
     */
    public Jugador obtenerJugador(int idJugador) {
        return jugadorDAO.obtenerPorId(idJugador);
    }

    /**
     * Busca un jugador por nombre.
     */
    public Jugador obtenerJugadorPorNombre(String nombre) {
        return jugadorDAO.obtenerPorNombre(nombre);
    }

    /**
     * Devuelve todos los jugadores.
     */
    public List<Jugador> obtenerJugadores() {
        return jugadorDAO.obtenerTodos();
    }

    /**
     * Actualiza un jugador.
     */
    public boolean actualizarJugador(Jugador jugador) {
        if (jugador == null || jugador.getIdJugador() <= 0) {
            return false;
        }

        return jugadorDAO.actualizarJugador(jugador);
    }

    /**
     * Elimina un jugador.
     */
    public boolean eliminarJugador(int idJugador) {
        return jugadorDAO.eliminarJugador(idJugador);
    }

    /**
     * Comprueba si existe un jugador.
     */
    public boolean existeJugador(int idJugador) {
        return jugadorDAO.existeJugador(idJugador);
    }
}
package controllers;

import DAO.LuchadorDAO;
import dataAccess.contenedores.LuchadoresXML;
import model.Luchador;

import java.util.List;

public class LuchadorController {

    private final LuchadorDAO luchadorDAO;

    /**
     * Constructor por defecto.
     */
    public LuchadorController() {
        this.luchadorDAO = new LuchadorDAO(new LuchadoresXML());
    }

    /**
     * Constructor para inyección del DAO.
     */
    public LuchadorController(LuchadorDAO luchadorDAO) {
        this.luchadorDAO = luchadorDAO;
    }

    /**
     * Crea y guarda un luchador.
     */
    public boolean crearLuchador(String nombre, String pais) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        if (luchadorDAO.obtenerPorNombre(nombre.trim()) != null) {
            return false;
        }

        Luchador luchador = new Luchador(nombre.trim(), pais);

        return luchadorDAO.guardarLuchador(luchador);
    }

    /**
     * Guarda un luchador.
     */
    public boolean guardarLuchador(Luchador luchador) {
        if (luchador == null ||
                luchador.getNombre() == null ||
                luchador.getNombre().trim().isEmpty()) {
            return false;
        }

        return luchadorDAO.guardarLuchador(luchador);
    }

    /**
     * Busca un luchador por ID.
     */
    public Luchador obtenerLuchador(int idLuchador) {
        return luchadorDAO.obtenerPorId(idLuchador);
    }

    /**
     * Busca un luchador por nombre.
     */
    public Luchador obtenerLuchadorPorNombre(String nombre) {
        return luchadorDAO.obtenerPorNombre(nombre);
    }

    /**
     * Devuelve todos los luchadores.
     */
    public List<Luchador> obtenerLuchadores() {
        return luchadorDAO.obtenerTodos();
    }

    /**
     * Busca luchadores por país.
     */
    public List<Luchador> obtenerLuchadoresPorPais(String pais) {
        return luchadorDAO.obtenerPorPais(pais);
    }

    /**
     * Actualiza un luchador.
     */
    public boolean actualizarLuchador(Luchador luchador) {
        if (luchador == null || luchador.getIdLuchador() <= 0) {
            return false;
        }

        return luchadorDAO.actualizarLuchador(luchador);
    }

    /**
     * Elimina un luchador.
     */
    public boolean eliminarLuchador(int idLuchador) {
        return luchadorDAO.eliminarLuchador(idLuchador);
    }

    /**
     * Comprueba si existe un luchador.
     */
    public boolean existeLuchador(int idLuchador) {
        return luchadorDAO.existeLuchador(idLuchador);
    }
}


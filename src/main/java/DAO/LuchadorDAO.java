package DAO;

import dataAccess.contenedores.LuchadoresXML;
import model.Luchador;

import java.util.ArrayList;
import java.util.List;

public class LuchadorDAO {

    private LuchadoresXML luchadoresXML;

    public LuchadorDAO(LuchadoresXML luchadoresXML) {
        this.luchadoresXML = luchadoresXML;
    }

    /**
     * Guarda un nuevo luchador autoincrementando su ID.
     */
    public boolean guardarLuchador(Luchador luchador) {
        List<Luchador> luchadores = luchadoresXML.cargarLuchadores();
        if (luchadores == null) {
            luchadores = new ArrayList<>();
        }

        // Lógica de autoincremento para el ID
        int nuevoId = 1;
        for (Luchador l : luchadores) {
            if (l.getIdLuchador() >= nuevoId) {
                nuevoId = l.getIdLuchador() + 1;
            }
        }
        luchador.setIdLuchador(nuevoId);

        luchadores.add(luchador);
        return luchadoresXML.guardarLuchadores(luchadores);
    }

    /**
     * Actualiza la información de un luchador existente.
     */
    public boolean actualizarLuchador(Luchador luchadorActualizado) {
        List<Luchador> luchadores = luchadoresXML.cargarLuchadores();
        if (luchadores == null) return false;

        boolean encontrado = false;
        for (int i = 0; i < luchadores.size(); i++) {
            if (luchadores.get(i).getIdLuchador() == luchadorActualizado.getIdLuchador()) {
                luchadores.set(i, luchadorActualizado);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            return luchadoresXML.guardarLuchadores(luchadores);
        }
        return false;
    }

    /**
     * Elimina un luchador según su ID.
     */
    public boolean eliminarLuchador(int idLuchador) {
        List<Luchador> luchadores = luchadoresXML.cargarLuchadores();
        if (luchadores == null) return false;

        boolean borrado = luchadores.removeIf(l -> l.getIdLuchador() == idLuchador);
        if (borrado) {
            return luchadoresXML.guardarLuchadores(luchadores);
        }
        return false;
    }

    /**
     * Busca un luchador por su ID.
     */
    public Luchador obtenerPorId(int idLuchador) {
        List<Luchador> luchadores = luchadoresXML.cargarLuchadores();
        if (luchadores != null) {
            for (Luchador l : luchadores) {
                if (l.getIdLuchador() == idLuchador) {
                    return l;
                }
            }
        }
        return null;
    }

    /**
     * Busca un luchador por su nombre.
     */
    public Luchador obtenerPorNombre(String nombre) {
        List<Luchador> luchadores = luchadoresXML.cargarLuchadores();
        if (luchadores != null && nombre != null) {
            for (Luchador l : luchadores) {
                if (nombre.equalsIgnoreCase(l.getNombre())) {
                    return l;
                }
            }
        }
        return null;
    }

    /**
     * Devuelve la lista con todos los luchadores disponibles.
     */
    public List<Luchador> obtenerTodos() {
        List<Luchador> luchadores = luchadoresXML.cargarLuchadores();
        return luchadores != null ? luchadores : new ArrayList<>();
    }

    /**
     * Filtra luchadores por su país de origen.
     */
    public List<Luchador> obtenerPorPais(String pais) {
        List<Luchador> luchadores = luchadoresXML.cargarLuchadores();
        List<Luchador> filtrados = new ArrayList<>();

        if (luchadores != null && pais != null) {
            for (Luchador l : luchadores) {
                if (pais.equalsIgnoreCase(l.getPais())) {
                    filtrados.add(l);
                }
            }
        }
        return filtrados;
    }

    /**
     * Comprueba si existe un luchador por su ID.
     */
    public boolean existeLuchador(int idLuchador) {
        return obtenerPorId(idLuchador) != null;
    }
}

package DAO;

import dataAccess.contenedores.LogrosXML;
import model.Logro;

import java.util.ArrayList;
import java.util.List;

public class LogroDAO {

    private LogrosXML logrosXML;

    public LogroDAO(LogrosXML logrosXML) {
        this.logrosXML = logrosXML;
    }

    /**
     * Guarda un nuevo logro autoincrementando su ID.
     */
    public boolean guardarLogro(Logro logro) {
        List<Logro> logros = logrosXML.cargarLogros();
        if (logros == null) {
            logros = new ArrayList<>();
        }

        // Lógica de autoincremento para el ID
        int nuevoId = 1;
        for (Logro l : logros) {
            if (l.getIdLogro() >= nuevoId) {
                nuevoId = l.getIdLogro() + 1;
            }
        }

        // Se crea la instancia con el nuevo ID asignado
        Logro nuevoLogro = new Logro(nuevoId, logro.getNombre(), logro.getDescripcion());

        logros.add(nuevoLogro);
        return logrosXML.guardarLogros(logros);
    }

    /**
     * Actualiza la información de un logro existente.
     */
    public boolean actualizarLogro(Logro logroActualizado) {
        List<Logro> logros = logrosXML.cargarLogros();
        if (logros == null) return false;

        boolean encontrado = false;
        for (int i = 0; i < logros.size(); i++) {
            if (logros.get(i).getIdLogro() == logroActualizado.getIdLogro()) {
                logros.set(i, logroActualizado);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            return logrosXML.guardarLogros(logros);
        }
        return false;
    }

    /**
     * Elimina un logro a partir de su ID.
     */
    public boolean eliminarLogro(int idLogro) {
        List<Logro> logros = logrosXML.cargarLogros();
        if (logros == null) return false;

        boolean borrado = logros.removeIf(l -> l.getIdLogro() == idLogro);
        if (borrado) {
            return logrosXML.guardarLogros(logros);
        }
        return false;
    }

    /**
     * Busca un logro por su ID.
     */
    public Logro obtenerPorId(int idLogro) {
        List<Logro> logros = logrosXML.cargarLogros();
        if (logros != null) {
            for (Logro l : logros) {
                if (l.getIdLogro() == idLogro) {
                    return l;
                }
            }
        }
        return null;
    }

    /**
     * Busca un logro por su nombre exacto.
     */
    public Logro obtenerPorNombre(String nombre) {
        List<Logro> logros = logrosXML.cargarLogros();
        if (logros != null && nombre != null) {
            for (Logro l : logros) {
                if (nombre.equalsIgnoreCase(l.getNombre())) {
                    return l;
                }
            }
        }
        return null;
    }

    /**
     * Devuelve el catálogo completo de logros.
     */
    public List<Logro> obtenerTodos() {
        List<Logro> logros = logrosXML.cargarLogros();
        return logros != null ? logros : new ArrayList<>();
    }

    /**
     * Comprueba si existe un logro con el ID especificado.
     */
    public boolean existeLogro(int idLogro) {
        return obtenerPorId(idLogro) != null;
    }
}

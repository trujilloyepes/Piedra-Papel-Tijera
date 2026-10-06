package DAO;

import dataAccess.contenedores.CombatesXML;
import model.Combate;

import java.util.ArrayList;
import java.util.List;

public class CombateDAO {


    private CombatesXML combatesXML;

    /**
     * Constructor del DAO. Recibe el DataAccess para poder comunicarse con los datos.
     */
    public CombateDAO(CombatesXML combatesXML) {
        this.combatesXML = combatesXML;
    }

    /**
     * Guarda un nuevo combate. Se encarga de asignarle un ID autoincremental
     * para que no haya duplicados.
     */
    public boolean guardarCombate(Combate combate) {
        List<Combate> combates = combatesXML.cargarCombates();
        if (combates == null) {
            combates = new ArrayList<>();
        }

        // Lógica de autoincremento para el ID
        int nuevoId = 1;
        for (Combate c : combates) {
            if (c.getIdCombate() >= nuevoId) {
                nuevoId = c.getIdCombate() + 1;
            }
        }
        combate.setIdCombate(nuevoId);

        // Añadimos a la lista y le decimos al DataAccess que la guarde
        combates.add(combate);
        return combatesXML.guardarCombates(combates);
    }

    /**
     * Actualiza la información de un combate que ya existe.
     */
    public boolean actualizarCombate(Combate combateActualizado) {
        List<Combate> combates = combatesXML.cargarCombates();
        if (combates == null) return false;

        boolean encontrado = false;

        // Buscamos el combate por su ID y lo sustituimos
        for (int i = 0; i < combates.size(); i++) {
            if (combates.get(i).getIdCombate() == combateActualizado.getIdCombate()) {
                combates.set(i, combateActualizado);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            return combatesXML.guardarCombates(combates);
        }
        return false;
    }

    /**
     * Elimina un combate concreto según su ID.
     */
    public boolean eliminarCombate(int idCombate) {
        List<Combate> combates = combatesXML.cargarCombates();
        if (combates == null) return false;

        // removeIf elimina el combate y devuelve true si lo puede borrar
        boolean borrado = combates.removeIf(c -> c.getIdCombate() == idCombate);

        if (borrado) {
            return combatesXML.guardarCombates(combates);
        }
        return false;
    }

    /**
     * Busca un combate específico por su ID.
     */
    public Combate obtenerPorId(int idCombate) {
        List<Combate> combates = combatesXML.cargarCombates();
        if (combates != null) {
            for (Combate c : combates) {
                if (c.getIdCombate() == idCombate) {
                    return c;
                }
            }
        }
        return null; // Devuelve null si no lo encuentra
    }

    /**
     * Devuelve la lista con todos los combates registrados.
     */
    public List<Combate> obtenerTodos() {
        List<Combate> combates = combatesXML.cargarCombates();
        return combates != null ? combates : new ArrayList<>();
    }

    /**
     * Filtra y devuelve solo los combates en los que haya participado un jugador concreto.
     */
    public List<Combate> obtenerCombatesPorJugador(int idJugador) {
        List<Combate> combates = combatesXML.cargarCombates();
        List<Combate> combatesDelJugador = new ArrayList<>();

        if (combates != null) {
            for (Combate c : combates) {
                if (c.getIdJugador() == idJugador) {
                    combatesDelJugador.add(c);
                }
            }
        }
        return combatesDelJugador;
    }

    /**
     * Comprueba si existe un combate con una ID determinada.
     */
    public boolean existeCombate(int idCombate) {
        return obtenerPorId(idCombate) != null;
    }
}

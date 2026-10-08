package controllers;

import DAO.CombateDAO;
import DAO.JugadorDAO;
import DAO.LuchadorDAO;
import dataAccess.contenedores.CombatesXML;
import dataAccess.contenedores.JugadoresXML;
import dataAccess.contenedores.LuchadoresXML;
import model.Combate;
import model.Jugador;
import model.Luchador;
import model.Partida;
import model.Turno;
import model.enums.Ganador;
import model.enums.Movimiento;
import model.enums.ResultadoCombate;

import java.util.List;

public class CombateController {

    private final CombateDAO combateDAO;
    private final JugadorDAO jugadorDAO;
    private final LuchadorDAO luchadorDAO;

    /**
     * Constructor por defecto.
     */
    public CombateController() {
        this.combateDAO = new CombateDAO(new CombatesXML());
        this.jugadorDAO = new JugadorDAO(new JugadoresXML());
        this.luchadorDAO = new LuchadorDAO(new LuchadoresXML());
    }

    /**
     * Constructor para inyección de dependencias.
     */
    public CombateController(CombateDAO combateDAO,
                             JugadorDAO jugadorDAO,
                             LuchadorDAO luchadorDAO) {
        this.combateDAO = combateDAO;
        this.jugadorDAO = jugadorDAO;
        this.luchadorDAO = luchadorDAO;
    }

    /**
     * Inicia una nueva partida.
     *
     * @param idJugador jugador que participa
     * @param idLuchadorJugador luchador elegido por el jugador
     * @param idLuchadorCpu luchador controlado por la CPU
     * @return una Partida preparada para jugar o null si algún dato no existe
     */
    public Partida iniciarPartida(int idJugador,
                                  int idLuchadorJugador,
                                  int idLuchadorCpu) {

        Jugador jugador = jugadorDAO.obtenerPorId(idJugador);
        Luchador luchadorJugador = luchadorDAO.obtenerPorId(idLuchadorJugador);
        Luchador luchadorCpu = luchadorDAO.obtenerPorId(idLuchadorCpu);

        if (jugador == null ||
                luchadorJugador == null ||
                luchadorCpu == null) {
            return null;
        }

        return new Partida(luchadorJugador, luchadorCpu);
    }

    /**
     * Ejecuta un turno de una partida.
     */
    public Turno jugarTurno(Partida partida, Movimiento movimiento) {
        if (partida == null || movimiento == null) {
            return null;
        }

        if (partida.combateTerminado()) {
            return null;
        }

        return partida.jugarTurno(movimiento);
    }

    /**
     * Comprueba si la ronda actual ha terminado.
     */
    public boolean rondaTerminada(Partida partida) {
        return partida != null && partida.rondaTerminada();
    }

    /**
     * Cierra la ronda actual y devuelve su ganador.
     */
    public Ganador cerrarRonda(Partida partida) {
        if (partida == null || !partida.rondaTerminada()) {
            return null;
        }

        return partida.cerrarRonda();
    }

    /**
     * Prepara la siguiente ronda.
     *
     * No debe llamarse si el combate ya ha terminado.
     */
    public boolean siguienteRonda(Partida partida) {
        if (partida == null || partida.combateTerminado()) {
            return false;
        }

        partida.siguienteRonda();
        return true;
    }

    /**
     * Comprueba si el combate ha terminado.
     */
    public boolean combateTerminado(Partida partida) {
        return partida != null && partida.combateTerminado();
    }

    /**
     * Guarda el resultado final de una partida.
     *
     * Este método debe llamarse cuando Partida.combateTerminado()
     * devuelve true.
     */
    public boolean guardarPartida(Partida partida,
                                  int idJugador) {

        if (partida == null ||
                !partida.combateTerminado()) {
            return false;
        }

        if (!jugadorDAO.existeJugador(idJugador)) {
            return false;
        }

        Luchador luchadorJugador = partida.getLuchadorJugador();
        Luchador luchadorCpu = partida.getLuchadorCpu();

        ResultadoCombate resultado;

        if (partida.jugadorGana()) {
            resultado = ResultadoCombate.VICTORIA;
        } else {
            resultado = ResultadoCombate.DERROTA;
        }

        Combate combate = new Combate(
                idJugador,
                luchadorJugador.getIdLuchador(),
                luchadorCpu.getIdLuchador(),
                partida.getRondasJugador(),
                partida.getRondasCpu(),
                resultado,
                partida.getTurnos()
        );

        boolean guardado = combateDAO.guardarCombate(combate);

        /*
         * Si el jugador ha conseguido una ronda PERFECT,
         * se puede desbloquear aquí el logro correspondiente
         * cuando exista ese logro en logros.xml.
         *
         * No se fuerza ningún ID concreto porque el proyecto
         * no define todavía una constante para los logros.
         */

        return guardado;
    }

    /**
     * Guarda una partida ya terminada y devuelve el combate creado.
     *
     * Es útil si después de guardar necesitamos consultar el ID
     * asignado por el DAO.
     */
    public Combate finalizarPartida(Partida partida, int idJugador) {

        if (!guardarPartida(partida, idJugador)) {
            return null;
        }

        List<Combate> combates = combateDAO.obtenerCombatesPorJugador(idJugador);

        if (combates.isEmpty()) {
            return null;
        }

        Combate ultimo = null;

        for (Combate combate : combates) {
            if (ultimo == null ||
                    combate.getIdCombate() > ultimo.getIdCombate()) {
                ultimo = combate;
            }
        }

        return ultimo;
    }

    /**
     * Busca un combate por ID.
     */
    public Combate obtenerCombate(int idCombate) {
        return combateDAO.obtenerPorId(idCombate);
    }

    /**
     * Devuelve todos los combates.
     */
    public List<Combate> obtenerCombates() {
        return combateDAO.obtenerTodos();
    }

    /**
     * Devuelve el historial de combates de un jugador.
     */
    public List<Combate> obtenerCombatesPorJugador(int idJugador) {
        return combateDAO.obtenerCombatesPorJugador(idJugador);
    }

    /**
     * Actualiza un combate.
     */
    public boolean actualizarCombate(Combate combate) {
        if (combate == null || combate.getIdCombate() <= 0) {
            return false;
        }

        return combateDAO.actualizarCombate(combate);
    }

    /**
     * Elimina un combate.
     */
    public boolean eliminarCombate(int idCombate) {
        return combateDAO.eliminarCombate(idCombate);
    }

    /**
     * Comprueba si existe un combate.
     */
    public boolean existeCombate(int idCombate) {
        return combateDAO.existeCombate(idCombate);
    }
}

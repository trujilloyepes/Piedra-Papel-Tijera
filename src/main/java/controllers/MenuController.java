package controllers;

import model.Jugador;
import model.Luchador;
import model.Partida;
import model.Turno;
import model.Combate;
import model.enums.Ganador;
import model.enums.Movimiento;
import view.ConsoleView;

import java.util.List;
import java.util.Random;

/**
     * Muestra el menú principal y reparte cada opción. Usa los controllers de los
     * compañeros para los datos y ConsoleView para imprimir y leer.
     */
    public class MenuController {
        private final ConsoleView view;
        private final JugadorController jugadorController;
        private final LuchadorController luchadorController;
        private final CombateController combateController;
        private final Random random = new Random();

        public MenuController(ConsoleView view,
                              JugadorController jugadorController,
                              LuchadorController luchadorController,
                              CombateController combateController) {
            this.view = view;
            this.jugadorController = jugadorController;
            this.luchadorController = luchadorController;
            this.combateController = combateController;
        }

        /** Bucle principal: se repite hasta que el usuario elige salir */
        public void iniciar() {
            view.mostrarTitulo();
            boolean salir = false;

            while (!salir) {
                switch (view.mostrarMenuPrincipal()) {
                    case 1 -> nuevoCombate();
                    case 2 -> gestionarJugadores();
                    case 3 -> gestionarLuchadores();
                    case 4 -> historial();
                    case 0 -> {
                        view.mostrarMensaje("¡Hasta la próxima, guerrero!");
                        salir = true;
                    }
                }
            }
        }


        private void gestionarJugadores() {
            boolean volver = false;
            while (!volver) {
                switch (view.mostrarMenuGestion("JUGADORES")) {
                    case 1 -> view.mostrarJugadores(jugadorController.obtenerJugadores());
                    case 2 -> anadirJugador();
                    case 3 -> editarJugador();
                    case 4 -> eliminarJugador();
                    case 0 -> volver = true;
                }
            }
        }

        private void anadirJugador() {
            String nombre = view.pedirTexto("Nombre del nuevo jugador: ");
            if (jugadorController.crearJugador(nombre)) {
                view.mostrarMensaje("Jugador añadido.");
            } else {
                view.mostrarError("No se pudo añadir (ese nombre ya existe).");
            }
        }

        private void editarJugador() {
            List<Jugador> jugadores = jugadorController.obtenerJugadores();
            if (jugadores.isEmpty()) {
                view.mostrarError("No hay jugadores que editar.");
                return;
            }
            view.mostrarJugadores(jugadores);

            int id = view.pedirEntero("Id del jugador a editar (0 = cancelar): ", 0, 9999);
            if (id == 0) return;

            Jugador jugador = jugadorController.obtenerJugador(id);
            if (jugador == null) {
                view.mostrarError("No existe ningún jugador con ese id.");
                return;
            }

            String nombre = valorOActual(
                    view.pedirTextoOpcional("Nombre [" + jugador.getNombre() + "] (Enter = no cambiar): "),
                    jugador.getNombre());

            Jugador otro = jugadorController.obtenerJugadorPorNombre(nombre);
            if (otro != null && otro.getIdJugador() != jugador.getIdJugador()) {
                view.mostrarError("Ya existe un jugador con ese nombre.");
                return;
            }

            jugador.setNombre(nombre);
            if (jugadorController.actualizarJugador(jugador)) {
                view.mostrarMensaje("Jugador actualizado.");
            } else {
                view.mostrarError("No se pudo actualizar.");
            }
        }

        private void eliminarJugador() {
            List<Jugador> jugadores = jugadorController.obtenerJugadores();
            if (jugadores.isEmpty()) {
                view.mostrarError("No hay jugadores que eliminar.");
                return;
            }
            view.mostrarJugadores(jugadores);

            int id = view.pedirEntero("Id del jugador a eliminar (0 = cancelar): ", 0, 9999);
            if (id == 0) return;

            Jugador jugador = jugadorController.obtenerJugador(id);
            if (jugador == null) {
                view.mostrarError("No existe ningún jugador con ese id.");
                return;
            }

            int combates = combateController.obtenerCombatesPorJugador(id).size();
            if (combates > 0) {
                view.mostrarError("No se puede eliminar: tiene " + combates + " combate(s) en el historial.");
                return;
            }

            if (view.confirmar("¿Eliminar a " + jugador.getNombre() + "?")) {
                if (jugadorController.eliminarJugador(id)) {
                    view.mostrarMensaje("Jugador eliminado.");
                } else {
                    view.mostrarError("No se pudo eliminar.");
                }
            }
        }


        private void gestionarLuchadores() {
            boolean volver = false;
            while (!volver) {
                switch (view.mostrarMenuGestion("LUCHADORES")) {
                    case 1 -> view.mostrarLuchadores(luchadorController.obtenerLuchadores());
                    case 2 -> anadirLuchador();
                    case 3 -> editarLuchador();
                    case 4 -> eliminarLuchador();
                    case 0 -> volver = true;
                }
            }
        }

        private void anadirLuchador() {
            String nombre = view.pedirTexto("Nombre: ");
            String pais = view.pedirTexto("País: ");

            if (luchadorController.crearLuchador(nombre, pais)) {
                view.mostrarMensaje("Luchador añadido.");
            } else {
                view.mostrarError("No se pudo añadir (ese nombre ya existe).");
            }
        }

        private void editarLuchador() {
            List<Luchador> luchadores = luchadorController.obtenerLuchadores();
            if (luchadores.isEmpty()) {
                view.mostrarError("No hay luchadores que editar.");
                return;
            }
            view.mostrarLuchadores(luchadores);

            int id = view.pedirEntero("Id del luchador a editar (0 = cancelar): ", 0, 9999);
            if (id == 0) return;

            Luchador luchador = luchadorController.obtenerLuchador(id);
            if (luchador == null) {
                view.mostrarError("No existe ningún luchador con ese id.");
                return;
            }

            view.mostrarMensaje("Pulsa Enter para dejar un campo como está.");
            String nombre = valorOActual(view.pedirTextoOpcional("Nombre [" + luchador.getNombre() + "]: "), luchador.getNombre());
            String pais = valorOActual(view.pedirTextoOpcional("País [" + luchador.getPais() + "]: "), luchador.getPais());

            Luchador otro = luchadorController.obtenerLuchadorPorNombre(nombre);
            if (otro != null && otro.getIdLuchador() != luchador.getIdLuchador()) {
                view.mostrarError("Ya existe un luchador con ese nombre.");
                return;
            }

            luchador.setNombre(nombre);
            luchador.setPais(pais);

            if (luchadorController.actualizarLuchador(luchador)) {
                view.mostrarMensaje("Luchador actualizado.");
            } else {
                view.mostrarError("No se pudo actualizar.");
            }
        }

        private void eliminarLuchador() {
            List<Luchador> luchadores = luchadorController.obtenerLuchadores();
            if (luchadores.isEmpty()) {
                view.mostrarError("No hay luchadores que eliminar.");
                return;
            }
            view.mostrarLuchadores(luchadores);

            int id = view.pedirEntero("Id del luchador a eliminar (0 = cancelar): ", 0, 9999);
            if (id == 0) return;

            Luchador luchador = luchadorController.obtenerLuchador(id);
            if (luchador == null) {
                view.mostrarError("No existe ningún luchador con ese id.");
                return;
            }

            long combates = combateController.obtenerCombates().stream()
                    .filter(c -> c.getIdLuchadorJugador() == id || c.getIdLuchadorCpu() == id)
                    .count();
            if (combates > 0) {
                view.mostrarError("No se puede eliminar: ha participado en " + combates + " combate(s) del historial.");
                return;
            }

            if (view.confirmar("¿Eliminar a " + luchador.getNombre() + "?")) {
                if (luchadorController.eliminarLuchador(id)) {
                    view.mostrarMensaje("Luchador eliminado.");
                } else {
                    view.mostrarError("No se pudo eliminar.");
                }
            }
        }
    // ==================== COMBATE ====================

    private void nuevoCombate() {
        List<Jugador> jugadores = jugadorController.obtenerJugadores();
        List<Luchador> luchadores = luchadorController.obtenerLuchadores();

        if (jugadores.isEmpty()) {
            view.mostrarError("Primero tienes que registrar un jugador.");
            return;
        }
        if (luchadores.size() < 2) {
            view.mostrarError("Necesitas al menos dos luchadores registrados para combatir.");
            return;
        }

        Jugador jugador = view.elegirJugador(jugadores);
        if (jugador == null) return;
        Luchador luchador = view.elegirLuchador(luchadores);
        if (luchador == null) return;

        // La CPU elige un rival al azar, distinto del tuyo
        List<Luchador> rivales = luchadores.stream()
                .filter(l -> l.getIdLuchador() != luchador.getIdLuchador())
                .toList();
        Luchador rival = rivales.get(random.nextInt(rivales.size()));

        Partida partida = combateController.iniciarPartida(
                jugador.getIdJugador(), luchador.getIdLuchador(), rival.getIdLuchador());
        if (partida == null) {
            view.mostrarError("No se pudo iniciar el combate.");
            return;
        }

        view.mostrarVersus(luchador.getNombre(), rival.getNombre());

        // Bucle de rondas: se repite hasta que alguien gana el combate
        while (!combateController.combateTerminado(partida)) {
            view.mostrarRonda(partida.getRondaActual());

            // Bucle de turnos: se repite hasta que alguien se queda sin vida
            while (!combateController.rondaTerminada(partida)) {
                view.mostrarEstado(partida);
                Movimiento movimiento = view.pedirMovimiento();
                Turno turno = combateController.jugarTurno(partida, movimiento);
                view.mostrarTurno(turno, partida);
            }

            view.mostrarEstado(partida);
            int ronda = partida.getRondaActual();
            Ganador ganador = combateController.cerrarRonda(partida);
            view.mostrarFinRonda(ronda, ganador, partida.isUltimaRondaPerfect(), partida);
            view.esperarEnter();

            if (!combateController.combateTerminado(partida)) {
                combateController.siguienteRonda(partida);
            }
        }

        view.mostrarFinCombate(partida.jugadorGana(), partida);

        if (combateController.guardarPartida(partida, jugador.getIdJugador())) {
            view.mostrarMensaje("Combate guardado en el historial.");
        } else {
            view.mostrarError("No se pudo guardar el combate.");
        }
    }

    // ==================== HISTORIAL ====================

    private void historial() {
        List<Combate> combates = combateController.obtenerCombates();
        if (combates.isEmpty()) {
            view.mostrarError("Todavía no hay combates en el historial. ¡Juega uno primero!");
            return;
        }

        List<Jugador> jugadores = jugadorController.obtenerJugadores();
        List<Luchador> luchadores = luchadorController.obtenerLuchadores();

        boolean volver = false;
        while (!volver) {
            view.mostrarHistorial(combates, jugadores, luchadores);
            int id = view.pedirEntero("Id del combate para ver sus turnos (0 = volver): ", 0, 9999);

            if (id == 0) {
                volver = true;
            } else {
                Combate combate = combateController.obtenerCombate(id);
                if (combate == null) {
                    view.mostrarError("No existe ningún combate con ese id.");
                } else {
                    view.mostrarDetalleCombate(combate, jugadores, luchadores);
                    view.esperarEnter();
                }
            }
        }
    }

        /** Si el usuario no escribe nada al editar, se conserva el valor actual */
        private String valorOActual(String nuevo, String actual) {
            return nuevo.isEmpty() ? actual : nuevo;
        }
    }

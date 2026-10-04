package model;

import model.enums.Ganador;
import model.enums.Movimiento;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Lógica de un combate en curso (vida, rondas y turnos).
 * No se guarda en XML: al terminar, el controller crea un Combate con sus turnos.
 */
public class Partida {
    public static final int VIDA_MAXIMA = 100;
    public static final int RONDAS_PARA_GANAR = 2;
    private static final int DAMAGE_CHOQUE = 5;
    private static final int DAMAGE_MINIMO = 15;
    private static final int DAMAGE_MAXIMO = 30;

    private final Luchador luchadorJugador;
    private final Luchador luchadorCpu;
    private final List<Turno> turnos = new ArrayList<>();
    private final Random random = new Random();

    private int vidaJugador = VIDA_MAXIMA;
    private int vidaCpu = VIDA_MAXIMA;
    private int rondasJugador = 0;
    private int rondasCpu = 0;
    private int rondaActual = 1;
    private int turnoActual = 1;
    private boolean jugadorRecibioDamage = false;
    private boolean cpuRecibioDamage = false;
    private boolean ultimaRondaPerfect = false;
    private boolean perfectConseguido = false;

    public Partida(Luchador luchadorJugador, Luchador luchadorCpu) {
        this.luchadorJugador = luchadorJugador;
        this.luchadorCpu = luchadorCpu;
    }

    /**
     * Juega un turno: la CPU elige al azar y el perdedor recibe daño.
     * Si hay empate, los dos ataques chocan y ambos pierden 5 de vida.
     */
    public Turno jugarTurno(Movimiento movimientoJugador) {
        Movimiento movimientoCpu = Movimiento.values()[this.random.nextInt(Movimiento.values().length)];
        Ganador ganador;
        int damage;

        if (movimientoJugador == movimientoCpu) {
            ganador = Ganador.EMPATE;
            damage = DAMAGE_CHOQUE;
            this.vidaJugador = Math.max(0, this.vidaJugador - damage);
            this.vidaCpu = Math.max(0, this.vidaCpu - damage);
            this.jugadorRecibioDamage = true;
            this.cpuRecibioDamage = true;
        } else {
            damage = DAMAGE_MINIMO + this.random.nextInt(DAMAGE_MAXIMO - DAMAGE_MINIMO + 1);
            if (movimientoJugador.vence(movimientoCpu)) {
                ganador = Ganador.JUGADOR;
                this.vidaCpu = Math.max(0, this.vidaCpu - damage);
                this.cpuRecibioDamage = true;
            } else {
                ganador = Ganador.CPU;
                this.vidaJugador = Math.max(0, this.vidaJugador - damage);
                this.jugadorRecibioDamage = true;
            }
        }

        Turno turno = new Turno(this.rondaActual, this.turnoActual, movimientoJugador, movimientoCpu, ganador, damage);
        this.turnos.add(turno);
        this.turnoActual++;
        return turno;
    }

    /** La ronda termina cuando alguno se queda sin vida */
    public boolean rondaTerminada() {
        return this.vidaJugador <= 0 || this.vidaCpu <= 0;
    }

    /**
     * Cierra la ronda: suma el punto al ganador y detecta si ha sido PERFECT
     * (ganar sin recibir daño). Si caen los dos a la vez, es doble K.O. y no puntúa nadie.
     */
    public Ganador cerrarRonda() {
        Ganador ganador;
        this.ultimaRondaPerfect = false;

        if (this.vidaJugador <= 0 && this.vidaCpu <= 0) {
            ganador = Ganador.EMPATE;
        } else if (this.vidaCpu <= 0) {
            ganador = Ganador.JUGADOR;
            this.rondasJugador++;
            this.ultimaRondaPerfect = !this.jugadorRecibioDamage;
            if (this.ultimaRondaPerfect) {
                this.perfectConseguido = true;
            }
        } else {
            ganador = Ganador.CPU;
            this.rondasCpu++;
            this.ultimaRondaPerfect = !this.cpuRecibioDamage;
        }
        return ganador;
    }

    /** Prepara la siguiente ronda: restaura la vida de los dos luchadores */
    public void siguienteRonda() {
        this.vidaJugador = VIDA_MAXIMA;
        this.vidaCpu = VIDA_MAXIMA;
        this.jugadorRecibioDamage = false;
        this.cpuRecibioDamage = false;
        this.rondaActual++;
        this.turnoActual = 1;
    }

    /** El combate termina cuando alguno gana las rondas necesarias */
    public boolean combateTerminado() {
        return this.rondasJugador >= RONDAS_PARA_GANAR || this.rondasCpu >= RONDAS_PARA_GANAR;
    }

    public boolean jugadorGana() {
        return this.rondasJugador > this.rondasCpu;
    }

    public Luchador getLuchadorJugador() { return this.luchadorJugador; }

    public Luchador getLuchadorCpu() { return this.luchadorCpu; }

    public List<Turno> getTurnos() { return this.turnos; }

    public int getVidaJugador() { return this.vidaJugador; }

    public int getVidaCpu() { return this.vidaCpu; }

    public int getRondasJugador() { return this.rondasJugador; }

    public int getRondasCpu() { return this.rondasCpu; }

    public int getRondaActual() { return this.rondaActual; }

    public boolean isUltimaRondaPerfect() { return this.ultimaRondaPerfect; }

    public boolean isPerfectConseguido() { return this.perfectConseguido; }
}
package model;

import model.enums.Ganador;
import model.enums.Movimiento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "turno")
@XmlAccessorType(XmlAccessType.FIELD)
public class Turno {
    private int numeroRonda;
    private int numeroTurno;
    private Movimiento movimientoJugador;
    private Movimiento movimientoCpu;
    private Ganador ganador;
    private int damage;

    public Turno() {}  // El constructor vacío es para JAXB

    public Turno(int numeroRonda, int numeroTurno, Movimiento movimientoJugador, Movimiento movimientoCpu, Ganador ganador, int damage) {
        this.numeroRonda = numeroRonda;
        this.numeroTurno = numeroTurno;
        this.movimientoJugador = movimientoJugador;
        this.movimientoCpu = movimientoCpu;
        this.ganador = ganador;
        this.damage = damage;
    }

    public int getNumeroRonda() {
        return this.numeroRonda;
    }

    public int getNumeroTurno() {
        return this.numeroTurno;
    }

    public Movimiento getMovimientoJugador() {
        return this.movimientoJugador;
    }

    public Movimiento getMovimientoCpu() {
        return this.movimientoCpu;
    }

    public Ganador getGanador() {
        return this.ganador;
    }

    public int getDamage() {
        return this.damage;
    }
}
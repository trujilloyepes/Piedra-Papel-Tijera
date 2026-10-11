package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "jugador")
@XmlAccessorType(XmlAccessType.FIELD)
public class Jugador{
    private int idJugador;
    private String nombre;

    public Jugador() {} // Constructor vacío para JAXB

    public Jugador(int idJugador, String nombre) {
        this.idJugador = idJugador;
        this.nombre = nombre;
    }

    /**
     * Para jugadores nuevos sin ID todavía
     * @param nombre Nombre del jugador
     */
    public Jugador (String nombre) {
        this.nombre = nombre;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return this.nombre;
    }
}
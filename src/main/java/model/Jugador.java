package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "jugador")
@XmlAccessorType(XmlAccessType.FIELD)
public class Jugador{
    private int idJugador;
    private String nombre;

    @XmlElementWrapper(name = "logros")
    @XmlElement(name = "idLogro")
    private List<Integer> idsLogros = new ArrayList<Integer>();

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

    /**
     * Comprueba si el jugador ya tiene desbloqueado ese logro
     * @param idLogro ID del logro
     */
    public boolean tieneLogro (int idLogro){
        return this.idsLogros.contains(idLogro);
    }

    /**
     * Añade un logro al jugador, si ya lo tenía, no hace nada
     * @param idLogro ID del logro
     */
    public void addLogro (int idLogro){
        if (!tieneLogro(idLogro)){
            this.idsLogros.add(idLogro);
        }
    }

    /**
     * Quita un logro al jugador
     * @param idLogro ID del logro
     */
    public void removeLogro (int idLogro){
        this.idsLogros.remove(idLogro);
    }

    @Override
    public String toString(){
        return this.nombre;
    }
}
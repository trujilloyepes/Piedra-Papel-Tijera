package model;

import model.enums.ResultadoCombate;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "combate")
@XmlAccessorType(XmlAccessType.FIELD)
public class Combate {
    private int idCombate;
    private int idJugador;
    private int idLuchadorJugador;
    private int idLuchadorCpu;
    private int rondasJugador;
    private int rondasCpu;
    private ResultadoCombate resultado;
    private String fecha;

    @XmlElementWrapper(name = "turnos")
    @XmlElement(name = "turno")
    private List<Turno> turnos = new ArrayList<>();

    public Combate() {} // Constructor vacío para JAXB

    public Combate(int idCombate, int idJugador, int idLuchadorJugador, int idLuchadorCpu,
                   int rondasJugador, int rondasCpu, ResultadoCombate resultado, String fecha, List<Turno> turnos) {
        this.idCombate = idCombate;
        this.idJugador = idJugador;
        this.idLuchadorJugador = idLuchadorJugador;
        this.idLuchadorCpu = idLuchadorCpu;
        this.rondasJugador = rondasJugador;
        this.rondasCpu = rondasCpu;
        this.resultado = resultado;
        this.fecha = fecha;
        this.turnos = turnos;
    }

    /**
     * Constructor para combates nuevos: Sin ID todavía y con la fecha de ahora
     */
    public Combate (int idJugador, int idLuchadorJugador, int idLuchadorCpu,
                     int rondasJugador, int rondasCpu, ResultadoCombate resultado,
                     List<Turno> turnos){
        this (0, idJugador, idLuchadorJugador, idLuchadorCpu, rondasJugador, rondasCpu, resultado, LocalDateTime.now().toString(), turnos);
    }

    public int getIdCombate() {
        return idCombate;
    }

    public void setIdCombate(int idCombate) {
        this.idCombate = idCombate;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public int getIdLuchadorJugador() {
        return idLuchadorJugador;
    }

    public int getIdLuchadorCpu() {
        return idLuchadorCpu;
    }

    public int getRondasJugador() {
        return rondasJugador;
    }

    public int getRondasCpu() {
        return rondasCpu;
    }

    public ResultadoCombate getResultado() {
        return resultado;
    }

    public String getFecha() {
        return fecha;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }
}
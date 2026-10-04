package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "luchador")
@XmlAccessorType(XmlAccessType.FIELD)
public class Luchador {
    private int idLuchador;
    private String nombre;
    private String pais;
    private String estilo;
    private String golpeEspecial;

    public Luchador() {} // Constructor vacío para JAXB

    public Luchador(int idLuchador, String nombre, String pais, String estilo, String golpeEspecial) {
        this.idLuchador = idLuchador;
        this.nombre = nombre;
        this.pais = pais;
        this.estilo = estilo;
        this.golpeEspecial = golpeEspecial;
    }

    public Luchador (String nombre, String pais, String estilo, String golpeEspecial) {
        this.nombre = nombre;
        this.pais = pais;
        this.estilo = estilo;
        this.golpeEspecial = golpeEspecial;
    }

    public int getIdLuchador() {
        return this.idLuchador;
    }

    public void setIdLuchador(int idLuchador) {
        this.idLuchador = idLuchador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return this.pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstilo() {
        return this.estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getGolpeEspecial() {
        return this.golpeEspecial;
    }

    public void setGolpeEspecial(String golpeEspecial) {
        this.golpeEspecial = golpeEspecial;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}
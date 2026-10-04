package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "logro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Logro {
    private int idLogro;
    private String nombre;
    private String descripcion;

    public Logro() {} // Constructor vacío para JAXB

    public Logro(int idLogro, String nombre, String descripcion) {
        this.idLogro = idLogro;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdLogro() {
        return idLogro;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String toString(){
        return this.nombre;
    }
}
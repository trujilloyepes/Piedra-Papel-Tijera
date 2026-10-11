package dataAccess.contenedores;

import dataAccess.XMLManager;
import model.Jugador;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

public class JugadoresXML {

    private final String RUTA_ARCHIVO = "data/jugadores.xml";

    @XmlRootElement(name = "jugadores")
    private static class Wrapper {
        private List<Jugador> lista = new ArrayList<>();

        @XmlElement(name = "jugador")
        public List<Jugador> getLista() { return lista; }
        public void setLista(List<Jugador> lista) { this.lista = lista; }
    }

    public List<Jugador> cargarJugadores() {
        Wrapper wrapper = XMLManager.readXML(new Wrapper(), RUTA_ARCHIVO);
        return wrapper.getLista() != null ? wrapper.getLista() : new ArrayList<>();
    }

    public boolean guardarJugadores(List<Jugador> jugadores) {
        Wrapper wrapper = new Wrapper();
        wrapper.setLista(jugadores);
        return XMLManager.writeXML(wrapper, RUTA_ARCHIVO);
    }
}
package dataAccess.contenedores;

import dataAccess.XMLManager;
import model.Luchador;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

public class LuchadoresXML {

    private final String RUTA_ARCHIVO = "data/luchadores.xml";

    @XmlRootElement(name = "luchadores")
    private static class Wrapper {
        private List<Luchador> lista = new ArrayList<>();

        @XmlElement(name = "luchador")
        public List<Luchador> getLista() { return lista; }
        public void setLista(List<Luchador> lista) { this.lista = lista; }
    }

    public List<Luchador> cargarLuchadores() {
        Wrapper wrapper = XMLManager.readXML(new Wrapper(), RUTA_ARCHIVO);
        return wrapper.getLista() != null ? wrapper.getLista() : new ArrayList<>();
    }

    public boolean guardarLuchadores(List<Luchador> luchadores) {
        Wrapper wrapper = new Wrapper();
        wrapper.setLista(luchadores);
        return XMLManager.writeXML(wrapper, RUTA_ARCHIVO);
    }
}
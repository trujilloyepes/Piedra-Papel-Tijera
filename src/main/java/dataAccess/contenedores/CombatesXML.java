package dataAccess.contenedores;

import dataAccess.XMLManager;
import model.Combate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

public class CombatesXML {

    private final String RUTA_ARCHIVO = "data/combates.xml";

    @XmlRootElement(name = "combates")
    private static class Wrapper {
        private List<Combate> lista = new ArrayList<>();

        @XmlElement(name = "combate")
        public List<Combate> getLista() { return lista; }
        public void setLista(List<Combate> lista) { this.lista = lista; }
    }

    public List<Combate> cargarCombates() {
        Wrapper wrapper = XMLManager.readXML(new Wrapper(), RUTA_ARCHIVO);
        return wrapper.getLista() != null ? wrapper.getLista() : new ArrayList<>();
    }

    public boolean guardarCombates(List<Combate> combates) {
        Wrapper wrapper = new Wrapper();
        wrapper.setLista(combates);
        return XMLManager.writeXML(wrapper, RUTA_ARCHIVO);
    }
}
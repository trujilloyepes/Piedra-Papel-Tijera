package dataAccess.contenedores;

import model.Luchador;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LuchadoresXML {

    private final String RUTA_ARCHIVO = "luchadores.xml";

    @XmlRootElement(name = "luchadores")
    private static class Wrapper {
        private List<Luchador> lista = new ArrayList<>();

        @XmlElement(name = "luchador")
        public List<Luchador> getLista() { return lista; }
        public void setLista(List<Luchador> lista) { this.lista = lista; }
    }

    public List<Luchador> cargarLuchadores() {
        File file = new File(RUTA_ARCHIVO);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class, Luchador.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Wrapper wrapper = (Wrapper) unmarshaller.unmarshal(file);
            return wrapper.getLista() != null ? wrapper.getLista() : new ArrayList<>();
        } catch (JAXBException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean guardarLuchadores(List<Luchador> luchadores) {
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class, Luchador.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Wrapper wrapper = new Wrapper();
            wrapper.setLista(luchadores);

            marshaller.marshal(wrapper, new File(RUTA_ARCHIVO));
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }
}

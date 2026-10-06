package dataAccess.contenedores;


import model.Combate;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CombatesXML {

    private final String RUTA_ARCHIVO = "combates.xml";

    // Clase contenedora interna necesaria para JAXB
    @XmlRootElement(name = "combates")
    private static class Wrapper {
        private List<Combate> lista = new ArrayList<>();

        @XmlElement(name = "combate")
        public List<Combate> getLista() { return lista; }
        public void setLista(List<Combate> lista) { this.lista = lista; }
    }

    /**
     * Lee el archivo XML y devuelve la lista de combates.
     */
    public List<Combate> cargarCombates() {
        File file = new File(RUTA_ARCHIVO);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class, Combate.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Wrapper wrapper = (Wrapper) unmarshaller.unmarshal(file);
            return wrapper.getLista() != null ? wrapper.getLista() : new ArrayList<>();
        } catch (JAXBException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Guarda la lista completa de combates en el archivo XML.
     */
    public boolean guardarCombates(List<Combate> combates) {
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class, Combate.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Wrapper wrapper = new Wrapper();
            wrapper.setLista(combates);

            marshaller.marshal(wrapper, new File(RUTA_ARCHIVO));
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }
}

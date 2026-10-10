package dataAccess.contenedores;

import model.Logro;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LogrosXML {

    private final String RUTA_ARCHIVO = "data/logros.xml";

    @XmlRootElement(name = "logros")
    private static class Wrapper {
        private List<Logro> lista = new ArrayList<>();

        @XmlElement(name = "logro")
        public List<Logro> getLista() { return lista; }
        public void setLista(List<Logro> lista) { this.lista = lista; }
    }

    public List<Logro> cargarLogros() {
        File file = new File(RUTA_ARCHIVO);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class, Logro.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Wrapper wrapper = (Wrapper) unmarshaller.unmarshal(file);
            return wrapper.getLista() != null ? wrapper.getLista() : new ArrayList<>();
        } catch (JAXBException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean guardarLogros(List<Logro> logros) {
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class, Logro.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Wrapper wrapper = new Wrapper();
            wrapper.setLista(logros);

            File file = new File(RUTA_ARCHIVO);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();   // crea data/ si no existe
            }
            marshaller.marshal(wrapper, file);
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }
}
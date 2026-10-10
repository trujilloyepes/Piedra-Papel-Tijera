package dataAccess.contenedores;

import model.Jugador;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
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
        File file = new File(RUTA_ARCHIVO);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class, Jugador.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Wrapper wrapper = (Wrapper) unmarshaller.unmarshal(file);
            return wrapper.getLista() != null ? wrapper.getLista() : new ArrayList<>();
        } catch (JAXBException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean guardarJugadores(List<Jugador> jugadores) {
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class, Jugador.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Wrapper wrapper = new Wrapper();
            wrapper.setLista(jugadores);

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

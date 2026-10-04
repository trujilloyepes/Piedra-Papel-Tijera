import model.Logro;
import model.Luchador;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Prueba {
    public static void main(String[] args) throws JAXBException {
        Luchador l = new Luchador(1, "Ryu", "Japón", "Shotokan", "Hadouken");
        Logro g = new Logro(1, "Perfect", "Gana una ronda sin recibir daño");

        Marshaller m = JAXBContext.newInstance(Luchador.class, Logro.class).createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(l, System.out);
        m.marshal(g, System.out);
    }
}
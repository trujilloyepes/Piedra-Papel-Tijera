package dataAccess;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLManager {

    /**
     * Función que permite escribir en un xml los datos de una clase para almacenarlos.
     * Si la carpeta del fichero no existe (por ejemplo data/), la crea.
     * @param t objeto concreto (normalmente un contenedor con una lista)
     * @param fileName Nombre del archivo a almacenar
     * @return Devuelve TRUE si se ha guardado y FALSE si no se ha podido
     * @param <T> Genérico de la clase del objeto a guardar
     */
    public static <T> boolean writeXML(T t, String fileName) {
        boolean isWritten = false;
        try {
            File file = new File(fileName);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            JAXBContext context = JAXBContext.newInstance(t.getClass());
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(t, file);
            isWritten = true;

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return isWritten;
    }

    /**
     * Función que permite cargar de un xml los datos de una clase.
     * Si el fichero todavía no existe (primera ejecución), devuelve el mismo objeto vacío que recibe.
     * @param t objeto vacío del tipo que se quiere leer, que sirve de plantilla
     * @param fileName Nombre del archivo a leer
     * @return Devuelve el objeto con los datos del XML, o el objeto recibido si el fichero no existe
     * @param <T> Genérico de la clase del objeto a leer
     */
    @SuppressWarnings("unchecked")
    public static <T> T readXML(T t, String fileName) {
        T result = t;
        File file = new File(fileName);

        if (file.exists()) {
            try {
                JAXBContext context = JAXBContext.newInstance(t.getClass());
                Unmarshaller unmarshaller = context.createUnmarshaller();
                result = (T) unmarshaller.unmarshal(file);

            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
package socket;

import java.util.HashSet;
import java.util.Set;
public class ControlParental {
	private static Set<String> malasPalabras = new HashSet<>();

    static {
       
        malasPalabras.add("gil");
        malasPalabras.add("wachin");
        malasPalabras.add("puto");
        malasPalabras.add("mierda");
        malasPalabras.add("pija");
    }

    //  verifica si el mensaje contiene alguna mala palabra
    public static boolean contieneMalaPalabra(String mensaje) {
        
        String mensajeLower = mensaje.toLowerCase();

        for (String malaPalabra : malasPalabras) {
            if (mensajeLower.contains(malaPalabra)) {
                return true;
            }
        }
        return false;
    }

    // censura las malas palabras
    public static String censurarMensaje(String mensaje) {
        String mensajeCensurado = mensaje;

        for (String malaPalabra : malasPalabras) {
            
            mensajeCensurado = mensajeCensurado.replaceAll("(?i)" + malaPalabra, "****");
        }
        return mensajeCensurado;
    }
}


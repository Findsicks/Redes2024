package GuiaEjercicios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EjercicioHTML {
	
	public static void modificarHTML() {
		File archivoHTML = new File("C:\\Users\\laure\\OneDrive\\Documents\\GitHub\\2024-Prog_Redes\\GuiaEjercicios//archivo.html");  // Reemplaza con la ruta correcta de tu archivo ejemplo.html

	    try {
	     
	        FileReader fr = new FileReader(archivoHTML);
	        BufferedReader br = new BufferedReader(fr);


	        File archivoModificado = new File("C:\\Users\\laure\\OneDrive\\Documents\\GitHub\\2024-Prog_Redes\\GuiaEjercicios//modificado.html"); 
	        FileWriter fw = new FileWriter(archivoModificado);
	        BufferedWriter bw = new BufferedWriter(fw);

	        String linea;
	        boolean contenidoModificado = false;

	      
            while ((linea = br.readLine()) != null) {
            
                if (linea.contains("<p class=\"lorem\">")) {
              
                    contenidoModificado = true;
                    continue;
                }
	           
	            bw.write(linea);
	            bw.newLine();
	        }

	        br.close();
	        fr.close();
	        bw.close();
	        fw.close();

	        if (contenidoModificado) {
	            System.out.println("Archivo modificado guardado correctamente.");
	        } else {
	            System.out.println("No se encontró 'lorem ipsum' en el archivo.");
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }	
	}
    
}

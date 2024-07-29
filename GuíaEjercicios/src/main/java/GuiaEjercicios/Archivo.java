package GuiaEjercicios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Archivo {
	private File archivo;
	private PrintStream ps;

	public Archivo(String rut, String name, String ext) {
		String ruta = rut; 
		String nombre = name; 
		String extension = ext; 

		try {
			System.setErr(new PrintStream(new FileOutputStream(new File("Errores.log")), true));
		} catch (FileNotFoundException e) {
			Logger.getLogger(Archivo.class.getName()).log(Level.WARNING, null, e);
		}
		archivo = new File(ruta.concat(nombre.concat(extension)));
	}
	public Archivo() {
		
	}
	public File getArchivo() {
		return this.archivo;
	}
	
	public void createFilePrintStream(File a, String escritura) {
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(a, true);
			ps = new PrintStream(fos); 

			ps.println(escritura);
			ps.flush();

		} catch (FileNotFoundException e) {
			Logger.getLogger(Archivo.class.getName()).log(Level.WARNING, null, e);
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					Logger.getLogger(Archivo.class.getName()).log(Level.WARNING, null, e);
				}
		}
	} 
	public void escribirNumero(File a, Double escritura) {
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(a, true);
			ps = new PrintStream(fos); 

			ps.println(escritura);
			ps.flush();

		} catch (FileNotFoundException e) {
			Logger.getLogger(Archivo.class.getName()).log(Level.WARNING, null, e);
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					Logger.getLogger(Archivo.class.getName()).log(Level.WARNING, null, e);
				}
		}
	}
}

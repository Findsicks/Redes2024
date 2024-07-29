package TP_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Archivo {
		private File archivo;
		private PrintStream ps;
		
		public Archivo() {archivo = new File("Inventario.dat");}
		public void createFilePrintStream(File a, String nombreP, double precioC, double precioV, int stock) {
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(a, true);
			ps = new PrintStream(fos); 

			ps.println(nombreP+";"+precioC+";"+precioV+";"+stock);
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
	public File getArchivo() {
		return this.archivo;
	}
		public String leerConReader(File a) {
		FileReader fr = null;
		BufferedReader br = null;
		String texto = "";
		try {
			fr = new FileReader(a);
			br = new BufferedReader(fr);

			String linea = "";
			while ((linea = br.readLine()) != null) {
				texto += linea.concat("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return texto;
	}
}

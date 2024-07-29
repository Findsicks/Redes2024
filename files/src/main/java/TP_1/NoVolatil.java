package TP_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NoVolatil {
	public static void resolucion(Volatil v) {
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(reader);

		PrintStream ps = new PrintStream(System.out);

		ArrayList<Integer> lista = new ArrayList(v.crearLista());

		File numeros = new File("numeros.txt");
		FileReader fr = null;
		{
			try {
				fr = new FileReader(numeros);
				BufferedReader br2 = new BufferedReader(fr);

				String linea = "";
				while ((linea = br2.readLine()) != null) {
					int numFile = Integer.parseInt(linea);
					for (int i = 0; i < lista.size() - 1; i++) {
						ps.println("i:"+i);
						int numE = (i + 1) >= 5 ? (lista.get(i) - 3) : (lista.get(i + 1) - 3);
						try {
							File resultado = new File("resultado.txt");
							FileWriter fw2 = new FileWriter(resultado, true);
							BufferedWriter bw2 = new BufferedWriter(fw2);
							ps.println("File:"+numFile);
							ps.println("Lista:"+numE);
							float res = numFile / numE;
							
							ps.println("Res:"+res);
							bw2.write(numFile + "/" + numE + "=" + res + "\n");
						} catch (java.lang.ArithmeticException e) {
							System.err.println("ERRORRRRR");
							File errores = new File("errores.txt");
							FileWriter fw = new FileWriter(errores, true);
							BufferedWriter bw = new BufferedWriter(fw);
							
							System.err.println("ERRORRRRR");
							bw.write("ERROR: Intentaste dividir por cero");
						}
					}
				}
			} catch (FileNotFoundException e1) {

				e1.printStackTrace();
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
		}
	}

}

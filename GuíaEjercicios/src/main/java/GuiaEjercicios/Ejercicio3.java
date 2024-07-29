package GuiaEjercicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Ejercicio3 {

	public static void menuEj() {
		PrintStream ps = new PrintStream( System.out );
		Reader lectura = new Reader();

		int lecturaN = 1;
		Archivo b = new Archivo("", "numeros", ".txt");
		 File impar = b.getArchivo();
		 
		while(lecturaN != 0) {
			ps.println("Bienvenido, seleccione un ejercicio \n" + 
						"1- Ultimo dato \n" +
						"2- Guardar todos los datos numericos \n" +
						"3- Guardar numeros pares \n" +
						"4- Mostrar datos del numero 3 \n" +
						"5- Utilizar datos del numero 3 y borrar multiplos de 3 \n" +
						"6- Primos \n"+
						"7- Eñe \n"+
						"0- Salir" );
			
			String leer = lectura.leer();
			lecturaN = Integer.parseInt(leer);
			
			switch(lecturaN) {
				case 1: guardarUltimo(); break;
				case 2: guardarNumericos(); break;
				case 3: guardarNumeroPares(b.getArchivo()); break;
				case 4: mostrarNumerosTXT(b.getArchivo()); break;
				case 5: impar = eliminarNumMulTres(b.getArchivo()); break;
				case 6: guardarNumerosPrimos(impar); break;
				case 7: manejarArchivoCaracteres(); break;
				case 0: break;
			}
			
		}
	}
	public static void guardarUltimo() {
		Archivo b = new Archivo("", "UltimoGuardado", ".txt");
		PrintStream ps = new PrintStream( System.out );
		Reader lectura = new Reader();
		ps.println("Ingrese algo: ");
		String lec = lectura.leer();
		
		b.getArchivo().delete();
		b.createFilePrintStream(b.getArchivo(), lec);
		
	}
	public static void guardarNumericos() {
		Archivo b = new Archivo("", "PuntoB", ".txt");
		PrintStream ps = new PrintStream( System.out );
		Reader lectura = new Reader();
		
		ps.println("coloque la palabra salir cuando termine: ");
		String lec = null;
		while(!(lec != null && lec.equalsIgnoreCase("salir"))) {
			ps.println("Ingrese algo: ");
			lec = lectura.leer();
			try {
				double numeroTransf = Double.parseDouble(lec);
				b.escribirNumero(b.getArchivo(), numeroTransf);
				
			}catch(NumberFormatException e) {
				ps.println("Numero no valido");
			}
			
		}
		
	}
	public static void guardarNumeroPares(File a) {
		Archivo b = new Archivo();
		PrintStream ps = new PrintStream( System.out );
		Reader lectura = new Reader();
		
		ps.println("Se procede a colocar numeros del 0 al 1000");
		for(double i = 0; i <= 1000; i++) {
			if(i%2 == 0) {
				b.escribirNumero(a, i);
			}
		}
	}
	public static void mostrarNumerosTXT(File a) {
		try {
			FileReader Fr = new FileReader(a);
			BufferedReader Br = new BufferedReader(Fr);
			PrintStream ps2 = new PrintStream( System.out );
			String linea;
			for(int i = 0; i <= 1002; i++) {
				if((linea = Br.readLine()) != null) {
					ps2.println(linea);
				}
			}
			Br.close();
			Fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static File eliminarNumMulTres(File a) {
	    PrintStream ps = new PrintStream(System.out);

	    Archivo copia = new Archivo("C:\\Users\\laure\\OneDrive\\Documents\\GitHub\\2024-Prog_Redes\\", "copianumeros", ".txt");

	    try {
	        FileReader fr = new FileReader(a);
	        BufferedReader br = new BufferedReader(fr);

	        String linea;
	        while ((linea = br.readLine()) != null) {
	            if (!(Double.parseDouble(linea) % 3.0 == 0)) {
	                copia.escribirNumero(copia.getArchivo(), Double.parseDouble(linea));
	            }
	        }

	        br.close();
	        fr.close();

	        if (a.exists()) {
	            a.delete();
	        }

	        if (copia.getArchivo().exists()) {
	            String nombreActual = copia.getArchivo().getName();
	            String nuevoNombre = a.getName(); 

	            File nuevoArchivo = new File(copia.getArchivo().getParent() + File.separator + nuevoNombre);

	            if (copia.getArchivo().renameTo(nuevoArchivo)) {
	                ps.println("Archivo renombrado con éxito a: " + nuevoArchivo.getAbsolutePath());
	                copia = new Archivo(nuevoArchivo.getParent() + File.separator, nuevoNombre, "");
	            } else {
	                ps.println("No se pudo renombrar el archivo.");
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    ps.println(copia.getArchivo().getAbsolutePath());
	    return copia.getArchivo();
	}

	
	public static void guardarNumerosPrimos(File a) {
	    PrintStream ps = new PrintStream(System.out);

	    if (!a.exists()) {
	        ps.println("El archivo " + a.getAbsolutePath() + " no existe.");
	        return;
	    }

	    Archivo archivoPrimos = new Archivo("C:\\Users\\laure\\OneDrive\\Documents\\GitHub\\2024-Prog_Redes\\", "primos", ".dat");

	    try (BufferedReader br = new BufferedReader(new FileReader(a))) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            double numero = Double.parseDouble(linea);
	            if (esPrimo(numero)) {
	                archivoPrimos.escribirNumero(archivoPrimos.getArchivo(), numero);
	                ps.println("Número primo encontrado y escrito: " + numero); 
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    ps.println("Archivo con números primos creado en: " + archivoPrimos.getArchivo().getAbsolutePath());
	}

	public static boolean esPrimo(double numero) {
	    if (numero <= 1) return false;
	    if (numero == 2) return true;
	    if (numero % 2 == 0) return false;
	    for (int i = 3; i <= Math.sqrt(numero); i += 2) {
	        if (numero % i == 0) return false;
	    }
	    return true;
	}
	
	public static void manejarArchivoCaracteres() {
	    PrintStream ps = new PrintStream(System.out);
	    Reader lectura = new Reader();
	    Archivo archivo = new Archivo("C:\\Users\\laure\\OneDrive\\Documents\\GitHub\\2024-Prog_Redes\\", "caracteres", ".dat");

	    try {
	        FileOutputStream fos = new FileOutputStream(archivo.getArchivo());
	        PrintStream archivoPs = new PrintStream(fos);

	        ps.println("Ingresa 10 palabras:");
	        for (int i = 0; i < 10; i++) {
	            String palabra = lectura.leer();
	            archivoPs.println(palabra);
	        }
	        archivoPs.close();
	        fos.close();

	        ps.println("Fichero original:");
	        mostrarContenidoArchivo(archivo.getArchivo());

	        FileReader fr = new FileReader(archivo.getArchivo());
	        BufferedReader br = new BufferedReader(fr);

	        StringBuilder contenidoModificado = new StringBuilder();
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            contenidoModificado.append(linea.replace("ñ", "nie")).append("\n");
	        }
	        br.close();
	        fr.close();

	   
	        FileOutputStream fosModificado = new FileOutputStream(archivo.getArchivo());
	        PrintStream archivoPsModificado = new PrintStream(fosModificado);
	        archivoPsModificado.print(contenidoModificado.toString());
	        archivoPsModificado.close();
	        fosModificado.close();
	        
	        ps.println("Fichero arreglado:");
	        mostrarContenidoArchivo(archivo.getArchivo());

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	 private static void mostrarContenidoArchivo(File archivo) {
	        try {
	            FileReader fr = new FileReader(archivo);
	            BufferedReader br = new BufferedReader(fr);
	            PrintStream ps = new PrintStream(System.out);

	            String linea;
	            while ((linea = br.readLine()) != null) {
	                ps.println(linea);
	            }

	            br.close();
	            fr.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}

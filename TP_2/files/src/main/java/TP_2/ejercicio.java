package TP_2;

import java.io.IOException;
import java.io.PrintStream;



public class ejercicio {


	public static String metodoLeerTexto() {
		
		String cadena = "";
		try {
			int Byte = -1;
			while(    (Byte = System.in.read())  != '\n'    ) 
			{
				if( Byte != 13 )
					cadena += (char)Byte;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return cadena;
	}
	
	public static void metodoVerificarNumero() {
		
		/*NO DEJA HACERLO CON MAS DE UNA CIFRA*/
		
		int hayPunto = 0;
		float numero = 0;
		PrintStream ps = new PrintStream(System.out);
		
		ps.println("Ingrese una cadena de caracteres (pruebe con numeros:P):");
		String cadena = metodoLeerTexto();
		
		if(cadena.contains(",")){
			cadena=cadena.replaceAll(",", ".");
		}
		if(cadena.endsWith(".")) {
			cadena = cadena.substring(0,cadena.length()-2);
		}
		
		
		try {
		if(cadena.contains(".")) {
			float numer = Float.parseFloat(cadena);
			ps.println("Es un float");
		}
		else {
		int num = Integer.parseInt(cadena);
		ps.println("Es un INT");
		}
		}catch( java.lang.NumberFormatException e) {
			ps.println("Es un String");
		}
	
	
}
	
}
package GuiaEjercicios;


import java.io.IOException;
import java.io.PrintStream;


public class Ejecutable {
	

	public static void main(String[] args) {
		
		PrintStream ps = new PrintStream( System.out );
		int Opcion;
		
		int lecturaN = 1;
		
		while(lecturaN != 0) {
			ps.println("Bienvenido, seleccione un ejercicio \n" + 
						"1- Ejercicio numero 1 \n" +
						"2- Ejercicio numero 2 \n" +
						"3- Ejercicio numero 3 \n" +
						"4- Ejercicio numero 4 \n" +
						"5- Ejercicio lorem \n" +
						"6- Otro ejercicio \n"+
						"0- Salir" );
			
			String leer = entradaDeDatos();
			lecturaN = Integer.parseInt(leer);
			
			Ejercicio1 ej1 = new Ejercicio1();
			Ejercicio2 ej2 = new Ejercicio2();
			Ejercicio3 ej3 = new Ejercicio3();		
			Ejercicios4 ej4 = new Ejercicios4();	
			Clima c = new Clima();
			EjercicioHTML EjHTML = new EjercicioHTML();	
			switch(lecturaN) {
			case(1):  ej1.menuEj();
			case(2):  ej2.menuEj();
			case(3):  ej3.menuEj();
			case(4):  ej4.menuEj();
			case(5): EjHTML.modificarHTML();
			case(6): c.menuEj();
			case(0):
				   break;
			}
	}
		
	}
	public static String entradaDeDatos() {
		
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
	
}
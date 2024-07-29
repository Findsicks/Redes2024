package TP_2;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class main {

	public static void main(String[] args) {
		
		menu();
		
	}
	
	static void menu() {
		
		boolean continuar = true;
		PrintStream ps = new PrintStream(System.out);
		
		while (continuar) {
		
			ps.println(Utils.ANSI_CYAN + "Opciones:");
			ps.println(Utils.ANSI_BLUE_BRIGHT+ "1- Verificar si tu texto es un numero");
			ps.println(Utils.ANSI_BLUE_BRIGHT + "2- Agregar Producto");
			ps.println(Utils.ANSI_BLUE_BRIGHT + "3- Mostrar datos en consola");
			ps.println(Utils.ANSI_BLUE_BRIGHT + "0- Salir");
			
			int opcion = main.leerOpcion();
			
			switch (opcion) {
			case 1:
				/*float numero = ejercicio.metodoVerificarNumero();*/
				ejercicio.metodoVerificarNumero();
				ps.println("");
				break;
			case 2:
				ps.println(Utils.ANSI_GREEN_BRIGHT + "Ingrese un nombre para el producto: ");
				String nombreProducto = ejercicio.metodoLeerTexto();
				ps.println(Utils.ANSI_GREEN_BRIGHT + "Ingrese el precio de compra del producto (arg): ");
				float precioC = Float.parseFloat(ejercicio.metodoLeerTexto());
				ps.println(Utils.ANSI_GREEN_BRIGHT + "Ingrese el precio de venta del producto (arg): ");
				float precioV = Float.parseFloat(ejercicio.metodoLeerTexto());
				ps.println(Utils.ANSI_GREEN_BRIGHT + "Ingrese el stock del producto: ");
				int stock = Integer.parseInt(ejercicio.metodoLeerTexto());
				
				Producto p = new Producto(nombreProducto, precioC, precioV, stock);
				
				Archivo archivoI = new Archivo();
				archivoI.createFilePrintStream(archivoI.getArchivo(), nombreProducto, precioC, precioV, stock);
				
				break;
			case 3:
				Archivo a = new Archivo();
				mostrarDatosConsola(a.getArchivo());
			case 0:
				ps.println("Saliendo del menú...");
				ps.println("");
				continuar = false;
				break;
			default:
				ps.println("Opción inválida. Por favor, ingrese una opción válida.");
				ps.println("");
				break;
			}
			
		
		}
		
	}
	
	static int leerOpcion() {
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PrintStream ps = new PrintStream(System.out);
		int opcion = 0;
		boolean entradaValida = false;
		
		while (!entradaValida) {
			ps.println("Ingrese la opción:");
			
			try {
				String input = reader.readLine();
				opcion = Integer.parseInt(input);
				if (opcion >= 0) {
					entradaValida = true;
				} else {
					ps.println("Error: Por favor, ingrese un número entero positivo.");
				}
			} catch (NumberFormatException | IOException e) {
				ps.println("Error: Por favor, ingrese un número entero válido.");
			}
		
		}
		
		return opcion;
	
	}
	public static void mostrarDatosConsola(File a) {
		PrintStream ps = new PrintStream(System.out);

		Archivo archivoInventario = new Archivo();
		File archivo = archivoInventario.getArchivo();
		String lectura = archivoInventario.leerConReader(archivo);
		 
		 ps.println(lectura);
		 
		 ArrayList<String> seccion = new ArrayList();
		 
		 String auxiliar = "";
		 for(char caracter : lectura.toCharArray()) { 
			 if(caracter == ';') { 
				 seccion.add(auxiliar);
				 
				 auxiliar = "";
			 }else {
				 auxiliar += caracter;
			 }
		 }
		 if(!auxiliar.isEmpty()) {
			 seccion.add(auxiliar);
		 }
		 
		 ps.println(Utils.ANSI_GREEN + "Nombre producto: " + Utils.ANSI_RESET + seccion.get(0));
		 ps.println(Utils.ANSI_GREEN + "Precio compra: "+ Utils.ANSI_RESET + seccion.get(1));
		 ps.println(Utils.ANSI_GREEN + "Precio Venta: " + Utils.ANSI_RESET + seccion.get(2));
		 ps.println(Utils.ANSI_GREEN + "Stock: " + Utils.ANSI_RESET + seccion.get(3));
	}
	
}


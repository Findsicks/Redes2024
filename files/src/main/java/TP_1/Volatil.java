package TP_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;


public class Volatil {
	
	
	public static ArrayList crearLista() {
		
		PrintStream ps = new PrintStream(System.out);
		
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(reader);
		
		ps.println("Escoge los numeros, por lo menos 2 ceros: ");
		
		ArrayList <Integer> listaNums = new ArrayList();
		
		for(int i = 0; i < 5; i++) {
			String numS = null;
			try {
				numS = br.readLine();
				int numInt = Integer.parseInt(numS);
				
				listaNums.add(numInt);
				
			}catch (IOException e ) {
				e.printStackTrace();
			}catch(NullPointerException e) {
				e.printStackTrace();
			}catch(NumberFormatException e) {
				if(numS.isEmpty()){
					ps.println("No se ingreso un num");
					numS = null;
				}else {
					ps.println("El valor no es valido");
				}continue;
			}
			
		}
		return listaNums;
		
	}
	public static boolean verificarLista(ArrayList<Integer> listaNums) {
		
		PrintStream ps = new PrintStream(System.out);
		
		int contador = 0;
		for(int i = 0; i < listaNums.size()-1; i++) {
			if(listaNums.get(i) == 0) {
				contador += 1;
			}

		}
		if(contador < 2) {
			ps.println("No se encontraron 2 ceros. Por favor rehacé la lista. ");
			listaNums.clear();
			return false;
			
		}else {                                                                                                                                                                                                                                                                          
			return true;
		}
		

		
	}

	
}
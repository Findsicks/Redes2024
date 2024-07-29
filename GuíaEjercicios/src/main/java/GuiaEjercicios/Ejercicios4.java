package GuiaEjercicios;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Ejercicios4 {
public static void menuEj() {
		
		PrintStream ps = new PrintStream( System.out );
		int Opcion;
		
		int lecturaN = 1;
		
		while(lecturaN != 0) {
			ps.println("Bienvenido, seleccione un ejercicio \n" + 
						"1- Serie de numeros \n" +
						"2- Escuela \n" +
						"3- Dias de la semana \n" +
						"4- Jugadores FC Barcelona \n" +
						"5- Dos colores \n" +
						"6- Ordenar puntaje \n"+
						"7- Provincias \n"+
						"0- Salir" );
			
            String leer = entradaDeDatos();
            lecturaN = Integer.parseInt(leer);

            switch (lecturaN) {
                case 1:
                    ArrayList<Integer> listaN = leerNumeros();
                    int suma = calcularSuma(listaN);
                    mostrarResultados(listaN, suma);
                    break;
                case 3: diasSemana(); break;
                case 4: jugadoresEj(); break;
                case 5: bolasColores(); break;
                case 6: ps.println("No lo hice"); break;
                case 7: provincias(); break;
                case 0:
                    ps.println("Saliendo chuchu");
                    break;
                default:
                    ps.println("No valido");
                    break;
            }
        }
    }

    public static ArrayList<Integer> leerNumeros() {
        PrintStream ps = new PrintStream(System.out);
        ArrayList<Integer> numeros = new ArrayList<>();

        ps.println("Ingrese numeros termina con -99:");
        int seleccion;
        do {
            String leer = entradaDeDatos();
            seleccion = Integer.parseInt(leer);
            if (seleccion != -99) {
                numeros.add(seleccion);
            }
        } while (seleccion != -99);

        return numeros;
    }

    public static int calcularSuma(ArrayList<Integer> numeros) {
        int suma = 0;
        for (int num : numeros) {
            suma += num;
        }
        return suma;
    }

    public static void mostrarResultados(ArrayList<Integer> numeros, int suma) {
        PrintStream ps = new PrintStream(System.out);

        int cantidadValores = numeros.size();
        double media = (double) suma / cantidadValores;
        int mayoresQM = 0;

        ps.println("Resultados:");
        ps.println("Cantidad de valores leídos: " + cantidadValores);
        ps.println("Suma de los valores: " + suma);
        ps.println("Media de los valores: " + media);

        for (int num : numeros) {
            if (num > media) {
                mayoresQM++;
            }
            ps.println("Valor: " + num);
        }

        ps.println("Cantidad de valores mayores que la media: " + mayoresQM);
    }

    public static void diasSemana() {
    	ArrayList<String> listDias = new ArrayList<>();
        PrintStream ps = new PrintStream(System.out);

        listDias.add("lunes");
        listDias.add("martes");
        listDias.add("miércoles");
        listDias.add("juernes");
        listDias.add("viernes");
        listDias.add("sabado");
        listDias.add("domingo");
    	ArrayList<String> listaDos = new ArrayList<>();

    	listDias.addAll(listaDos);
    	
    	ps.println("Posicion 3: " + listDias.get(2));
    	ps.println("Posicion 4: " + listDias.get(3));
    	ps.println("Primero: " + listDias.get(0));
    	ps.println("Ultimo: " +  listDias.get(listDias.size() - 1));
    	
    	listDias.remove("juernes");
    	ps.println(listDias);
    	
    	for( String dia : listDias ) {
    		ps.println(dia);
    	}
        boolean contieneL = listDias.contains("Lunes");
        ps.println("Boleano para ver si contiene o no lunes: " + contieneL);
        
        listDias.sort(null);
        ps.println("Ordenada: " + listDias);
    }
    public static void jugadoresEj() {
        PrintStream ps = new PrintStream(System.out);

        Set<String> jugadores = new HashSet<>();
        Set<String> jugadores2 = new HashSet<>();
        
    	jugadores.add("Jordi Alba");
    	jugadores.add("Pique");
    	jugadores.add("Busquets");
    	jugadores.add("Iniesta");
    	jugadores.add("Messi");
    	
    	jugadores2.add("Pique");
    	jugadores2.add("Busquets");
    	
    	for( String nombre: jugadores) {
    		ps.println("Jugador: " + nombre);
    	}

    	 Set<String> unionJugadores = new HashSet<>(jugadores);
         unionJugadores.addAll(jugadores2);
    	
    	boolean existeN = jugadores.contains("Neymar JR");
    	if(existeN == false) {
    		ps.println("Neymar 'Caidas' Jr no existe");
    	}else {
    		ps.println("Existe dentr ode la lista");
    	}
    	
    	 boolean agregado = jugadores.add("Pique");
         if (!agregado) {
             ps.println("Ya esta en la lista Pique");
         }
    }
    
    public static void bolasColores() {
        PrintStream ps = new PrintStream(System.out);

    	Random numeroRandom = new Random();
    	Set<Integer> numerosRojos = new HashSet<>();
    	
    	while(numerosRojos.size() < 6) {
    		int numeroR = numeroRandom.nextInt(33) + 1; //Recordatorio: el +1 es para que genere el rango desde 1 hasta 33
    		numerosRojos.add(numeroR);
    	}
    	int numAzul = numeroRandom.nextInt(16) + 1;
    	
    	ps.println("Rojo: " + numerosRojos);
    	ps.println("Azul: "+ numAzul);
    }
    
    public static void provincias() {
        PrintStream ps = new PrintStream(System.out);

    	String[] provincias = {"Provincia de Heilongjiang", "Provincia de Zhejiang", "Provincia de Jiangxi", "Provincia de Guangdong", "Provincia de Fujian"};
        String[] ciudades = {"Harbin", "Hangzhou", "Nanchang", "Guangzhou", "Fuzhou"};
        
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < provincias.length; i++) {
            map.put(provincias[i], ciudades[i]);
        }
        
        ps.println("Entry: ");
        for (Map.Entry<String, String> entrada : map.entrySet()) {
        	ps.println(entrada.getKey() + " = " + entrada.getValue());
        }
        ps.println("keySet: ");
        for (String clave : map.keySet()) {
            System.out.println(clave + " = " + map.get(clave));
        }
    }
    
    public static String entradaDeDatos() {
        String cadena = "";
        try {
            int Byte = -1;
            while ((Byte = System.in.read()) != '\n') {
                if (Byte != 13) {
                    cadena += (char) Byte;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cadena.trim(); 
    }
}
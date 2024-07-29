package GuiaEjercicios;

import java.io.IOException;
import java.io.PrintStream;

public class Ejercicio1 {

	public static void menuEj() {
		
		PrintStream ps = new PrintStream( System.out );
		int Opcion;
		
		int lecturaN = 1;
		
		while(lecturaN != 0) {
			ps.println("Bienvenido, seleccione un ejercicio \n" + 
						"1- Calculo de sueldo \n" +
						"2- Triangulo angulos \n" +
						"3- Calculo de superficie \n" +
						"4- Pasaje de grados \n" +
						"5- Horas a segundos \n" +
						"6- Incremento y descuento \n"+
						"7- Signo zoodiacal \n"+
						"0- Salir" );
			
			String leer = entradaDeDatos();
			lecturaN = Integer.parseInt(leer);
			
			
			
				
			switch(lecturaN) {
			case(1): CalculadoraSueldo();
			 break;
			case(2): calculadTriangulo(); break;
			case(3): calcularPerimetro(); break;
			case(4): ps.println(calcularGrados());break;
			case(5): calculoTiempo();break;
			case(6): planesPago();break;
			case(7): ps.println(signosZoo());break;
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
	
	public static void CalculadoraSueldo() {
		PrintStream ps = new PrintStream( System.out );
		
		ps.println("Ingrese su sueldo por hora:");
		String leer = entradaDeDatos();
		double sueldo = Double.parseDouble(leer);
		
		ps.println("Ingrese sus horas trabajadas:");
		leer = entradaDeDatos();
		int horas = Integer.parseInt(leer);
		
		ps.println("Sueldo por sus horas trabajadas:"  + sueldo * horas);
	}
	
	public static void calculadTriangulo() {
PrintStream ps = new PrintStream( System.out );
		
		ps.println("Ingrese el primer angulo:");
		String leer = entradaDeDatos();
		int angulo = Integer.parseInt(leer);
		
		ps.println("Ingrese el segundo angulo:");
		leer = entradaDeDatos();
		int angulo2 = Integer.parseInt(leer);
		
		int angulo3 = 180 - (angulo + angulo2);
		
		
		ps.println("El angulo restante es de:" + angulo3);
	}
	public static void calcularPerimetro() {
		PrintStream ps = new PrintStream( System.out );
		
		ps.println("Ingrese un lado del cuadrado:");
		String leer = entradaDeDatos();
		Double superficie = Double.parseDouble(leer);
		double raizCuadrada = Math.sqrt(superficie);
		
		double perimetro = raizCuadrada*4;
		
		ps.println("El perimetro del cuadrado es: " + perimetro);
	}
	
	public static double calcularGrados() {
		PrintStream ps = new PrintStream( System.out );
		ps.println("Coloca los grados fahrenheit: ");
		
		String leer = entradaDeDatos();
		Double grados = Double.parseDouble(leer);
		
		Double gradosEnCelcius = ((grados - 32) * 0.5555555555555556);
		
		return gradosEnCelcius; 
	}
	public static void calculoTiempo() {
		PrintStream ps = new PrintStream( System.out );
		ps.println("Ingrese los segundos: ");
		String leer = entradaDeDatos();
		int segundos = Integer.parseInt(leer);
		
		int dias = segundos / (24 * 3600);
		int remanente = segundos % (24 * 3600);
		
		int horas = remanente / 3600;
		remanente = remanente % 3600;
		
		int minutos = remanente / 60;
		int segundosT = remanente % 60;
		
		ps.println("Dias: " + dias + " Horas: " + horas + " Minutos: " + minutos + " Segundos: " + segundosT);
				
	}
	public static void planesPago() {
		PrintStream ps = new PrintStream( System.out );
		ps.println("Coloca el valor del producto: ");
		
		String leer = entradaDeDatos();
		Double valor = Double.parseDouble(leer);
		
		ps.println("Escoge un plan: ");
		ps.println("1- Plan 1");
		ps.println("2- Plan 2");
		ps.println("3- Plan 3");
		ps.println("4- Plan 4");
		String plan = entradaDeDatos();
		int planInt = Integer.parseInt(plan);
		switch(planInt) {
			case 1: valor = valor - (valor * 0.10);
			ps.println("Total a pagar: " + valor);
			case 2: valor = valor + (valor * 0.10);
			
			double cuota = valor * 0.25;
			ps.println("Total a pagar: " + valor);
			ps.println("Dos cuotas de: " + cuota);
			
			case 3: valor = valor + (valor * 0.15);
			
			double cuota3 = (valor * 0.75)/5;
			ps.println("Total a pagar: " + valor);
			ps.println("Cinco cuotas de: " + cuota3);
			
			case 4: valor = valor + (valor * 0.25);
			
			double cuota4_1 = (valor * 0.60) / 4;
			double cuota4_2 = (valor * 0.40) / 4;
			ps.println("Total a pagar: " + valor);
			ps.println("Cuatro primeras cuotas de: " + cuota4_1);
			ps.println("Cuatro ultimas cuotas de: " + cuota4_2);
		}
	}
	public static String signosZoo() {
		PrintStream ps = new PrintStream( System.out );
		
		ps.println("Escoge el mes con numero, por ejemplo 1 = Enero: ");
		String leerMes = entradaDeDatos();
		int valorMes = Integer.parseInt(leerMes);
		
		ps.println("Introduce el dia de tu cumpleaños: ");
		String leer = entradaDeDatos();
		int dia = Integer.parseInt(leer);
		
		String signoZodiacal = null;
		
		switch(valorMes) {
        case 1: 
            if (dia <= 19) {
                signoZodiacal = "Capricornio";
            } else {
                signoZodiacal = "Acuario";
            }
            break;
        case 2:
            if (dia <= 18) {
                signoZodiacal = "Acuario";
            } else {
                signoZodiacal = "Piscis";
            }
            break;
        case 3:
            if (dia <= 20) {
                signoZodiacal = "Piscis";
            } else {
                signoZodiacal = "Aries";
            }
            break;
        case 4:
            if (dia <= 19) {
                signoZodiacal = "Aries";
            } else {
                signoZodiacal = "Tauro";
            }
            break;
        case 5:
            if (dia <= 20) {
                signoZodiacal = "Tauro";
            } else {
                signoZodiacal = "Géminis";
            }
            break;
        case 6:
            if (dia <= 20) {
                signoZodiacal = "Géminis";
            } else {
                signoZodiacal = "Cáncer";
            }
            break;
        case 7:
            if (dia <= 22) {
                signoZodiacal = "Cáncer";
            } else {
                signoZodiacal = "Leo";
            }
            break;
        case 8:
            if (dia <= 22) {
                signoZodiacal = "Leo";
            } else {
                signoZodiacal = "Virgo";
            }
            break;
        case 9:
            if (dia <= 22) {
                signoZodiacal = "Virgo";
            } else {
                signoZodiacal = "Libra";
            }
            break;
        case 10:
            if (dia <= 22) {
                signoZodiacal = "Libra";
            } else {
                signoZodiacal = "Escorpio";
            }
            break;
        case 11:
            if (dia <= 21) {
                signoZodiacal = "Escorpio";
            } else {
                signoZodiacal = "Sagitario";
            }
            break;
        case 12:
            if (dia <= 21) {
                signoZodiacal = "Sagitario";
            } else {
                signoZodiacal = "Capricornio";
            }
            break;
        default:
            signoZodiacal = "Mes no válido";
            break;
		
		}
		return signoZodiacal;
		
	}
}

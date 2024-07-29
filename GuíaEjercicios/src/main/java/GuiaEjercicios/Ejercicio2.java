package GuiaEjercicios;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

public class Ejercicio2 {

	public static void menuEj() {
		PrintStream ps = new PrintStream( System.out );
		Reader lectura = new Reader();

		int lecturaN = 1;

		while(lecturaN != 0) {
			ps.println("Bienvenido, seleccione un ejercicio \n" + 
						"1- Apellidos alfabeticamente \n" +
						"2- Dar numeros y decir cual es menor \n" +
						"3- Si es par o impar \n" +
						"4- Divisibles entre si \n" +
						"5- Signo de zoodiaco \n" +
						"6- Apellidos largos \n"+
						"7- Tabla de multiplicar \n"+
						"8- Si es primo \n"+
						"0- Salir" );
			
			String leer = lectura.leer();
			lecturaN = Integer.parseInt(leer);
			
			switch(lecturaN) {
				case 1: apellidosAlfabetico();break;
				case 2: ps.println("El menor: " + numerosReales());break;
				case 3: numeroPar();break;
				case 4: mayorDivisible();break;
				case 5: ps.println(signoZoo());break;
				case 6: ps.println("Apellido mas largo: " + apellidoMasLargo());break;
				case 7: MultiplicarNum();break;
				case 8: Primo();break;
				case 0: break;
			}
			
		}
		
	}
	

	public static void apellidosAlfabetico() {
		PrintStream ps = new PrintStream( System.out );
		
		ArrayList<String> apellidos = new ArrayList<>();
		Reader lectura = new Reader();
		
		for(int i = 0; i < 3; i++) {
			ps.println("Ingrese un apellido: ");
			String apellido = lectura.leer();
			apellidos.add(apellido);
		}
		
		Collections.sort(apellidos);
		ps.println("Apellidos ordenados: ");
	        for (String ap : apellidos) {
	            ps.println(ap);
	        }
	}
	public static Double numerosReales() {
		PrintStream ps = new PrintStream( System.out );
		Reader lectura = new Reader();
		
		ps.println("Ingrese el numero 1:");
		String leer = lectura.leer();
		double num1 = Double.parseDouble(leer);
		ps.println("Ingrese el numero 2:");
		leer = lectura.leer();
		double num2 = Double.parseDouble(leer);
		ps.println("Ingrese el numero 3:");
		leer = lectura.leer();
		double num3 = Double.parseDouble(leer);
		ps.println("Ingrese el numero 4:");
		leer = lectura.leer();
		double num4 = Double.parseDouble(leer);
		
		double menor = num1;
		
		if (num2 < menor) {
            menor = num2;
        }
        if (num3 < menor) {
            menor = num3;
        }
        if (num4 < menor) {
            menor = num4;
        }
        
         return menor;
	}
	
	public static void numeroPar() {
		PrintStream ps = new PrintStream( System.out );
		Reader lectura = new Reader();
		
		ps.println("Ingrese el numero:");
		String leer = lectura.leer();
		double numero = Integer.parseInt(leer);
		
		if(numero%2 == 0) {
			ps.println("Es par");
		}else {
			ps.println("No lo es par");
		}
		
	}
	public static void mayorDivisible() {
		PrintStream ps = new PrintStream( System.out );
		Reader lectura = new Reader();
		
		ps.println("Ingrese el numero 1:");
		String leer = lectura.leer();
		double num1 = Double.parseDouble(leer);
		ps.println("Ingrese el numero 2:");
		leer = lectura.leer();
		double num2 = Double.parseDouble(leer);
		double menor = 0.0;
		double mayor = 0.0;
		
		if(num1 < num2 ) {
			mayor = num2;
			menor = num1;
		}else {
			mayor = num1;
			menor = num2;
		}

		if(mayor % menor == 0) {
			ps.println("Es divisible");
		}else {
			ps.println("No es divisible");
		}
		
	}
	public static String signoZoo() {
		PrintStream ps = new PrintStream( System.out );
		Reader lectura = new Reader();
		
		ps.println("Escoge el mes con numero, por ejemplo 1 = Enero: ");
		String leerMes = lectura.leer();
		int valorMes = Integer.parseInt(leerMes);
		
		ps.println("Introduce el dia de tu cumpleaños: ");
		String leer = lectura.leer();
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
	public static String apellidoMasLargo() {
		PrintStream ps = new PrintStream( System.out );
		Reader lectura = new Reader();
		String mayor = null;
		
		String apellido1 = null;
		String apellido2 = null;
		
		for(int i = 0; i < 2; i++) {
			ps.println("Ingrese el apellido: " + (i + 1));
			if(i == 0) {
				apellido1 = lectura.leer();
			}else{
				apellido2 = lectura.leer();
			}
		}
		
		int ap1 = apellido1.length();
		int ap2 = apellido2.length();
		
		if(ap1 < ap2) {
			mayor = apellido2;
		}else if(ap2 < ap1) {
			mayor = apellido1;
		}else if(ap2 == ap1) {
			mayor = "No hay uno mayor que otro";
		}
		
		return mayor;
	
	}
	public static void MultiplicarNum() {
		PrintStream ps = new PrintStream( System.out );
		Reader lectura = new Reader();
		
		ps.println("Coloca un numero: ");
		String numero = lectura.leer();
		int numeroMod = Integer.parseInt(numero);
		
		for(int i = 0; i < 10; i++) {
			ps.println("Numero modificado por " + (i + 1) + ":" + numeroMod * (i + 1));
		}
	}
	public static void Primo() {
		PrintStream ps = new PrintStream( System.out );
		Reader lectura = new Reader();
		
		ps.println("Ingrese el numero:");
		String leer = lectura.leer();
		double num1 = Double.parseDouble(leer);
		
		int contadorDivi = 0;
		
		for(int i = 0; i <= num1; i++) {
			if(num1%1 == 0) {
				contadorDivi++;
			}
		}
		if( contadorDivi == 2) {
			ps.println("Es primo");
		}else {
			ps.println("No es primo");
		}
		
	}
}

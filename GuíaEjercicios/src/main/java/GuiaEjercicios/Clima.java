package GuiaEjercicios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Clima {
    static Archivo archivo;

    public static void menuEj() {
        PrintStream ps = new PrintStream(System.out);
        int lecturaN = 0;

        while (true) {
            ps.println("Decidir: ");
            ps.println("1. Cargar datos");
            ps.println("2. Mostrar datos");
            ps.println("3. Borrar un registro");
            ps.println("0. Salir");

            String leer = entradaDeDatos();
            lecturaN = Integer.parseInt(leer);

            switch (lecturaN) {
                case 1:
                    cargarDatoC();
                    break;
                case 2:
                    ArrayList<String> datos = leerYProcesarArchivo(archivo.getArchivo());
                    mostrarDatos(datos);
                    break;
                case 3:
                    
                    break;
                case 0:
                    return;
                default:
                    ps.println("Opción no válida");
                    break;
            }
        }
    }

    public static void cargarDatoC() {
        PrintStream ps = new PrintStream(System.out);
        Reader reader = new Reader();
        String[] dato = {"El Dia", "El Mes", "El Tipo de cielo(Soleado/Nublado)", "La Probabilidad Lluvia", "Los Grados"};
        ArrayList<String> datosEscritos = new ArrayList<>();

        int contador = 0;
        while (contador < 5) {
            ps.println("Escribir " + dato[contador]);
            String lec = reader.leer();
            datosEscritos.add(lec);
            contador++;
        }

        String concatenadoSeparado = "";
        for (String elem : datosEscritos) {
            concatenadoSeparado += elem + ";";
        }
        ps.println(concatenadoSeparado);
        archivo.createFilePrintStream(archivo.getArchivo(), concatenadoSeparado);
    }

    public static ArrayList<String> leerYProcesarArchivo(File file) {
        ArrayList<String> datosProcesados = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] separadoTodo = linea.split(";");
                for (String parte : separadoTodo) {
                    datosProcesados.add(parte);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return datosProcesados;
    }

    public static void mostrarDatos(ArrayList<String> datos) {
        PrintStream ps = new PrintStream(System.out);
        String[] d = {"El Dia", "El Mes", "El Tipo de cielo(Soleado/Nublado)", "La Probabilidad Lluvia", "Los Grados"};

        int contador = 0;
        for (String dato : datos) {
            ps.println(d[contador] + ":" + dato );
            contador++;

            if (contador == d.length) {
                contador = 0;
                ps.println();  
            }
        }
    }

    public static void guardarDatos(File file, ArrayList<String> datos) {
        try (FileWriter writer = new FileWriter(file)) {
            for (String dato : datos) {
                writer.write(dato + ";");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String entradaDeDatos() {
        StringBuilder cadena = new StringBuilder();
        try {
            int Byte;
            while ((Byte = System.in.read()) != '\n') {
                if (Byte != 13)
                    cadena.append((char) Byte);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cadena.toString();
    }
}


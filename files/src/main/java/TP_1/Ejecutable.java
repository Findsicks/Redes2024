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


public class Ejecutable {

	public static void main(String[] args) {
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(reader);
		
		PrintStream ps = new PrintStream(System.out);
		Volatil v = new Volatil();
		NoVolatil nv = new NoVolatil();
		nv.resolucion(v);
		}
	}

			

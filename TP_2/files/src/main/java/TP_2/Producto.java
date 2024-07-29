package TP_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producto {
	String nombreProducto;
	float precioCompra;
	float precioVenta;
	int stock;
	private PrintStream ps;
	public Producto(String nombreP, float precioC, float precioV, int s) {
		nombreP = this.nombreProducto;
		precioC = this.precioCompra;
		precioV = this.precioVenta;
		s = this.stock;
	}

}

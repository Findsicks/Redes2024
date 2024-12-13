package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread {

	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_MAGENTA = "\u0033[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_RESET = "\u001B[0m";

	PrintStream ps;
    public static Map<String, ClienteCli> ClientesConectados = new HashMap<>();

	ServerSocket serverSock;
	Socket sockCli;
	DataInputStream dis;
	DataOutputStream dos;
	int puerto = 7777;

	public Servidor() {
		try {
			ps = new PrintStream(System.out);
			dis = null;
			dos = null;

	
			serverSock = new ServerSocket(puerto);
			
			// verificacion de clientes conectados
			Thread verificarLista = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						for (ClienteCli cli : ClientesConectados.values()) {
							if (!cli.getSock().isConnected() || !cli.isConected()) {
								ClientesConectados.remove(cli.getNickName());
								cli.notificarClientes(false);
							}
							if(cli.checkBan()) {
								if(cli.isBanned()) {
									ps.println(cli.getNickName()+" esta baneado");
								}
							}
						}
						//esto es precario
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});

		} catch (IOException ex) {
			Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				ps.println("Esperando conexion de un clinete...\n");
				sockCli = serverSock.accept();

				ps.println(Servidor.ANSI_CYAN + "Cliente Conectado: " + sockCli.getInetAddress().getHostAddress()
						+ Servidor.ANSI_RESET);

				dis = new DataInputStream(sockCli.getInputStream());
				dos = new DataOutputStream(sockCli.getOutputStream());

				ps.println(
						Servidor.ANSI_CYAN + "Creando un cliente... esperado identificacion..." + Servidor.ANSI_RESET);
				String nickName = dis.readUTF();
				
				if (ClientesConectados.containsKey(nickName)) {
                    dos.writeUTF(Servidor.ANSI_RED + "Ya estan usando el nombre"
                            + Servidor.ANSI_RESET);
                  
                    continue; 
                }else {
				
				ClienteCli cli = new ClienteCli(sockCli, nickName, dis, dos);
				Servidor.ClientesConectados.put(cli.getNickName(), cli);

				ps.println(Servidor.ANSI_RED + "El cliente " + cli.getNickName() + " accedio al servidor.\n"
						+ Servidor.ANSI_RESET);

				cli.getHilo().start();
				cli.notificarClientes(true);
                }
			} catch (IOException ex) {
				Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
			} // catch
		} // while
	}// run
	
	public void enviarMensajeM(String mensaje) throws IOException {
		for (ClienteCli cli : Servidor.ClientesConectados.values()) {
	        if (cli.isConected()) {

	                cli.dosCliente.writeUTF(Servidor.ANSI_YELLOW + "Mensaje del servidor: " +  Servidor.ANSI_RESET + mensaje);

	        }
	    }
	}
	
	private void enviarprivado(String nombreC, String mensaje) throws IOException {
		String nombre = nombreC.replace("#", "");
	    ClienteCli cliente = Servidor.ClientesConectados.get(nombre);
	    if (cliente.isConected()) {
	          cliente.dosCliente.writeUTF(Servidor.ANSI_PURPLE + "MENSAJE DEL SERVIDOR PIBE: " + Servidor.ANSI_RESET + mensaje);
	    } else {
	        System.out.println("El cliente no está conectado");
	    }
	}

	
	public void escucharConsola() throws IOException {
        Scanner scanner = new Scanner(System.in);

	    while (true) {
	        System.out.print("escribi: ");
	        String mensaje = scanner.nextLine();

	        if (mensaje.trim().isEmpty()) {
	            continue;
	        }

	        if (mensaje.startsWith("! ")) {
	            enviarMensajeM(mensaje.substring(2));
	        }

	        else if(mensaje.startsWith("#")) {
	        	String[] partes = mensaje.split(" ", 2);
	            if (partes.length == 2) {
	            	enviarprivado(partes[0], partes[1]);
	            }
	        }
	    }
	}


}// class

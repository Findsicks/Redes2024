package socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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
	static Map<String, ClienteCli> ClientesConectados;

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
			ClientesConectados = new HashMap<>();

			serverSock = new ServerSocket(puerto);
			
			// verificacion de clientes conectados
			Thread verificarLista = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						for (Map.Entry<String, ClienteCli> entry : ClientesConectados.entrySet()) {
							ClienteCli cli = entry.getValue();
							if (!cli.getSock().isConnected() || !cli.isConected()) {
								ClientesConectados.remove(cli);
								cli.notificarClientes(false);
							}
							if (cli.estaBaneado()) {
                                cli.tiempoRestanteBan--; // disminuyo el tiempo restante
                                if (cli.getTiempoRestanteBan() <= 0) {
                                    cli.desbanearCliente(); // desbaneo al cliente si el tiempo se ha agotado
                                }
                            }
						}
						//esto es precario
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
				}
			});
            verificarLista.start();
            Thread consolaServidor = new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));
                    while (true) {
                        try {
                            ps.print("Servidor: ");
                            String mensaje = consola.readLine().trim();

                            if (mensaje.startsWith("#")) {
                                // msg dirigido a un cliente en particular (#cliente mensaje)
                                String[] partes = mensaje.split(" ", 2);
                                String destinatario = partes[0].substring(1); // remueve el '#'
                                String mensajePrivado = partes.length > 1 ? partes[1] : "";

                                ClienteCli cli = ClientesConectados.get(destinatario);
                                if (cli != null) {
                                    cli.dosCliente.writeUTF(ANSI_PURPLE + "[Servidor]: " + ANSI_RESET + mensajePrivado);
                                    ps.println("Mensaje enviado a " + destinatario);
                                } else {
                                    ps.println(ANSI_RED + "Cliente " + destinatario + " no disponible." + ANSI_RESET);
                                }
                            } else {
                                // msg masivo
                                for (ClienteCli cli : ClientesConectados.values()) {
                                    cli.dosCliente.writeUTF(ANSI_BLUE + "[Servidor]: " + ANSI_RESET + mensaje);
                                }
                                ps.println(ANSI_GREEN + "Mensaje masivo enviado a todos los clientes." + ANSI_RESET);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            consolaServidor.start();
		} catch (IOException ex) {
			Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	
	@Override
	public void run() {
		while (true) {
			try {
				ps.println("Esperando conexion de un cliente...\n");
				sockCli = serverSock.accept();

				ps.println(Servidor.ANSI_CYAN + "Cliente Conectado: " + sockCli.getInetAddress().getHostAddress()
						+ Servidor.ANSI_RESET);

				dis = new DataInputStream(sockCli.getInputStream());
				dos = new DataOutputStream(sockCli.getOutputStream());

				ps.println(
						Servidor.ANSI_CYAN + "Creando un cliente... esperado identificacion..." + Servidor.ANSI_RESET);
				String nickName = dis.readUTF();

				
                if (ClientesConectados.containsKey(nickName)) {
                    ps.println(ANSI_RED + "Nickname duplicado: " + nickName + ANSI_RESET);
                    dos.writeUTF(ANSI_RED + "El nickname '" + nickName + "' ya está en uso. Conexión rechazada." + ANSI_RESET);
                    sockCli.close(); 
                    continue; 
                }

             
                ClienteCli cli = new ClienteCli(sockCli, nickName, dis, dos);
                ClientesConectados.put(nickName, cli); 

				ps.println(Servidor.ANSI_RED + "El cliente " + cli.getNickName() + " accedio al servidor.\n"
						+ Servidor.ANSI_RESET);

				cli.getHilo().start();
				cli.notificarClientes(true);
			} catch (IOException ex) {
				Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
			} // catch
		} // while
	}// run

}// class

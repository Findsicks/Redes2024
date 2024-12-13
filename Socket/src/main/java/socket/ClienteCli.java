package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

public class ClienteCli implements Runnable {
	
    String nickName = "";
    Socket sock;
    Thread hilo;
    
	final DataInputStream disCliente;
    final DataOutputStream dosCliente;
    boolean isConected;
    private boolean isBanned;  
    private long banTime; 
    private long banDuration;  
    private long banStartTime;  
    PrintStream ps;
   
    static final List<String> listaMalasPalabras = (List<String>) Arrays.asList("Puta", "Tonto", "Bobo", "Hdp", "Hinchaderacing");
    
    public ClienteCli(Socket sock, String nick, DataInputStream in, DataOutputStream out) {
        this.nickName = nick;
        this.sock = sock;
        this.disCliente = in;
        this.dosCliente = out;    
        
        ps = new PrintStream(System.out);
        this.isConected = true;
        this.isBanned = false;
        this.hilo = new Thread(this, nickName);
    }
    
    
	@Override
	public void run() {
		String msgRecibido = "";
		String destino = "";
		 String mensajeDes = "";
		
		while( this.sock.isConnected() && this.isConected )
		{
			
			try {
				//identificaos el mensaje
				msgRecibido = this.disCliente.readUTF().trim();
				String claveEn = "1828419284912342";
				byte[] iv = Cifrado.generarIV();
				String[] partes = msgRecibido.split(":");
                byte[] ivRecibido = Base64.getDecoder().decode(partes[0]);
                String mensajeCifrado = partes[1];

                try {
                    mensajeDes = Cifrado.decriptar(claveEn, ivRecibido, mensajeCifrado);
                } catch (Exception e) {
                    ps.println("Error al descifrar el mensaje: " + e.getMessage());
                }						
				//identificar el destinaratio
				//   destino # mensaje a enviar
				// Franco# Todo bien?
				if( mensajeDes.contains("#") )
				{
					StringTokenizer token = new StringTokenizer(mensajeDes,"#");
					destino = token.nextToken().trim().toLowerCase();
					mensajeDes = token.nextToken().trim();
					
				}else {
					destino = "";
				}
				
				ps.println("\n"
						+ Servidor.ANSI_PURPLE
						+ "El cliente " 
						+ Servidor.ANSI_GREEN 
						+ this.nickName 
						+ Servidor.ANSI_PURPLE
						+ " envia: "
						+ Servidor.ANSI_YELLOW
						+ msgRecibido + "\n\t"
						+ Servidor.ANSI_PURPLE
						+ " al cliente =>"
						+ Servidor.ANSI_CYAN
						+ (destino.equalsIgnoreCase("") ? " Todos" : " ".concat(destino.toUpperCase()))
						+ "\n"
						+ Servidor.ANSI_RESET
					);
				
				//filtro de comandos
				//  mensaje = /salir
				//  /conectados para ver los conectados
				
				
				
				//enviar mensaje
				for( ClienteCli cli : Servidor.ClientesConectados.values())
				{
					//si el mensaje a enviar esta vacio
					if(mensajeDes.equalsIgnoreCase("")) {
						break;
					}
					desbanear();
					
					
					if (mensajeDes.equalsIgnoreCase("/salir")) {
					    this.isConected = false;  
					    try {
					        this.dosCliente.writeUTF(Servidor.ANSI_RED + "Has salido del chat." + Servidor.ANSI_RESET);
					      //  this.sock.close();  // cerrar el socket del cliente (Lo comento por que me rompe todo preguntar despues)
					        this.isConected = false; //le agrego esto para que no pueda escribir, chau privilegios, puede seguir viendo pero no escribir
					    } catch (IOException e) {
					        e.printStackTrace();
					    }

					    notificarClientes(false);
					    // Servidor.ClientesConectados.remove(this);  la comento por que si lo remuevo no puedo comprobar que esta conectado, a lo mejor hacer una sublista de desconectados
					    break;
					}
					if (mensajeDes.equalsIgnoreCase("/conectados")) {					    
					    try {
					        this.dosCliente.writeUTF(Servidor.ANSI_RED + "Lista de personas conectadas" + Servidor.ANSI_RESET);
					      
					        for (String nombre : Servidor.ClientesConectados.keySet()) {
								this.dosCliente.writeUTF(Servidor.ANSI_BLUE + nombre + Servidor.ANSI_RESET);
					        }
					        
					    	/*for(int i = 0; i<Servidor.ClientesConectados.values().size(); i++) {
								this.dosCliente.writeUTF(Servidor.ANSI_BLUE + Servidor.ClientesConectados.keySet() + Servidor.ANSI_RESET);
							}for viejo lo guardo por las duidas */ 
					        
					    } catch (IOException e) {
					        e.printStackTrace();
					    }
					    break;
					}
					if(cli.getNickName().equalsIgnoreCase(destino) && this.isConected && !this.isBanned  && !listaMalasPalabras.contains(mensajeDes))
					{
						if(cli.isConected) {
							cli.dosCliente.writeUTF(Servidor.ANSI_PURPLE 
									+this.nickName
									+ ":"
									+Servidor.ANSI_RESET
									+ mensajeDes
								);
						}else {
							this.dosCliente.writeUTF(Servidor.ANSI_RED + 
									"Cliente no disponible F en el chat" + Servidor.ANSI_RESET);
						}
						break;
					}else if(destino.equalsIgnoreCase("") && 
							this.isConected && 
							!cli.getNickName().toLowerCase().equalsIgnoreCase(this.nickName) && !this.isBanned && !listaMalasPalabras.contains(mensajeDes) ){
						cli.dosCliente.writeUTF(Servidor.ANSI_YELLOW 
								+this.nickName
								+ ":"
								+Servidor.ANSI_RESET
								+ mensajeDes
							);
					}else if(this.isBanned) {
						this.dosCliente.writeUTF(Servidor.ANSI_RED + "Estas baneado pt" + Servidor.ANSI_RESET);
					}
					if(listaMalasPalabras.contains(mensajeDes)) {
						this.dosCliente.writeUTF(Servidor.ANSI_RED + 
								"No se permiten malas palabras." + Servidor.ANSI_RESET);
					}
					
				}
				if (mensajeDes.startsWith("/ban")) {
				  
				    String[] parts = mensajeDes.split(" ");
				    if (parts.length == 3) {
				        String targetNickName = parts[1];
				        long banDurationInSeconds = Long.parseLong(parts[2]);


				        ClienteCli targetClient = null;
				        for (ClienteCli cli : Servidor.ClientesConectados.values()) {
				            if (cli.getNickName().equalsIgnoreCase(targetNickName)) {
				                targetClient = cli;
				                break;
				            }
				        }

				        if (targetClient != null) {
				            targetClient.banear(banDurationInSeconds); 
				            this.dosCliente.writeUTF(Servidor.ANSI_RED + targetNickName + " fue baneado por " + banDurationInSeconds + " segundos" + Servidor.ANSI_RESET);
				        } else {
				            this.dosCliente.writeUTF(Servidor.ANSI_RED + "No se encontro cliente" + Servidor.ANSI_RESET);
				        }
				    }
				}

			} catch (IOException ex) {
				Logger.getLogger(ClienteCli.class.getName()).log(Level.SEVERE,null,ex);
			}
		}
	}
    
	
	
	void notificarClientes(boolean estado) {
		for( ClienteCli cli : Servidor.ClientesConectados.values() )
		{
			if( !cli.getNickName().equals(this.nickName) && cli.isConected() )
			{
				try {
					if(estado)
					{
						cli.dosCliente.writeUTF(Servidor.ANSI_GREEN
								+ "\t---"
								+ this.getNickName()
								+ " se ah CONECTADO---"
								+ Servidor.ANSI_RESET
						);						
					}else {
						cli.dosCliente.writeUTF(Servidor.ANSI_RED
								+ "\t---"
								+ this.getNickName()
								+ " se ah DESCONECTADO---"
								+ Servidor.ANSI_RESET
						);
					}
				}catch(IOException ex) {
					Logger.getLogger(ClienteCli.class.getName()).log(Level.SEVERE,null,ex);
				}
			}
		}
	}
	
	// metodos para baneo
	
	public void banear(long banDuracion) {
		this.isBanned = true;
		this.banDuration = banDuracion * 1000; // esto va a milisegundos
		this.banStartTime = System.currentTimeMillis(); // registra cuando comienza 
	}
	
	public boolean desbanear() {
		if(isBanned && (System.currentTimeMillis() - banStartTime) >= banDuration ) {
			this.isBanned = false; //debsanea 
		}
		return isBanned;
	}
	
    public String getNickName() {
		return nickName;
	}

	public Socket getSock() {
		return sock;
	}

	public Thread getHilo() {
		return hilo;
	}

	public boolean isConected() {
		return isConected;
	}	
	public boolean isBanned() {
		return isBanned;
	}




	public boolean checkBan() {
		return isBanned;
	}
}

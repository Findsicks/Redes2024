	package socket;
	
	import java.io.DataInputStream;
	import java.io.DataOutputStream;
	import java.io.IOException;
	import java.io.PrintStream;
	import java.net.Socket;
	import java.util.StringTokenizer;
	import java.util.logging.Level;
	import java.util.logging.Logger;
	
	public class ClienteCli implements Runnable {
		
	    String nickName = "";
	    Socket sock;
	    Thread hilo;
	    static final String clave = "1234567890123456"; 
	    static final byte[] iv = Cifrado.generarIV();
		final DataInputStream disCliente;
	    final DataOutputStream dosCliente;
	    boolean isConected;
	    private boolean estaBaneado = false;
	    private long tiempoBan; 
	    long tiempoRestanteBan; 
	    PrintStream ps;
	    
	    public ClienteCli(Socket sock, String nick, DataInputStream in, DataOutputStream out) {
	        this.nickName = nick;
	        this.sock = sock;
	        this.disCliente = in;
	        this.dosCliente = out;    
	        
	        ps = new PrintStream(System.out);
	        this.isConected = true;
	        this.hilo = new Thread(this, nickName);
	    }
	
	    
	    public void banearCliente(long tiempoEnSegundos) {
	        this.estaBaneado = true;
	        this.tiempoBan = tiempoEnSegundos;
	        this.tiempoRestanteBan = tiempoEnSegundos;
	        // mandar mensaje al cliente para informarle que está baneado
	        try {
	            dosCliente.writeUTF(Servidor.ANSI_RED + "estás baneado por " + tiempoEnSegundos + " segundos" + Servidor.ANSI_RESET);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public void desbanearCliente() {
	        this.estaBaneado = false;
	        this.tiempoRestanteBan = 0;
	        // se manda mensaje al cliente que fue desbaneado
	        try {
	            dosCliente.writeUTF(Servidor.ANSI_GREEN + "desbaneado" + Servidor.ANSI_RESET);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public boolean estaBaneado() {
	        return estaBaneado;
	    }

	    public long getTiempoRestanteBan() {
	        return tiempoRestanteBan;
	    }
	    
		@Override
		public void run() {
			String msgRecibido = "";
			String destino = "";
			
			
			while( this.sock.isConnected() && this.isConected )
			{
				try {
					msgRecibido = this.disCliente.readUTF().trim();
		            String mensajeDesencriptado = Cifrado.decriptar(clave, iv, msgRecibido);
					if (estaBaneado) {
	                    continue;
	                }
					
					
					
					
					//identificar el destinaratio
					//   destino # mensaje a enviar
					// Furno# Todo bien?
					if( msgRecibido.contains("#") )
					{
						StringTokenizer token = new StringTokenizer(msgRecibido,"#");
						destino = token.nextToken().trim().toLowerCase();
						msgRecibido = token.nextToken().trim();
						
					}
					
					else {
						destino = "";
					}
					if (msgRecibido.startsWith("/ban")) {
	                    // código para aplicar el ban, ejemplo: /ban 60 (para banear por 60 segundos)
	                    String[] parts = msgRecibido.split(" ");
	                    if (parts.length == 2) {
	                        try {
	                            long tiempoBan = Long.parseLong(parts[1]);
	                            banearCliente(tiempoBan);  // llama a la función banear
	                        } catch (NumberFormatException e) {
	                            dosCliente.writeUTF(Servidor.ANSI_RED + "El tiempo de ban debe ser un número." + Servidor.ANSI_RESET);
	                        }
	                    }
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
					//  mensaje= /salir
					
					
					
					//enviar mensaje
					for( ClienteCli cli : Servidor.ClientesConectados.values())
					{
						boolean encontrado = false;
						//si el mensaje a enviar esta vacio
						if(msgRecibido.equalsIgnoreCase(""))
							break;
						
						if(cli.getNickName().toLowerCase().equalsIgnoreCase(destino) && this.isConected )
						{
							if (ControlParental.contieneMalaPalabra(msgRecibido)) {
				                   
			                    msgRecibido = ControlParental.censurarMensaje(msgRecibido);

			                    
			                    dosCliente.writeUTF(Servidor.ANSI_YELLOW + "[Mensaje censurado] " + mensajeDesencriptado + Servidor.ANSI_RESET);
			                }else {
							cli.dosCliente.writeUTF(Servidor.ANSI_RED
									+"[MP de "
									+this.nickName
									+ ":"
									+Servidor.ANSI_RESET
									+ mensajeDesencriptado
								);
							encontrado = true;
							break;
			                }	
						}else if(destino.equalsIgnoreCase("") && 
								this.isConected && 
								!cli.getNickName().toLowerCase().equalsIgnoreCase(this.nickName) ){
							if (ControlParental.contieneMalaPalabra(msgRecibido)) {
				                   
			                    msgRecibido = ControlParental.censurarMensaje(msgRecibido);

			                    
			                    dosCliente.writeUTF(Servidor.ANSI_YELLOW + "[Mensaje censurado] " + mensajeDesencriptado + Servidor.ANSI_RESET);
			                }else {
							cli.dosCliente.writeUTF(Servidor.ANSI_YELLOW 
									+this.nickName
									+ ":"
									+Servidor.ANSI_RESET
									+ mensajeDesencriptado
								);
						}}
						if (!encontrado) {
					        this.dosCliente.writeUTF(Servidor.ANSI_RED + "El cliente " + destino + " no está disponible." + Servidor.ANSI_RESET);
					    }
						if (msgRecibido.equalsIgnoreCase("/clientes")) {
					        StringBuilder listaClientes = new StringBuilder();
					        listaClientes.append("Clientes conectados:\n");
					        
					        for (ClienteCli clienteConectado : Servidor.ClientesConectados.values()) {
					            if (clienteConectado.isConected()) {
					                listaClientes.append("- ").append(clienteConectado.getNickName()).append("\n");
					            }
					        }
					        cli.dosCliente.writeUTF(Servidor.ANSI_CYAN + listaClientes.toString() + Servidor.ANSI_RESET);
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
		
	}

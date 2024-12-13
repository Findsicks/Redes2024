package socket;

import java.io.IOException;

public class mainServidor {

	public static void main(String[] args) throws IOException {
		
		Servidor server = new Servidor();
		server.start();
	    server.escucharConsola();

	}

}

package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import shared.Request;
import shared.Pokemon;

/**
 * This class represents the server application, which is a Pokémon Bank.
 * It is a shared account: everyone's Pokémons are stored together.
 * @author strift
 *
 */
public class PokemonBank {
	
	public static final int SERVER_PORT = 3000;
	public static final String DB_FILE_NAME = "pokemons.db";
	
	/**
	 * The database instance
	 */
	private Database db;
	
	/**
	 * The ServerSocket instance
	 */
	private ServerSocket server;
	
	/**
	 * The Pokémons stored in memory
	 */
	private ArrayList<Pokemon> pokemons;
	
	/**
	 * Constructor
	 * @param port		the port on which the server should listen
	 * @param fileName	the name of the file used for the database
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public PokemonBank() throws IOException, ClassNotFoundException {
		
                db= new Database(DB_FILE_NAME);
		server= new ServerSocket(SERVER_PORT, 1, InetAddress.getLocalHost());
		

		System.out.println("Banque Pokémon (" + DB_FILE_NAME + ") démarrée sur le port " + SERVER_PORT);
		
		// Let's load all the Pokémons stored in database
		this.pokemons = this.db.loadPokemons();
		this.printState();
	}
	
	/**
	 * The main loop logic of your application goes there.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void handleClient() throws IOException, ClassNotFoundException {
		System.out.println("En attente de connexion...");
                Socket client=server.accept();
				
                ObjectInputStream ois =new ObjectInputStream(client.getInputStream());
		ObjectOutputStream oos =new ObjectOutputStream(client.getOutputStream());
		
		// For as long as the client wants it
		boolean running = true;
		while (running) {

			Request request;
                        request= (Request) ois.readObject();
                        
                        
                        
                        
                        
			switch(request) {
			case LIST:
				System.out.println("Request: LIST");
				if (this.pokemons.size() == 0) {
                                    oos.writeObject("the database is empty.");
                                    oos.flush();

					
				} else {
                                    pokemons=db.loadPokemons();
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 0; i < pokemons.size(); i++) {
                                        sb.append(pokemons.get(i));
                                        sb.append("\n");
                                     }
                                     oos.writeObject(sb);
                                     oos.flush();
				}
				break;
				
			case STORE:
				System.out.println("Request: STORE");
				pokemons.add((Pokemon) ois.readObject());
                                oos.flush();
                                
				oos.writeObject("your" +pokemons.toString()+ "is well stored");
                                oos.flush();
				

				break;
				
			case CLOSE:
				System.out.println("Request: CLOSE");
				oos.writeObject("client is disconnected.");
				// Closing the connection
				System.out.println("Fermeture de la connexion...");
				running = false;
				break;
			}
			this.printState();
		};
		client.close();
		oos.close();
                ois.close();
                
		db.savePokemons(pokemons);
	}
	
	/**
	 * Print the current state of the bank
	 */
	private void printState() {
		System.out.print("[");
		for (int i = 0; i < this.pokemons.size(); i++) {
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(this.pokemons.get(i));
		}
		System.out.println("]");
	}
	
	/**
	 * Stops the server.
	 * Note: This function will never be called in this project.
	 * @throws IOException 
	 */
	public void stop() throws IOException {
		this.server.close();
	}
}

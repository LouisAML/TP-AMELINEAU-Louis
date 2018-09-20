package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import shared.Pokemon;
import shared.Request;

/**
 * This class represents a Pokémon Bank client, which is a Pokémon Trainer.
 *
 * @author strift
 *
 */
public class PokemonTrainer {

    public static final String SERVER_HOST = null; // localhost
    public static final int SERVER_PORT = 3000;

    /**
     * The client socket
     */
    private Socket client;

    /**
     * The client output stream
     */
    private ObjectOutputStream outputStream;

    /**
     * The client input stream
     */
    private ObjectInputStream inputStream;

    /**
     * Constructor
     *
     * @throws UnknownHostException
     * @throws IOException
     */
    public PokemonTrainer() throws UnknownHostException, IOException {
        client = new Socket(InetAddress.getLocalHost(), SERVER_PORT);
        outputStream = new ObjectOutputStream(client.getOutputStream());
        inputStream = new ObjectInputStream(client.getInputStream());

    }

    /**
     * Send a LIST request to the server and read its response
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void getPokemonList() throws IOException, ClassNotFoundException {
        System.out.println("Request: LIST");
        outputStream.writeObject(Request.LIST);
        this.readResponse();
    }

    /**
     * Send a STORE request to the server and read its response
     *
     * @param pokemon
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void sendPokemon(Pokemon pokemon) throws IOException, ClassNotFoundException {
        System.out.println("Request: STORE");

        outputStream.writeObject(Request.STORE);
        outputStream.flush();

        outputStream.writeObject(pokemon);
        outputStream.flush();

        System.out.println("Envoi en cours : " + pokemon);
        this.readResponse();
    }

    /**
     * Send a CLOSE request to the server, read its response, and close
     * everything
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void disconnect() throws IOException, ClassNotFoundException {
        System.out.println("Request: CLOSE");
        outputStream.writeObject(Request.CLOSE);
        outputStream.flush();
                
	this.readResponse();
	
        client.close();
        outputStream.close();
        inputStream.close();
        
		
    }
efe 
    /**
     * Read the response from the server
     *
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private void readResponse() throws ClassNotFoundException, IOException {
        System.out.println("Server's answer : ");
        System.out.println(inputStream.readObject());

    }
}

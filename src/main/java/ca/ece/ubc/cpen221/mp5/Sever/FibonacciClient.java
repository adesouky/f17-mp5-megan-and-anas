package ca.ece.ubc.cpen221.mp5.Sever;


import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

import ca.ece.ubc.cpen221.mp5.YelpDBServer;

/**
 * USED FORR TESTING ONLY
 */
public class FibonacciClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    // Rep invariant: socket, in, out != null
    
    /**
     * Make a FibonacciClient and connect it to a server running on
     * hostname at the specified port.
     * @throws IOException if can't connect
     */
    public FibonacciClient(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    
    /**
     * Send a request to the server. Requires this is "open".
     * @param x to find Fibonacci(x)
     * @throws IOException if network or server failure
     */
    public void sendRequest(String s) throws IOException {
        out.print(s + "\n");
        out.flush(); // important! make sure x actually gets sent
    }
    
    /**
     * Get a reply from the next request that was submitted.
     * Requires this is "open".
     * @return the requested Fibonacci number
     * @throws IOException if network or server failure
     */
    public String getReply() throws IOException {
        String reply = in.readLine();
        if (reply == null) {
            throw new IOException("connection terminated unexpectedly");
        }
        
        return reply;
//        try {
//            return new BigInteger(reply);
//        } catch (NumberFormatException nfe) {
//            throw new IOException("misformatted reply: " + reply);
//        }
    }

    /**
     * Closes the client's connection to the server.
     * This client is now "closed". Requires this is "open".
     * @throws IOException if close fails
     */
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
    
    
    
    
    private static final int N = 100;
    
    /**
     * Use a FibonacciServer to find the first N Fibonacci numbers.
     */
    public static void main(String[] args) {
    	  for(int i=0; i< args.length; i++) {   
    	try {
    		
            FibonacciClient client = new FibonacciClient("localhost", YelpDBServer.YELP_PORT);
            
          
            client.sendRequest(args[i].toString());
            String s = client.getReply();
            
            System.out.println("QUERY WAS: " + args[i] + " Answer is " + s);
            
            client.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    	  }
    }
    
    
    
}

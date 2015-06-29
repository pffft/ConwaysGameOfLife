// File Name GreetingServer.java

import java.net.*;
import java.io.*;

public class ServerThread extends Thread
{
    private ServerSocket serverSocket;

    public ServerThread(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run()
    {
        //      while(true)
        //      {
        try
        {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            server.setKeepAlive(true);
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress());
            while (true)
            {
                String input = in.readUTF();
                System.out.println(input);
                out.writeUTF("SERVER ECHO: " + input);
                try {sleep(1);} catch (Exception e) {}
            }
        }catch(SocketTimeoutException s)
        {
            System.out.println("Socket timed out!");
            //break;
        }catch(IOException e)
        {
            e.printStackTrace();
            //break;
        }
        //      }
    }

    public static void main(String [] args)
    {
        int port = 7777;
        try
        {
            Thread t = new ServerThread(port);
            t.start();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
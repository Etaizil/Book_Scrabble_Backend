package test;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/** A server that listens on a port and handles clients using a {@link ClientHandler}. */
public class MyServer {

  private final int port;
  private final ClientHandler clientHandler;
  private volatile boolean stop;
  private Socket clientSocket;
  private ServerSocket serverSocket;
  private Thread serverThread;

  public MyServer(int port, ClientHandler clientHandler) {
    this.port = port;
    this.clientHandler = clientHandler;
  }

  /** Starts the server. A new thread is created to run the server, to avoid a blocking call. * */
  public void start() {
    stop = false;
    serverThread = new Thread(() -> runServer());
    serverThread.start();
  }

  /** Runs the server. As long as the server is not stopped, it listens for clients. */
  private void runServer() {
    try {
      serverSocket = new ServerSocket(port);
      while (!stop) {
        clientSocket = serverSocket.accept();
        clientHandler.handleClient(clientSocket.getInputStream(), clientSocket.getOutputStream());
        if (!stop) {
          Thread.sleep(200);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Stops the server. Waits for the server thread to finish handling the current client, then
   * closes the resources.
   */
  public void close() {
    stop = true;
    try {
      if (serverThread != null) {
        serverThread.join();
      }
      if (clientSocket != null) {
        clientSocket.close();
      }
      if (serverSocket != null) {
        serverSocket.close();
      }
      clientHandler.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

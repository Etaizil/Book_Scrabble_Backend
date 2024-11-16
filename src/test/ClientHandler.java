package test;

/*
@created 20/04/2024 - 18:06
@project PTM1 Project
@author  Zilberman Etai
*/

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
  void handleClient(InputStream inFromclient, OutputStream outToClient);

  void close();
}

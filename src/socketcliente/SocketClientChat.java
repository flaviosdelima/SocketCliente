/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketcliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Flávio
 */
public class SocketClientChat {
   public static void main(String[] args) 
         throws UnknownHostException, IOException {
     // dispara cliente
     new SocketClientChat("127.0.0.1", 12345).executa();
   }
   
   private String host;
   private int porta;
   
   public SocketClientChat (String host, int porta) {
     this.host = host;
     this.porta = porta;
   }
   
   public void executa() throws UnknownHostException, IOException {
     Socket cliente = new Socket(this.host, this.porta);
     System.out.println("O cliente se conectou ao servidor!");
 
     // thread para receber mensagens do servidor
     Recebedor r = new Recebedor(cliente.getInputStream());
     new Thread(r).start();
     
     // lê msgs do teclado e manda pro servidor
     Scanner teclado = new Scanner(System.in);
     PrintStream saida = new PrintStream(cliente.getOutputStream());
     while (teclado.hasNextLine()) {
       saida.println(teclado.nextLine());
     }
     
     saida.close();
     teclado.close();
     cliente.close();    
   } 
}

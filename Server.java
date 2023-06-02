import java.io.*;
import java.net.*;

class Server{


    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    // constructor
    public Server()
    {
        try{
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting...");
         
             socket= server.accept();

             br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out=new PrintWriter(socket.getOutputStream());  

             startReading();
             startWriting();  


        }catch(Exception e){
               e.printStackTrace();
        }
    }

    // multithreading
 public void startReading(){

    // thread -- read krnar data la
    Runnable r1=()->{
            System.out.println("reader started");

            try{

         
       while(true)
       
       {
         

       
    String msg= br.readLine();
    
    if(msg.equals("exit")){
        System.out.println("Client terminated the chat");

        // connection close
        socket.close();

        break;
    }

    System.out.println("client : "+ msg);
   
        
        }
    }catch(Exception e){
        e.printStackTrace();;
    }
        };
        new Thread(r1).start();
 }

 public void startWriting(){

  // thread -- data user kadun ghenar and send krnar client la 


  Runnable r2=()->{
    System.out.println("Writer started....");
    try{
while(!socket.isClosed()){
  
     BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
     String content=br1.readLine();

     out.println(content);
     out.flush();
     if(content.equals("exit")){
        socket.close();
        break;
     }

  
} 
System.out.println("Connection is closed"); }
catch(Exception e){
// e.printStackTrace();
System.out.println("Connection is closed");
}  

  };
 

  new Thread(r2).start();

 }

    public static void main(String[] args) {
        System.out.println("This  is server.. going to start server");
      new Server();
        


    }
}
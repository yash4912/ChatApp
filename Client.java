import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.TrayIcon.MessageType;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;


public class Client extends JFrame {
     Socket socket;

     BufferedReader br;
     PrintWriter out;

     // declare components
private JLabel heading=new JLabel("Client Area");
private JTextArea messageArea=new JTextArea();
private JTextField messageInput=new JTextField();
private Font font=new Font("Roboto",Font.BOLD,20);

     // client construtor
    public  Client(){
        try{
            System.out.println("Sending request to server");
            socket=new Socket("192.168.43.187", 7777);
            // socket=new Socket("127.0.0.1", 7777);
     System.out.println("Connection done.");

     br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
     out=new PrintWriter(socket.getOutputStream());  

 createGUI();
 handleEvents(); // je ky type kru message input madhe te message area mdhe disyla pahije  manun


  startReading();
//   startWriting();  

}catch(Exception e){

}

    }

    private void createGUI(){
        this.setTitle("Client Messager[END]");
        this.setSize(600, 700);
        this.setLocationRelativeTo(null);   // center mdhe set krel
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
             // coding for component    
            heading.setFont(font);
            messageArea.setFont(font);
            messageInput.setFont(font);
//  heading.setIcon(new ImageIcon("chatlo.png"));   // size set kara****** pahila mg insert kara
 heading.setHorizontalTextPosition(SwingConstants.CENTER);
 heading.setVerticalAlignment(SwingConstants.BOTTOM);
            //  heading.setIcon(new ImageIcon("logoo.png"));  // logo image insert keli
            
            heading.setHorizontalAlignment(SwingConstants.CENTER);  // heading center la aany sathi
         heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
messageArea.setEditable(false);
                   // frame ka layout set kru ya
                   this.setLayout(new BorderLayout());


          //adding componenets to frame
          this.add(heading,BorderLayout.NORTH);
JScrollPane jScrollPane=new JScrollPane(messageArea); // jscrollpane  will provide u scroller 
          this.add(jScrollPane, BorderLayout.CENTER);
          this.add(messageInput, BorderLayout.SOUTH);


        this.setVisible(true);
    }

    private void handleEvents(){
       messageInput.addKeyListener(new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            
        //    System.out.println("key relased"+ e.getKeyCode());
           // line 104 vrun keycode bhetla press kel ki 10 hoty terminal mdhe so aapan 10 la condition ne define kel

           if(e.getKeyCode()==10){
            //    System.out.println("You have pressd enter button");
          String contentToSend=messageInput.getText();
          messageArea.append("Me :"+contentToSend+ "\n");
          out.println(contentToSend);
          out.flush();
          messageInput.setText("");
          messageInput.requestFocus();
        }
        }
        
       });

    }
// methods
    public void startReading(){

        // thread -- read krnar data la
        Runnable r1=()->{
                System.out.println("reader started");
                try{

                
           while(true)
           {
           
      
    
        String msg= br.readLine();
        
        if(msg.equals("exit")){
            System.out.println("Server terminated the chat");
            JOptionPane.showMessageDialog(null, "Server terminated the chat" );
            messageInput.setEnabled(false);;
            socket.close();
            break;
        }
    
        // System.out.println("Server : "+ msg);
        messageArea.append("Server : "+msg+"\n");
       
            
            }
        }
        
        catch(Exception e) {  
        e.printStackTrace();      
        
        }
            };
            new Thread(r1).start();
     }
     // method 2
     public void startWriting(){

        // thread -- data user kadun ghenar and send krnar client la 
      
      
        Runnable r2=()->{
          System.out.println("Writer started....");
          try{

          
      while(!socket.isClosed() ){
       
           BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
           String content=br1.readLine();
           out.println(content);
           out.flush();
           if(content.equals("exit")){
            socket.close();
            break;
         }
    
         
      }
      System.out.println("Connection is closed");
     }
      catch(Exception e){
  System.out.println("Connection is closed");
      }
        };
       
      
        new Thread(r2).start();
      
       }
    public static void main(String[] args) {
        System.out.println("client");
        new Client();
    }
}

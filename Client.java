import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class Client {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 9000;
    private Socket socket = null;

    public Client(){}

    public void connect_to_server(){
        System.out.println("conecting to server..");
        try {
            this.socket = new Socket(SERVER_IP ,SERVER_PORT);
        }catch (IOException e){
            System.out.println("[Client] - faild conecting to server - IP " + SERVER_IP + " Port " + SERVER_PORT);
        }

    }

    public String read_msg() throws IOException{

        BufferedReader re = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        String respone = re.readLine();

        return respone;
    }

    public void send_msg_to_server() throws IOException, InterruptedException {

        System.out.println("enter the massege :");
        Scanner input = new Scanner(System.in);
        PrintWriter send = new PrintWriter(this.socket.getOutputStream(),true);
        String msg = input.nextLine();
        while (true) {
            if(msg.equals("exit")) {
                send.println(msg);
                break;
            }
            else {
                send.println(msg);
                System.out.println("the server respose - " + read_msg());


            }
            msg = input.nextLine();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {

        Client c = new Client();
        c.connect_to_server();
        c.send_msg_to_server();
        c.socket.close();

    }
}

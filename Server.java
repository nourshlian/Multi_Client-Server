import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static Server server_instance = null;
    private static final int PORT = 9000;
    private ServerSocket listener = null;

    private ArrayList<Thread> clients ;

    private Server(){
        this.clients = new ArrayList<>();
    }

    public static Server getSerevr(){
        if (server_instance == null)
            server_instance = new Server();
        return server_instance;
    }

    public void start_server(){
        System.out.println("server  - start listing on port " + PORT);

        try {
            this.listener = new ServerSocket(PORT);
        }catch (IOException e){
            System.out.println("[Server - faild to start server ]" + e);
        }
    }

   // public Socket start_listing() {
    public void start_listing() {
        Socket client = null;

        System.out.println("server  - waiting for connections ..");
        while(true) {
            try {
                client = this.listener.accept();
                ClientWorker tmp = new ClientWorker(client);
                Thread tmpt = new Thread(tmp);
                clients.add(tmpt);
                tmpt.start();
                System.out.println("connection established");
            } catch (IOException e) {
                System.out.println("[Server - faild to accept client ]" + e);
            }
        }
        //return client;
    }

    public static void send_msg(Socket client, String msg) throws IOException {
        System.out.println("server  - sending to client " + "massege -> " + msg);
        PrintWriter out = new PrintWriter(client.getOutputStream());
        out.println(msg);
        out.flush();

    }

    public void read_C_msg(Socket client) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String cmsg = in.readLine();

        while (true) {


            if(cmsg.equals("exit")) {
                System.out.println("exiting..");
                break;
            }

            else if(cmsg != null) {
                System.out.println("massege recieved\n\t" + cmsg);
                send_msg(client, "msg = " + cmsg);
            }
            cmsg = in.readLine();

        }
    }



    public void close_server() throws IOException {
        System.out.println("closing the sockets .. !!\ngood bye.");
        this.listener.close();
    }





    public static void main(String[] args) throws IOException {

        Server server = Server.getSerevr();
        server.start_server();
        server.start_listing();
        //Socket client = server.start_listing();
        //server.read_C_msg(client);
        //server.send_msg(client, "hello I'm the server !!");

        //client.close();
        server.close_server();

    }
}
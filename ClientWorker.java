import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWorker implements Runnable{

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;


    public ClientWorker(Socket clientSocket) throws IOException {
        this.client = clientSocket;
        in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        out = new PrintWriter(this.client.getOutputStream(),true);
    }



    @Override
    public void run() {
        try {
            String cmsg = this.in.readLine();

            while (true) {


                if (cmsg.equals("exit")) {
                    System.out.println("exiting..");
                    break;
                } else if (cmsg != null) {
                    System.out.println("massege recieved\n\t" + cmsg);
                    out.println("server response ->" + cmsg);
                }

                cmsg = in.readLine();
            }
        }catch (IOException e) {
                e.printStackTrace();
            }


    }
}

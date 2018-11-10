package support.kajstech.kajbot;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

class Server {

    static void run(String port) {
        try {
            ServerSocket ss = new ServerSocket(Integer.parseInt(port));
            for (; ; ) {
                Socket client = ss.accept();


                OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8);

                // RESPONSE
                JSONObject json = new JSONObject();
                json.put("status", "ONLINE");
                json.put("botStatus", Main.jda.getPresence().getStatus());
                json.put("botGame", Main.jda.getPresence().getGame().getName());

                out.write(json.toString());

                out.close();
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Usage: java HTTPMirror <port>");
        }
    }
}
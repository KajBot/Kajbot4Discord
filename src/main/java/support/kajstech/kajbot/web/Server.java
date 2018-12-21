package support.kajstech.kajbot.web;

import com.sun.net.httpserver.HttpServer;
import support.kajstech.kajbot.web.context.API.APIv1;
import support.kajstech.kajbot.web.context.panel.Panel;

import java.net.InetSocketAddress;

public class Server {
    public static void run(Integer port) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        //PANEL
        server.createContext("/panel", Panel::context);

        //API
        server.createContext("/api/v1", APIv1::context);


        server.setExecutor(null);
        server.start();

    }
}
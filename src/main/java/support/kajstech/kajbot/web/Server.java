package support.kajstech.kajbot.web;

import com.sun.net.httpserver.HttpServer;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.web.context.API.v1.APIServerV1;
import support.kajstech.kajbot.web.context.API.v1.PostHandlerV1;

import java.net.InetSocketAddress;

public class Server {
    public static void run() throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(ConfigHandler.getProperty("API port"))), 0);

        //APIv1
        server.createContext("/api/v1", APIServerV1::context);
        //APIv1 POST handler
        server.createContext("/api/v1/request", PostHandlerV1::context);


        server.setExecutor(null);
        server.start();

    }
}

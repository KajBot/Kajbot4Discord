package support.kajstech.kajbot.API;

import com.sun.net.httpserver.HttpServer;
import support.kajstech.kajbot.API.context.APIv1;
import support.kajstech.kajbot.utils.ConfigManager;

import java.net.InetSocketAddress;
import java.util.UUID;

public class Server {

    public static void run(Integer port) throws Exception {

        if (!ConfigManager.containsProperty("API token")) {
            ConfigManager.setProperty("API token", UUID.randomUUID().toString());
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/api/v1", APIv1::v1);
        server.setExecutor(null);
        server.start();

    }
}
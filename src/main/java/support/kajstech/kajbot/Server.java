package support.kajstech.kajbot;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;
import support.kajstech.kajbot.utils.CustomCommandsManager;
import support.kajstech.kajbot.utils.KajbotLogger;
import support.kajstech.kajbot.utils.KeywordManager;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

//import org.json.JSONObject;

class Server {

    static void run(String port) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(port)), 0);
        server.createContext("/", Server::status);
        server.setExecutor(null);
        server.start();

    }

    private static void status(HttpExchange httpExchange) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(httpExchange.getResponseBody(), StandardCharsets.UTF_8);
        Headers headers = httpExchange.getResponseHeaders();

        headers.add("Content-Type", "Application/json");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate, private");

        JSONObject json = new JSONObject();
        json.put("Status", "ONLINE");
        json.put("Game Status", Main.jda.getPresence().getGame().getName());
        json.put("Online Status", Main.jda.getPresence().getStatus());
        json.put("Custom commands", CustomCommandsManager.getCommands());
        json.put("Custom keywords", KeywordManager.getKeywords());

        osw.write(json.toString());

        httpExchange.sendResponseHeaders(200, 0);

        osw.close();

        String hostIP = httpExchange.getRemoteAddress().getAddress().getHostAddress();
        int hostPort = httpExchange.getRemoteAddress().getPort();

        KajbotLogger.info(KajbotLogger.server, "Connection established: " + hostIP + ":" + hostPort);
    }
}
package support.kajstech.kajbot.API.context;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.utils.ConfigManager;
import support.kajstech.kajbot.utils.CustomCommandsManager;
import support.kajstech.kajbot.utils.KajbotLogger;
import support.kajstech.kajbot.utils.KeywordManager;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class APIv1 {
    public static void v1(HttpExchange httpExchange) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(httpExchange.getResponseBody(), StandardCharsets.UTF_8);

        httpExchange.getResponseHeaders().add("Content-Type", "Application/json");
        httpExchange.getResponseHeaders().add("Cache-Control", "no-cache, no-store, must-revalidate, private");

        JSONObject json = new JSONObject();

        try {
            Map<String, String> args = qToM(httpExchange.getRequestURI().getQuery());
            if (args.containsKey("token") && args.get("token").contentEquals(ConfigManager.getProperty("API token"))) {
                json.put("game", Bot.jda.getPresence().getGame().getName());
                json.put("status", Bot.jda.getPresence().getStatus());
                json.put("commands", CustomCommandsManager.getCommands());
                json.put("keywords", KeywordManager.getKeywords());

                httpExchange.sendResponseHeaders(200, 0);
            } else {
                json.put("401", "Unauthorized");
                httpExchange.sendResponseHeaders(401, 0);
            }

        } catch (Exception ignored) {
            json.put("401", "Unauthorized");
            httpExchange.sendResponseHeaders(401, 0);
        }

        osw.write(json.toString());
        osw.close();


        KajbotLogger.info(KajbotLogger.server, "Connection established: " + httpExchange.getRemoteAddress().getAddress().getHostAddress() + ":" + httpExchange.getRemoteAddress().getPort());
    }

    private static Map<String, String> qToM(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
}

package support.kajstech.kajbot.web.context.API.v1;

import com.sun.net.httpserver.HttpExchange;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.json.JSONObject;
import support.kajstech.kajbot.Bot;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.CustomCommandsHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;
import support.kajstech.kajbot.utils.LogHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PostHandlerV1 {

    public static void context(HttpExchange http) throws IOException {

        LogHelper.info("Post request from: " + http.getRemoteAddress().getAddress().getHostAddress() + ":" + http.getRemoteAddress().getPort());

        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = new BufferedReader(new InputStreamReader(http.getRequestBody(), StandardCharsets.UTF_8)).read()) != -1)
            buf.append((char) b);

        JSONObject body = new JSONObject(buf.toString());

        if (!http.getRequestHeaders().containsKey("token") || !http.getRequestHeaders().get("token").get(0).equals(ConfigHandler.getProperty("API token")) || body.length() < 1)
            return;

        if (!body.isNull("add_command")) {
            for (int i = 0; i < body.getJSONObject("add_command").names().length(); ++i) {
                CustomCommandsHandler.addCommand(body.getJSONObject("add_command").names().getString(i), body.getJSONObject("add_command").getString(body.getJSONObject("add_command").names().getString(i)));

            }
        }

        if (!body.isNull("remove_command")) {
            for (int i = 0; i < body.getJSONObject("remove_command").names().length(); ++i) {
                CustomCommandsHandler.removeCommand(body.getJSONObject("remove_command").names().getString(i));

            }
        }

        if (!body.isNull("add_keyword")) {
            for (int i = 0; i < body.getJSONObject("add_keyword").names().length(); ++i) {
                KeywordHandler.addKeyword(body.getJSONObject("add_keyword").names().getString(i), body.getJSONObject("add_keyword").getString(body.getJSONObject("add_keyword").names().getString(i)));

            }
        }

        if (!body.isNull("remove_keyword")) {
            for (int i = 0; i < body.getJSONObject("remove_keyword").names().length(); ++i) {
                KeywordHandler.removeKeyword(body.getJSONObject("remove_keyword").names().getString(i));

            }
        }

        if (!body.isNull("set_status")) {
            if (!body.getJSONObject("set_status").isNull("game")) {
                ConfigHandler.setProperty("Bot game", body.getJSONObject("set_status").getString("game"));
                Bot.jda.getPresence().setActivity(Activity.playing(body.getJSONObject("set_status").getString("game")));
            }

            if (!body.getJSONObject("set_status").isNull("online")) {
                String status = body.getJSONObject("set_status").getString("online");
                if (status.equalsIgnoreCase("dnd")) status = OnlineStatus.DO_NOT_DISTURB.toString();
                if (!(status.equalsIgnoreCase(OnlineStatus.INVISIBLE.toString()) || status.equalsIgnoreCase(OnlineStatus.ONLINE.toString()) || status.equalsIgnoreCase(OnlineStatus.DO_NOT_DISTURB.toString()) || status.equalsIgnoreCase(OnlineStatus.IDLE.toString())))
                    return;
                Bot.jda.getPresence().setStatus(OnlineStatus.valueOf(status.toUpperCase()));
            }
        }


        String response = "SUCCESS";
        http.sendResponseHeaders(200, response.length());
        OutputStream os = http.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}

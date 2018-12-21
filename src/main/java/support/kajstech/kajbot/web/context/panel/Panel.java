package support.kajstech.kajbot.web.context.panel;

import com.sun.net.httpserver.HttpExchange;
import support.kajstech.kajbot.handlers.ConfigHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class Panel {


    public static void context(HttpExchange httpExchange) throws IOException {
        String index = new Scanner(Panel.class.getResourceAsStream("/web/index.html"), "UTF-8").useDelimiter("\\A").next();
        String cmdlist = "<iframe src='/api/v1?token=" + ConfigHandler.getProperty("API token") + "' width='100%' height='100%' frameBorder='0'>Browser not compatible.</iframe>";
        System.out.println(cmdlist);
        index = index.replaceAll("%cmdlist%", cmdlist);

        httpExchange.sendResponseHeaders(200, index.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(index.getBytes());
        os.close();

    }
}

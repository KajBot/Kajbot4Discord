package support.kajstech.kajbot.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import support.kajstech.kajbot.Main;
import support.kajstech.kajbot.utils.Config;

public class JettyServer {

    public static void run() throws Exception {
        Server server = new Server(Integer.parseInt(Config.cfg.get("API port")));
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        for (Class<? extends Servlet> servlet : Main.servlets) {
            if (servlet.newInstance().path == null) {
                handler.addServletWithMapping(servlet, "/" + servlet.newInstance().name);
            } else {
                handler.addServletWithMapping(servlet, servlet.newInstance().path + "/" + servlet.newInstance().name);
            }
        }


        server.start();
        server.join();
    }

}

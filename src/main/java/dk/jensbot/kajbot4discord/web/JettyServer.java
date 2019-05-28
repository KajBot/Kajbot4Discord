package dk.jensbot.kajbot4discord.web;

import dk.jensbot.kajbot4discord.Main;
import dk.jensbot.kajbot4discord.utils.Config;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.Calendar;

public class JettyServer {

    public static void run() throws Exception {
        Server server = new Server(Integer.parseInt(Config.cfg.get("API-port")));
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        NCSARequestLog log = new NCSARequestLog(System.getProperty("user.dir") + "/http.log");
        log.setAppend(true);
        log.setExtended(true);
        log.setLogTimeZone(Calendar.getInstance().getTimeZone().getID());
        log.setLogLatency(true);
        server.setRequestLog(log);

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

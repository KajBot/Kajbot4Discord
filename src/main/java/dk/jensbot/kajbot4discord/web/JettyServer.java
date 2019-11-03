package dk.jensbot.kajbot4discord.web;

import dk.jensbot.kajbot4discord.Main;
import dk.jensbot.kajbot4discord.utils.Config;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.util.Calendar;

public class JettyServer {

    public static void run() throws Exception {
        Server server = new Server(Integer.parseInt(Config.cfg.get("API.port")));
        ServletHandler handler = new ServletHandler();

        NCSARequestLog log = new NCSARequestLog(System.getProperty("user.dir") + "/http.log");
        log.setAppend(true);
        log.setExtended(true);
        log.setLogTimeZone(Calendar.getInstance().getTimeZone().getID());
        log.setLogLatency(true);
        log.setLogCookies(false);
        log.setLogCookies(true);
        log.setRetainDays(0);
        server.setRequestLog(log);

        //Cross-Origin
        FilterHolder holder = new FilterHolder(CrossOriginFilter.class);
        holder.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        holder.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        holder.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET, POST, HEAD");
        holder.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With, Content-Type, Accept, Origin, token");
        holder.setName("cross-origin");
        FilterMapping fm = new FilterMapping();
        fm.setFilterName("cross-origin");
        fm.setPathSpec("*");
        handler.addFilter(holder, fm);

        //SERVLETS
        for (Class<? extends Servlet> servlet : Main.servlets) {
            if (servlet.newInstance().path == null) {
                handler.addServletWithMapping(servlet, "/" + servlet.newInstance().name);
            } else {
                handler.addServletWithMapping(servlet, servlet.newInstance().path + "/" + servlet.newInstance().name);
            }
        }


        server.setHandler(handler);
        server.start();
        server.join();
    }

}

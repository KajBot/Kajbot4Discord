package dk.jensbot.kajbot4discord.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Servlet extends HttpServlet {
    protected String path = null;
    protected String name = "null";

    protected abstract void get(Context c) throws IOException;

    protected abstract void post(Context c) throws IOException;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        post(new Context(request, response));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        get(new Context(request, response));
    }

}

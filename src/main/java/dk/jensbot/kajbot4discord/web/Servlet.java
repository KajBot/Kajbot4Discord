package dk.jensbot.kajbot4discord.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Servlet extends HttpServlet {
    protected String path = null;
    protected String name = "null";


    protected void get(Context c) throws ServletException, IOException {
        doGet(c.request(), c.response());
    }

    protected void post(Context c) throws ServletException, IOException {
        doGet(c.request(), c.response());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        get(new Context(request, response));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        post(new Context(request, response));
    }


}

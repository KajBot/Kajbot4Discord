package support.kajstech.kajbot.web;

import support.kajstech.kajbot.utils.LogHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Servlet extends HttpServlet {
    protected String path = null;
    protected String name = "null";


    protected void get(Context c) throws ServletException, IOException {
        LogHelper.logToFile("Connection from: " + c.request().getRemoteAddr());
        doGet(c.request(), c.response());
    }

    protected void post(Context c) throws ServletException, IOException {
        LogHelper.logToFile("Connection from: " + c.request().getRemoteAddr());
        doGet(c.request(), c.response());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogHelper.logToFile("Connection from: " + request.getRemoteAddr());
        get(new Context(request, response));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogHelper.logToFile("Connection from: " + request.getRemoteAddr());
        post(new Context(request, response));
    }


}

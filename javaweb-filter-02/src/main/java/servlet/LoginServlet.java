package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入了LoginServlet");
        String username = req.getParameter("username");
        if (username.equals("admin")) {
            req.getSession().setAttribute("Session_Id", req.getSession().getId());
            resp.sendRedirect(req.getContextPath()+"/success.jsp");
        }else
            resp.sendRedirect(req.getContextPath()+"/error.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

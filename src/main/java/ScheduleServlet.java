import restapi.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by Илья on 04.04.2016.
 */
@WebServlet(name = "ScheduleServlet", urlPatterns = "/schedule")
public class ScheduleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("URL-encoded");
            String groupNumber = request.getParameter("groupNumber");
            System.err.println(groupNumber);
            //String groupNumber = new String(groupNumber1.getBytes("ISO-8859-1"), "utf-8");
            //System.err.println(groupNumber);

            String arr = DAO.getScheduleJSON(groupNumber).toString();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.print(arr);
        } catch (SQLException  | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}

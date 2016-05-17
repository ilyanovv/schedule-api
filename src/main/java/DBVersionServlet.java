import restapi.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Илья on 18.05.2016.
 */
@WebServlet(name = "DBVersionServlet", urlPatterns = "/get_db_version")
public class DBVersionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String groupID = request.getParameter("groupID");
        String teacherID = request.getParameter("teacherID");
        try {
            String arr = null;
            if (teacherID != null) {
                 arr = DAO.getDBVersion("teacher", teacherID).toString();
            }
            else if(groupID != null)
                arr = DAO.getDBVersion("group", groupID).toString();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.print(arr);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

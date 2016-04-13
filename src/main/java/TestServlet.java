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
 * Created by Илья on 03.04.2016.
 */
@WebServlet(name = "TestServlet",  urlPatterns = "/all_buildings")
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*try {
            request.setAttribute("buildings_list", DAO.getAllBuildings());
        } catch (SQLException   | ClassNotFoundException e) {
            e.printStackTrace();
        }*/

            try {
                String arr = DAO.getAllBuildingsJSON().toString();
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                PrintWriter out = response.getWriter();
                out.print(arr);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
    }
}

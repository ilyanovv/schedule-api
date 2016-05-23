import restapi.DAO;
import restapi.TimeDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Илья on 15.05.2016.
 */
@WebServlet(name = "DeleteLessonServlet", urlPatterns = "/delete_lesson")
public class DeleteLessonServlet extends HttpServlet {
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("utf-8");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            Connection connection = DAO.getCon(login, password);
            String sqlCall = "{call " + DAO.DB_NAME + ".DELETE_LESSONS_ID(?,?)}";
            CallableStatement stmt = connection.prepareCall(sqlCall);
            stmt.setString("p_record_id", request.getParameter("record_id"));
            stmt.setInt("param", Integer.valueOf(request.getParameter("param")));
            int affRows = stmt.executeUpdate();
            System.out.println("affected rows: " + affRows);
            //connection.commit(); //стоит autocommit
            if (affRows > 0){
                response.setStatus(HttpServletResponse.SC_NO_CONTENT); //204
             }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            String arr = "Error has occured. Access denied";
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            out.print(arr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

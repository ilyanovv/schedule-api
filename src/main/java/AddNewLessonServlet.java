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
import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * Created by Илья on 15.05.2016.
 */
@WebServlet(name = "AddNewLessonServlet", urlPatterns = "/add_new_lesson")
public class AddNewLessonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("utf-8");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            Connection connection = DAO.getConnection(login, password);
            String sqlCall = "{call " + DAO.DB_NAME + ".ADD_LESSONS_ID(?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(sqlCall);
            stmt.setString("p_group_id", request.getParameter("group_id"));
            stmt.setString("p_discipline_id", request.getParameter("discipline_id"));
            stmt.setString("p_lesson_type_id", request.getParameter("lesson_type_id"));
            stmt.setString("p_lecture_room_id", request.getParameter("lecture_room_id"));
            stmt.setDate("p_lesson_date", TimeDate.getDate(request.getParameter("lesson_date")));
            stmt.setTime("p_time_begin", TimeDate.getTime(request.getParameter("time_begin")));
            stmt.setTime("p_time_end", TimeDate.getTime(request.getParameter("time_end")));
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

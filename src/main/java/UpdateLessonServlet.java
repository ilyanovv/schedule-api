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
@WebServlet(name = "UpdateLessonServlet", urlPatterns = "/update_lesson")
public class UpdateLessonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("start UpdateLessonServlet, " +
                    "request params = " + request.getParameterMap().values().toString());
            System.out.println("IN LESSON UPDATE");
            request.setCharacterEncoding("utf-8");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            Connection connection = DAO.getConnection(login, password);
            String sqlCall = "{call " + DAO.DB_NAME + ".UPDATE_LESSONS(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(sqlCall);
            stmt.setInt("p_record_id", Integer.valueOf(request.getParameter("record_id")));
            stmt.setInt("p_teacher_id", Integer.valueOf(request.getParameter("teacher_id")));
            stmt.setInt("p_lesson_type_id", Integer.valueOf(request.getParameter("lesson_type_id")));
            stmt.setInt("p_lesson_id", Integer.valueOf(request.getParameter("lesson_id")));
            stmt.setInt("p_lecture_room_id", Integer.valueOf(request.getParameter("lecture_room_id")));
            stmt.setInt("p_group_id", Integer.valueOf(request.getParameter("group_id")));
            stmt.setTime("p_time_begin", TimeDate.getTime(request.getParameter("time_begin")));
            stmt.setTime("p_time_end", TimeDate.getTime(request.getParameter("time_end")));
            stmt.setDate("p_lesson_date", TimeDate.getDate(request.getParameter("lesson_date")));
            stmt.setInt("param", Integer.valueOf(request.getParameter("param")));
            int affRows = stmt.executeUpdate();
            System.out.println("affected rows: " + affRows);
            //connection.commit(); //стоит autocommit
            if (affRows > 0){
                response.setStatus(HttpServletResponse.SC_NO_CONTENT); //204
                System.out.println("finish UpdateLessonServlet successfully");
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

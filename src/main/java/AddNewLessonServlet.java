import restapi.DAO;

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
@WebServlet(name = "AddNewLessonServlet", urlPatterns = "/add_new_lesson")
public class AddNewLessonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("utf-8");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            Connection connection = DAO.getCon(login, password);
            java.sql.Date lesson_date = java.sql.Date.valueOf("2016-05-15"); //yyyy-mm-dd
            String last_name = "Лукин";
            String first_name = "Владимир";
            String patronymic_name = "Николаевич";
            String building_name = "ГУК";
            String lecture_room = "444";
            String lesson_type = "Лекция";
            String lesson_name = "Базы данных";
            int group_id = 1;
            java.sql.Time time_begin = java.sql.Time.valueOf("09:00:00");
            java.sql.Time time_end = java.sql.Time.valueOf("10:30:00");
            int param = 0;
            String sqlCall = "{call " + DAO.DB_NAME + ".ADD_LESSONS(?,?,?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(sqlCall);
            stmt.setString("p_last_name", last_name);
            stmt.setString("p_first_name", first_name);
            stmt.setString("p_patronymic_name", patronymic_name);
            stmt.setString("p_building_name", building_name);
            stmt.setString("p_lecture_room_number", lecture_room);
            stmt.setString("p_lesson_type_name", lesson_type);
            stmt.setString("p_lesson_name", lesson_name);
            stmt.setInt("p_group_id", group_id);
            stmt.setTime("p_time_begin", time_begin);
            stmt.setTime("p_time_end", time_end);
            stmt.setDate("p_lesson_date", lesson_date);
            stmt.setInt("param", param);
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
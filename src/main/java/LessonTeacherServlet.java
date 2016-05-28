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
@WebServlet(name = "LessonTeacherServlet", urlPatterns = "/lesson_teacher")
public class LessonTeacherServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("utf-8");
            String lessonID = request.getParameter("lessonID");
            String arr = DAO.getLessonTeacherJSON(lessonID).toString();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            out.print(arr);
        } catch (SQLException  | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}

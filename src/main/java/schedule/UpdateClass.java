package schedule;

import connection.DBConnection;
import connection.context.Context;
import org.springframework.context.ApplicationContext;
import restapi.DAO;
import restapi.TimeDate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.sql.Types;

import static java.sql.Types.VARCHAR;

public class UpdateClass {
    private static ApplicationContext context = Context.getContext();
    private static DBConnection dbConnection = context.getBean(DBConnection.class);



    public static void getScheduleForGroup(String groupNumber)
            throws IOException, SQLException, ClassNotFoundException {
        Connection connection = dbConnection.getConnection("root", "root");

        String url = "https://mai.ru/education/schedule/data/" + groupNumber + ".txt";

        URL obj = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();
        urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
        urlConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

        urlConnection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine).append("\n");
            handleResponseLine(inputLine, groupNumber, connection);

        }
        in.close();

        System.out.println(response.toString());
        String[] split = response.toString().split("\n");
        connection.close();
    }


    private static void handleResponseLine(String line, String groupNumber,  Connection connection) {
        try {
            String[] params = line.split("\t");
            Date date = TimeDate.getDate(params[0].trim().replace("\uFEFF", ""));
            Time timeBegin = Time.valueOf(params[2].trim());
            Time timeEnd = Time.valueOf(params[3].trim());
            String lesson = params[4].trim();
            String teacher = params[5].equals("") ? null : params[5].trim();
            String[] teacherData = teacher != null ? teacher.split("\\s+", 3) : null;
            String teacherFirstName = teacherData != null ? teacherData[1] : null;
            String teacherLastName = teacherData != null ? teacherData[0] : null;
            String teacherPatronymicName = teacherData != null ? teacherData[2] : null;
            String lessonRoom = params[6].equals("") ? null : params[6].trim();
            String lessonType = params[7].trim();



            String sqlCall = "{call " + DAO.DB_NAME + ".INIT_ADD_LESSON(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(sqlCall);
            if (teacherLastName == null){
                stmt.setNull("p_last_name", VARCHAR);
            } else {
                stmt.setString("p_last_name", teacherLastName);
            }
            stmt.setString("p_first_name", teacherFirstName);
            stmt.setString("p_patronymic_name", teacherPatronymicName);

            if (lessonRoom == null) {
                stmt.setNull("p_lecture_room_number", VARCHAR);
            } else {
                stmt.setString("p_lecture_room_number", lessonRoom);
            }
            stmt.setString("p_lesson_type_name", lessonType);
            stmt.setString("p_lesson_name", lesson);
            stmt.setString("p_group_number", groupNumber);
            stmt.setTime("p_time_begin", timeBegin);
            stmt.setTime("p_time_end", timeEnd);
            stmt.setDate("p_lesson_date", date);

            int affRows = stmt.executeUpdate();
            stmt.close();
            System.out.println("affected rows: " + affRows);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        getScheduleForGroup("М8О-206Б-16");
        //getScheduleForGroup("МИО-107Бк-17");
    }
}

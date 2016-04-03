package restapi;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.ArrayList;


/**
 * Created by Илья on 29.03.2016.
 */
public class DAO {
    private static Connection connection = null;
    private static final String
            HOST = System.getenv("OPENSHIFT_MYSQL_DB_HOST"),
            PORT = System.getenv("OPENSHIFT_MYSQL_DB_PORT"),
            USERNAME = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME"),
            PASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD"),
            DB_NAME = "scheduledb";

    private static Connection getCon() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            System.out.println("Connected to MYDB");
        }
        return connection;
    }

    public static ArrayList<Building> getAllBuildings()
            throws SQLException, ClassNotFoundException
    {
        Connection c = getCon();
        ArrayList<Building> buildings_list = new ArrayList<Building>();
        PreparedStatement ps = c.prepareStatement("SELECT building_id, building_name " +
           "FROM building");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next())
        {
            int building_id = resultSet.getInt("building_id");
            String building_name = resultSet.getString("building_name");
            buildings_list.add(new Building(building_id, building_name));
        }
        return  buildings_list;
    }

    @SuppressWarnings("unchecked")
    public static JSONArray getAllBuildingsJSON()
            throws SQLException, ClassNotFoundException
    {
        Connection c = getCon();
        JSONArray jsonArray = new JSONArray();
        PreparedStatement ps = c.prepareStatement("SELECT building_id, building_name " +
                "FROM building");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next())
        {
            int building_id = resultSet.getInt("building_id");
            String building_name = resultSet.getString("building_name");
            JSONObject resultJson = new JSONObject();
            resultJson.put("building_id", building_id);
            resultJson.put("building_name", building_name);
            jsonArray.add(resultJson);
        }
        return  jsonArray;
    }

    @SuppressWarnings("unchecked")
    public static JSONArray getScheduleJSON()
            throws SQLException, ClassNotFoundException
    {
        Connection c = getCon();
        JSONArray jsonArray = new JSONArray();
        String groupNumber = "8О-408Б";
        PreparedStatement ps = c.prepareStatement("SELECT w.group_number, w.time_begin, w.time_end, w.lesson_type_name, w.lecture_room_number, \n" +
                "\t\tw.building_name, t.last_name, t.first_name, t.patronymic_name FROM (\n" +
                "\tSELECT z.group_number, z.time_begin, z.time_end, z.lesson_type_name, z.lecture_room_number, \n" +
                "\t\tdid, z.building_name, lesson_teacher.teacher_id FROM (\t\n" +
                "\t\tSELECT y.group_number, y.time_begin, y.time_end, y.lesson_type_name, y.lecture_room_number, \n" +
                "\t\ty.discipline_id, y.bid, building.building_name, y.did  FROM (\n" +
                "   \t \tSELECT group_tab.group_number, lesson_time.time_begin, lesson_time.time_end, lesson_type.lesson_type_name, lecture_room.lecture_room_number, \n" +
                "\t\tx.discipline_id, lecture_room.building_id as bid, x.discipline_id as did\n" +
                "\t\tFROM schedule_tab AS x\n" +
                "\t\t\tJOIN group_tab ON x.group_id = group_tab.group_id AND x.group_number = ? \n" +
                "\t\t\tJOIN lesson_time ON lesson_time.time_id = x.time_id\n" +
                "\t\t\tJOIN lesson_type ON lesson_type.lesson_type_id = x.lesson_type_id\n" +
                "\t\t\tJOIN lecture_room ON lecture_room.lecture_room_id = x.lecture_room_id) AS y\n" +
                "\tJOIN building ON bid = building.building_id) AS z \n" +
                "JOIN lesson_teacher ON did = lesson_teacher.discipline_id) AS w\n" +
                "JOIN teacher AS t ON w.teacher_id = t.teacher_id");
        ps.setString(1, groupNumber);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next())
        {
            JSONObject resultJson = new JSONObject();
            resultJson.put("time_begin", resultSet.getString("time_begin"));
            resultJson.put("time_end", resultSet.getString("time_end"));
            resultJson.put("lesson_type_name", resultSet.getString("lesson_type_name"));
            resultJson.put("lecture_room_number", resultSet.getString("lecture_room_number"));
            resultJson.put("building_name", resultSet.getString("building_name"));
            resultJson.put("last_name", resultSet.getString("last_name"));
            resultJson.put("first_name", resultSet.getString("first_name"));
            resultJson.put("patronymic_name", resultSet.getString("patronymic_name"));
            jsonArray.add(resultJson);
        }
        return  jsonArray;
    }

}
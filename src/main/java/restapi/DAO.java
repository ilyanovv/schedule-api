package restapi;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;


/**
 * Created by Илья on 29.03.2016.
 */
@SuppressWarnings("SqlResolve")
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
            Properties properties=new Properties();
            properties.setProperty("user",USERNAME);
            properties.setProperty("password",PASSWORD);
            /*
            настройки указывающие о необходимости конвертировать данные из Unicode
	        в UTF-8, который используется в нашей таблице для хранения данных
            */
            properties.setProperty("useUnicode","true");
            properties.setProperty("characterEncoding","UTF-8");
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
            connection = DriverManager.getConnection(url, properties);
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
        resultSet.close();
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
        resultSet.close();
        return  jsonArray;
    }

    @SuppressWarnings("unchecked")
    public static JSONArray getScheduleJSON(final String groupID)
            throws SQLException, ClassNotFoundException
    {
        Connection c = getCon();
        JSONArray jsonArray = new JSONArray();
       // String groupNumber = "8О-408Б";
        PreparedStatement ps = c.prepareStatement(SQLQueries.getScheduleForGroup);
        System.err.println(groupID);
        ps.setString(1, groupID);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next())
        {
            JSONObject resultJson = new JSONObject();
            resultJson.put("record_id", resultSet.getInt("record_id"));
            resultJson.put("time_begin", resultSet.getString("time_begin"));
            resultJson.put("time_end", resultSet.getString("time_end"));
            resultJson.put("lesson_type_name", resultSet.getString("lesson_type_name"));
            resultJson.put("lecture_room_number", resultSet.getString("lecture_room_number"));
            resultJson.put("building_name", resultSet.getString("building_name"));
            resultJson.put("last_name", resultSet.getString("last_name"));
            resultJson.put("first_name", resultSet.getString("first_name"));
            resultJson.put("patronymic_name", resultSet.getString("patronymic_name"));
            resultJson.put("lesson_date", resultSet.getString("lesson_date"));
            resultJson.put("lesson_name", resultSet.getString("lesson_name"));
            jsonArray.add(resultJson);
        }
        resultSet.close();
        return  jsonArray;
    }

    @SuppressWarnings("unchecked")
    public static JSONArray getAllGroupsJSON() throws SQLException, ClassNotFoundException {
        Connection c = getCon();
        JSONArray jsonArray = new JSONArray();
        PreparedStatement ps = c.prepareStatement(SQLQueries.getAllGroups);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("group_id", resultSet.getString("group_id"));
            jsonObject.put("group_number", resultSet.getString("group_number"));
            jsonObject.put("year_of_study", resultSet.getString("year_of_study"));
            jsonObject.put("version", resultSet.getString("version"));
            jsonArray.add(jsonObject);
        }
        resultSet.close();
        return jsonArray;
    }

    @SuppressWarnings("unchecked")
    public static JSONArray getAllTeachersJSON() throws SQLException, ClassNotFoundException {
        Connection c = getCon();
        JSONArray jsonArray = new JSONArray();
        PreparedStatement ps = c.prepareStatement(SQLQueries.getGetAllTeachers);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("teacher_id", resultSet.getString("teacher_id"));
            jsonObject.put("last_name", resultSet.getString("last_name"));
            jsonObject.put("first_name", resultSet.getString("first_name"));
            jsonObject.put("patronymic_name", resultSet.getString("patronymic_name"));
            jsonObject.put("version", resultSet.getString("version"));
            jsonArray.add(jsonObject);
        }
        resultSet.close();
        return  jsonArray;
    }

    @SuppressWarnings("unchecked")
    public static JSONArray getTeachersScheduleJSON(final String teacherID)
            throws SQLException, ClassNotFoundException {
        Connection c = getCon();
        JSONArray jsonArray = new JSONArray();
        PreparedStatement ps = c.prepareStatement(SQLQueries.getScheduleForTeacher);
        ps.setString(1, teacherID);
        ResultSet resultSet = ps.executeQuery();
        String prevLessonName = " ";
        String prevLessonType = " ";
        String prevTimeBegin = " ";
        JSONObject jsonObject = null;
        JSONArray jsonGroups = null;
        int i =0;
        while (resultSet.next()) {
            i++;
            String curLessonName = resultSet.getString("lesson_name");
            String curLessonType = resultSet.getString("lesson_type_name");
            String curTimeBegin = resultSet.getString("time_begin");
            if(curLessonName.equals(prevLessonName) &&
                    curLessonType.equals(prevLessonType) &&
                    curTimeBegin.equals(prevTimeBegin)){
               jsonGroups.add(resultSet.getString("group_number"));
            }
            else{
                if(jsonObject != null) {
                    jsonObject.put("groups", jsonGroups);
                    jsonArray.add(jsonObject);
                }
                jsonObject = new JSONObject();
                jsonGroups = new JSONArray();
                jsonObject.put("record_id", resultSet.getInt("record_id"));
                jsonObject.put("time_begin", curTimeBegin);
                jsonObject.put("time_end", resultSet.getString("time_end"));
                jsonObject.put("lesson_type_name", curLessonType);
                jsonObject.put("lecture_room_number", resultSet.getString("lecture_room_number"));
                jsonObject.put("building_name", resultSet.getString("building_name"));
                jsonObject.put("lesson_date", resultSet.getString("lesson_date"));
                jsonObject.put("lesson_name", curLessonName);
                jsonGroups.add(resultSet.getString("group_number"));
            }
            prevLessonName = curLessonName;
            prevLessonType = curLessonType;
            prevTimeBegin = curTimeBegin;
        }
        resultSet.close();
        System.out.println("i = " + i);
        return jsonArray;
    }

}
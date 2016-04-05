package restapi;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.*;
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
    public static JSONArray getScheduleJSON(final String groupNumber)
            throws SQLException, ClassNotFoundException
    {
        Connection c = getCon();
        JSONArray jsonArray = new JSONArray();
       // String groupNumber = "8О-408Б";
        PreparedStatement ps = c.prepareStatement(SQLQueries.getScheduleForGroup);
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
            resultJson.put("lesson_date", resultSet.getString("lesson_date"));
            resultJson.put("lesson_name", resultSet.getString("lesson_name"));
            jsonArray.add(resultJson);
        }
        return  jsonArray;
    }

}
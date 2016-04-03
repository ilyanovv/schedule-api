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
            DB_NAME = "shifar";

    private static Connection getCon() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
            connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            //connection = DriverManager.getConnection("jdbc:mysql:adminUlNAhCm@127.4.100.130/scheduledb", "adminUlNAhCm", "y4fmhUPK5iEe");
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
}
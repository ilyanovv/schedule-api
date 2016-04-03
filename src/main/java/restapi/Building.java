package restapi;

/**
 * Created by Илья on 29.03.2016.
 */
public class Building {
    int Building_id;
    String Building_name;

    public Building(int building_id, String building_name)
    {
        Building_id = building_id;
        Building_name = building_name;
    }

    public String getBuilding_name() {
        return Building_name;
    }

    public int getBuilding_id() {
        return Building_id;
    }

    public void setBuilding_id(int building_id) {
        Building_id = building_id;
    }

    public void setBuilding_name(String building_name) {
        Building_name = building_name;
    }

}

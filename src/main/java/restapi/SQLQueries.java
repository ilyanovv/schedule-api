package restapi;

/**
 * Created by Илья on 05.04.2016.
 */
public class SQLQueries {
    public static final String getScheduleForGroup = ""+
            "SELECT * FROM lesson JOIN \n" +
            "(SELECT DATE_FORMAT(w.lesson_date, '%m.%d.%Y') AS lesson_date,\n" +
            "        w.lesson_id, w.group_number, w.time_begin, w.time_end, w.lesson_type_name, w.lecture_room_number, \n" +
            "\t\tw.building_name, t.last_name, t.first_name, t.patronymic_name FROM (\n" +
            "\tSELECT z.lesson_date, z.group_number, z.time_begin, z.time_end, z.lesson_type_name, z.lecture_room_number, \n" +
            "\t\tdid, z.building_name, lesson_teacher.teacher_id, lesson_teacher.lesson_id FROM (\t\n" +
            "\t\tSELECT y.group_number, y.time_begin, y.time_end, y.lesson_type_name, y.lecture_room_number, \n" +
            "\t\ty.discipline_id, y.bid, building.building_name, y.did, y.lesson_date  FROM (\n" +
            "   \t \tSELECT group_tab.group_number, lesson_time.time_begin, lesson_time.time_end, lesson_type.lesson_type_name, lecture_room.lecture_room_number, \n" +
            "\t\tx.discipline_id, lecture_room.building_id as bid, x.discipline_id as did, x.lesson_date \n" +
            "\t\tFROM schedule_tab AS x\n" +
            "\t\t\tJOIN group_tab ON x.group_id = group_tab.group_id AND group_tab.group_number = ? \n" +
            "\t\t\tJOIN lesson_time ON lesson_time.time_id = x.time_id\n" +
            "\t\t\tJOIN lesson_type ON lesson_type.lesson_type_id = x.lesson_type_id\n" +
            "\t\t\tJOIN lecture_room ON lecture_room.lecture_room_id = x.lecture_room_id) AS y\n" +
            "\tJOIN building ON bid = building.building_id) AS z \n" +
            "JOIN lesson_teacher ON did = lesson_teacher.discipline_id) AS w\n" +
            "JOIN teacher AS t ON w.teacher_id = t.teacher_id) AS a\n" +
            "ON lesson.lesson_id = a.lesson_id";
}
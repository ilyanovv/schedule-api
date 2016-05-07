package restapi;

/**
 * Created by Илья on 05.04.2016.
 */
class SQLQueries {
    static final String getScheduleForGroup = ""+
            "SELECT * FROM lesson JOIN \n" +
            "(SELECT DATE_FORMAT(w.lesson_date, '%d.%m.%Y') AS lesson_date,\n" +
            " w.record_id, w.lesson_id, w.group_number, " +
            "TIME_FORMAT(w.time_begin,  '%H:%i') AS time_begin, " +
            "TIME_FORMAT(w.time_end,  '%H:%i' ) AS time_end, w.lesson_type_name, w.lecture_room_number, \n" +
            "\t\tw.building_name, t.last_name, t.first_name, t.patronymic_name FROM (\n" +
            "\tSELECT z.record_id, z.lesson_date, z.group_number, z.time_begin, z.time_end, z.lesson_type_name, z.lecture_room_number, \n" +
            "\t\tdid, z.building_name, lesson_teacher.teacher_id, lesson_teacher.lesson_id FROM (\t\n" +
            "\t\tSELECT y.group_number, y.time_begin, y.time_end, y.lesson_type_name, y.lecture_room_number, \n" +
            "\t\ty.discipline_id, y.record_id, y.bid, building.building_name, y.did, y.lesson_date  FROM (\n" +
            "   \t \tSELECT group_tab.group_number, lesson_time.time_begin, lesson_time.time_end, lesson_type.lesson_type_name, lecture_room.lecture_room_number, \n" +
            "\t\tx.discipline_id, lecture_room.building_id as bid, x.discipline_id as did, x.lesson_date, x.record_id \n" +
            "\t\tFROM schedule_tab AS x\n" +
            "\t\t\tJOIN group_tab ON x.group_id = group_tab.group_id AND group_tab.group_id = ? \n" +
            "\t\t\tJOIN lesson_time ON lesson_time.time_id = x.time_id\n" +
            "\t\t\tJOIN lesson_type ON lesson_type.lesson_type_id = x.lesson_type_id\n" +
            "\t\t\tJOIN lecture_room ON lecture_room.lecture_room_id = x.lecture_room_id) AS y\n" +
            "\tJOIN building ON bid = building.building_id) AS z \n" +
            "JOIN lesson_teacher ON did = lesson_teacher.discipline_id) AS w\n" +
            "JOIN teacher AS t ON w.teacher_id = t.teacher_id) AS a\n" +
            "ON lesson.lesson_id = a.lesson_id " +
            "ORDER BY lesson_date, time_begin";

    static final String getAllGroups = "SELECT * \n" +
            "FROM group_tab x\n" +
            "JOIN schedule_version_group y ON x.group_id = y.group_id";

    static final String getGetAllTeachers = "SELECT * FROM teacher x \n" +
            "JOIN schedule_version_teacher y \n" +
            "ON x.teacher_id = y.teacher_id";

    static final String getScheduleForTeacher = ""+
            "SELECT * FROM lesson JOIN \n" +
            "(SELECT DATE_FORMAT(w.lesson_date, '%d.%m.%Y') AS lesson_date,\n" +
            " w.record_id, w.lesson_id, w.group_number, " +
            "TIME_FORMAT(w.time_begin, '%H:%i') AS time_begin, " +
            "TIME_FORMAT(w.time_end, '%H:%i') AS time_end, w.lesson_type_name, w.lecture_room_number, \n" +
            "\t\tw.building_name, t.last_name, t.first_name, t.patronymic_name FROM (\n" +
            "\tSELECT z.record_id, z.lesson_date, z.group_number, z.time_begin, z.time_end, z.lesson_type_name, z.lecture_room_number, \n" +
            "\t\tdid, z.building_name, lesson_teacher.teacher_id, lesson_teacher.lesson_id FROM (\t\n" +
            "\t\tSELECT y.group_number, y.time_begin, y.time_end, y.lesson_type_name, y.lecture_room_number, \n" +
            "\t\ty.discipline_id, y.record_id, y.bid, building.building_name, y.did, y.lesson_date  FROM (\n" +
            "   \t \tSELECT group_tab.group_number, lesson_time.time_begin, lesson_time.time_end, lesson_type.lesson_type_name, lecture_room.lecture_room_number, \n" +
            "\t\tx.discipline_id, lecture_room.building_id as bid, x.discipline_id as did, x.lesson_date, x.record_id \n" +
            "\t\tFROM schedule_tab AS x\n" +
            "\t\t\tJOIN group_tab ON x.group_id = group_tab.group_id\n" +
            "\t\t\tJOIN lesson_time ON lesson_time.time_id = x.time_id\n" +
            "\t\t\tJOIN lesson_type ON lesson_type.lesson_type_id = x.lesson_type_id\n" +
            "\t\t\tJOIN lecture_room ON lecture_room.lecture_room_id = x.lecture_room_id) AS y\n" +
            "\tJOIN building ON bid = building.building_id) AS z \n" +
            "JOIN lesson_teacher ON did = lesson_teacher.discipline_id) AS w\n" +
            "JOIN teacher AS t ON w.teacher_id = t.teacher_id AND w.teacher_id = ?) AS a\n" +
            "ON lesson.lesson_id = a.lesson_id " +
            "ORDER BY lesson_date, time_begin, group_number";
}

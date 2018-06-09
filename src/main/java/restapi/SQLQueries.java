package restapi;

/**
 * Created by Илья on 05.04.2016.
 */
public class SQLQueries {
    public static final String GET_SCHEDULE_FOR_GROUP = ""+
            "SELECT * FROM lesson JOIN \n" +
            "(SELECT DATE_FORMAT(w.lesson_date, '%d.%m.%Y') AS lesson_date,\n" +
            " w.record_id, w.lesson_id, w.group_number, w.is_local, w.global_ver, " +
            "TIME_FORMAT(w.time_begin,  '%H:%i') AS time_begin, " +
            "TIME_FORMAT(w.time_end,  '%H:%i' ) AS time_end, w.lesson_type_name, w.lecture_room_number, \n" +
            "\t\tw.building_name, t.last_name, t.first_name, t.patronymic_name FROM (\n" +
            "\tSELECT z.record_id, z.lesson_date, z.group_number, z.time_begin, z.time_end, z.lesson_type_name, z.lecture_room_number, z.is_local, z.global_ver, \n" +
            "\t\tdid, z.building_name, lesson_teacher.teacher_id, lesson_teacher.lesson_id FROM (\t\n" +
            "\t\tSELECT y.group_number, y.time_begin, y.time_end, y.lesson_type_name, y.lecture_room_number, \n" +
            "\t\ty.discipline_id, y.record_id, y.bid, building.building_name, y.did, y.lesson_date, y.is_local, y.global_ver  FROM (\n" +
            "   \t \tSELECT group_tab.group_number, time_begin, time_end, lesson_type.lesson_type_name, lecture_room.lecture_room_number, \n" +
            "\t\tx.discipline_id, lecture_room.building_id as bid, x.discipline_id as did, x.lesson_date, x.record_id, x.is_local, x.global_ver \n" +
            "\t\tFROM schedule_tab AS x\n" +
            "\t\t\tJOIN group_tab ON x.group_id = group_tab.group_id AND group_tab.group_id = ? \n" +
            "\t\t\tJOIN lesson_type ON lesson_type.lesson_type_id = x.lesson_type_id\n" +
            "\t\t\tJOIN lesson_time ON x.time_id = lesson_time.time_id\n" +
            "\t\t\tLEFT JOIN lecture_room ON lecture_room.lecture_room_id = x.lecture_room_id) AS y\n" +
            "\tLEFT JOIN building ON bid = building.building_id) AS z \n" +
            "JOIN lesson_teacher ON did = lesson_teacher.discipline_id) AS w\n" +
            "JOIN teacher AS t ON w.teacher_id = t.teacher_id) AS a\n" +
            "ON lesson.lesson_id = a.lesson_id " +
            "ORDER BY UNIX_TIMESTAMP(STR_TO_DATE(lesson_date, '%d.%m.%Y')), time_begin";

    public static final String GET_ALL_GROUPS = "SELECT * \n" +
            "FROM group_tab x\n" +
            "JOIN schedule_version_group y ON x.group_id = y.group_id " +
            "ORDER BY group_number";

    public static final String GET_ALL_TEACHERS = "SELECT * FROM teacher x \n" +
            "JOIN schedule_version_teacher y \n" +
            "ON x.teacher_id = y.teacher_id " +
            "ORDER BY last_name, first_name, patronymic_name";

    public static final String GET_SCHEDULE_FOR_TEACHER = ""+
            "SELECT * FROM lesson JOIN \n" +
            "(SELECT DATE_FORMAT(w.lesson_date, '%d.%m.%Y') AS lesson_date,\n" +
            " w.record_id, w.lesson_id, w.group_number, w.is_local, w.global_ver, " +
            "TIME_FORMAT(w.time_begin, '%H:%i') AS time_begin, " +
            "TIME_FORMAT(w.time_end, '%H:%i') AS time_end, w.lesson_type_name, w.lecture_room_number, \n" +
            "\t\tw.building_name, t.last_name, t.first_name, t.patronymic_name FROM (\n" +
            "\tSELECT z.record_id, z.lesson_date, z.group_number, z.time_begin, z.time_end, z.lesson_type_name, z.lecture_room_number, z.is_local, z.global_ver, \n" +
            "\t\tdid, z.building_name, lesson_teacher.teacher_id, lesson_teacher.lesson_id FROM (\t\n" +
            "\t\tSELECT y.group_number, y.time_begin, y.time_end, y.lesson_type_name, y.lecture_room_number, \n" +
            "\t\ty.discipline_id, y.record_id, y.bid, building.building_name, y.did, y.lesson_date, y.is_local, y.global_ver  FROM (\n" +
            "   \t \tSELECT group_tab.group_number, time_begin, time_end, lesson_type.lesson_type_name, lecture_room.lecture_room_number, \n" +
            "\t\tx.discipline_id, lecture_room.building_id as bid, x.discipline_id as did, x.lesson_date, x.record_id, x.is_local, x.global_ver \n" +
            "\t\tFROM schedule_tab AS x\n" +
            "\t\t\tJOIN group_tab ON x.group_id = group_tab.group_id\n" +
            "\t\t\tJOIN lesson_type ON lesson_type.lesson_type_id = x.lesson_type_id\n" +
            "\t\t\tJOIN lesson_time ON x.time_id = lesson_time.time_id\n" +
            "\t\t\tLEFT JOIN lecture_room ON lecture_room.lecture_room_id = x.lecture_room_id) AS y\n" +
            "\tLEFT JOIN building ON bid = building.building_id) AS z \n" +
            "JOIN lesson_teacher ON did = lesson_teacher.discipline_id) AS w\n" +
            "JOIN teacher AS t ON w.teacher_id = t.teacher_id AND w.teacher_id = ?) AS a\n" +
            "ON lesson.lesson_id = a.lesson_id " +
            "ORDER BY UNIX_TIMESTAMP(STR_TO_DATE(lesson_date, '%d.%m.%Y')), time_begin, group_number";

    public static final String GET_GROUP_DB_VERSION = "SELECT version FROM schedule_version_group WHERE group_id = ?";
    public static final String GET_TEACHER_DB_VERSION = "SELECT version FROM schedule_version_teacher WHERE teacher_id = ?";

    public static final String GET_ALL_BUILDINGS = "SELECT * FROM building ORDER BY building_name";
    public static final String GET_LESSON_ROOMS = "SELECT * FROM lecture_room WHERE building_id = ? ORDER BY lecture_room_number";
  //  static final String getLessons = "SELECT * FROM lesson_teacher x JOIN lesson y ON x.lesson_id = y.lesson_id WHERE teacher_id = ?";
    public static final String GET_LESSON_TYPES = "SELECT * FROM lesson_type";

    public static final String GET_ALL_LESSONS = "SELECT * FROM lesson ORDER BY lesson_name";
    static final String getTeachers = "SELECT * FROM lesson_teacher x JOIN teacher y ON x.teacher_id = y.teacher_id " +
            "WHERE lesson_id = ? ORDER BY last_name";

    public static final String GET_ALL_LESSON_ROOMS = "SELECT * FROM lecture_room";

    public static final String GET_GROUP_NUMBER_BY_ID = "SELECT group_number FROM group_tab WHERE group_id = ?";
}

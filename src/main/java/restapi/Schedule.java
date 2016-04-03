package restapi;

/**
 * Created by Илья on 04.04.2016.
 */
public class Schedule {
    String Group_number;
    String Time_begin;
    String Time_end;
    String Lesson_type_name;
    String Lecture_room_number;
    String Building_name;
    String Last_name;
    String First_name;
    String Patronymic_name;

    public void setBuilding_name(String building_name) {
        Building_name = building_name;
    }

    public String getBuilding_name() {
        return Building_name;
    }

    public String getGroup_number() {
        return Group_number;
    }

    public String getLesson_type_name() {
        return Lesson_type_name;
    }

    public String getTime_begin() {
        return Time_begin;
    }

    public String getFirst_name() {
        return First_name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public String getLecture_room_number() {
        return Lecture_room_number;
    }

    public String getPatronymic_name() {
        return Patronymic_name;
    }

    public String getTime_end() {
        return Time_end;
    }

    public void setFirst_name(String first_name) {
        First_name = first_name;
    }

    public void setGroup_number(String group_number) {
        Group_number = group_number;
    }

    public void setLast_name(String last_name) {
        Last_name = last_name;
    }

    public void setLecture_room_number(String lecture_room_number) {
        Lecture_room_number = lecture_room_number;
    }

    public void setLesson_type_name(String lesson_type_name) {
        Lesson_type_name = lesson_type_name;
    }

    public void setPatronymic_name(String patronymic_name) {
        Patronymic_name = patronymic_name;
    }

    public void setTime_begin(String time_begin) {
        Time_begin = time_begin;
    }

    public void setTime_end(String time_end) {
        Time_end = time_end;
    }
}



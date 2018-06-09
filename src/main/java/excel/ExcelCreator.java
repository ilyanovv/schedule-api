package excel;

import javafx.util.Pair;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import restapi.DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ExcelCreator {
    private Workbook workbook;

    public ExcelCreator() {
        workbook =  new XSSFWorkbook();
    }

    public Workbook create(String sheetName) throws SQLException, ClassNotFoundException {
        Sheet sheet = workbook.createSheet(sheetName);
        for (Pair<String, String> group : getAllGroups()) {
            writeScheduleForGroup(sheet, group);
        }
        return workbook;
    }

    private void writeScheduleForGroup(Sheet sheet, Pair<String, String> group)
            throws SQLException, ClassNotFoundException {
        JSONArray jsonArray = DAO.getScheduleJSON(group.getKey());
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
            row.createCell(0).setCellValue(group.getValue());
            row.createCell(1).setCellValue((String) jsonObject.get("lesson_date"));
            row.createCell(2).setCellValue((String) jsonObject.get("time_begin"));
            row.createCell(3).setCellValue((String) jsonObject.get("time_end"));
            row.createCell(4).setCellValue((String) jsonObject.get("lesson_name"));
            row.createCell(5).setCellValue((String) jsonObject.get("last_name") + " " +
                    (String) jsonObject.get("first_name") + " " +
                    (String) jsonObject.get("patronymic_name"));
            row.createCell(6).setCellValue((String) jsonObject.get("lecture_room_number"));
            row.createCell(7).setCellValue((String) jsonObject.get("lesson_type_name"));

        }
    }


    private List<Pair<String, String>> getAllGroups() throws SQLException, ClassNotFoundException {
        JSONArray jsonArray = DAO.getAllGroupsJSON();
        List<Pair<String, String>> list = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            list.add(new Pair<>((String) jsonObject.get("group_id"),
                    (String) jsonObject.get("group_number")));
        }
        return list;
    }


}

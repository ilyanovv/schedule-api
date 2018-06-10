package excel;

import javafx.util.Pair;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import restapi.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExcelCreator {
    private static final int COLUMN_COUNT = 8;
    private Workbook workbook;

    public ExcelCreator() {
        workbook =  new XSSFWorkbook();
    }

    public Workbook create(String sheetName) throws SQLException, ClassNotFoundException {
        Sheet sheet = workbook.createSheet(sheetName);
        createFirstRow(sheet);

        for (Pair<String, String> group : getAllGroups()) {
            writeScheduleForGroup(sheet, group);
        }
//        for (int i = 0; i < COLUMN_COUNT; i++) {
//            sheet.autoSizeColumn(i);
//        }
        return workbook;
    }

    private void createFirstRow(Sheet sheet) {
        Row row = sheet.createRow(sheet.getPhysicalNumberOfRows());
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short)10);
        font.setBold(true);
        style.setFont(font);

        row.createCell(0).setCellValue("Номер группы");
        row.getCell(0).setCellStyle(style);
        row.createCell(1).setCellValue("Дата");
        row.getCell(1).setCellStyle(style);
        row.createCell(2).setCellValue("Начало");
        row.getCell(2).setCellStyle(style);
        row.createCell(3).setCellValue("Конец");
        row.getCell(3).setCellStyle(style);
        row.createCell(4).setCellValue("Предмет");
        row.getCell(4).setCellStyle(style);
        row.createCell(5).setCellValue("ФИО преподавателя");
        row.getCell(5).setCellStyle(style);
        row.createCell(6).setCellValue("Аудитория");
        row.getCell(6).setCellStyle(style);
        row.createCell(7).setCellValue("Тип занятия");
        row.getCell(7).setCellStyle(style);
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

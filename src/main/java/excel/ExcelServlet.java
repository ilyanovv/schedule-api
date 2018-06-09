package excel;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "ExcelServlet", urlPatterns = "/get_xlsx")
public class ExcelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //XLSX format
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "inline; filename=\"my.xlsx\"");
            ExcelCreator excelCreator = new ExcelCreator();
            Workbook wb = excelCreator.create("schedule");
// Write the output
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            String arr = "Error has occurred. No answer";
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = response.getWriter();
            out.print(arr);
        }
    }
}

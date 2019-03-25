package employee;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Row;

import java.text.SimpleDateFormat;
import java.util.* ;

public class XlsFile {

    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet ;
    private static Row row;
    private static Cell cell;
    private static FileOutputStream fileOut ;

    String filename = "C:\\Users\\ea22mfd\\IdeaProjects\\EmployeeDetails\\EmployeeSearch";

    public XlsFile () {

        Date date = new Date () ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd") ;
        String yyyymmdd = dateFormat.format(date) ;
        filename=filename+yyyymmdd+".xlsx";

        workbook =new XSSFWorkbook() ;
        sheet = workbook.createSheet("Employees");

        // Create the Row Header
        row = sheet.createRow(0);
        // Create cells
        row.createCell(0).setCellValue("Corp Key");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Email");
        row.createCell(3).setCellValue("Tribe");

        try {
            fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void XlsWrite (String corpKey, String name, String email , String tribe) {

        int rownum = sheet.getLastRowNum() ;
        row = sheet.createRow(++rownum);
        row.createCell(0).setCellValue(corpKey);
        row.createCell(1).setCellValue(name);
        row.createCell(2).setCellValue(email);
        row.createCell(3).setCellValue(tribe);

        try {
            fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        XlsFile xlsfile = new XlsFile();

    }

}

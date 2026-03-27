
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class Example1_7 {
    public static void main(String[] args) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Товары");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Товар");
        headerRow.createCell(1).setCellValue("Характеристики");
        headerRow.createCell(2).setCellValue("Стоимость");

        Row dataRow1 = sheet.createRow(1);
        dataRow1.createCell(0).setCellValue("Книга");
        dataRow1.createCell(1).setCellValue("Жанр: Фантастика, Автор: Иванов И.И.");
        dataRow1.createCell(2).setCellValue(500.0);

        Row dataRow2 = sheet.createRow(2);
        dataRow2.createCell(0).setCellValue("Компьютер");
        dataRow2.createCell(1).setCellValue("Процессор: Intel Core i5, Память 8гб");
        dataRow2.createCell(2).setCellValue(25000.0);

        String filepath = "src/main/java/example1_7.xlsx";
        FileOutputStream outputstream = new FileOutputStream(filepath);
        workbook.write(outputstream);
        workbook.close();
        outputstream.close();

        System.out.println("Данные записаны в файл: " + filepath);
    }
}

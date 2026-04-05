import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Example5 {
    public static void main(String[] args) {
        String filepath = "src/main/java/example1_7.xlsx";
        //Для проверки обработки ошибки отсутствия файла:
        //filepath = "BADPATH.XLSX";
        try (FileInputStream inputStream = new FileInputStream(filepath)) {

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            XSSFSheet sheet = workbook.getSheet("Товары");
            //Для проверки обработки ошибки некорректного названия листа:
            //sheet = workbook.getSheet("Товарыыыыыыыы");

            if (sheet == null) {
                throw new NullPointerException();
            }

            for (Row row : sheet) {
                for (Cell cell : row) {
                    System.out.print(cell.toString() + "\t");
                }
                System.out.println();
            }
            workbook.close();
        } catch (NullPointerException npe) {
            System.out.println("Лист с указанным именем не существует, проверьте корректность названия листа");
        } catch (FileNotFoundException fnfe) {
            System.out.println("Файл не найден, проверьте указанный путь к файлу, а также наличие файла");
        } catch (IOException ioe) {
            System.out.println("IO ошибка, проверьте корректность написания кода");
        }
    }
}

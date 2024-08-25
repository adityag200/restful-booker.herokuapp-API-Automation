package Utils;

import RequestPOJO.BookingDates;
import RequestPOJO.CreateBooking;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {

    public static List<CreateBooking> readExcel(String filePath, String sheetName) {
        List<CreateBooking> request = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            for (Row currentRow : sheet) {
                Iterator<Cell> cellsInRow = currentRow.iterator();

                // Assuming the Excel file has columns in this order: firstName, lastName, totalPrice, depositPaid, checkin, checkout, additionalNeeds
                String firstName = getCellValueAsString(cellsInRow.next());
                String lastName = getCellValueAsString(cellsInRow.next());
                int totalPrice = (int) Double.parseDouble(getCellValueAsString(cellsInRow.next()));
                boolean depositPaid = Boolean.parseBoolean(getCellValueAsString(cellsInRow.next()));
                String checkin = getCellValueAsString(cellsInRow.next());
                String checkout = getCellValueAsString(cellsInRow.next());
                String additionalNeeds = getCellValueAsString(cellsInRow.next());

                BookingDates bookingDates = new BookingDates(checkin, checkout);
                CreateBooking booking = new CreateBooking(firstName, lastName, totalPrice, depositPaid, bookingDates, additionalNeeds);

                request.add(booking);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return request;
    }

    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}
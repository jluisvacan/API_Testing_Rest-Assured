package files;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class dataDriven {

    public ArrayList<String> getData(String testcaseName, String sheetName) throws IOException {


        ArrayList<String> a = new ArrayList<>();
        //Create object to get hold of excel
        FileInputStream fis = new FileInputStream("C:\\Users\\VN\\Documents\\Academia\\RestAssured\\project\\demodata.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        //Get access to data Sheet
        int sheets = workbook.getNumberOfSheets();
        for(int i=0; i<sheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)){
                XSSFSheet sheet = workbook.getSheetAt(i);

                //Get access to specific cells of Rows
                Iterator<Row> rows = sheet.iterator();
                Row firstRow = rows.next();
                Iterator<Cell> cell = firstRow.cellIterator();

                int k=0;
                int coloumn = 0;
                while(cell.hasNext()){
                    Cell value = cell.next();
                    if (value.getStringCellValue().equalsIgnoreCase("Testcases")){
                        coloumn = k;
                    }
                    k++;
                }

                System.out.println("->  Column: "+coloumn);


                while(rows.hasNext()){
                    Row r = rows.next();
                    if (r.getCell(coloumn).getStringCellValue().equalsIgnoreCase(testcaseName)){
                        Iterator<Cell> cv = r.cellIterator();
                        while(cv.hasNext()){

                            Cell c = cv.next();
                            if(c.getCellType()== CellType.STRING){
                                a.add(c.getStringCellValue());
                            } else
                                a.add(NumberToTextConverter.toText(c.getNumericCellValue()));

                        }
                    }
                }

            }


        }
        return a;
    }
}

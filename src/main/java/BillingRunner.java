import com.opencsv.exceptions.CsvException;
import org.w3c.dom.Text;

import java.io.*;

public class BillingRunner {
    public static void main(String[] args) throws IOException, CsvException {
        //System.out.println("Program ran");

        String csvPath = "sample.csv";
        String textFilePath = "sample.txt";

        CSVReader.Read(csvPath);
        TextFileReader.Read(textFilePath);
    }
}

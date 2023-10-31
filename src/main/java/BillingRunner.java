import com.opencsv.exceptions.CsvException;
import java.io.*;
import java.util.List;

public class BillingRunner {
    public static void main(String[] args) throws IOException, CsvException {
        //System.out.println("Program ran");

        String csvPath = "sample.csv";
        String textFilePath = "sample.txt";

        List<String[]> myCSV = HandlerCSV.Read(csvPath);
        String text = HandlerTxt.Read(textFilePath);

        HandlerCSV.Print(myCSV);
        System.out.println(text);

        System.out.println("Finished");
    }
}

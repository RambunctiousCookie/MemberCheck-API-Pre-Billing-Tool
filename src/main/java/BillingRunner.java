import Service.ApiService;
import Service.HandlerCSV;
import Service.HandlerTxt;
import com.google.gson.JsonElement;
import com.opencsv.exceptions.CsvException;
import java.io.*;
import java.util.List;

public class BillingRunner {
    public static void main(String[] args) throws IOException, CsvException {
        String csvPath = "sample.csv";
        String textFilePath = "sample.txt";

        List<String[]> myCSV = HandlerCSV.Read(csvPath);
        String text = HandlerTxt.Read(textFilePath);

        HandlerCSV.Print(myCSV);
        System.out.println(text);

        String apiKey = "632b36d11b82451380165944921ce1ee";
        String orgId = "John_Org";
        try{
            JsonElement jsonElement= ApiService.fetchSingleMemberScanData(apiKey, orgId);
            System.out.println("Success");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        System.out.println("Finished");
    }
}

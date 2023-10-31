import Service.ApiService;
import Util.DateUtil;
import Util.HandlerCSV;
import Util.HandlerTxt;
import com.google.gson.JsonElement;
import com.opencsv.exceptions.CsvException;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class BillingRunner {
    public static void main(String[] args) throws IOException, CsvException {
        String csvPath = "/sample.csv";
        String textFilePath = "/sample.txt";

        List<String[]> myCSV = HandlerCSV.Read(csvPath);
        String text = HandlerTxt.Read(textFilePath);

        HandlerCSV.Print(myCSV);
        System.out.println(text);

        ApiService apiService = new ApiService("632b36d11b82451380165944921ce1ee");
        String orgId = "John_Org";
        try{
            LocalDate[] desiredDate = DateUtil.getQuartileDates(2023,4);
            JsonElement jsonElement= apiService.fetchSingleMemberScanData(orgId, desiredDate[0],desiredDate[1]);

            System.out.println("Elements: " + jsonElement.getAsJsonArray().size());

            System.out.println("API Call Success");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Finished");
    }
}

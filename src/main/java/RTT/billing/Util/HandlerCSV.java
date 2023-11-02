package RTT.billing.Util;

import com.opencsv.exceptions.CsvException;
import java.io.*;
import java.util.List;
import java.util.Map;

public class HandlerCSV {
    public static List<String[]> read(String csvPath) throws IOException, CsvException {
        InputStream inputStream = HandlerCSV.class.getResourceAsStream(csvPath);
        if (inputStream ==null)
            throw new FileNotFoundException();

        Reader reader = new InputStreamReader(inputStream);

        com.opencsv.CSVReader csvReader = new com.opencsv.CSVReader(reader);
        List<String[]> records = csvReader.readAll();
        csvReader.close();

        return records;
    }

    public static void Print(List<String[]> records){
        for (String[] record : records) {
            for (String s : record) {
                System.out.print(s);
                System.out.print("\t");
            }
            System.out.println();
        }
    }

//    public static void WriteOrgTree(){
//        //TODO: implement
//    }

    public static void WriteUsage(Map<String,Integer> usageList, String path){

        try (FileWriter csvWriter = new FileWriter(path)) {
            // Write the header row
            csvWriter.append("Org_Id, Usage Statistics\n");

            // Iterate over the map and write each entry to the CSV file
            for (Map.Entry<String, Integer> entry : usageList.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                csvWriter.append(key).append(",").append(value.toString()).append("\n");
            }

            System.out.println("Data has been written to " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void WriteMonitoredScanDetails(){
        //TODO: implement
    }

}
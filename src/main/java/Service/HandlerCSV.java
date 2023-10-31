package Service;

import com.opencsv.exceptions.CsvException;
import java.io.*;
import java.util.List;

public class HandlerCSV {
    public static List<String[]> Read(String csvPath) throws IOException, CsvException {
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

}
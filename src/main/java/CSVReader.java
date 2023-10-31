import com.opencsv.exceptions.CsvException;
import java.io.*;
import java.util.List;

public class CSVReader {
    String csvPath = "sample.csv";

    public static void Read(String csvPath) throws IOException, CsvException {
        InputStream inputStream = CSVReader.class.getResourceAsStream(csvPath);
        if (inputStream ==null)
            throw new FileNotFoundException();

        Reader reader = new InputStreamReader(inputStream);

        com.opencsv.CSVReader csvReader = new com.opencsv.CSVReader(reader);
        List<String[]> records = csvReader.readAll();

        for (String[] record : records) {
            // Assuming a sample CSV with two columns (name and age)
            String name = record[0];
            String age = record[1];

            System.out.println("Name: " + name + ", Age: " + age);
        }
        csvReader.close();

    }
}
import com.opencsv.exceptions.CsvException;


import java.io.*;
import java.util.List;


public class CSVReader {
    public static void main(String[] args) {
        InputStream inputStream = CSVReader.class.getResourceAsStream("sample.csv");
        Reader reader = new InputStreamReader(inputStream);

        com.opencsv.CSVReader csvReader = new com.opencsv.CSVReader(reader);

        //String csvFile = "sample.csv"; // Replace with the path to your CSV file

        try {
//            FileReader fileReader = new FileReader(csvFile);
//            CSVReader csvReader = new CSVReader(fileReader);l

            List<String[]> records = csvReader.readAll();

            for (String[] record : records) {
                // Assuming a sample CSV with two columns (name and age)
                String name = record[0];
                String age = record[1];

                System.out.println("Name: " + name + ", Age: " + age);
            }

            csvReader.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
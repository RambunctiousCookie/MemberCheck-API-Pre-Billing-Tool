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
//        for (String[] record : records) {
//            //TODO: modify to fit current design
//            // Assuming a sample CSV with two columns (name and age)
////            String name = record[0];
////            String age = record[1];
////
////            System.out.println("Name: " + name + ", Age: " + age);
//
//
//
//        }
        for(int i=0;i<records.size();i++){
            for(int j=0;j<records.get(i).length;j++){
                System.out.print(records.get(i)[j]);
                System.out.print("\t\t");
            }
            System.out.println();
        }
    }

}
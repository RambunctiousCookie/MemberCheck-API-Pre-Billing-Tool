import java.io.*;

public class TextFileReader {
    public static void main(String[] args) throws IOException {
        String textFilePath = "sample.txt"; // Replace with the path to your text file

        InputStream inputStream = TextFileReader.class.getResourceAsStream(textFilePath);
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }
}

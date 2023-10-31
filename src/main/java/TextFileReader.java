import java.io.*;

public class TextFileReader {
    String textFilePath = "sample.txt";
    public static void Read(String textFilePath) throws IOException {

        InputStream inputStream = TextFileReader.class.getResourceAsStream(textFilePath);

        if(inputStream==null)
            throw new IOException("InputStream is Null!");

        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }
}

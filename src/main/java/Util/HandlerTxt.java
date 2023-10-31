package Util;

import java.io.*;

public class HandlerTxt {
    public static String Read(String textFilePath) throws IOException {

        InputStream inputStream = HandlerTxt.class.getResourceAsStream(textFilePath);

        if(inputStream==null)
            throw new IOException("InputStream is Null!");

        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line); // Append each line to the StringBuilder
            stringBuilder.append("\n");
        }
        reader.close();
        return stringBuilder.toString();
    }


}

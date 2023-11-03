package RTT.billing.Util;

import RTT.billing.Config.SystemParameters;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HandlerYaml {

    private static final String paramPath = "/config.yaml";
    //private static final String paramPath="src/main/resources/config.yaml";
    public static final SystemParameters parameters = loadParams();
    private static SystemParameters loadParams() {
        try{

            InputStream inputStream = HandlerYaml.class.getResourceAsStream(paramPath);
            if (inputStream == null) {
                throw new FileNotFoundException("Could not find resource: " + paramPath);
            }
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            YamlReader yamlReader = new YamlReader(reader);
            SystemParameters parameters = yamlReader.read(SystemParameters.class);
            reader.close();
            return parameters;

//            YamlReader reader = new YamlReader(new FileReader(paramPath));
//            SystemParameters parameters = reader.read(SystemParameters.class);
//            reader.close();
//            return parameters;
        } catch (FileNotFoundException e){
            throw new RuntimeException("Could not find file: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Could not successfully parse config file: " + e.getMessage());
        }
    }

}

package RTT.billing.Util;

import RTT.billing.Config.SystemParameters;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HandlerYaml {
    private static final String paramPath="src/main/resources/config.yaml";
    public static final SystemParameters parameters = loadParams();
    private static SystemParameters loadParams() {
        try{YamlReader reader = new YamlReader(new FileReader(paramPath));
            SystemParameters parameters = reader.read(SystemParameters.class);
            reader.close();
            return parameters;
        } catch (FileNotFoundException e){
            throw new RuntimeException("Could not find file: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Could not successfully parse config file: " + e.getMessage());
        }
    }

}

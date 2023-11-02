package RTT.billing.Config;

import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.*;

public class YamlConfig {
    //public static final String CONFIG_FILE_NAME = "/config.yaml";
    public static final YamlConfig config = loadConfig();
    public static SystemParameters parameters;

    private static YamlConfig loadConfig() {
        try {
            //YamlReader reader = new YamlReader(Files.newBufferedReader(Path.of(CONFIG_FILE_NAME), CharsetConstants.CHARSET));

            InputStream inputStream = YamlConfig.class.getResourceAsStream("/config.yaml");
            if (inputStream ==null)
                throw new FileNotFoundException();

            YamlReader reader = new YamlReader(new FileReader("/config.yaml"));
            parameters = reader.read(SystemParameters.class);
            reader.close();
            return config;

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find file: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Could not successfully parse config file: " + e.getMessage());
        }
    }
}

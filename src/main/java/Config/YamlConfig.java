package Config;

import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class YamlConfig {
    public static final String CONFIG_FILE_NAME = "/config.yaml";
    public static final YamlConfig config = loadConfig();
    public SystemParameters parameters;

    private static YamlConfig loadConfig() {
        try {
            YamlReader reader = new YamlReader(Files.newBufferedReader(Path.of(CONFIG_FILE_NAME), CharsetConstants.CHARSET));
            YamlConfig config = reader.read(YamlConfig.class);
            reader.close();
            return config;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not read config file " + YamlConfig.CONFIG_FILE_NAME + ": " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Could not successfully parse config file " + YamlConfig.CONFIG_FILE_NAME + ": " + e.getMessage());
        }
    }
}

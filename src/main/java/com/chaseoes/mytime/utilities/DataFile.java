package com.chaseoes.mytime.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.chaseoes.mytime.MyTime;
import com.google.common.io.Files;

public class DataFile {

    static DataFile instance = new DataFile();
    private YamlConfiguration customConfig = null;
    private File customConfigFile = null;

    public static DataFile getDataFile() {
        return instance;
    }

    public boolean reloadDataConfig() {
        try {
            if (customConfigFile == null) {
                customConfigFile = new File(MyTime.getInstance().getDataFolder(), "data.yml");
                customConfigFile.createNewFile();
            }

            customConfig = new YamlConfiguration();
            customConfig.loadFromString(Files.toString(customConfigFile, Charset.forName("UTF-8")));

            InputStream defConfigStream = MyTime.getInstance().getResource("data.yml");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                customConfig.setDefaults(defConfig);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public FileConfiguration getDataConfig() {
        if (customConfig == null) {
            this.reloadDataConfig();
        }
        return customConfig;
    }

    public void saveDataConfig() {
        if (customConfig == null || customConfigFile == null) {
            return;
        }
        try {
            String data = customConfig.saveToString();
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(customConfigFile), "UTF-8");
            out.write(data, 0, data.length());
            out.flush();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

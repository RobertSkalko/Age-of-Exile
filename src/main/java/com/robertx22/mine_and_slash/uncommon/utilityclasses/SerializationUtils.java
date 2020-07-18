package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SerializationUtils {
    public static final String CONFIG_PATH = FMLPaths.CONFIGDIR.get()
            .toAbsolutePath()
            .toString() + "/" + Ref.MOD_NAME + "/";

    public static void makeIfDoesNotExist(String path, String filename, String text) {
        if (!new File(path + "/" + filename).exists()) {
            makeFileAndDirAndWrite(path, filename, text);
        }
    }

    public static void makeFileAndDirAndWrite(String path, String filename, String text) {
        makeFileAndDirAndWrite(path, filename, text, false);

    }

    public static void makeFileAndDirAndWrite(String path, String filename, String text, boolean overwrite) {

        /*
        if (path.contains(CONFIG_PATH) == false) {
            path = CONFIG_PATH + path;
        }
                 */

        String combined = path + "/" + filename;

        try {
            new File(path).mkdirs();

            if (overwrite || new File(combined).exists() == false) {

                new File(combined).createNewFile();
                FileWriter fileWriter;
                fileWriter = new FileWriter(combined);
                fileWriter.write(text);
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

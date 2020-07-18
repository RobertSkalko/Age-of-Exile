package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Files;
import java.nio.file.Paths;

public class DirUtils {

    public static String modDir() {
        return FMLPaths.GAMEDIR.get().toString().replace("run", "src");
    }

    public static String langFolderDir() {
        return modDir() + "/main/resources/assets/mmorpg/lang/";
    }

    public static String langFilePath() {
        return langFolderDir() + "en_us.json";
    }

    private static String manualLangFilePath() {
        return langFolderDir() + "manual.json";
    }

    public static String getManualString() {
        try {
            String str = new String(Files.readAllBytes(Paths.get(DirUtils.manualLangFilePath())));

            return str;

        } catch (Exception e) {
            System.out.println(e);
        }
        return "";

    }

    public static String modelsPath() {
        return modDir() + "/main/resources/assets/mmorpg/models/";
    }

    public static String curiosItemTagsPath() {
        return modDir() + "/main/resources/data/curios/tags/items/";
    }
}

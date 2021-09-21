package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirUtils {

    public static Path generatedResourcesDir() {

        String path = FMLPaths.GAMEDIR.get()
            .toString();

        String movedpath = path;
        movedpath = movedpath.replace("run", "src/generated/resources");
        return Paths.get(movedpath);

    }

    public static String modDir() {
        return FMLPaths.GAMEDIR.get()
            .toString()
            .replace("run", "src");
    }

    public static String langFolderDir() {
        return modDir() + "/main/resources/assets/mmorpg/lang/";
    }

    public static String genWikiFolderDir() {
        return modDir() + "/main/resources/assets/mmorpg/wiki/";
    }

    public static String modpackHelperFolderDir() {
        return modDir() + "/main/resources/assets/mmorpg/modpack_dev_helper/";
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

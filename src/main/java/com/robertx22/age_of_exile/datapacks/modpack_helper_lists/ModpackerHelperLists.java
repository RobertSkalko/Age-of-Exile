package com.robertx22.age_of_exile.datapacks.modpack_helper_lists;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DirUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ModpackerHelperLists {

    public static void generate() {

        System.out.println("Starting to create lang file");

        SlashRegistry.getAllRegistries()
            .forEach(x -> {
                try {

                    List<String> ids = new ArrayList<>();

                    ids.add("These are all default registry entries of this type.");
                    if (!x.getSerializable()
                        .isEmpty()) {
                        ids.add("This registry is modify-able through datapacks.");
                    } else {
                        ids.add("This registry can't be modified or added to.");
                    }

                    for (Object g : x.getList()) {
                        IGUID gu = (IGUID) g;
                        ids.add(gu.GUID());
                    }

                    String stuff = String.join("\n", ids);
                    String filepath = DirUtils.modpackHelperFolderDir() + x.getType().id;

                    if (Files.exists(Paths.get(DirUtils.modpackHelperFolderDir())) == false) {
                        Files.createFile(Paths.get(filepath));
                    }

                    File file = new File(DirUtils.langFilePath());

                    FileWriter fw = new FileWriter(file);
                    fw.write(stuff);

                    fw.close();

                    System.out.println("Saved lang file to " + file.toPath()
                        .toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

    }
}
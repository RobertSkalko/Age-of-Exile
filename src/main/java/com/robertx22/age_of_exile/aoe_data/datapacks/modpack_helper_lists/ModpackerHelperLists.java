package com.robertx22.age_of_exile.aoe_data.datapacks.modpack_helper_lists;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.registry.Database;
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

        Database.getAllRegistries()
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
                    String filepath = DirUtils.modpackHelperFolderDir() + x.getType().id + ".txt";

                    new File(DirUtils.modpackHelperFolderDir()).mkdirs();

                    if (!Files.exists(Paths.get(DirUtils.modpackHelperFolderDir()))) {
                        Files.createFile(Paths.get(filepath));
                    }

                    File file = new File(filepath);
                    FileWriter fw = new FileWriter(file);
                    fw.write(stuff);

                    fw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

    }
}
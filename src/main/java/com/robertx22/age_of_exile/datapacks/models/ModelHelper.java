package com.robertx22.age_of_exile.datapacks.models;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DirUtils;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ModelHelper {

    Item item;
    Type type;

    public ModelHelper(Item item, Type type) {
        this.item = item;
        this.type = type;
    }

    public void generate() {

        String tex = getTextureString();
        String parent = "";

        if (type == Type.GENERATED) {
            parent = "item/generated";
        } else {
            parent = "item/handheld";
        }

        String filecontent = getBaseJsonString();
        filecontent = filecontent.replace("[PARENT]", parent);
        filecontent = filecontent.replace("[TEXTURE]", tex);

        Path path = DirUtils.generatedResourcesDir();

        String reg = "assets/" + Ref.MODID + "/models/item/" + Registry.ITEM.getId(item)
            .getPath()
            + ".json";
        path = path.resolve(reg);

        int index = path.toString()
            .lastIndexOf("\\");

        String withoutname = path.toString()
            .substring(0, index + 1);

        String name = path.toString()
            .substring(index + 1);

        try {
            new File(withoutname).mkdirs();
            File file = new File(path.toString());
            file.createNewFile();
            FileWriter fileWriter;
            fileWriter = new FileWriter(file);
            fileWriter.write(filecontent);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getTextureString() {
        return Registry.ITEM.getId(item)
            .toString()
            .replace(Ref.MODID + ":", Ref.MODID + ":items/");
    }

    private String getBaseJsonString() {
        return "{\n  \"parent\": \"[PARENT]\",\n  \"textures\": {\n    \"layer0\": \"[TEXTURE]\"\n  }\n}";
    }

    public enum Type {
        GENERATED, HANDHELD
    }

}

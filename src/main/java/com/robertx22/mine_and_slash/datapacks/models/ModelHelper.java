package com.robertx22.mine_and_slash.datapacks.models;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.DirUtils;
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

        String reg = "assets/" + Registry.ITEM.getId(item)
            .toString()
            .replace(":", "/") + ".json";
        path = path.resolve(reg);

        String withoutname = path.toString()
            .replace(".json", "");

        try {
            new File(withoutname).mkdirs();
            new File(path.toString()).createNewFile();
            FileWriter fileWriter;
            fileWriter = new FileWriter("");
            fileWriter.write(filecontent);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getTextureString() {
        return Registry.ITEM.getId(item)
            .toString();
    }

    private String getBaseJsonString() {
        return "{\n  \"parent\": \"[PARENT]\",\n  \"textures\": {\n    \"layer0\": \"[TEXTURE]\"\n  }\n}";
    }

    public enum Type {
        GENERATED, HANDHELD
    }

}

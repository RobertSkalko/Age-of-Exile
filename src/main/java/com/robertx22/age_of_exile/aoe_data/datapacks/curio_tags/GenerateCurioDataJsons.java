package com.robertx22.age_of_exile.aoe_data.datapacks.curio_tags;

import com.google.common.base.Joiner;
import com.robertx22.age_of_exile.a_libraries.curios.interfaces.ICuriosType;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DirUtils;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateCurioDataJsons {

    public static void generate() {

        HashMap<String, List<String>> map = new HashMap();

        for (Item item : Registry.ITEM) {

            if (Registry.ITEM.getKey(item)
                .getNamespace()
                .equals(SlashRef.MODID) && item instanceof ICuriosType) {

                ICuriosType type = (ICuriosType) item;
                String slot = type.curioTypeName();

                List<String> list = new ArrayList<>();

                if (map.containsKey(slot)) {
                    list.addAll(map.get(slot));
                }

                list.add("\"" + Registry.ITEM.getKey(item)
                    .toString() + "\"");

                map.put(slot, list);

            }
        }
        // loop end

        Path dir = Paths.get(DirUtils.curiosItemTagsPath());
        try {
            if (Files.exists(dir) == false) {
                Files.createDirectory(dir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {

            String json = getJson(entry);

            Path path = Paths.get(dir.toAbsolutePath() + "/" + entry.getKey() + ".json");
            byte[] strToBytes = json.getBytes();

            try {
                Files.write(path, strToBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private static String getJson(Map.Entry<String, List<String>> entry) {

        String json = "{\n  \"replace\": \"false\",\n  \"values\": [REPLACE]\n}";
        Joiner joiner = Joiner.on(",");
        String join = joiner.join(entry.getValue());
        return json.replaceAll("REPLACE", join);

    }

}

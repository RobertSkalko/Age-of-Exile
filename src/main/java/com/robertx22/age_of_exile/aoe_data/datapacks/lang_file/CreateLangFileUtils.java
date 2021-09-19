package com.robertx22.age_of_exile.aoe_data.datapacks.lang_file;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IBaseAutoLoc;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CreateLangFileUtils {

    public static String replaceLast(String string, String toReplace,
                                     String replacement) {
        int pos = string.lastIndexOf(toReplace);
        if (pos > -1) {
            return string.substring(0, pos) + replacement + string.substring(pos + toReplace
                .length(), string.length());
        } else {
            return string;
        }
    }

    public static String comment(String str) {
        return "\n" + "\"_comment\": \"" + " [CATEGORY]: " + str + "\",\n" + "\n";
    }

    public static boolean matches(ResourceLocation loc) {
        if (loc == null || loc.getNamespace()
            .equals(Ref.MODID) == false) {
            return false;
        }
        return true;
    }

    public static <T extends IBaseAutoLoc> List<T> getFromRegistries(Class<T> theclass) {

        List<T> list = new ArrayList<>();

        for (Item item : Registry.ITEM) {
            if (matches(Registry.ITEM.getKey(item)) && theclass.isAssignableFrom(item.getClass())) {
                list.add((T) item);
            }
        }

        for (Block item : Registry.BLOCK) {
            if (matches(Registry.BLOCK.getKey(item)) && theclass.isAssignableFrom(item.getClass())) {
                list.add((T) item);
            }
        }
        for (Effect item : Registry.MOB_EFFECT) {
            if (matches(Registry.MOB_EFFECT.getKey(item)) && theclass.isAssignableFrom(item.getClass())) {
                list.add((T) item);
            }
        }

        return list;

    }

    public static void sortName(List<IAutoLocName> list) {
        if (list != null && list.size() > 1) {
            try {
                Collections.sort(list, Comparator.comparing(x -> x.locNameLangFileGUID()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sortDesc(List<IAutoLocDesc> list) {
        if (list != null && list.size() > 1) {
            try {
                Collections.sort(list, Comparator.comparing(x -> x.locDescLangFileGUID()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

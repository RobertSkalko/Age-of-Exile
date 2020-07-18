package com.robertx22.mine_and_slash.uncommon.develeper;

import com.robertx22.mine_and_slash.uncommon.interfaces.IBaseAutoLoc;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocMultiLore;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.ForgeRegistries;

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

    public static boolean matches(Identifier loc) {
        if (loc == null || loc.getNamespace().equals(Ref.MODID) == false) {
            return false;
        }
        return true;
    }

    public static <T extends IBaseAutoLoc> List<T> getFromRegistries(Class<T> theclass) {

        List<T> list = new ArrayList<>();

        for (Item item : ForgeRegistries.ITEMS) {
            if (matches(item.getRegistryName()) && theclass.isAssignableFrom(item.getClass())) {
                list.add((T) item);
            }
        }
        for (Block item : ForgeRegistries.BLOCKS) {
            if (matches(item.getRegistryName()) && theclass.isAssignableFrom(item.getClass())) {
                list.add((T) item);
            }
        }
        for (StatusEffect item : ForgeRegistries.POTIONS) {
            if (matches(item.getRegistryName()) && theclass.isAssignableFrom(item.getClass())) {
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

    public static void sortLores(List<IAutoLocMultiLore> list) {
        if (list != null && list.size() > 1) {
            try {
                Collections.sort(list, Comparator.comparing(x -> x.formattedGUID()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

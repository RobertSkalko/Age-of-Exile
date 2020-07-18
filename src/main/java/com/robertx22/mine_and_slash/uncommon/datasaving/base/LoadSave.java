package com.robertx22.mine_and_slash.uncommon.datasaving.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import info.loenwind.autosave.Reader;
import info.loenwind.autosave.Writer;
import net.minecraft.nbt.CompoundTag;

public class LoadSave {

    //Use this if AutoSave ever stops working and you need to conditionIsMet the mod
    private static final boolean useGSON = false;
    private static final String GSONLOC = "_GSON";

    private static final Gson gson = new GsonBuilder().create();

    public static CompoundTag Save(Object obj, CompoundTag nbt, String loc) {

        if (nbt == null) {
            nbt = new CompoundTag();
        }

        if (useGSON) {
            String json = gson.toJson(obj);
            nbt.putString(loc + GSONLOC, json);

        } else {
            CompoundTag objNbt = new CompoundTag();
            Writer.write(objNbt, obj);
            nbt.put(loc, objNbt);
        }

        return nbt;

    }

    public static <OBJ extends Object> OBJ Load(Class theclass, OBJ newobj,
                                                CompoundTag nbt, String loc) {

        if (nbt == null) {
            return null;
        }

        if (useGSON) {
            String json = nbt.getString(loc + GSONLOC);

            if (json.isEmpty()) {
                return null;
            }

            return (OBJ) gson.fromJson(json, theclass);
        } else {

            if (nbt.contains(loc)) {
                CompoundTag thenbt = (CompoundTag) nbt.get(loc);
                Reader.read(thenbt, newobj);
                //System.out.println(thenbt.toString());
            } else {
                return null;
            }
        }

        return newobj;
    }

}

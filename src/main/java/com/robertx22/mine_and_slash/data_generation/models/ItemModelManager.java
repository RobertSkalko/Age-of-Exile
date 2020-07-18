package com.robertx22.mine_and_slash.data_generation.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.Crossbow;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.HunterBow;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.nio.file.Path;

public class ItemModelManager extends ItemModelProvider {

    public ItemModelManager(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Ref.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        ForgeRegistries.ITEMS.forEach(x -> {
            if (x instanceof IAutoModel) {
                IAutoModel auto = (IAutoModel) x;
                auto.generateModel(this);
            }
        });

        SlashRegistry.UniqueGears()
            .getSerializable()
            .forEach(x -> {
                if (x.getBaseGearType() != HunterBow.INSTANCE && x.getBaseGearType() != Crossbow.INSTANCE) {
                    if (x.getBaseGearType()
                        .family()
                        .equals(BaseGearType.SlotFamily.Weapon)) {
                        handheld(x.getUniqueItem());
                    } else {
                        generated(x.getUniqueItem());
                    }
                }
            });
        SlashRegistry.GearTypes()
            .getList()
            .forEach(x -> {

                if (x != HunterBow.INSTANCE && x != Crossbow.INSTANCE && !x.isShield()) {
                    if (x.family()
                        .equals(BaseGearType.SlotFamily.Weapon)) {
                        handheld(x.getItem());
                    } else {
                        generated(x.getItem());
                    }
                }

            });

    }

    @Override
    public String getName() {
        return "Mine and Slash Item Models";
    }

    public String modid(Item item) {
        return item
            .asItem()
            .getRegistryName()
            .getNamespace();
    }

    public String name(Item item) {
        return item
            .asItem()
            .getRegistryName()
            .getPath();
    }

    public ItemModelBuilder generated(Item item) {
        return generated(item, itemTexture(item));
    }

    public ItemModelBuilder generated(Item item, Identifier... layers) {
        ItemModelBuilder ret = withExistingParent(name(item), "item/generated");
        for (int i = 0; i < layers.length; i++) {
            ret = ret.texture("layer" + i, layers[i]);
        }
        return ret;
    }

    public Identifier itemTexture(Item item) {
        return modLoc("items/" + name(item));
    }

    public Identifier overlay(Item item) {
        return modLoc("items/" + name(item) + "_overlay");
    }

    public ItemModelBuilder handheld(Item item) {
        return handheld(item, itemTexture(item));
    }

    public ItemModelBuilder handheld(Item item, Identifier texture) {
        return withExistingParent(name(item), "item/handheld").texture("layer0", texture);
    }

    // TEMP WORKAROUND UNTIL FORGE FIXES SHIT
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting()
        .create();

    @Override
    protected void generateAll(DataCache cache) {
        for (ItemModelBuilder model : generatedModels.values()) {
            Path target = getPath(model);
            try {
                DataProvider.writeToPath(GSON, cache, model.toJson(), target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Path getPath(ItemModelBuilder model) {
        Identifier loc = model.getLocation();
        return generator.getOutput()
            .resolve("assets/" + loc.getNamespace() + "/models/item/" + loc.getPath() + ".json");
    }
    // TEMP WORKAROUND UNTIL FORGE FIXES SHIT
}

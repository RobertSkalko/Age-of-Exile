package com.robertx22.mine_and_slash.data_generation.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.Crossbow;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.HunterBow;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ItemModelManager {

    // TODO REMAKE THIS
    public ItemModelManager(DataGenerator generator) {
        super();
    }

    protected void registerModels() {

        Registry.ITEM.forEach(x -> {
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

    public String modid(Item item) {
        return Registry.ITEM.getId(item
            .asItem())
            .getNamespace();
    }

    public String name(Item item) {
        return Registry.ITEM.getId(item
            .asItem())
            .getPath();
    }

    public void generated(Item item) {

    }

    public void handheld(Item item) {

    }

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting()
        .create();

    protected void generateAll(DataCache cache) {
        /*
        for (ItemModelBuilder model : generatedModels.values()) {
            Path target = getPath(model);
            try {
                DataProvider.writeToPath(GSON, cache, model.toJson(), target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

         */
    }

    /*
    private Path getPath(ItemModelBuilder model) {
        Identifier loc = model.getLocation();
        return generator.getOutput()
            .resolve("assets/" + loc.getNamespace() + "/models/item/" + loc.getPath() + ".json");
    }

    @Override
    public void run(DataCache dataCache) throws IOException {

    }

    @Override
    public String getName() {
        return "mmorpg item models";
    }


     */
}

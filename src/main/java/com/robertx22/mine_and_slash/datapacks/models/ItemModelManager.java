package com.robertx22.mine_and_slash.datapacks.models;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.Crossbow;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.HunterBow;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ItemModelManager {

    public static ItemModelManager INSTANCE = new ItemModelManager();

    public void generateModels() {

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

    public void generated(Item item) {
        new ModelHelper(item, ModelHelper.Type.GENERATED).generate();
    }

    public void handheld(Item item) {
        new ModelHelper(item, ModelHelper.Type.HANDHELD).generate();
    }

}

package com.robertx22.age_of_exile.aoe_data.datapacks.models;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.ExileDB;
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

        ExileDB.UniqueGears()
            .getSerializable()
            .forEach(x -> {
                if (!x.getBaseGearType()
                    .getTags()
                    .contains(BaseGearType.SlotTag.ranged_weapon)) {
                    if (x.getBaseGearType()
                        .family()
                        .equals(BaseGearType.SlotFamily.Weapon)) {
                        //handheld(x.getUniqueItem());
                    } else {
                        generated(x.getUniqueItem());
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

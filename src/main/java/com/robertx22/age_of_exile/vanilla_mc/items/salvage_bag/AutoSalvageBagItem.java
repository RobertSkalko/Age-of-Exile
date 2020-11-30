package com.robertx22.age_of_exile.vanilla_mc.items.salvage_bag;

import com.robertx22.age_of_exile.a_libraries.curios.interfaces.ISalvageBag;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AutoSalvageBagItem extends Item implements IAutoLocName, IAutoModel, IShapedRecipe, ISalvageBag {

    public AutoSalvageBagItem() {
        super(new Settings().group(CreativeTabs.MyModTab)
            .maxCount(1));
    }

    public abstract List<String> salvagableRaritiesInternal();

    public List<GearRarity> getRaritiesThatCanSalvage() {
        return salvagableRaritiesInternal().stream()
            .map(x -> Database.GearRarities()
                .get(x))
            .collect(Collectors.toList());
    }

    public abstract float chanceToDestroyOutput();

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String GUID() {
        return "";
    }

    public final boolean shouldSalvage(PlayerEntity en, GearItemData gear) {

        try {
            if (gear.isUnique() || gear.hasRuneWord()) {
                return false; // just to be safe
            }
            if (gear.sockets != null) {
                if (!gear.sockets.sockets.isEmpty()) {
                    return false; // dont salvage socketed gear
                }
            }
            if (!this.getRaritiesThatCanSalvage()
                .contains(gear.getRarity())) {
                return false;
            }

            List<ItemStack> equipstacks = PlayerUtils.getEquippedStacksOf(en, gear.GetBaseGearType());

            if (equipstacks.isEmpty()) {
                return false; // no equipped, means any gear is better!
            }

            List<GearItemData> gears = equipstacks.stream()
                .map(x -> {
                    return Gear.Load(x);
                })
                .collect(Collectors.toList());

            if (gears.isEmpty()) {
                return false; // no equipped, means any gear is better!
            }
            gears.removeIf(x -> x == null);

            return gears.stream()
                .allMatch(g -> g.isBetterThan(gear));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}

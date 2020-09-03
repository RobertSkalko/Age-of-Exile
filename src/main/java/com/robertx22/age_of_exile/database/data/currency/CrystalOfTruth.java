package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.generators.util.GearCreationUtils;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class CrystalOfTruth extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {
    @Override
    public String GUID() {
        return "currency/crystal_of_truth";
    }

    public static final String ID = Ref.MODID + ":currency/crystal_of_truth";

    @Override
    public int getWeight() {
        return 250;
    }

    public CrystalOfTruth() {

        super(ID);

    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);

        GearBlueprint gearPrint = new GearBlueprint(gear.level);
        gearPrint.gearItemSlot.set(gear.gear_type);
        gearPrint.rarity.setSpecificRarity(IRarity.Rare);
        gearPrint.level.set(gear.level);

        gearPrint.isUniquePart.set(false);

        GearItemData newgear = gearPrint.createData();
        gear.WriteOverDataThatShouldStay(newgear);

        ItemStack result = ItemStack.EMPTY;

        if (gear.changesItemStack()) {
            result = GearCreationUtils.CreateStack(newgear);
        } else {
            result = stack;
            Gear.Save(result, newgear);
        }

        return result;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.IS_COMMON);
    }

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public int getRarityRank() {
        return IRarity.Magical;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Crystal of Truth";
    }

    @Override
    public String locDescForLangFile() {
        return "Transform Common Item into a Rare";
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(ModRegistry.CURRENCIES.CRYSTAL_OF_TRUTH)
            .input('t', Items.REDSTONE)
            .input('v', ModRegistry.CURRENCIES.ORB_OF_TRANSMUTATION)
            .input('o', ModRegistry.MISC_ITEMS.RARE_MAGIC_ESSENCE)
            .pattern("ovo")
            .pattern("vtv")
            .pattern("ovo")
            .criterion("player_level", trigger());
    }

}
package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.IRenamed;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class StoneOfHopeItem extends CurrencyItem implements ICurrencyItemEffect, IRenamed, IShapedRecipe {
    @Override
    public String GUID() {
        return "currency/stone_of_hope";
    }

    public static final String ID = Ref.MODID + ":currency/stone_of_hope";

    @Override
    public List<String> oldNames() {
        return Arrays.asList(Ref.MODID + ":stone_of_hope");
    }

    public StoneOfHopeItem() {

        super(ID);

    }

    @Override
    public float getInstability() {
        return 250;
    }

    @Override
    public int getWeight() {
        return 15;
    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);

        GearBlueprint blueprint = new GearBlueprint(gear.lvl);
        blueprint.gearItemSlot.set(gear.gear_type);
        blueprint.rarity.set(gear.getRarity()
            .getHigherRarity());

        GearItemData newgear = blueprint.createData();
        gear.WriteOverDataThatShouldStay(newgear);

        ItemStack result = ItemStack.EMPTY;

        result = stack;
        Gear.Save(result, newgear);

        return result;

    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.HAS_HIGHER_RARITY, SimpleGearLocReq.IS_NOT_HIGHEST_RARITY, SimpleGearLocReq.IS_NOT_UNIQUE);
    }

    @Override
    public String getRarityRank() {
        return IRarity.MYTHIC_ID;
    }

    @Override
    public int getTier() {
        return 2;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Stone Of Hope";
    }

    @Override
    public String locDescForLangFile() {
        return "Transform any rarity gear into higher rarity";
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(ModRegistry.CURRENCIES.STONE_OF_HOPE)
            .input('#', ModRegistry.MISC_ITEMS.MYTHIC_ESSENCE)
            .input('t', ModRegistry.CURRENCIES.ORB_OF_INFINITY)
            .input('v', ModRegistry.CURRENCIES.ORB_OF_UNIQUE_BLESSING)
            .input('o', ModRegistry.CURRENCIES.ORB_OF_INFINITY)
            .pattern("#o#")
            .pattern("#t#")
            .pattern("vvv")
            .criterion("player_level", trigger());
    }
}
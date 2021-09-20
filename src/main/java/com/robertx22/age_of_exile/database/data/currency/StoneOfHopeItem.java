package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class StoneOfHopeItem extends CurrencyItem implements ICurrencyItemEffect {
    @Override
    public String GUID() {
        return "currency/stone_of_hope";
    }

    public static final String ID = SlashRef.MODID + ":currency/stone_of_hope";

    public StoneOfHopeItem() {

        super(ID);

    }

    @Override
    public int getTier() {
        return 5;
    }

    @Override
    public float getBreakChance() {
        return 25;
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
    public ItemStack internalModifyMethod(ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);

        GearBlueprint blueprint = new GearBlueprint(stack.getItem(), gear.lvl);
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
        return IRarity.EPIC_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Stone Of Hope";
    }

    @Override
    public String locDescForLangFile() {
        return "Transform any rarity gear into higher rarity.";
    }

}
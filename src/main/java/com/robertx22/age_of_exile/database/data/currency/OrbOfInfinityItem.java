package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.SimpleGearLocReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class OrbOfInfinityItem extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {

    @Override
    public String GUID() {
        return "currency/orb_of_infinity";
    }

    private static final String name = Ref.MODID + ":currency/orb_of_infinity";

    public OrbOfInfinityItem() {
        super(name);
    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack Currency) {

        GearItemData gear = Gear.Load(stack);

        List<Affix.Type> types = Arrays.asList(Affix.Type.prefix, Affix.Type.suffix);

        Affix.Type type = RandomUtils.randomFromList(types);

        if (!gear.affixes.canGetMore(type, gear)) {
            type = type.getOpposite();
        }

        if (gear.affixes.canGetMore(type, gear)) {
            AffixData affix = new AffixData(type);
            affix.RerollFully(gear);
            gear.affixes.add(affix);
        }

        Gear.Save(stack, gear);

        return stack;
    }

    @Override
    public int getWeight() {
        return 40;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE, SimpleGearLocReq.IS_NOT_UNIQUE, SimpleGearLocReq.CAN_GET_MORE_AFFIXES);
    }

    @Override
    public float getInstability() {
        return 20;
    }

    @Override
    public String getRarityRank() {
        return IRarity.LEGENDARY_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb of Infinity";
    }

    @Override
    public String locDescForLangFile() {
        return "Adds another affix.";
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(ModRegistry.CURRENCIES.ORB_OF_INFINITY)
            .input('#', ModRegistry.MISC_ITEMS.MYTHIC_ESSENCE)
            .input('t', ModRegistry.CURRENCIES.ORB_OF_DISORDER)
            .input('v', Items.DIAMOND)
            .input('o', ModRegistry.MISC_ITEMS.GOLDEN_ORB)
            .pattern("v#v")
            .pattern("vtv")
            .pattern("ooo")
            .criterion("player_level", trigger());
    }

}
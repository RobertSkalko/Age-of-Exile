package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.item_types.GearReq;
import com.robertx22.age_of_exile.database.data.stats.types.misc.MoreSocketsStat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RerollAffixIntoSocket extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {

    @Override
    public String GUID() {
        return "currency/roll_socket";
    }

    public static final String ID = Ref.MODID + ":currency/roll_socket";

    @Override
    public int getWeight() {
        return 50;
    }

    public RerollAffixIntoSocket() {
        super(ID);
    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack Currency) {
        GearItemData gear = Gear.Load(stack);
        gear.is_cor = false;

        Optional<AffixData> pre = gear.affixes.pre.stream()
            .filter(x -> x.getAffix()
                .getTierStats(1)
                .stream()
                .anyMatch(e -> e.GetStat() instanceof MoreSocketsStat == false))
            .findAny();
        Optional<AffixData> suf = gear.affixes.suf.stream()
            .filter(x -> x.getAffix()
                .getTierStats(1)
                .stream()
                .anyMatch(e -> e.GetStat() instanceof MoreSocketsStat == false))
            .findAny();

        AffixData data = null;

        if (pre.isPresent()) {
            data = pre.get();

        }
        if (suf.isPresent()) {
            data = suf.get();
        }

        if (data != null) {
            Affix.Type type = data.type;

            Affix affix = Database.Affixes()
                .getFilterWrapped(x -> x.type == type)
                .of(x -> x
                    .getTierStats(1)
                    .stream()
                    .anyMatch(e -> e.GetStat() instanceof MoreSocketsStat))
                .allThatMeetRequirement(gear)
                .random();
            data.create(gear, affix);

        }
        Gear.Save(stack, gear);
        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GearReq.INSTANCE);
    }

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public float getInstability() {
        return 100;
    }

    @Override
    public String getRarityRank() {
        return IRarity.EPIC_ID;
    }

    @Override
    public String locNameForLangFile() {
        return nameColor + "Orb of Sockets";
    }

    @Override
    public String locDescForLangFile() {
        return "Rerolls a random affix into one that adds sockets";
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(this)
            .input('t', ModRegistry.CURRENCIES.ORB_OF_TRANSMUTATION)
            .input('v', ModRegistry.ALCHEMY.CONDENSED_ESSENCE_MAP.get(SkillItemTier.EMPYREAN))
            .input('o', ModRegistry.MISC_ITEMS.T2_DUST)
            .pattern("ovo")
            .pattern("vtv")
            .pattern("ovo")
            .criterion("player_level", trigger());
    }

}
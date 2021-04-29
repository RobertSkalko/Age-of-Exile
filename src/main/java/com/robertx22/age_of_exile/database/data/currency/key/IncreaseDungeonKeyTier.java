package com.robertx22.age_of_exile.database.data.currency.key;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.dimension.item.DungeonKeyItem;
import com.robertx22.age_of_exile.uncommon.datasaving.ItemType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ITiered;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;
import java.util.List;

public abstract class IncreaseDungeonKeyTier extends CurrencyItem implements ICurrencyItemEffect {

    public IncreaseDungeonKeyTier() {
        super("");
        this.itemTypesUsableOn = ItemType.DUNGEON_KEY;
    }

    public abstract int increaseTierBy();

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack currency) {

        int tier = DungeonKeyItem.getTier(stack);

        tier += increaseTierBy();

        DungeonKeyItem.setTier(stack, MathHelper.clamp(tier, 1, ITiered.getMaxTier()));

        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(new BaseLocRequirement() {
            @Override
            public MutableText getText() {
                return Words.IsNotMaxTier.locName();
            }

            @Override
            public boolean isAllowed(LocReqContext context) {
                return DungeonKeyItem.getTier(context.stack) < ITiered.getMaxTier();
            }
        });
    }

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public float getInstability() {
        return 0;
    }

    @Override
    public String getRarityRank() {
        return IRarity.EPIC_ID;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases the tier of the dungeon key by " + increaseTierBy() + " tiers.";
    }

}
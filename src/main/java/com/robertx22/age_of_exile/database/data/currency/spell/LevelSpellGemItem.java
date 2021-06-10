package com.robertx22.age_of_exile.database.data.currency.spell;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.spell.GemReq;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.spell.IsnotSupportGem;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.datasaving.ItemType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public class LevelSpellGemItem extends CurrencyItem implements ICurrencyItemEffect, IShapedRecipe {

    @Override
    public String GUID() {
        return "currency/level_spell";
    }

    public static final String ID = Ref.MODID + ":currency/level_spell";

    @Override
    public int getWeight() {
        return 0;
    }

    public LevelSpellGemItem() {
        super(ID);
        this.itemTypesUsableOn = ItemType.SPELL_GEM;
    }

    @Override
    public ItemStack ModifyItem(ItemStack stack, ItemStack Currency) {
        SkillGemData data = SkillGemData.fromStack(stack);
        data.tryLevel();
        data.saveToStack(stack);
        return stack;
    }

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList(GemReq.INSTANCE, new IsnotSupportGem());
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
    public String locNameForLangFile() {
        return nameColor + "Orb of Elevation";
    }

    @Override
    public String locDescForLangFile() {
        return "Increases level of a spell gem";
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(this)
            .input('t', ModRegistry.CURRENCIES.ORB_OF_TRANSMUTATION)
            .input('v', ModRegistry.TIERED.CONDENSED_ESSENCE_MAP.get(SkillItemTier.TIER0))
            .input('o', Items.GOLD_INGOT)
            .pattern("ovo")
            .pattern("vtv")
            .pattern("ovo")
            .criterion("player_level", trigger());
    }

}

package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientTextureUtils;
import com.robertx22.library_of_exile.registry.IAutoGson;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class GearRarity extends BaseRarity implements IGearRarity, IAutoGson<GearRarity> {
    public static GearRarity SERIALIZER = new GearRarity();

    public GearRarity() {
        super(RarityType.GEAR);
    }

    @Override
    public Class<GearRarity> getClassForSerialization() {
        return GearRarity.class;
    }

    public int item_model_data_num = -1;

    public int drops_after_tier = -1;

    public int affixes = 0;
    public int sockets = 1;
    public int upgrade_lvl_to_increase_rar = -1;

    public int unbreaking_chance = 0;

    public int rar_ess_per_sal = 0;

    public MinMax default_stat_percents = new MinMax(0, 100);
    public MinMax affix_stat_percents = new MinMax(0, 100);
    public MinMax unique_stat_percents = new MinMax(0, 100);
    public MinMax base_stat_percents = new MinMax(0, 100);
    public MinMax dust_per_sal = new MinMax(1, 1);
    public MinMax upgrades = new MinMax(0, 0);

    public int item_tier = -1;

    public boolean hasTier() {
        return item_tier > -1;
    }

    public float item_tier_power;
    public float item_value_multi;
    public int bonus_effective_lvls = 0;
    public boolean announce_in_chat = false;

    public boolean is_unique_item = false;

    transient ResourceLocation glintFull;
    transient ResourceLocation glintTexBorder;

    public boolean canUpgradeRarityIfUpgradeLevelIsHighEnough() {
        return this.hasHigherRarity() && upgrade_lvl_to_increase_rar > 0;
    }

    public ItemStack getRarityUpgradeStack() {
        ItemStack stack = new ItemStack(SlashItems.RARITY_UPGRADE.get());
        stack.getOrCreateTag()
            .putString("rar", GUID());
        stack.getOrCreateTag()
            .putInt("CustomModelData", this.item_model_data_num);
        return stack;
    }

    public ItemStack getRarityEssenceStack() {
        ItemStack stack = new ItemStack(SlashItems.RARITY_ESSENCE.get());

        stack.getOrCreateTag()
            .putString("rar", GUID());
        stack.getOrCreateTag()
            .putInt("CustomModelData", this.item_model_data_num);

        return stack;
    }

    public static GearRarity getRarityFromEssence(ItemStack stack) {
        return ExileDB.GearRarities()
            .get(stack.getOrCreateTag()
                .getString("rar"));
    }

    public ResourceLocation getGlintTextureFull() {

        if (glintFull == null) {
            ResourceLocation tex = SlashRef.id("textures/gui/rarity_glint/full/" + GUID() + ".png");
            if (ClientTextureUtils.textureExists(tex)) {
                glintFull = tex;
            } else {
                glintFull = SlashRef.id("textures/gui/rarity_glint/full/default.png");
            }
        }
        return glintFull;

    }

    public ResourceLocation getGlintTextureBorder() {

        if (glintTexBorder == null) {
            ResourceLocation tex = SlashRef.id("textures/gui/rarity_glint/border/" + GUID() + ".png");
            if (ClientTextureUtils.textureExists(tex)) {
                glintTexBorder = tex;
            } else {
                glintTexBorder = SlashRef.id("textures/gui/rarity_glint/border/default.png");
            }
        }
        return glintTexBorder;

    }

    public boolean isHigherThan(GearRarity other) {
        return this.valueMulti() > other.valueMulti();
        // todo can be better
    }

    @Override
    public MinMax affixStatPercents() {
        return affix_stat_percents;
    }

    @Override
    public MinMax baseStatPercents() {
        return base_stat_percents;
    }

    @Override
    public float valueMulti() {
        return this.item_value_multi;
    }

    @Override
    public MinMax uniqueStatPercents() {
        return unique_stat_percents;
    }

    @Override
    public float itemTierPower() {
        return item_tier_power;
    }

    @Override
    public int getAffixAmount() {
        return affixes;
    }

    @Override
    public MinMax StatPercents() {
        return default_stat_percents;
    }

    public boolean hasHigherRarity() {
        return ExileDB.GearRarities()
            .isRegistered(higher_rar);
    }

    public GearRarity getHigherRarity() {
        return ExileDB.GearRarities()
            .get(higher_rar);
    }
}

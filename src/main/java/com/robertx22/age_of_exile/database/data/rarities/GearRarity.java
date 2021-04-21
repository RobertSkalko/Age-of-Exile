package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientTextureUtils;
import net.minecraft.util.Identifier;

public final class GearRarity extends BaseRarity implements IGearRarity, IAutoGson<GearRarity> {
    public static GearRarity SERIALIZER = new GearRarity();

    public GearRarity() {
        super(RarityType.GEAR);
    }

    @Override
    public Class<GearRarity> getClassForSerialization() {
        return GearRarity.class;
    }

    public static class Part {
        public int min_amount;
        public int max_amount;
        public int chance_for_more;

        public Part(int min_amount, int max_amount, int chance_for_more) {
            this.min_amount = min_amount;
            this.max_amount = max_amount;
            this.chance_for_more = chance_for_more;
        }
    }

    public Part affixes;

    public int max_worn_at_once = -1;

    public int drops_after_tier = -1;

    public MinMax default_stat_percents = new MinMax(0, 100);
    public MinMax affix_stat_percents = new MinMax(0, 100);
    public MinMax unique_stat_percents = new MinMax(0, 100);
    public MinMax base_stat_percents = new MinMax(0, 100);
    public MinMax essence_per_sal = new MinMax(1, 1);

    public int item_tier = -1;

    public boolean hasTier() {
        return item_tier > -1;
    }

    public float item_tier_power;
    public float item_value_multi;
    public float stat_req_multi;
    public int unidentified_chance;
    public MinMax spawn_durability_hit = new MinMax(60, 80);
    public boolean announce_in_chat = false;

    public boolean is_unique_item = false;
    public float special_spawn_chance = 0;
    public float special_spawn_chest_bonus_chance = 0;

    transient Identifier glintFull;
    transient Identifier glintTexBorder;

    public Identifier getGlintTextureFull() {

        if (glintFull == null) {
            Identifier tex = Ref.id("textures/gui/rarity_glint/full/" + GUID() + ".png");
            if (ClientTextureUtils.textureExists(tex)) {
                glintFull = tex;
            } else {
                glintFull = Ref.id("textures/gui/rarity_glint/full/default.png");
            }
        }
        return glintFull;

    }

    public Identifier getGlintTextureBorder() {

        if (glintTexBorder == null) {
            Identifier tex = Ref.id("textures/gui/rarity_glint/border/" + GUID() + ".png");
            if (ClientTextureUtils.textureExists(tex)) {
                glintTexBorder = tex;
            } else {
                glintTexBorder = Ref.id("textures/gui/rarity_glint/border/default.png");
            }
        }
        return glintTexBorder;

    }

    public boolean isHigherThan(GearRarity other) {
        return this.valueMulti() > other.valueMulti();
        // todo can be better
    }

    @Override
    public MinMax SpawnDurabilityHit() {
        return spawn_durability_hit;
    }

    @Override
    public float statReqMulti() {
        return this.stat_req_multi;
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
    public int AffixChance() {
        return affixes.chance_for_more;
    }

    @Override
    public int maxAffixes() {
        return affixes.max_amount;
    }

    @Override
    public int minAffixes() {
        return affixes.min_amount;
    }

    @Override
    public float itemTierPower() {
        return item_tier_power;
    }

    @Override
    public float unidentifiedChance() {
        return unidentified_chance;
    }

    @Override
    public MinMax StatPercents() {
        return default_stat_percents;
    }

    public boolean hasHigherRarity() {
        return Database.GearRarities()
            .isRegistered(higher_rar);
    }

    public GearRarity getHigherRarity() {
        return Database.GearRarities()
            .get(higher_rar);
    }
}

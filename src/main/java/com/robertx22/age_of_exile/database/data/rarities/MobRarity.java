package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;

public final class MobRarity extends BaseRarity implements Rarity, IAutoGson<MobRarity> {
    public static MobRarity SERIALIZER = new MobRarity();

    public int min_lvl;
    public int loot_lvl_modifier = 0;
    public float dmg_multi;
    public float extra_hp_multi;
    public float stat_multi;
    public float loot_multi;
    public float exp_multi;
    public int affixes = 0;
    public String name_add = "";

    public MobRarity() {
        super(RarityType.MOB);
    }

    public int minMobLevelForRandomSpawns() {
        return min_lvl;
    }

    public float DamageMultiplier() {
        return dmg_multi;
    }

    public float ExtraHealthMulti() {
        return extra_hp_multi;
    }

    public float StatMultiplier() {
        return stat_multi;
    }

    public float LootMultiplier() {
        return loot_multi;
    }

    public float expMulti() {
        return exp_multi;
    }

    public boolean hasHigherRarity() {
        return ExileDB.MobRarities()
            .isRegistered(higher_rar);
    }

    public MobRarity getHigherRarity() {
        return ExileDB.MobRarities()
            .get(higher_rar);
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.MOB_RARITY;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".mob_rarity." + GUID();
    }

    @Override
    public Class<MobRarity> getClassForSerialization() {
        return MobRarity.class;
    }
}

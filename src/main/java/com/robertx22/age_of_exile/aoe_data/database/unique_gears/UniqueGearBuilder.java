package com.robertx22.age_of_exile.aoe_data.database.unique_gears;

import com.robertx22.age_of_exile.aoe_data.database.runewords.RunewordBuilder;
import com.robertx22.age_of_exile.database.all_keys.base.BaseGearKey;
import com.robertx22.age_of_exile.database.all_keys.base.RunewordKey;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.UniqueStatsData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ErrorUtils;

import java.util.List;

public class UniqueGearBuilder {

    UniqueGear uniq = new UniqueGear();

    public static UniqueGearBuilder of(String id, String locname, String basegear) {
        UniqueGearBuilder b = new UniqueGearBuilder();
        b.uniq.langName = locname;
        b.uniq.guid = id;
        b.uniq.base_gear = basegear;
        return b;
    }

    public static UniqueGearBuilder of(RunewordKey word, String locname, BaseGearKey gearType) {
        return of(word.id, locname, gearType.GUID());
    }

    public static UniqueGearBuilder of(String id, String locname, BaseGearKey gearType) {
        return of(id, locname, gearType.GUID());
    }

    public UniqueGearBuilder stats(List<StatModifier> stats) {
        this.uniq.uniqueStats = stats;
        return this;
    }

    public UniqueGearBuilder setReplacesName() {
        this.uniq.replaces_name = true;
        return this;
    }

    public UniqueGearBuilder makeRuneWordOnly() {
        RunewordBuilder.of(uniq.guid, uniq.guid, uniq.getBaseGear().gear_slot);
        this.uniq.uniqueRarity = IRarity.RUNEWORD_ID;
        this.uniq.weight = 0;
        return this;
    }

    public UniqueGearBuilder randomAffixes(int amount) {
        this.uniq.random_affixes = amount;
        return this;
    }

    public UniqueGearBuilder makeRunicSpell() {
        if (uniq.random_affixes < 1) {
            throw new RuntimeException("Runic spells should have at least 1 random affix! Otherwise they are runewords.");
        }
        RunewordBuilder.of(uniq.guid, uniq.guid, uniq.getBaseGear().gear_slot);
        this.uniq.uniqueRarity = IRarity.RUNIC_SPELL_ID;
        this.uniq.weight = 0;
        return this;
    }

    public UniqueGearBuilder baseStats(List<StatModifier> stat) {
        this.uniq.base_stats.addAll(stat);

        return this;
    }

    public UniqueGearBuilder gearSet(String set) {
        this.uniq.set = set;
        return this;
    }

    public UniqueGearBuilder devComment(String comment) {
        // OMAE WA MOU SHINDEIRU
        return this;
    }

    public UniqueGear build() {
        ErrorUtils.ifFalse(!uniq.uniqueStats.isEmpty());
        ErrorUtils.ifFalse((uniq.base_stats.size() + uniq.uniqueStats.size()) < UniqueStatsData.MAX_STATS);

        uniq.addToSerializables();
        return uniq;
    }

}
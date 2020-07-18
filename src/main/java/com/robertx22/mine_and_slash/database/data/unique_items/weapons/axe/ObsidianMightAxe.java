package com.robertx22.mine_and_slash.database.data.unique_items.weapons.axe;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.PrimitiveAxe;
import com.robertx22.mine_and_slash.database.data.stats.types.bonus_dmg_to_status_affected.BonusDmgToStatusAffected;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Strength;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalHit;
import com.robertx22.mine_and_slash.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.PlusResourceOnKill;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class ObsidianMightAxe implements IUnique {

    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(3, 5, 5, 12, new WeaponDamage(Elements.Fire), ModType.FLAT),
            new StatModifier(15, 50, CriticalHit.getInstance(), ModType.LOCAL_INCREASE),
            new StatModifier(1, 3, PlusResourceOnKill.HEALTH, ModType.FLAT),
            new StatModifier(15, 30, BonusDmgToStatusAffected.BURN, ModType.FLAT),
            new StatModifier(0.5F, 1, new FlatIncreasedReq(Strength.INSTANCE), ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "This thirst for blood only seems lower than it's desire to taste burning flesh.";
    }

    @Override
    public String locNameForLangFile() {
        return "Obsidian's Might";
    }

    @Override
    public String GUID() {
        return "obsi_might";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return PrimitiveAxe.INSTANCE;
    }
}

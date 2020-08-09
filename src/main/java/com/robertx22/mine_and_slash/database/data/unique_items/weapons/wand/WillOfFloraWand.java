package com.robertx22.mine_and_slash.database.data.unique_items.weapons.wand;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Strength;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.HealPower;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.database.registrators.BaseGearTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class WillOfFloraWand implements IUnique {

    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(5, 5, 5, 5, new WeaponDamage(Elements.Nature), ModType.FLAT),
            new StatModifier(10, 30, HealPower.getInstance(), ModType.FLAT),
            new StatModifier(0.25F, 0.5F, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT),
            new StatModifier(0.25F, 0.5F, new FlatIncreasedReq(Strength.INSTANCE), ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "Everything must be exactly as required.";
    }

    @Override
    public String locNameForLangFile() {
        return "Will of Flora";
    }

    @Override
    public String GUID() {
        return "will_of_flora";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return BaseGearTypes.HIGH_WAND;
    }
}

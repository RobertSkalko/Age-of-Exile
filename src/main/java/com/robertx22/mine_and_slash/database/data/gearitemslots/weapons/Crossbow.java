package com.robertx22.mine_and_slash.database.data.gearitemslots.weapons;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseWeapon;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalHit;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class Crossbow extends BaseWeapon {
    public static BaseGearType INSTANCE = new Crossbow();

    private Crossbow() {

    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(2, 4, 3, 7, new WeaponDamage(Elements.Physical), ModType.FLAT),
            new StatModifier(3, 6, CriticalHit.getInstance(), ModType.FLAT)

        );
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().dexterity(0.5F);
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList();
    }

    @Override
    public List<SlotTag> getTags() {
        return Arrays.asList(SlotTag.Crossbow, SlotTag.RangedWeapon);
    }

    @Override
    public Item getItem() {
        return ModItems.CROSSBOW.get();
    }

    @Override
    public WeaponTypes weaponType() {
        return WeaponTypes.CrossBow;
    }

    @Override
    public String GUID() {
        return "crossbow";
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public String locNameForLangFile() {
        return "Crossbow";
    }

}
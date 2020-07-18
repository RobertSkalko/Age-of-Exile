package com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseWeapon;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalHit;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.SpellDamage;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class SageWand extends BaseWeapon {
    public static BaseGearType INSTANCE = new SageWand();

    private SageWand() {
        this.attacksPerSecond = Constants.WAND_ATK_SPEED;
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(1, 3, 3, 5, new WeaponDamage(Elements.Physical), ModType.FLAT),
            new StatModifier(3, 10, CriticalHit.getInstance(), ModType.FLAT)
        );
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList(new StatModifier(3, 10, SpellDamage.getInstance(), ModType.FLAT));
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().intelligence(0.5F);
    }

    @Override
    public List<SlotTag> getTags() {
        return Arrays.asList(SlotTag.MageWeapon, SlotTag.Wand, SlotTag.MeleeWeapon);
    }

    @Override
    public Item getItem() {
        return ModItems.SAGE_WAND.get();
    }

    @Override
    public String GUID() {
        return "sage_wand";
    }

    @Override
    public WeaponTypes weaponType() {
        return WeaponTypes.Wand;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public String locNameForLangFile() {
        return "Sage Wand";
    }
}
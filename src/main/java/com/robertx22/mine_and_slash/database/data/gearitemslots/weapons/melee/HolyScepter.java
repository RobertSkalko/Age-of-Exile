package com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseWeapon;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.TagList;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalHit;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.HealPower;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class HolyScepter extends BaseWeapon {
    public static BaseGearType INSTANCE = new HolyScepter();

    private HolyScepter() {
        this.attacksPerSecond = Constants.WAND_ATK_SPEED;
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(2, 4, 4, 6, new WeaponDamage(Elements.Physical), ModType.FLAT),
            new StatModifier(2, 5, CriticalHit.getInstance(), ModType.FLAT)
        );
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList(new StatModifier(3, 10, HealPower.getInstance(), ModType.FLAT));
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().intelligence(0.5F);
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.mage_weapon, SlotTag.wand, SlotTag.melee_weapon, SlotTag.intelligence);
    }

    @Override
    public Item getItem() {
        return ModRegistry.GEAR_ITEMS.HOLY_SCEPTER;
    }

    @Override
    public String GUID() {
        return "holy_scepter";
    }

    @Override
    public WeaponTypes weaponType() {
        return WeaponTypes.Wand;
    }

    @Override
    public int Weight() {
        return 750;
    }

    @Override
    public String locNameForLangFile() {
        return "Holy Scepter";
    }
}

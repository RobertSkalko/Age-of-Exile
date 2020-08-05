package com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseWeapon;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.TagList;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalHit;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class PrimitiveAxe extends BaseWeapon {
    public static BaseGearType INSTANCE = new PrimitiveAxe();

    private PrimitiveAxe() {
        this.attacksPerSecond = Constants.AXE_ATK_SPEED;
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(2, 3, 3, 8, new WeaponDamage(Elements.Physical), ModType.FLAT),
            new StatModifier(4, 15, CriticalHit.getInstance(), ModType.FLAT)

        );
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().strength(0.5F);
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList();
    }

    @Override
    public TagList getTags() {
        return new TagList(SlotTag.warrior_casting_weapon, SlotTag.axe, SlotTag.weapon_family, SlotTag.melee_weapon, SlotTag.strength);
    }

    @Override
    public Item getItem() {
        return ModRegistry.GEAR_ITEMS.PRIMITIVE_AXE;
    }

    @Override
    public WeaponTypes weaponType() {
        return WeaponTypes.Axe;
    }

    @Override
    public String GUID() {
        return "primitive_axe";
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public String locNameForLangFile() {
        return "Primitive Axe";
    }

}
package com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseWeapon;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalHit;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Lifesteal;
import com.robertx22.mine_and_slash.mmorpg.registers.common.ModItems;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.List;

public class GemstoneSword extends BaseWeapon {
    public static BaseGearType INSTANCE = new GemstoneSword();

    private GemstoneSword() {
        this.attacksPerSecond = Constants.SWORD_ATK_SPEED;
    }

    @Override
    public List<StatModifier> baseStats() {
        return Arrays.asList(
            new StatModifier(1, 3, 2, 4, new WeaponDamage(Elements.Physical), ModType.FLAT),
            new StatModifier(2, 6, CriticalHit.getInstance(), ModType.FLAT)

        );
    }

    @Override
    public List<StatModifier> implicitStats() {
        return Arrays.asList(new StatModifier(1, 3, Lifesteal.getInstance(), ModType.FLAT));
    }

    @Override
    public StatRequirement getStatRequirements() {
        return new StatRequirement().strength(0.3F)
            .dexterity(0.1F);
    }

    @Override
    public List<SlotTag> getTags() {
        return Arrays.asList(SlotTag.Sword, SlotTag.MeleeWeapon);
    }

    @Override
    public Item getItem() {
        return ModItems.GEMSTONE_SWORD.get();
    }

    @Override
    public WeaponTypes weaponType() {
        return WeaponTypes.Sword;
    }

    @Override
    public String GUID() {
        return "gemstone_sword";
    }

    @Override
    public String locNameForLangFile() {
        return "Gemstone Sword";
    }

    @Override
    public int Weight() {
        return 1500;
    }

}

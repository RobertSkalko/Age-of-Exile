package com.robertx22.age_of_exile.aoe_data.database.stats.old;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.CoreStatData;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.GlobalCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.GlobalCriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.Arrays;
import java.util.UUID;

public class DatapackStats implements ExileRegistryInit {

    public static Stat INT = new CoreStat("intelligence", "Intelligence", CoreStatData.of(Arrays.asList(
        new OptScaleExactStat(0.25F, 0.25F, Stats.SPELL_CRIT_CHANCE.get(), ModType.FLAT),
        new OptScaleExactStat(0.25F, 0.25F, Stats.HEAL_CRIT_CHANCE.get(), ModType.FLAT),
        new OptScaleExactStat(1, 1, Mana.getInstance(), ModType.LOCAL_INCREASE)
    )));

    public static Stat STR = new CoreStat("strength", "Strength", CoreStatData.of(Arrays.asList(
        new OptScaleExactStat(0.25F, 0.25F, Stats.CRIT_DAMAGE.get(), ModType.FLAT),
        new OptScaleExactStat(2, 2, Armor.getInstance(), ModType.LOCAL_INCREASE)
    )));
    public static Stat DEX = new CoreStat("dexterity", "Dexterity", CoreStatData.of(Arrays.asList(
        new OptScaleExactStat(0.25F, 0.25F, Stats.CRIT_CHANCE.get(), ModType.FLAT),
        new OptScaleExactStat(2, 2, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
    )));
    public static Stat VIT = new CoreStat("vitality", "Vitality", CoreStatData.of(Arrays.asList(
        new OptScaleExactStat(10, 10, Health.getInstance(), ModType.FLAT),
        new OptScaleExactStat(0.5F, 0.5F, HealthRegen.getInstance(), ModType.FLAT)
    )));
    public static Stat WIS = new CoreStat("wisdom", "Wisdom", CoreStatData.of(Arrays.asList(
        new OptScaleExactStat(10, 10, Mana.getInstance(), ModType.FLAT),
        new OptScaleExactStat(0.5F, 0.5F, ManaRegen.getInstance(), ModType.FLAT)
    )));
    public static Stat AGI = new CoreStat("agility", "Agility", CoreStatData.of(Arrays.asList(
        new OptScaleExactStat(20, 20, Stats.ACCURACY.get(), ModType.FLAT),
        new OptScaleExactStat(0.5F, 0.5F, Stats.CRIT_CHANCE.get(), ModType.FLAT)
    )));

    public static Stat HEAL_TO_SPELL_DMG = new AddPerPercentOfOther(Stats.HEAL_STRENGTH.get(), SpellDamage.getInstance());
    public static Stat PHYS_DMG_PER_MANA = new AddPerPercentOfOther(Mana.getInstance(), new AttackDamage(Elements.Physical));

    public static Stat BLOOD_PER_10VIT = new MoreXPerYOf(DatapackStats.VIT, Blood.getInstance(), 10);
    public static Stat HEALTH_PER_10_INT = new MoreXPerYOf(DatapackStats.INT, Health.getInstance(), 10);
    public static Stat MANA_PER_10_INT = new MoreXPerYOf(DatapackStats.INT, Mana.getInstance(), 10);
    public static Stat CRIT_DMG_PER_10_ATK_SPEED_REG = new MoreXPerYOf(Stats.ATTACK_SPEED.get(), Stats.CRIT_DAMAGE.get(), 10);

    public static Stat GLOBAL_CRIT_CHANCE_PER_MAGIC_FIND_25 = new MoreXPerYOf(TreasureQuality.getInstance(), GlobalCriticalHit.getInstance(), 25);
    public static Stat GLOBAL_CRIT_DMG_PER_ITEM_FIND_25 = new MoreXPerYOf(TreasureQuantity.getInstance(), GlobalCriticalDamage.getInstance(), 25);

    public static Stat CONVERT_HEALTH_TO_PHYS_DMG = new ConvertFromOneToOtherStat(Health.getInstance(), new AttackDamage(Elements.Physical));

    public static Stat MOVE_SPEED = new AttributeStat("move_speed", "Move Speed", UUID.fromString("7e286d81-3fcf-471c-85b8-980072b30907"), EntityAttributes.GENERIC_MOVEMENT_SPEED, true);

    public static Stat MANA_PER_10_WIS = new MoreXPerYOf(DatapackStats.WIS, Mana.getInstance(), 10);
    public static Stat MINUS_MANA_PER_10_VIT = new MoreXPerYOf(DatapackStats.VIT, Mana.getInstance(), 10);

    public static Stat ACCURACY_PER_DEX = new MoreXPerYOf(DatapackStats.DEX, Stats.ACCURACY.get(), 10);
    public static Stat CRIT_PER_STR = new MoreXPerYOf(DatapackStats.STR, Stats.CRIT_CHANCE.get(), 10);
    public static Stat CRIT_PER_DEX = new MoreXPerYOf(DatapackStats.DEX, Stats.CRIT_CHANCE.get(), 10);
    public static Stat HP_REGEN_PER_WISDOM = new MoreXPerYOf(DatapackStats.WIS, HealthRegen.getInstance(), 10);
    public static Stat HP_PER_DEX = new MoreXPerYOf(DatapackStats.DEX, Health.getInstance(), 10);
    public static Stat ARMOR_PER_MANA = new MoreXPerYOf(Mana.getInstance(), Armor.getInstance(), 10);
    public static Stat PROJ_DMG_PER_STR = new MoreXPerYOf(DatapackStats.STR, Stats.PROJECTILE_DAMAGE.get(), 10);

    @Override
    public void registerAll() {

        DEX.addToSerializables();
        INT.addToSerializables();
        STR.addToSerializables();
        VIT.addToSerializables();
        WIS.addToSerializables();
        AGI.addToSerializables();

        HEAL_TO_SPELL_DMG.addToSerializables();
        CRIT_DMG_PER_10_ATK_SPEED_REG.addToSerializables();
        PROJ_DMG_PER_STR.addToSerializables();
        ARMOR_PER_MANA.addToSerializables();
        ACCURACY_PER_DEX.addToSerializables();
        HP_PER_DEX.addToSerializables();
        HP_REGEN_PER_WISDOM.addToSerializables();
        CRIT_PER_DEX.addToSerializables();
        MANA_PER_10_WIS.addToSerializables();
        MINUS_MANA_PER_10_VIT.addToSerializables();
        BLOOD_PER_10VIT.addToSerializables();
        CONVERT_HEALTH_TO_PHYS_DMG.addToSerializables();
        HEALTH_PER_10_INT.addToSerializables();
        MANA_PER_10_INT.addToSerializables();
        GLOBAL_CRIT_CHANCE_PER_MAGIC_FIND_25.addToSerializables();
        GLOBAL_CRIT_DMG_PER_ITEM_FIND_25.addToSerializables();
        MOVE_SPEED.addToSerializables();
        PHYS_DMG_PER_MANA.addToSerializables();
    }
}

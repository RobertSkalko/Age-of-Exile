package com.robertx22.age_of_exile.aoe_data.database.stats.old;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AddPerPercentOfOther;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.ConvertFromOneToOtherStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MoreXPerYOf;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
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
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.UUID;

public class DatapackStats implements ISlashRegistryInit {

    public static Stat HEAL_TO_SPELL_DMG = new AddPerPercentOfOther(Stats.HEAL_STRENGTH.get(), SpellDamage.getInstance());
    public static Stat PHYS_DMG_PER_MANA = new AddPerPercentOfOther(Mana.getInstance(), new AttackDamage(Elements.Physical));

    public static Stat BLOOD_PER_10VIT = new MoreXPerYOf(Vitality.INSTANCE, Blood.getInstance(), 10);
    public static Stat HEALTH_PER_10_INT = new MoreXPerYOf(Intelligence.INSTANCE, Health.getInstance(), 10);
    public static Stat MANA_PER_10_INT = new MoreXPerYOf(Intelligence.INSTANCE, Mana.getInstance(), 10);
    public static Stat CRIT_DMG_PER_10_ATK_SPEED_REG = new MoreXPerYOf(Stats.ATTACK_SPEED.get(), Stats.CRIT_DAMAGE.get(), 10);

    public static Stat GLOBAL_CRIT_CHANCE_PER_MAGIC_FIND_25 = new MoreXPerYOf(TreasureQuality.getInstance(), GlobalCriticalHit.getInstance(), 25);
    public static Stat GLOBAL_CRIT_DMG_PER_ITEM_FIND_25 = new MoreXPerYOf(TreasureQuantity.getInstance(), GlobalCriticalDamage.getInstance(), 25);

    public static Stat CONVERT_HEALTH_TO_PHYS_DMG = new ConvertFromOneToOtherStat(Health.getInstance(), new AttackDamage(Elements.Physical));

    public static Stat MOVE_SPEED = new AttributeStat("move_speed", "Move Speed", UUID.fromString("7e286d81-3fcf-471c-85b8-980072b30907"), EntityAttributes.GENERIC_MOVEMENT_SPEED, true);

    public static Stat MANA_PER_10_WIS = new MoreXPerYOf(Wisdom.INSTANCE, Mana.getInstance(), 10);
    public static Stat MINUS_MANA_PER_10_VIT = new MoreXPerYOf(Vitality.INSTANCE, Mana.getInstance(), 10);

    public static Stat ACCURACY_PER_DEX = new MoreXPerYOf(Dexterity.INSTANCE, Stats.ACCURACY.get(), 10);
    public static Stat CRIT_PER_STR = new MoreXPerYOf(Strength.INSTANCE, Stats.CRIT_CHANCE.get(), 10);
    public static Stat CRIT_PER_DEX = new MoreXPerYOf(Dexterity.INSTANCE, Stats.CRIT_CHANCE.get(), 10);
    public static Stat HP_REGEN_PER_WISDOM = new MoreXPerYOf(Wisdom.INSTANCE, HealthRegen.getInstance(), 10);
    public static Stat HP_PER_DEX = new MoreXPerYOf(Dexterity.INSTANCE, Health.getInstance(), 10);
    public static Stat ARMOR_PER_MANA = new MoreXPerYOf(Mana.getInstance(), Armor.getInstance(), 10);

    @Override
    public void registerAll() {

        HEAL_TO_SPELL_DMG.addToSerializables();
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

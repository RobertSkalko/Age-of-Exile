package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AddPerPercentOfOther;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.ConvertFromOneToOtherStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.MoreXPerYOf;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Vitality;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.GlobalCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.GlobalCriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.UUID;

public class DatapackStatAdder implements ISlashRegistryInit {

    public static Stat HEAL_TO_SPELL_DMG = new AddPerPercentOfOther(HealPower.getInstance(), SpellDamage.getInstance());

    public static Stat BLOOD_PER_10VIT = new MoreXPerYOf(Vitality.INSTANCE, Blood.getInstance(), 10);
    public static Stat HEALTH_PER_10_INT = new MoreXPerYOf(Intelligence.INSTANCE, Health.getInstance(), 10);
    public static Stat MANA_PER_10_INT = new MoreXPerYOf(Intelligence.INSTANCE, Mana.getInstance(), 10);

    public static Stat GLOBAL_CRIT_CHANCE_PER_MAGIC_FIND_25 = new MoreXPerYOf(TreasureQuality.getInstance(), GlobalCriticalHit.getInstance(), 25);
    public static Stat GLOBAL_CRIT_DMG_PER_ITEM_FIND_25 = new MoreXPerYOf(TreasureQuantity.getInstance(), GlobalCriticalDamage.getInstance(), 25);

    public static Stat CONVERT_HEALTH_TO_PHYS_DMG = new ConvertFromOneToOtherStat(Health.getInstance(), new AttackDamage(Elements.Physical));

    public static Stat MOVE_SPEED = new AttributeStat("move_speed", "Move Speed", UUID.fromString("7e286d81-3fcf-471c-85b8-980072b30907"), EntityAttributes.GENERIC_MOVEMENT_SPEED, true);

    @Override
    public void registerAll() {

        HEAL_TO_SPELL_DMG.addToSerializables();
        BLOOD_PER_10VIT.addToSerializables();
        CONVERT_HEALTH_TO_PHYS_DMG.addToSerializables();
        HEALTH_PER_10_INT.addToSerializables();
        MANA_PER_10_INT.addToSerializables();
        GLOBAL_CRIT_CHANCE_PER_MAGIC_FIND_25.addToSerializables();
        GLOBAL_CRIT_DMG_PER_ITEM_FIND_25.addToSerializables();
        MOVE_SPEED.addToSerializables();
    }
}

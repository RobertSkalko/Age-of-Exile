package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.defense.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.*;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.misc.*;
import com.robertx22.age_of_exile.database.data.stats.types.offense.DualWieldDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.*;
import com.robertx22.age_of_exile.database.data.stats.types.resources.DamageAbsorbedByMana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.HealthRestorationToBlood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.ArrayList;
import java.util.List;

public class StatsRegister implements ExileRegistryInit {

    @Override
    public void registerAll() {

        // SpecialStats.init();

        List<Stat> All = new ArrayList<>();

        List<Stat> generated = new ArrayList<Stat>() {
            {
                {

                    add(RegeneratePercentStat.HEALTH);
                    add(RegeneratePercentStat.MANA);
                    add(ArmorPenetration.getInstance());

                    add(SpellDodge.getInstance());

                    add(TreasureQuality.getInstance());
                    add(TreasureQuantity.getInstance());

                    add(new AttackDamage(Elements.Physical));
                    add(new ElementalResist(Elements.Physical));
                    add(new ElementalPenetration(Elements.Physical));
                    add(new ElementalFocus(Elements.Physical));
                    add(new PhysConvertToEle(Elements.Physical));

                    add(AllAttributes.getInstance());
                    add(SpellDamage.getInstance());

                    add(ExtraMobDropsStat.getInstance());
                    add(BonusExp.getInstance());
                    add(BonusFavor.getInstance());
                    add(DamageTakenToMana.getInstance());
                    add(new BonusXpToMobsOfTier(SkillItemTier.TIER0));

                    add(new UnknownStat());

                    // Resources
                    add(DamageAbsorbedByMana.getInstance());
                    add(HealthRestorationToBlood.getInstance());
                    add(Blood.getInstance());
                    add(BloodUser.getInstance());
                    add(Health.getInstance());
                    add(HealthRegen.getInstance());

                    add(DualWieldDamage.getInstance());
                    add(Mana.getInstance());
                    add(ManaRegen.getInstance());

                    // Resources

                    add(Armor.getInstance());
                    add(DodgeRating.getInstance());
                    add(DamageShield.getInstance());

                    add(new BonusSkillExp(PlayerSkillEnum.NONE));

                    add(new BonusYield(BonusRequirement.COLD_BIOME));
                    add(new BonusSkillYield(PlayerSkillEnum.NONE));
                    add(DoubleDropChance.getInstance());
                    add(TripleDropChance.getInstance());
                }
            }
        };

        for (Stat stat : generated) {
            if (stat instanceof IGenerated) {
                for (Stat gen : ((IGenerated<Stat>) stat).generateAllPossibleStatVariations()) {
                    All.add(gen);
                }
            } else {
                All.add(stat);
            }
        }
        All.forEach(x -> x.registerToExileRegistry());

    }

}

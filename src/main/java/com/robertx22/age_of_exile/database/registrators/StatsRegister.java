package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.CraftingSuccessChance;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.IncreaseMinRarityStat;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.MaxUsesStat;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.RandomCraftingStat;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.craft_grid.IngNotTouchThis;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.craft_grid.IngToLeft;
import com.robertx22.age_of_exile.database.data.stats.types.crafting.craft_grid.IngTouchThis;
import com.robertx22.age_of_exile.database.data.stats.types.defense.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.*;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.misc.*;
import com.robertx22.age_of_exile.database.data.stats.types.offense.DarknessDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.DualWieldDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellPower;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.GlobalCriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.GlobalCriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.*;
import com.robertx22.age_of_exile.database.data.stats.types.resources.DamageAbsorbedByMana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.HealthRestorationToBlood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
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

                    add(DarknessDamage.getInstance());

                    add(SpellPower.getInstance());

                    add(SpellDodge.getInstance());

                    add(TreasureQuality.getInstance());
                    add(TreasureQuantity.getInstance());

                    add(new AttackDamage(Elements.Physical));
                    add(new ElementalResist(Elements.Physical));
                    add(new ElementalPenetration(Elements.Physical));
                    add(new ElementalFocus(Elements.Physical));
                    add(new PhysConvertToEle(Elements.Physical));
                    add(new MaxElementalResist(Elements.Earth));

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

                    add(EnergyRegen.getInstance());
                    add(Energy.getInstance());
                    // Resources

                    add(Armor.getInstance());
                    add(GlobalCriticalDamage.getInstance());
                    add(GlobalCriticalHit.getInstance());
                    add(DodgeRating.getInstance());
                    add(DamageShield.getInstance());

                    add(IngTouchThis.getInstance());
                    add(IngNotTouchThis.getInstance());
                    add(IngToLeft.getInstance());

                    add(RandomCraftingStat.getInstance());
                    add(IncreaseMinRarityStat.getInstance());
                    add(MaxUsesStat.getInstance());
                    add(CraftingSuccessChance.getInstance());

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

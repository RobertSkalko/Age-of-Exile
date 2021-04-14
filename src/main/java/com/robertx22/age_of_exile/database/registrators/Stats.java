package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.bonus_dmg_to_status_affected.BonusDmgToStatusAffected;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.defense.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.*;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.misc.*;
import com.robertx22.age_of_exile.database.data.stats.types.offense.*;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.*;
import com.robertx22.age_of_exile.database.data.stats.types.professions.all.*;
import com.robertx22.age_of_exile.database.data.stats.types.resources.*;
import com.robertx22.age_of_exile.database.data.stats.types.resources.aura.IncreasedEffectOfAuras;
import com.robertx22.age_of_exile.database.data.stats.types.resources.aura.ReducedManaReserved;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.HealthRestorationToBlood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Lifesteal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaBurn;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaBurnResistance;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.*;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;

import java.util.ArrayList;
import java.util.List;

public class Stats implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        SpecialStats.init();

        List<Stat> All = new ArrayList<>();

        List<Stat> generated = new ArrayList<Stat>() {
            {
                {

                    add(EffectImmunity.POISON);
                    add(EffectImmunity.HUNGER);
                    add(EffectImmunity.SLOW);
                    add(EffectImmunity.WITHER);

                    add(RegeneratePercentStat.HEALTH);
                    add(RegeneratePercentStat.MANA);

                    add(PlusResourceOnKill.HEALTH);
                    add(PlusResourceOnKill.MANA);

                    add(BonusDmgToStatusAffected.FROST);
                    add(BonusDmgToStatusAffected.BURN);
                    add(BonusDmgToStatusAffected.POISON);

                    add(ChanceToApplyEffect.FROSTBURN);
                    add(ChanceToApplyEffect.BURN);
                    add(ChanceToApplyEffect.POISON);
                    add(ChanceToApplyEffect.BLEED);
                    add(ChanceToApplyEffect.TORMENT);

                    add(AttackSpeed.getInstance());
                    add(ArmorPenetration.getInstance());
                    add(ReducedManaReserved.getInstance());
                    add(IncreasedEffectOfAuras.getInstance());

                    add(DayDamage.getInstance());
                    add(NightDamage.getInstance());

                    add(SpellAccuracy.getInstance());
                    add(SpellDodge.getInstance());
                    add(Accuracy.getInstance());

                    add(ProjectileDamage.getInstance());
                    add(DotDamage.getInstance());
                    add(AreaDamage.getInstance());

                    add(DamageUnderPotion.HUNGER);
                    add(DamageUnderPotion.POISON);
                    add(DamageUnderPotion.WITHER);

                    add(TreasureQuality.getInstance());
                    add(TreasureQuantity.getInstance());

                    add(new SpecificWeaponDamage(WeaponTypes.None));
                    add(new AttackDamage(Elements.Physical));
                    add(new ElementalDamageBonus(Elements.Physical));
                    add(new ElementalSpellDamage(Elements.Physical));
                    add(new ElementalResist(Elements.Physical));
                    add(new ElementalPenetration(Elements.Physical));
                    add(new ElementalFocus(Elements.Physical));
                    add(new PhysConvertToEle(Elements.Physical));
                    add(new MaxElementalResist(Elements.Nature));
                    add(new SpecificElementalWeaponDamage(WeaponTypes.Axe));

                    // generated

                    add(PiercingProjectile.getInstance());
                    add(ProjectileAmountStat.getInstance());
                    add(ProjectileSpeed.getInstance());
                    add(CooldownReduction.getInstance());
                    add(ManaCost.getInstance());
                    add(IncreasedAreaOfEffect.getInstance());
                    add(CastSpeed.getInstance());

                    add(AllAttributes.getInstance());
                    add(SpellDamage.getInstance());

                    add(Strength.INSTANCE);
                    add(Dexterity.INSTANCE);
                    add(Intelligence.INSTANCE);
                    add(Wisdom.INSTANCE);
                    add(Vitality.INSTANCE);
                    add(Agility.INSTANCE);

                    add(MoreSocketsStat.getInstance());
                    add(ExtraMobDropsStat.getInstance());
                    add(BonusExp.getInstance());
                    add(BonusFavor.getInstance());
                    add(DamageTakenToMana.getInstance());
                    add(new BonusXpToMobsOfTier(SkillItemTier.TIER0));

                    add(new UnknownStat());

                    add(TotalDamage.getInstance());
                    add(NonCritDamage.getInstance());

                    // Resources
                    add(IncreasedLeech.getInstance());
                    add(DamageAbsorbedByMana.getInstance());
                    add(HealthRestorationToBlood.getInstance());
                    add(Blood.getInstance());
                    add(BloodUser.getInstance());
                    add(Health.getInstance());
                    add(HealthRegen.getInstance());
                    add(Lifesteal.getInstance());

                    add(Mana.getInstance());
                    add(ManaRegen.getInstance());
                    // Resources

                    add(Armor.getInstance());
                    add(CriticalDamage.getInstance());
                    add(CriticalHit.getInstance());
                    add(SpellCriticalDamage.getInstance());
                    add(SpellCriticalHit.getInstance());
                    add(GlobalCriticalDamage.getInstance());
                    add(GlobalCriticalHit.getInstance());
                    add(DodgeRating.getInstance());
                    add(DamageShield.getInstance());

                    add(AttackStyleDamage.MELEE);
                    add(AttackStyleDamage.RANGED);
                    add(AttackStyleDamage.MAGIC);

                    add(ManaBurn.getInstance());
                    add(ManaBurnResistance.getInstance());

                    add(new ResourceLeech(new ResourceLeech.Info(Elements.Elemental, ResourceType.HEALTH, AttackType.ATTACK)));
                    add(new ResourceOnHit(new ResourceOnHit.Info(ResourceType.HEALTH, AttackType.ATTACK)));

                    add(new BonusSkillExp(PlayerSkillEnum.MINING));

                    add(new DamageOverTime(Elements.Elemental));

                    add(HealPower.getInstance());
                    add(HealEffectivenessOnSelf.getInstance());
                    // traits

                    add(new BonusYield(BonusRequirement.COLD_BIOME));
                    add(new BonusSkillYield(PlayerSkillEnum.FISHING));
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
        All.forEach(x -> x.registerToSlashRegistry());

    }

}

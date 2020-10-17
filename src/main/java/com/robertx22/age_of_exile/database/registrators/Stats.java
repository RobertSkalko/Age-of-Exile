package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.base.AllPreGenMapStats;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.bonus_dmg_to_status_affected.BonusDmgToStatusAffected;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.defense.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.*;
import com.robertx22.age_of_exile.database.data.stats.types.loot.IncreasedItemQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ChangeDmgElementStat;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.offense.*;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.ReducedAllStatReqOnItem;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealEffectivenessOnSelf;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.data.stats.types.resources.PlusResourceOnKill;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.LifeOnHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Lifesteal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicOnHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicSteal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.*;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.FasterCastRate;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ProjectileSpeedStat;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ReducedCooldownStat;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.ReducedManaCost;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;

import java.util.ArrayList;
import java.util.List;

public class Stats implements ISlashRegistryInit {

    public static AllPreGenMapStats allPreGenMapStatLists = new AllPreGenMapStats();

    @Override
    public void registerAll() {

        List<Stat> All = new ArrayList<>();

        List<Stat> generated = new ArrayList<Stat>() {
            {
                {

                    add(ChangeDmgElementStat.PHYS_TO_POISON);
                    add(ChangeDmgElementStat.PHYS_TO_FIRE);
                    add(ChangeDmgElementStat.PHYS_TO_FROST);
                    add(ChangeDmgElementStat.PHYS_TO_THUNDER);

                    add(EffectImmunity.POISON);
                    add(EffectImmunity.HUNGER);
                    add(EffectImmunity.SLOW);
                    add(EffectImmunity.WITHER);

                    add(RegeneratePercentStat.HEALTH);
                    add(RegeneratePercentStat.MAGIC_SHIELD);
                    add(RegeneratePercentStat.MANA);

                    add(PlusResourceOnKill.HEALTH);
                    add(PlusResourceOnKill.MAGIC_SHIELD);
                    add(PlusResourceOnKill.MANA);

                    add(BonusDmgToStatusAffected.FROST);
                    add(BonusDmgToStatusAffected.BURN);
                    add(BonusDmgToStatusAffected.POISON);
                    add(BonusDmgToStatusAffected.STATIC);

                    add(ChanceToApplyEffect.CHILL);
                    add(ChanceToApplyEffect.BURN);
                    add(ChanceToApplyEffect.POISON);
                    add(ChanceToApplyEffect.STATIC);

                    add(new FlatIncreasedReq(Dexterity.INSTANCE));
                    add(new FlatIncreasedReq(Strength.INSTANCE));
                    add(new FlatIncreasedReq(Intelligence.INSTANCE));

                    add(new ReducedAllStatReqOnItem());

                    add(AttackSpeed.getInstance());
                    add(ArmorPenetration.getInstance());

                    add(DayDamage.getInstance());
                    add(NightDamage.getInstance());

                    add(DamageUnderPotion.HUNGER);
                    add(DamageUnderPotion.POISON);
                    add(DamageUnderPotion.WITHER);

                    add(MagicFind.getInstance());
                    add(IncreasedItemQuantity.getInstance());

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

                    add(ProjectileSpeedStat.getInstance());
                    add(ReducedCooldownStat.getInstance());
                    add(ReducedManaCost.getInstance());
                    add(FasterCastRate.getInstance());

                    add(AllAttributes.getInstance());
                    add(SpellDamage.getInstance());

                    add(Strength.INSTANCE);
                    add(Dexterity.INSTANCE);
                    add(Intelligence.INSTANCE);
                    add(Willpower.INSTANCE);
                    add(Vitality.INSTANCE);
                    add(Agility.INSTANCE);

                    add(ExtraMobDropsStat.getInstance());
                    add(BonusExp.getInstance());

                    add(new UnknownStat());

                    // Resources
                    add(ManaSteal.getInstance());
                    add(MagicOnHit.getInstance());
                    add(Health.getInstance());
                    add(HealthRegen.getInstance());
                    add(Lifesteal.getInstance());
                    add(LifeOnHit.getInstance());
                    add(MagicSteal.getInstance());
                    add(Mana.getInstance());
                    add(ManaRegen.getInstance());
                    add(ManaOnHit.getInstance());
                    add(MagicShield.getInstance());
                    add(MagicShieldRegen.getInstance());
                    // Resources

                    add(Armor.getInstance());
                    add(CriticalDamage.getInstance());
                    add(CriticalHit.getInstance());
                    add(DodgeRating.getInstance());
                    add(DamageShield.getInstance());

                    add(ManaBurn.getInstance());
                    add(ManaBurnResistance.getInstance());

                    add(HealPower.getInstance());
                    add(HealEffectivenessOnSelf.getInstance());
                    // traits

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

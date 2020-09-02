package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.base.AllPreGenMapStats;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.bonus_dmg_to_status_affected.BonusDmgToStatusAffected;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.defense.*;
import com.robertx22.age_of_exile.database.data.stats.types.elementals.all_damage.AllEleDmg;
import com.robertx22.age_of_exile.database.data.stats.types.generated.*;
import com.robertx22.age_of_exile.database.data.stats.types.loot.IncreasedItemQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ChangeDmgElementStat;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.misc.HealToSpellDmgStat;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackSpeed;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.ReducedAllStatReqOnItem;
import com.robertx22.age_of_exile.database.data.stats.types.resources.*;
import com.robertx22.age_of_exile.database.data.stats.types.spell_calc.FasterCastRate;
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
                    add(HealToSpellDmgStat.getInstance());

                    add(ChangeDmgElementStat.PHYS_TO_POISON);
                    add(ChangeDmgElementStat.PHYS_TO_FIRE);
                    add(ChangeDmgElementStat.PHYS_TO_FROST);
                    add(ChangeDmgElementStat.PHYS_TO_THUNDER);

                    add(ImmuneToEffectStat.POISON);
                    add(ImmuneToEffectStat.HUNGER);
                    add(ImmuneToEffectStat.SLOW);
                    add(ImmuneToEffectStat.WITHER);

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

                    add(MagicFind.getInstance());
                    add(IncreasedItemQuantity.getInstance());

                    add(new SpecificWeaponDamage(WeaponTypes.None));
                    add(new WeaponDamage(Elements.Physical));
                    add(new ElementalDamageBonus(Elements.Physical));
                    add(new ElementalSpellDamage(Elements.Physical));
                    add(new ElementalResist(Elements.Physical));
                    add(new ElementalPenetration(Elements.Physical));
                    add(new ElementalFocus(Elements.Physical));
                    add(new PhysConvertToEle(Elements.Physical));
                    add(new MaxElementalResist(Elements.Nature));

                    // generated

                    add(ReducedCooldownStat.getInstance());
                    add(ReducedManaCost.getInstance());
                    add(FasterCastRate.getInstance());

                    add(AllAttributes.getInstance());
                    add(new AllEleDmg());
                    add(SpellDamage.getInstance());

                    add(Strength.INSTANCE);
                    add(Dexterity.INSTANCE);
                    add(Intelligence.INSTANCE);

                    add(ExtraMobDropsStat.getInstance());
                    add(BonusExp.getInstance());

                    add(new UnknownStat());

                    // Resources
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

        SpecificElementalWeaponDamage.register();

        All.forEach(x -> x.registerToSlashRegistry());

    }

}

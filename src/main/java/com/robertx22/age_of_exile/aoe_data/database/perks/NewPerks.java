package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Agility;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Wisdom;
import com.robertx22.age_of_exile.database.data.stats.types.defense.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalFocus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.*;

public class NewPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PerkBuilder.gameChanger("songbird", "Songbird",
            new OptScaleExactStat(-50, Stats.TOTAL_DAMAGE.get(), ModType.FLAT),
            new OptScaleExactStat(25, Stats.INCREASED_EFFECT_OF_AURAS_GIVEN.get()),
            new OptScaleExactStat(25, Stats.HEAL_CRIT_CHANCE.get()),
            new OptScaleExactStat(20, Stats.COOLDOWN_REDUCTION.get())
        );

        PerkBuilder.bigStat("tale_of_war", "Tale of War",
            new OptScaleExactStat(25, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.offensive))
        );

        PerkBuilder.bigStat("tale_of_ages", "Tale of Ages",
            new OptScaleExactStat(10, Stats.CHANCE_OF_HP_REGEN_ON_SPELL_CRIT.get())
        );
        PerkBuilder.bigStat("tale_of_love", "Tale of Love",
            new OptScaleExactStat(10, Stats.CHANCE_OF_MANA_REGEN_ON_HEAL_CRIT.get())
        );

        PerkBuilder.bigStat("tale_of_dreams", "Tale of Dreams",
            new OptScaleExactStat(10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.positive))
        );

        PerkBuilder.bigStat("robust_chainmail", "Robust Chainmail",
            new OptScaleExactStat(-20, Stats.PROJECTILE_DAMAGE_RECEIVED.get(), ModType.FLAT),
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.SHIELD_STRENGTH.get())
        );

        PerkBuilder.bigStat("big_ligthing_blare", "Lightning Blare",
            new OptScaleExactStat(10, new ElementalFocus(Elements.Light), ModType.FLAT),
            new OptScaleExactStat(10, new ElementalPenetration(Elements.Light)),
            new OptScaleExactStat(-10, Stats.MANA_COST.get()),
            new OptScaleExactStat(5, DatapackStats.HEAL_TO_SPELL_DMG)
        );
        PerkBuilder.bigStat("holy_envoy", "Holy Envoy",
            new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(1, new ElementalResist(Elements.Elemental)),
            new OptScaleExactStat(20, Stats.MORE_THREAT_WHEN_TAKING_DAMAGE.get())
        );

        PerkBuilder.bigStat("violent_strike", "Violent Strikes",
            new OptScaleExactStat(5, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(5, ArmorPenetration.getInstance()),
            new OptScaleExactStat(10, Stats.ACCURACY.get(), ModType.FLAT)
        );

        PerkBuilder.bigStat("mantra", "Mantra",
            new OptScaleExactStat(2, DatapackStats.PHYS_DMG_PER_MANA),
            new OptScaleExactStat(-5, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(-30, Stats.CRIT_CHANCE.get(), ModType.FLAT)
        );

        PerkBuilder.bigStat("no_common_sense", "No Common Sense",
            new OptScaleExactStat(-10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(-10, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(5, DatapackStats.CRIT_DMG_PER_10_ATK_SPEED_REG, ModType.FLAT)
        );

        PerkBuilder.bigStat("big_thousand_fists", "Thousand Fists",
            new OptScaleExactStat(10, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(100, Stats.ACCURACY.get()),
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.melee))
        );

        PerkBuilder.bigStat("big_no_rules", "No Rules",
            new OptScaleExactStat(10, Stats.PROJECTILE_SPEED.get()),
            new OptScaleExactStat(10, Stats.PROJECTILE_DAMAGE.get()),
            new OptScaleExactStat(-10, Stats.AREA_DAMAGE.get()),
            new OptScaleExactStat(-10, Stats.INCREASED_AREA.get())
        );

        PerkBuilder.bigStat("bright_presence", "Bright Presence",
            new OptScaleExactStat(1, new MaxElementalResist(Elements.Light)),
            new OptScaleExactStat(5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BLIND))
        );

        PerkBuilder.bigStat("exorcism", "Exorcism",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Light)),
            new OptScaleExactStat(2, new ElementalPenetration(Elements.Elemental)),
            new OptScaleExactStat(3, Stats.SPELL_CRIT_CHANCE.get())
        );

        PerkBuilder.bigStat("anomaly", "Anomaly",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Water)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Nature)),
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Stats.DOT_DAMAGE.get())
        );

        PerkBuilder.bigStat("prism", "Prism",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Water)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Light)),
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, new ElementalPenetration(Elements.Elemental))
        );

        PerkBuilder.bigStat("scepter_mastery", "Scepter Mastery",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.scepter)),
            new OptScaleExactStat(5, Stats.HEAL_STRENGTH.get()),
            new OptScaleExactStat(10, Wisdom.INSTANCE),
            new OptScaleExactStat(5, Stats.CAST_SPEED.get())
        );

        PerkBuilder.bigStat("open_seal", "Open Seal",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(2, Stats.SPELL_CRIT_CHANCE.get()),
            new OptScaleExactStat(5, Stats.SPELL_CRIT_DAMAGE.get())
        );
        PerkBuilder.bigStat("cristal_hands", "Crystal Hands",
            new OptScaleExactStat(-10, Stats.EFFECT_OF_BUFFS_ON_YOU_PER_EFFECT_TAG.get(EffectTags.negative)),
            new OptScaleExactStat(5, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(1, new ElementalResist(Elements.Elemental))
        );
        PerkBuilder.bigStat("faithless", "Faithless",
            new OptScaleExactStat(5, new ElementalFocus(Elements.Dark)),
            new OptScaleExactStat(5, Stats.SPELL_ACCURACY.get(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(-5, new ElementalResist(Elements.Light))
        );
        PerkBuilder.bigStat("manasensor", "Mana Sensor",
            new OptScaleExactStat(10, Stats.REDUCED_MANA_RESERVED.get()),
            new OptScaleExactStat(5, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.attack)))
        );

        PerkBuilder.bigStat("shattering_projectiles", "Shattering Projectiles",
            new OptScaleExactStat(10, Stats.PROJECTILE_DAMAGE.get()),
            new OptScaleExactStat(10, Stats.PROJECTILE_SPEED.get())
        );

        PerkBuilder.bigStat("worship", "Worship",
            new OptScaleExactStat(5, Stats.EFFECT_DURATION_ON_YOU_PER_TAG.get(EffectTags.negative)),
            new OptScaleExactStat(5, Stats.EFFECT_DURATION_ON_YOU_PER_TAG.get(EffectTags.positive))
        );
        PerkBuilder.bigStat("pentagram", "Pentagram",
            new OptScaleExactStat(3, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire)),
            new OptScaleExactStat(3, Stats.ELEMENTAL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(-5, new ElementalResist(Elements.Light))
        );

        PerkBuilder.bigStat("idol", "Idol",
            new OptScaleExactStat(10, Stats.ELEMENTAL_DAMAGE.get(Elements.Water)),
            new OptScaleExactStat(10, Stats.ELEMENTAL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(-5, new ElementalResist(Elements.Fire)),
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(-20, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("fundamentalism", "Fundamentalism",
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical)),
            new OptScaleExactStat(5, Stats.DOT_DAMAGE.get())
        );

        PerkBuilder.bigStat("pitchfork", "Pitchfork",
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(-5, Intelligence.INSTANCE),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical)),
            new OptScaleExactStat(-5, Stats.STYLE_DAMAGE.get(PlayStyle.magic))
        );

        PerkBuilder.bigStat("holy_water", "Holy Water",
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Light)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire)),
            new OptScaleExactStat(-5, new ElementalResist(Elements.Dark)),
            new OptScaleExactStat(-20, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("mindful_heart", "Mindful Heart",
            new OptScaleExactStat(20, DatapackStats.HEALTH_PER_10_INT),
            new OptScaleExactStat(5, Strength.INSTANCE)
        );

        PerkBuilder.bigStat("steel_caps", "Steel Caps",
            new OptScaleExactStat(5, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Stats.SHIELD_DURATION.get()),
            new OptScaleExactStat(1, new MaxElementalResist(Elements.Elemental))
        );

        PerkBuilder.bigStat("hammer_smash", "Hammer Smash",
            new OptScaleExactStat(5, Stats.ELEMENTAL_WEAPON_DAMAGE.get(WeaponTypes.hammer)),
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.hammer)),
            new OptScaleExactStat(15, Health.getInstance())
        );

        PerkBuilder.bigStat("mace_smash", "Mace Smash",
            new OptScaleExactStat(5, Stats.ELEMENTAL_WEAPON_DAMAGE.get(WeaponTypes.mace)),
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.mace)),
            new OptScaleExactStat(25, Mana.getInstance())
        );

        PerkBuilder.bigStat("rigor", "Rigor",
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Elemental)),
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(-5, Health.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("uppercut", "Uppercut",
            new OptScaleExactStat(15, Stats.DAMAGE_WHEN_LOW_HP.get())
        );

        PerkBuilder.bigStat("unleashed", "Unleashed",
            new OptScaleExactStat(5, Stats.CHANCE_TO_GIVE_EFFECT_ON_SELF.get(BeneficialEffects.BLOODLUST)),
            new OptScaleExactStat(5, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(-10, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("fury", "Fury",
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.melee)),
            new OptScaleExactStat(5, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(50, Stats.ACCURACY.get()),
            new OptScaleExactStat(5, Strength.INSTANCE)
        );

        PerkBuilder.bigStat("fast_barbarian", "Fast Barbarian",
            new OptScaleExactStat(2, Stats.CRIT_CHANCE.get()),
            new OptScaleExactStat(5, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(100, Stats.ACCURACY.get())
        );

        PerkBuilder.gameChanger("going_dark_side", "Going Dark",
            new OptScaleExactStat(25, Stats.ELEMENTAL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(1, Stats.ALWAYS_CRIT_WHEN_HIT_BY_ELEMENT.get(Elements.Light))
        );
        PerkBuilder.bigStat("swishing_winds", "Swishing Winds",
            new OptScaleExactStat(10, Agility.INSTANCE),
            new OptScaleExactStat(2, Strength.INSTANCE)
        );

        PerkBuilder.bigStat("hammer_mastery", "Hammer Mastery",
            new OptScaleExactStat(10, ArmorPenetration.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Stats.WEAPON_DAMAGE.get(WeaponTypes.hammer)),
            new OptScaleExactStat(10, Strength.INSTANCE)
        );
        PerkBuilder.bigStat("magic_bubble", "Magic Bubble",
            new OptScaleExactStat(10, SpellDodge.getInstance())
        );

        PerkBuilder.bigStat("guild_license", "Guild Licence",
            new OptScaleExactStat(10, Stats.INCREASED_AREA.get()),
            new OptScaleExactStat(10, Stats.DOT_DAMAGE.get()),
            new OptScaleExactStat(-10, Stats.PROJECTILE_SPEED.get()),
            new OptScaleExactStat(-10, Stats.PROJECTILE_DAMAGE.get())
        );

        PerkBuilder.bigStat("hot_headed", "Hot Headed",
            new OptScaleExactStat(5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BURN)),
            new OptScaleExactStat(5, new ElementalResist(Elements.Fire))
        );
    }

}

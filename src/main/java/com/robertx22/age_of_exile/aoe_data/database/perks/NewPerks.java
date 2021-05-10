package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.*;
import com.robertx22.age_of_exile.database.data.stats.types.defense.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalFocus;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.*;

public class NewPerks implements ISlashRegistryInit {

    @Override
    public void registerAll() {

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

        PerkBuilder.bigStat("wand_mastery", "Wand Mastery",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.wand)),
            new OptScaleExactStat(5, Stats.COOLDOWN_REDUCTION.get()),
            new OptScaleExactStat(10, Wisdom.INSTANCE)
        );
        PerkBuilder.bigStat("staff_mastery", "Staff Mastery",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.staff)),
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.magic)),
            new OptScaleExactStat(10, Intelligence.INSTANCE)
        );
        PerkBuilder.bigStat("scythe_mastery", "Scythe Mastery",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.scythe)),
            new OptScaleExactStat(10, Stats.SPELL_CRIT_DAMAGE.get()),
            new OptScaleExactStat(10, Intelligence.INSTANCE)
        );

        PerkBuilder.bigStat("scepter_mastery", "Scepter Mastery",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.scepter)),
            new OptScaleExactStat(5, Stats.HEAL_STRENGTH.get()),
            new OptScaleExactStat(10, Wisdom.INSTANCE),
            new OptScaleExactStat(5, Stats.CAST_SPEED.get())
        );
        PerkBuilder.bigStat("dagger_mastery", "Dagger Mastery",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.dagger)),
            new OptScaleExactStat(5, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(3, Stats.CRIT_CHANCE.get())
        );
        PerkBuilder.bigStat("spear_mastery", "Spear Mastery",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.spear)),
            new OptScaleExactStat(50, Stats.ACCURACY.get()),
            new OptScaleExactStat(5, Dexterity.INSTANCE)
        );
        PerkBuilder.bigStat("glove_mastery", "Glove Mastery",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.glove)),
            new OptScaleExactStat(10, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.health, AttackType.attack)))
        );

        PerkBuilder.bigStat("sword_mastery", "Sword Mastery",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.sword)),
            new OptScaleExactStat(5, Stats.CRIT_CHANCE.get()),
            new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("axe_mastery", "Axe Mastery",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.axe)),
            new OptScaleExactStat(10, Stats.CRIT_DAMAGE.get()),
            new OptScaleExactStat(10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("bow_mastery", "Bow Mastery",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.bow)),
            new OptScaleExactStat(5, Stats.PROJECTILE_DAMAGE.get()),
            new OptScaleExactStat(10, Dexterity.INSTANCE),
            new OptScaleExactStat(5, Stats.CRIT_DAMAGE.get())
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

        PerkBuilder.bigStat("brilliance", "Brilliance",
            new OptScaleExactStat(5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.SLOW)),
            new OptScaleExactStat(5, new ElementalResist(Elements.Water))
        );

        PerkBuilder.bigStat("emptiness", "Emptiness",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Light)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(5, Stats.SPELL_CRIT_DAMAGE.get()),
            new OptScaleExactStat(5, new ElementalPenetration(Elements.Elemental))
        );
        PerkBuilder.bigStat("frostburn", "Frostburn",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Water)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire)),
            new OptScaleExactStat(3, Stats.SPELL_CRIT_CHANCE.get()),
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("broad_knowledge", "Broad Knowledge",
            new OptScaleExactStat(1, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BURN)),
            new OptScaleExactStat(1, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.POISON)),
            new OptScaleExactStat(1, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.FROSTBURN)),
            new OptScaleExactStat(1, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BLIND)),
            new OptScaleExactStat(1, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BLEED)),
            new OptScaleExactStat(1, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.TORMENT))
        );

        PerkBuilder.bigStat("ancient_rune", "Ancient Rune",
            new OptScaleExactStat(10, Stats.SPELL_ACCURACY.get(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.spear)),
            new OptScaleExactStat(3, Stats.SPELL_CRIT_CHANCE.get())
        );

        PerkBuilder.bigStat("infusion", "Infusion",
            new OptScaleExactStat(5, new AttackDamage(Elements.Fire), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, new AttackDamage(Elements.Water), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, new AttackDamage(Elements.Nature), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, new AttackDamage(Elements.Light), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, new AttackDamage(Elements.Dark), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, new ElementalPenetration(Elements.Elemental))
        );

        PerkBuilder.bigStat("light_shackles", "Shackles of Light",
            new OptScaleExactStat(10, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.SLOW))
        );

        PerkBuilder.bigStat("runed_geode", "Runed Geode",
            new OptScaleExactStat(10, Stats.AREA_DAMAGE.get()),
            new OptScaleExactStat(10, Stats.SHIELD_STRENGTH.get())
        );

        PerkBuilder.bigStat("unforgiving_desert", "Unforgiving Desert",
            new OptScaleExactStat(5, Vitality.INSTANCE),
            new OptScaleExactStat(3, new ElementalResist(Elements.Fire)),
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("spellblade", "Spellblade",
            new OptScaleExactStat(25, Stats.CHANCE_ON_BASIC_ATK_TO_GIVE_EFFECT_ON_SELF.get(BeneficialEffects.INFUSED_BLADE)),
            new OptScaleExactStat(10, SpellDodge.getInstance())
        );

        PerkBuilder.bigStat("conquerer", "Conqueror",
            new OptScaleExactStat(10, Stats.CHANCE_TO_GIVE_EFFECT_ON_KILL.get(BeneficialEffects.BLESSING)),
            new OptScaleExactStat(15, Stats.SHIELD_STRENGTH.get())
        );

        PerkBuilder.bigStat("fanaticism", "Fanaticism",
            new OptScaleExactStat(5, Stats.SPELL_CRIT_CHANCE.get()),
            new OptScaleExactStat(15, Stats.SHIELD_STRENGTH.get())
        );
        PerkBuilder.bigStat("praise_zadall", "Praise Zadall",
            new OptScaleExactStat(5, Intelligence.INSTANCE),
            new OptScaleExactStat(5, Strength.INSTANCE),
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(2, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire)),
            new OptScaleExactStat(10, Stats.INCREASED_EFFECT_OF_AURAS_RECEIVED.get())
        );

        PerkBuilder.bigStat("stubborn", "Stubborn",
            new OptScaleExactStat(2, RegeneratePercentStat.HEALTH),
            new OptScaleExactStat(-5, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.melee)),
            new OptScaleExactStat(5, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("rain_of_sorrow", "Rain of Sorrow",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.dagger)),
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.bow)),
            new OptScaleExactStat(5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.POISON))
        );
        PerkBuilder.bigStat("slaughter", "Slaughter",
            new OptScaleExactStat(20, ArmorPenetration.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Strength.INSTANCE),
            new OptScaleExactStat(-5, Stats.ATTACK_SPEED.get())
        );

        PerkBuilder.bigStat("slice_and_dice", "Slice and Dice",
            new OptScaleExactStat(5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BLEED)),
            new OptScaleExactStat(5, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.ATTACK_SPEED.get())
        );

        PerkBuilder.bigStat("confrontation", "Confrontation",
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.DAY_DAMAGE.get()),
            new OptScaleExactStat(10, Stats.ATTACK_SPEED.get())
        );

        PerkBuilder.bigStat("jagged_blades", "Jagged Blades",
            new OptScaleExactStat(5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BLEED)),
            new OptScaleExactStat(10, Stats.CRIT_DAMAGE.get())
        );

        PerkBuilder.bigStat("anatomical_knowledge", "Anatomical Knowledge",
            new OptScaleExactStat(5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.SLOW)),
            new OptScaleExactStat(10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("execute", "Executioner",
            new OptScaleExactStat(10, Stats.DAMAGE_WHEN_TARGET_IS_LOW_HP.get())
        );

        PerkBuilder.bigStat("swiftness", "Swiftness",
            new OptScaleExactStat(5, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, SpellDodge.getInstance()),
            new OptScaleExactStat(-2, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.ranged)),
            new OptScaleExactStat(5, Agility.INSTANCE)
        );

        PerkBuilder.bigStat("good_posture", "Good Posture",
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.REDUCED_MANA_RESERVED.get())
        );

        PerkBuilder.bigStat("rusty_weapons", "Rusty Weapons",
            new OptScaleExactStat(5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.POISON)),
            new OptScaleExactStat(10, Stats.DOT_DAMAGE.get())
        );

        PerkBuilder.bigStat("drunken_boxing", "Drunken Boxing",
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(-5, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(20, Stats.CRIT_DAMAGE.get()),
            new OptScaleExactStat(-5, Wisdom.INSTANCE),
            new OptScaleExactStat(-5, Intelligence.INSTANCE)
        );
        PerkBuilder.bigStat("mana_vortex", "Mana Vortex",
            new OptScaleExactStat(5, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.REDUCED_MANA_RESERVED.get())
        );

        PerkBuilder.bigStat("rune_circle", "Rune Circle",
            new OptScaleExactStat(10, Stats.INCREASED_EFFECT_OF_AURAS_GIVEN.get()),
            new OptScaleExactStat(100, Stats.SPELL_ACCURACY.get())
        );

        PerkBuilder.bigStat("flexible_mind", "Flexible Mind",
            new OptScaleExactStat(5, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.SLOW)),
            new OptScaleExactStat(5, new ElementalResist(Elements.Water))
        );

        PerkBuilder.bigStat("atonement", "Atonement",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Nature)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Light)),
            new OptScaleExactStat(2, new ElementalPenetration(Elements.Elemental)),
            new OptScaleExactStat(5, Stats.DOT_DAMAGE.get())
        );

        PerkBuilder.bigStat("eclipse", "Eclipse",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Nature)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(10, Stats.SPELL_CRIT_DAMAGE.get()),
            new OptScaleExactStat(5, Stats.DOT_DAMAGE.get())
        );

        PerkBuilder.bigStat("ritual", "Ritual",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Water)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(10, Stats.SPELL_CRIT_DAMAGE.get()),
            new OptScaleExactStat(5, ManaRegen.getInstance())
        );

        PerkBuilder.bigStat("ignition", "Ignition",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Nature)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire)),
            new OptScaleExactStat(3, Stats.SPELL_CRIT_CHANCE.get()),
            new OptScaleExactStat(5, Stats.DOT_DAMAGE.get())
        );

        PerkBuilder.bigStat("gravedigger", "Grave Digger",
            new OptScaleExactStat(2, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.POISON)),
            new OptScaleExactStat(2, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BURN)),
            new OptScaleExactStat(2, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.SLOW)),
            new OptScaleExactStat(10, Stats.RESOURCE_ON_KILL.get(ResourceType.health))
        );
        PerkBuilder.bigStat("gathering_storm", "Gathering Storm",
            new OptScaleExactStat(25, Stats.CHANCE_ON_BASIC_ATK_TO_GIVE_EFFECT_ON_SELF.get(BeneficialEffects.GATHER_STORM)),
            new OptScaleExactStat(10, Stats.INCREASED_AREA.get())
        );

        PerkBuilder.bigStat("sandstream", "Sandstream",
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Nature)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Fire)),
            new OptScaleExactStat(2, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.SLOW))
        );

        PerkBuilder.bigStat("living_thunder", "Living Thunder",
            new OptScaleExactStat(2, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.FROSTBURN)),
            new OptScaleExactStat(2, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BURN)),
            new OptScaleExactStat(4, DatapackStats.MOVE_SPEED)
        );
        PerkBuilder.bigStat("deceiver", "Deceiver",
            new OptScaleExactStat(10, Stats.CHANCE_TO_GIVE_EFFECT_ON_KILL.get(BeneficialEffects.MARK)),
            new OptScaleExactStat(5, Stats.SHIELD_DURATION.get())
        );
        PerkBuilder.bigStat("last_rite", "Last Rite",
            new OptScaleExactStat(5, SpellDamage.getInstance()),
            new OptScaleExactStat(10, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(-2, Mana.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("sandtomb", "Sand Tomb",
            new OptScaleExactStat(5, Vitality.INSTANCE),
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(2, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("drums", "Drums",
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.melee)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Elemental))
        );

        PerkBuilder.bigStat("warmth", "Warmth",
            new OptScaleExactStat(10, Stats.HEALING_RECEIVED.get()),
            new OptScaleExactStat(5, Stats.REDUCED_MANA_RESERVED.get())
        );

        PerkBuilder.bigStat("deepfrost", "Deepfrost",
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.ranged)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Water))
        );

        PerkBuilder.bigStat("infernal_weapons", "Infernal Weapons",
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.melee)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire))
        );

        PerkBuilder.bigStat("cataclysm", "Cataclysm",
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Water)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Nature))
        );

        PerkBuilder.bigStat("warpath", "Warpath",
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.axe)),
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.spear)),
            new OptScaleExactStat(5, Stats.WEAPON_DAMAGE.get(WeaponTypes.mace))
        );

        PerkBuilder.bigStat("lucidity", "Lucidity",
            new OptScaleExactStat(50, Stats.SPELL_ACCURACY.get()),
            new OptScaleExactStat(5, Wisdom.INSTANCE)
        );
        PerkBuilder.bigStat("stone_armor", "Stone Armor",
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("sun_essence", "Sun Essence",
            new OptScaleExactStat(10, Stats.HEAL_STRENGTH.get()),
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("astralwalk", "Astral Walk",
            new OptScaleExactStat(10, Wisdom.INSTANCE),
            new OptScaleExactStat(3, DatapackStats.MOVE_SPEED)
        );

        PerkBuilder.bigStat("moon_essence", "Moon Essence",
            new OptScaleExactStat(10, Stats.HEAL_CRIT_DAMAGE.get()),
            new OptScaleExactStat(10, Mana.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("spiritbond", "Spirit Bond",
            new OptScaleExactStat(10, Stats.SHIELD_DURATION.get()),
            new OptScaleExactStat(10, Stats.SHIELD_STRENGTH.get())
        );

        PerkBuilder.bigStat("angelic_judgement", "Angelic Judgement",
            new OptScaleExactStat(20, Stats.RESOURCE_ON_KILL.get(ResourceType.mana)),
            new OptScaleExactStat(2, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.JUDGEMENT))
        );

        PerkBuilder.bigStat("burning_heart", "Burning Heart",
            new OptScaleExactStat(10, Stats.HEAL_STRENGTH.get()),
            new OptScaleExactStat(50, Mana.getInstance()),
            new OptScaleExactStat(10, Wisdom.INSTANCE)
        );

        PerkBuilder.bigStat("soft_breeze", "Soft Breeze",
            new OptScaleExactStat(5, Stats.HEAL_STRENGTH.get()),
            new OptScaleExactStat(5, SpecialStats.HEAL_CLEANSE),
            new OptScaleExactStat(3, Stats.HEAL_CRIT_CHANCE.get()),
            new OptScaleExactStat(5, Intelligence.INSTANCE)
        );

        PerkBuilder.bigStat("angelic_judgement", "Angelic Judgement",
            new OptScaleExactStat(20, Stats.RESOURCE_ON_KILL.get(ResourceType.mana)),
            new OptScaleExactStat(2, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.JUDGEMENT))
        );

        PerkBuilder.bigStat("hell", "Hell",
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Dark)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Water))
        );

        PerkBuilder.bigStat("purgatory", "Purgatory",
            new OptScaleExactStat(10, Stats.CRIT_DAMAGE.get()),
            new OptScaleExactStat(10, Stats.DAMAGE_WHEN_LOW_HP.get()),
            new OptScaleExactStat(50, Health.getInstance())
        );

        PerkBuilder.bigStat("blade_dance", "Blade Dance",
            new OptScaleExactStat(10, Stats.CHANCE_ON_BASIC_ATK_TO_GIVE_EFFECT_ON_SELF.get(BeneficialEffects.BLADE_DANCE))
        );

        PerkBuilder.bigStat("precise_preperation", "Precise Preparation",
            new OptScaleExactStat(5, Stats.COOLDOWN_REDUCTION.get()),
            new OptScaleExactStat(10, Stats.REDUCED_MANA_RESERVED.get()),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical))
        );

        PerkBuilder.bigStat("pyromaniac", "Pyromaniac",
            new OptScaleExactStat(5, Stats.INCREASED_AREA.get()),
            new OptScaleExactStat(3, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BURN)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire))
        );
        PerkBuilder.bigStat("spot_weakness", "Spot Weakness",
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.melee)),
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.ranged)),
            new OptScaleExactStat(10, ArmorPenetration.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Stats.CRIT_CHANCE.get())
        );

        PerkBuilder.bigStat("outlaw", "Outlaw",
            new OptScaleExactStat(10, Stats.DAMAGE_TO_LIVING.get()),
            new OptScaleExactStat(5, Stats.CRIT_CHANCE.get()),
            new OptScaleExactStat(-5, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(-5, Health.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("necronomicon", "Necronomicon",
            new OptScaleExactStat(10, Stats.RESOURCE_ON_KILL.get(ResourceType.health)),
            new OptScaleExactStat(10, Stats.RESOURCE_ON_KILL.get(ResourceType.mana))
        );
        PerkBuilder.bigStat("call_of_the_other_ones", "Call of the Other Ones",
            new OptScaleExactStat(20, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.negative))
        );
        PerkBuilder.bigStat("beyond_good_and_evil", "Beyond good and Evil",
            new OptScaleExactStat(-20, Stats.EFFECT_DURATION_ON_YOU_PER_TAG.get(EffectTags.negative))
        );
        PerkBuilder.bigStat("step_tension", "Step Tension",
            new OptScaleExactStat(3, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.SLOW)),
            new OptScaleExactStat(5, DatapackStats.MOVE_SPEED)
        );
        PerkBuilder.bigStat("eternity", "Eternity",
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Wisdom.INSTANCE)
        );
        PerkBuilder.bigStat("sunshine", "Sunshine",
            new OptScaleExactStat(10, Stats.EFFECT_DURATION_ON_YOU_PER_TAG.get(EffectTags.positive)),
            new OptScaleExactStat(5, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)
        );
        PerkBuilder.bigStat("moonshine", "Moonshine",
            new OptScaleExactStat(10, Stats.EFFECT_DURATION_YOU_CAST_PER_TAG.get(EffectTags.negative)),
            new OptScaleExactStat(5, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("demonic_seduction", "Demonic Seduction",
            new OptScaleExactStat(3, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.BLIND)),
            new OptScaleExactStat(10, Stats.RESOURCE_ON_KILL.get(ResourceType.health))
        );

        PerkBuilder.bigStat("burning_eyes", "Burning Eyes",
            new OptScaleExactStat(5, Stats.PROJECTILE_DAMAGE.get()),
            new OptScaleExactStat(10, Dexterity.INSTANCE),
            new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("backlash", "Backlash",
            new OptScaleExactStat(5, Stats.PROJECTILE_SPEED.get()),
            new OptScaleExactStat(10, Stats.ATTACK_SPEED.get()),
            new OptScaleExactStat(50, Stats.ACCURACY.get()),
            new OptScaleExactStat(10, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("juggler", "Juggler",
            new OptScaleExactStat(5, Stats.PROJECTILE_DAMAGE.get()),
            new OptScaleExactStat(5, Intelligence.INSTANCE),
            new OptScaleExactStat(5, Dexterity.INSTANCE)
        );

        PerkBuilder.bigStat("pathfinder", "Pathfinder",
            new OptScaleExactStat(5, DatapackStats.MOVE_SPEED),
            new OptScaleExactStat(5, Agility.INSTANCE),
            new OptScaleExactStat(5, Dexterity.INSTANCE)
        );

        PerkBuilder.bigStat("survivalist", "Survivalist",
            new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, Armor.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(5, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, HealthRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("mendacity", "Mendacity",
            new OptScaleExactStat(5, Stats.CRIT_CHANCE.get()),
            new OptScaleExactStat(5, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, ManaRegen.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("for_the_living", "For the Living",
            new OptScaleExactStat(5, Stats.CRIT_CHANCE.get()),
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.ranged)),
            new OptScaleExactStat(10, Stats.DAMAGE_TO_UNDEAD.get())
        );

        PerkBuilder.bigStat("runeleather", "Rune Leather",
            new OptScaleExactStat(5, Stats.SHIELD_DURATION.get()),
            new OptScaleExactStat(5, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new OptScaleExactStat(10, Armor.getInstance(), ModType.LOCAL_INCREASE)
        );

        PerkBuilder.bigStat("angelfeather", "Angel Feather",
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.ranged)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Light))
        );

        PerkBuilder.bigStat("ice_tip", "Ice Tip",
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.ranged)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Water))
        );
        PerkBuilder.bigStat("fire_arrows", "Fire Arrows",
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.ranged)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire))
        );
        PerkBuilder.bigStat("crystal_arrows", "Crystal Arrows",
            new OptScaleExactStat(5, Stats.STYLE_DAMAGE.get(PlayStyle.ranged)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Water)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Nature)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Light)),
            new OptScaleExactStat(5, Stats.ELEMENTAL_DAMAGE.get(Elements.Dark))
        );

        PerkBuilder.bigStat("hold_breath", "Hold Breath",
            new OptScaleExactStat(15, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTag.arrow))
        );

    }

}

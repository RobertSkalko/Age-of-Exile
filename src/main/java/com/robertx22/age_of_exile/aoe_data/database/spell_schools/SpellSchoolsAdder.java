package com.robertx22.age_of_exile.aoe_data.database.spell_schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.impl.TotemSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.*;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SpellSchoolsAdder implements ExileRegistryInit {

    public static String DIVINE = "divine";
    public static String HUNTING = "hunting";
    public static String FIRE = "fire";
    public static String NATURE = "nature";
    public static String WATER = "water";

    @Override
    public void registerAll() {

        SchoolBuilder.of(DIVINE, "Divine")
            .addSpell(HolySpells.HEALING_AURA_ID, new PointData(0, 0))
            .addSpell(HolySpells.GONG_STRIKE_ID, new PointData(2, 0))

            .addSpell(HolySpells.WHIRLWIND, new PointData(6, 1))
            .addSpell(HolySpells.CHARGE_ID, new PointData(4, 1))

            .addSpell(HolySpells.SHOOTING_STAR, new PointData(1, 2))
            .addSpell(HolySpells.UNDYING_WILL, new PointData(3, 2))

            .addSpell(HolySpells.PULL, new PointData(8, 3))

            .addSpell(HolySpells.INSPIRATION, new PointData(7, 4))

            .addSpell(HolySpells.HYMN_OF_PERSERVANCE, new PointData(1, 4))
            .addSpell(HolySpells.HYMN_OF_VALOR, new PointData(2, 4))
            .addSpell(HolySpells.HYMN_OF_VIGOR, new PointData(3, 4))

            .addSpell(HolySpells.TAUNT, new PointData(5, 5))
            .addSpell(HolySpells.BANISH, new PointData(10, 5))

            .addSpell(HolySpells.WISH, new PointData(3, 6))

            .build();

        SchoolBuilder.of(HUNTING, "Hunting")
            .addSpell(RangerSpells.ARROW_STORM, new PointData(0, 0))
            .addSpell(RangerSpells.MAKE_ARROWS, new PointData(10, 0))

            .addSpell(RangerSpells.DASH_ID, new PointData(5, 1))
            .addSpell(RangerSpells.HUNTER_POTION, new PointData(10, 1))

            .addSpell(RangerSpells.THE_HUNT, new PointData(10, 2))
            .addSpell(RangerSpells.POISON_ARROW, new PointData(2, 2))
            .addSpell(RangerSpells.SMOKE_BOMB, new PointData(7, 2))

            .addSpell(RangerSpells.CHARGED_BOLT, new PointData(0, 3))
            .addSpell(RangerSpells.BACKFLIP, new PointData(4, 3))

            .addSpell(RangerSpells.FIRE_TRAP, new PointData(6, 4))
            .addSpell(RangerSpells.FROST_TRAP, new PointData(7, 4))
            .addSpell(RangerSpells.POISON_TRAP, new PointData(8, 4))
            .addSpell(RangerSpells.NIGHT_VISION, new PointData(10, 4))

            .addSpell(RangerSpells.EXPLOSIVE_ARROW_ID, new PointData(2, 5))

            .build();

        SchoolBuilder.of(NATURE, "Nature")

            .addSpell(NatureSpells.POISON_WEAPONS, new PointData(7, 0))

            .addSpell(NatureSpells.THORN_ARMOR, new PointData(10, 1))
            .addSpell(NatureSpells.NATURE_BALM, new PointData(2, 1))

            .addSpell(NatureSpells.ENTANGLE_SEED, new PointData(4, 2))
            .addSpell(TotemSpells.HEAL_TOTEM_ID, new PointData(6, 2))
            .addSpell(TotemSpells.MANA_TOTEM_ID, new PointData(7, 2))
            .addSpell(TotemSpells.GUARD_TOTEM_ID, new PointData(8, 2))

            .addSpell(NatureSpells.POISON_CLOUD, new PointData(2, 3))

            .addSpell(NatureSpells.REFRESH, new PointData(10, 6))

            .build();

        SchoolBuilder.of(FIRE, "Fire")
            .addSpell(SpellKeys.MAGIC_PROJECTILE.id, new PointData(0, 0))
            .addSpell(FireSpells.FLAME_STRIKE_ID, new PointData(5, 0))

            .addSpell(FireSpells.FLAME_WEAPON, new PointData(7, 1))

            .addSpell(SpellKeys.METEOR.id, new PointData(2, 3))

            .addSpell(FireSpells.VAMP_BLOOD, new PointData(5, 4))
            .addSpell(FireSpells.DRACONIC_BLOOD, new PointData(6, 4))

            .addSpell(FireSpells.OVERLOAD, new PointData(10, 5))

            .build();

        SchoolBuilder.of(WATER, "Water")
            .addSpell(WaterSpells.TIDAL_STRIKE, new PointData(4, 0))

            .addSpell(WaterSpells.FROST_NOVA_AOE, new PointData(2, 1))
            .addSpell(WaterSpells.WATER_BREATH, new PointData(10, 1))
            .addSpell(SpellKeys.ICE_SHIELD.id, new PointData(7, 1))

            .addSpell(WaterSpells.ICY_WEAPON, new PointData(10, 2))
            .addSpell(WaterSpells.CHILLING_FIELD, new PointData(5, 2))

            .addSpell(WaterSpells.NOURISHMENT, new PointData(7, 3))
            .addSpell(WaterSpells.CHILL_ERUPTION, new PointData(2, 3))

            .addSpell(WaterSpells.MAGE_CIRCLE, new PointData(10, 4))

            //.addSynergy(SynergiesAdder.FROSTBALL_CHILL, new PointData(0, 1))

            .build();

    }
}

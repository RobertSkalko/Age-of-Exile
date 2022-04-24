package com.robertx22.age_of_exile.aoe_data.database.spell_schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.impl.TotemSpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.HolySpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.HunterSpells;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SpellSchoolsAdder implements ExileRegistryInit {

    public static String BARD = "bard";
    public static String HUNTER = "hunter";
    public static String ELEMENTALIST = "elementalist";
    public static String WARRIOR = "warrior";

    @Override
    public void registerAll() {

        SchoolBuilder.of(BARD, "Bard")

            .addSpell(SpellKeys.POWER_CHORD.id, new PointData(0, 0))
            .addSpell(SpellKeys.POWER_CHORD.id, new PointData(0, 2))
            .addSpell(HolySpells.HEALING_AURA_ID, new PointData(0, 5))

            .addSpell(HolySpells.SHOOTING_STAR, new PointData(1, 2))
            .addSpell(HolySpells.UNDYING_WILL, new PointData(3, 2))

            .addSpell(HolySpells.PULL, new PointData(8, 3))

            .addSpell(HolySpells.INSPIRATION, new PointData(7, 4))

            .addSpell(SpellKeys.SONG_OF_PERSEVERANCE.id, new PointData(1, 4))
            .addSpell(SpellKeys.SONG_OF_VALOR.id, new PointData(2, 4))
            .addSpell(SpellKeys.SONG_OF_VIGOR.id, new PointData(3, 4))

            .addSpell(HolySpells.TAUNT, new PointData(5, 5))

            .build();

        SchoolBuilder.of(HUNTER, "Hunting")

            .addSpell(SpellKeys.MAKE_ARROWS, new PointData(0, 0))
            .addSpell(SpellKeys.EXPLOSIVE_ARROW, new PointData(0, 2))

            .addSpell(HunterSpells.DASH_ID, new PointData(5, 1))
            .addSpell(HunterSpells.HUNTER_POTION, new PointData(10, 1))

            .addSpell(HunterSpells.POISON_ARROW, new PointData(2, 2))
            .addSpell(HunterSpells.SMOKE_BOMB, new PointData(7, 2))

            .addSpell(HunterSpells.CHARGED_BOLT, new PointData(0, 3))
            .addSpell(HunterSpells.BACKFLIP, new PointData(4, 3))

            .addSpell(HunterSpells.FIRE_TRAP, new PointData(6, 4))
            .addSpell(HunterSpells.FROST_TRAP, new PointData(7, 4))
            .addSpell(HunterSpells.POISON_TRAP, new PointData(8, 4))
            .addSpell(HunterSpells.NIGHT_VISION, new PointData(10, 4))

            .build();

        SchoolBuilder.of(ELEMENTALIST, "Elementalist")
            //.addSpell(SpellKeys.MAGIC_PROJECTILE.id, new PointData(0, 0))

            // fire
            .addSpell(SpellKeys.METEOR.id, new PointData(0, 0))

            // poison
            .addSpell(SpellKeys.POISON_CLOUD.id, new PointData(5, 0))
            .addSpell(SpellKeys.NATURE_BALM.id, new PointData(5, 1))
            .addSpell(SpellKeys.REFRESH.id, new PointData(5, 2))

            // ice
            .addSpell(SpellKeys.ICE_NOVA.id, new PointData(10, 0))
            .addSpell(SpellKeys.ICE_SHIELD.id, new PointData(10, 1))

            // misc
            .addSpell(SpellKeys.TELEPORT.id, new PointData(1, 5))

            .build();

        SchoolBuilder.of(WARRIOR, "Warrior")

            .addSpell(SpellKeys.METEOR_STRIKE.id, new PointData(0, 0))
            .addSpell(SpellKeys.VENOM_STRIKE.id, new PointData(5, 0))
            .addSpell(SpellKeys.TIDAL_WAVE.id, new PointData(10, 0))

            .addSpell(TotemSpells.HEAL_TOTEM_ID, new PointData(6, 2))
            .addSpell(TotemSpells.MANA_TOTEM_ID, new PointData(7, 2))

            .build();

    }
}

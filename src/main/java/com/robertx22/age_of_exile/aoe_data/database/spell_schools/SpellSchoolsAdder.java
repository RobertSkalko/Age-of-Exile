package com.robertx22.age_of_exile.aoe_data.database.spell_schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.schools.HolySpells;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.HunterSpells;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SpellSchoolsAdder implements ExileRegistryInit {

    public static String BARD = "bard";
    public static String HUNTER = "hunter";
    public static String ELEMENTALIST = "elementalist";
    public static String SHAMAN = "shaman";

    @Override
    public void registerAll() {

        SchoolBuilder.of(BARD, "Bard")

            .addSpell(SpellKeys.POWER_CHORD.id, new PointData(0, 0))
            .addSpell(SpellKeys.EXPLOSIVE_NOTE.id, new PointData(0, 1))
            .addSpell(SpellKeys.NOCTURNE.id, new PointData(0, 2))

            .addSpell(SpellKeys.SHOOTING_STAR, new PointData(5, 0))
            .addSpell(SpellKeys.HEALING_ARIA.id, new PointData(5, 1))
            .addSpell(SpellKeys.PURIFYING_TOUCH, new PointData(5, 2))

            .addSpell(SpellKeys.SONG_OF_VIGOR.id, new PointData(10, 0))
            .addSpell(SpellKeys.SONG_OF_PERSEVERANCE.id, new PointData(10, 1))
            .addSpell(SpellKeys.SONG_OF_VALOR.id, new PointData(10, 2))

            .build();

        SchoolBuilder.of(HUNTER, "Hunting")

            .addSpell(SpellKeys.MAKE_ARROWS, new PointData(0, 0))
            .addSpell(SpellKeys.EXPLOSIVE_ARROW, new PointData(0, 2))

            .addSpell(SpellKeys.FIRE_TRAP, new PointData(6, 4))
            .addSpell(SpellKeys.FROST_TRAP, new PointData(7, 4))
            .addSpell(SpellKeys.POISON_TRAP, new PointData(8, 4))

            .addSpell(HunterSpells.DASH_ID, new PointData(5, 1))
            .addSpell(HunterSpells.HUNTER_POTION, new PointData(10, 1))

            .addSpell(HunterSpells.POISON_ARROW, new PointData(2, 2))
            .addSpell(HunterSpells.SMOKE_BOMB, new PointData(7, 2))

            .addSpell(HunterSpells.CHARGED_BOLT, new PointData(0, 3))
            .addSpell(HunterSpells.BACKFLIP, new PointData(4, 3))

            .addSpell(HunterSpells.NIGHT_VISION, new PointData(10, 4))

            .build();

        SchoolBuilder.of(ELEMENTALIST, "Elementalist")

            // fire
            .addSpell(SpellKeys.SEEKER_FLAMES.id, new PointData(0, 0))
            .addSpell(SpellKeys.METEOR.id, new PointData(0, 1))
            .addSpell(SpellKeys.MAGMA_FLOWER.id, new PointData(0, 2))

            // poison
            .addSpell(SpellKeys.POISON_CLOUD.id, new PointData(5, 0))
            .addSpell(SpellKeys.NATURE_BALM.id, new PointData(5, 1))
            .addSpell(SpellKeys.BOULDER_TOSS.id, new PointData(5, 2))
            .addSpell(SpellKeys.REFRESH.id, new PointData(5, 3))

            // ice
            .addSpell(SpellKeys.ICE_SNAKE.id, new PointData(10, 0))
            .addSpell(SpellKeys.ICE_NOVA.id, new PointData(10, 1))
            .addSpell(SpellKeys.ICE_SHIELD.id, new PointData(10, 2))
            .addSpell(SpellKeys.FROST_STEPS.id, new PointData(10, 3))

            // misc
            .addSpell(SpellKeys.TELEPORT.id, new PointData(1, 5))

            .build();

        SchoolBuilder.of(SHAMAN, "Shaman")

            .addSpell(SpellKeys.METEOR_STRIKE.id, new PointData(0, 0))
            .addSpell(SpellKeys.DAMAGE_TOTEM.id, new PointData(0, 1))
            .addSpell(SpellKeys.CURSE_OF_AGONY.id, new PointData(0, 2))

            .addSpell(SpellKeys.VENOM_STRIKE.id, new PointData(5, 0))
            .addSpell(SpellKeys.HEAL_TOTEM.id, new PointData(5, 1))
            .addSpell(SpellKeys.CURSE_OF_DESPAIR.id, new PointData(5, 2))

            .addSpell(SpellKeys.TIDAL_WAVE.id, new PointData(10, 0))
            .addSpell(SpellKeys.MANA_TOTEM.id, new PointData(10, 1))
            .addSpell(SpellKeys.CURSE_OF_WEAKNESS.id, new PointData(10, 2))

            .addSpell(HolySpells.TAUNT, new PointData(5, 5))

            .build();

    }
}

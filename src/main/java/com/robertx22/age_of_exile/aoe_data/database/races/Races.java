package com.robertx22.age_of_exile.aoe_data.database.races;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.races.ExtraStatPerStat;
import com.robertx22.age_of_exile.database.data.races.PlayerRace;
import com.robertx22.age_of_exile.database.data.races.RaceLevelingPerk;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class Races implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        PlayerRace.of("human", "Human", "An all rounded race, ready to pick any path!",
            Arrays.asList(

                new ExtraStatPerStat(new OptScaleExactStat(2, Health.getInstance(), ModType.FLAT), Strength.INSTANCE
                    .GUID()),
                new ExtraStatPerStat(new OptScaleExactStat(3, DodgeRating.getInstance(), ModType.FLAT), Dexterity.INSTANCE
                    .GUID()),
                new ExtraStatPerStat(new OptScaleExactStat(3, Mana.getInstance(), ModType.FLAT), Intelligence.INSTANCE
                    .GUID())
            ),

            Arrays.asList(new OptScaleExactStat(5, Health.getInstance(), ModType.LOCAL_INCREASE),
                new OptScaleExactStat(5, Mana.getInstance(), ModType.LOCAL_INCREASE)
            )
            ,
            RaceLevelingPerk.profession(PlayerSkillEnum.COOKING)
        )
            .addToSerializables();

        PlayerRace.of("dwarf", "Dwarf", "Sturdy race that is known for their handicraft.",
            Arrays.asList(
                new ExtraStatPerStat(new OptScaleExactStat(3, Health.getInstance(), ModType.FLAT), Strength.INSTANCE
                    .GUID()),
                new ExtraStatPerStat(new OptScaleExactStat(1, Health.getInstance(), ModType.FLAT), Dexterity.INSTANCE
                    .GUID()),
                new ExtraStatPerStat(new OptScaleExactStat(1, Health.getInstance(), ModType.FLAT), Intelligence.INSTANCE
                    .GUID())
            ),
            Arrays.asList(new OptScaleExactStat(10, Health.getInstance(), ModType.LOCAL_INCREASE))
            ,
            RaceLevelingPerk.profession(PlayerSkillEnum.TINKERING)
        )
            .addToSerializables();

        PlayerRace.of("high_elf", "High Elf", "In tune with essence, these beings understand mana more effectively.",
            Arrays.asList(
                new ExtraStatPerStat(new OptScaleExactStat(1, Mana.getInstance(), ModType.FLAT), Strength.INSTANCE
                    .GUID()),
                new ExtraStatPerStat(new OptScaleExactStat(1, Mana.getInstance(), ModType.FLAT), Dexterity.INSTANCE
                    .GUID()),
                new ExtraStatPerStat(new OptScaleExactStat(4, Mana.getInstance(), ModType.FLAT), Intelligence.INSTANCE
                    .GUID())
            ),

            Arrays.asList(new OptScaleExactStat(10, new ElementalResist(Elements.Elemental))),

            RaceLevelingPerk.profession(PlayerSkillEnum.INSCRIBING)
        )
            .addToSerializables();

        PlayerRace.of("wood_elf", "Wood Elf", "Protectors of nature.",
            Arrays.asList(
                new ExtraStatPerStat(new OptScaleExactStat(2, DodgeRating.getInstance(), ModType.FLAT), Strength.INSTANCE
                    .GUID()),
                new ExtraStatPerStat(new OptScaleExactStat(5, DodgeRating.getInstance(), ModType.FLAT), Dexterity.INSTANCE
                    .GUID()),
                new ExtraStatPerStat(new OptScaleExactStat(2, DodgeRating.getInstance(), ModType.FLAT), Intelligence.INSTANCE
                    .GUID())
            ),
            Arrays.asList(new OptScaleExactStat(40, new ElementalResist(Elements.Nature))),

            RaceLevelingPerk.profession(PlayerSkillEnum.ALCHEMY)
        )
            .addToSerializables();
    }
}

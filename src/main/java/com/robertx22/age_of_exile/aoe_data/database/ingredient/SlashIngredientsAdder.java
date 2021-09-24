package com.robertx22.age_of_exile.aoe_data.database.ingredient;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.IngredientItems;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SlashIngredientsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new EpicIngredientsAdder().registerAll();
        new SpecialIngredientsAdder().registerAll();

        IngredientBuilder.of(IngredientItems.GUARDIAN_SCALES, IRarity.COMMON_ID)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.ARMOR_CRAFTING)
            .stats(new StatModifier(2, 6, new ElementalResist(Elements.Water)))
            .stats(new StatModifier(2, 4, Armor.getInstance(), ModType.PERCENT))
            .build();

        IngredientBuilder.of(IngredientItems.FRESH_HEART, IRarity.UNCOMMON)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.COOKING)
            .stats(new StatModifier(2, 8, HealthRegen.getInstance(), ModType.PERCENT))
            .build();

        IngredientBuilder.of(IngredientItems.SLIME_BOLUS, IRarity.COMMON_ID)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.COOKING)
            .allowedIn(PlayerSkillEnum.WEAPON_CRAFTING)
            .stats(new StatModifier(1, 5, Stats.ELEMENTAL_DAMAGE.get(Elements.Earth)))
            .stats(new StatModifier(1, 3, Stats.DOT_DAMAGE.get()))
            .build();

        IngredientBuilder.of(IngredientItems.ENDER_HEART, IRarity.RARE_ID)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.COOKING)
            .allowedIn(PlayerSkillEnum.INSCRIBING)
            .stats(new StatModifier(1, 5, DatapackStats.MOVE_SPEED))
            .build();

        IngredientBuilder.of(IngredientItems.MOONSTONE, IRarity.UNCOMMON)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.JEWEL_CRAFTING)
            .allowedIn(PlayerSkillEnum.INSCRIBING)
            .stats(new StatModifier(2, 6, ManaRegen.getInstance(), ModType.PERCENT))
            .build();

        IngredientBuilder.of(IngredientItems.WISDOM_FRUIT, IRarity.RARE_ID)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.COOKING)
            .stats(new StatModifier(2, 6, Mana.getInstance(), ModType.PERCENT))
            .stats(new StatModifier(1, 3, DatapackStats.WIS))
            .build();

        IngredientBuilder.of(IngredientItems.SAGE_FLOWER, IRarity.RARE_ID)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.COOKING)
            .stats(new StatModifier(1, 3, DatapackStats.INT))
            .stats(new StatModifier(1, 3, SpellDamage.getInstance()))
            .build();

        IngredientBuilder.of(IngredientItems.LOTUS_PETALS, IRarity.UNCOMMON)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.COOKING)
            .allowedIn(PlayerSkillEnum.JEWEL_CRAFTING)
            .stats(new StatModifier(2, 6, Stats.HEAL_STRENGTH.get()))
            .stats(new StatModifier(1, 3, Health.getInstance(), ModType.PERCENT))
            .build();

        IngredientBuilder.of(IngredientItems.BRYONY_ROOT, IRarity.RARE_ID)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.COOKING)
            .stats(new StatModifier(3, 15, Health.getInstance(), ModType.PERCENT))
            .build();

        IngredientBuilder.of(IngredientItems.YARROW_FLOWER, IRarity.UNCOMMON)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.COOKING)
            .stats(new StatModifier(2, 6, new ElementalResist(Elements.Fire)))
            .stats(new StatModifier(1, 3, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire)))
            .build();

        IngredientBuilder.of(IngredientItems.HEMLOCK, IRarity.UNCOMMON)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.COOKING)
            .stats(new StatModifier(2, 4, new ElementalResist(Elements.Earth)))
            .stats(new StatModifier(1, 3, ManaRegen.getInstance(), ModType.PERCENT))
            .build();

        IngredientBuilder.of(IngredientItems.CRYSTAL_BERYL, IRarity.UNCOMMON)
            .allowedIn(PlayerSkillEnum.JEWEL_CRAFTING)
            .allowedIn(PlayerSkillEnum.INSCRIBING)
            .stats(new StatModifier(4, 8, BonusExp.getInstance()))
            .build();

        IngredientBuilder.of(IngredientItems.RED_CORAL, IRarity.COMMON_ID)
            .allowedIn(PlayerSkillEnum.COOKING)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .stats(new StatModifier(1, 3, HealthRegen.getInstance()))
            .build();

        IngredientBuilder.of(IngredientItems.ECTOPLASM, IRarity.COMMON_ID)
            .allowedIn(PlayerSkillEnum.COOKING)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .stats(new StatModifier(0.5F, 1.5F, ManaRegen.getInstance()))
            .build();

        IngredientBuilder.of(IngredientItems.BLOOD_ROOT, IRarity.RARE_ID)
            .allowedIn(PlayerSkillEnum.COOKING)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .stats(new StatModifier(2, 4, Stats.LIFESTEAL.get()))
            .build();

        IngredientBuilder.of(IngredientItems.CRYSTAL_AMETHYST, IRarity.RARE_ID)
            .allowedIn(PlayerSkillEnum.JEWEL_CRAFTING)
            .allowedIn(PlayerSkillEnum.INSCRIBING)
            .allowedIn(PlayerSkillEnum.WEAPON_CRAFTING)
            .stats(new StatModifier(2, 4, Stats.SPELL_CRIT_CHANCE.get()))
            .build();

        IngredientBuilder.of(IngredientItems.ENDERMAN_HEART, IRarity.RARE_ID)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.ARMOR_CRAFTING)
            .stats(new StatModifier(1, 3, Stats.SPELL_ACCURACY.get()))
            .build();

        IngredientBuilder.of(IngredientItems.FIRE_STONE, IRarity.UNCOMMON)
            .allowedIn(PlayerSkillEnum.ARMOR_CRAFTING)
            .allowedIn(PlayerSkillEnum.WEAPON_CRAFTING)
            .stats(new StatModifier(1, 3, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire)))
            .stats(new StatModifier(1, 2, Stats.LIFESTEAL.get()))
            .build();

        IngredientBuilder.of(IngredientItems.CRYSTAL_BLUE_ROCK, IRarity.RARE_ID)
            .allowedIn(PlayerSkillEnum.ARMOR_CRAFTING)
            .allowedIn(PlayerSkillEnum.JEWEL_CRAFTING)
            .allowedIn(PlayerSkillEnum.INSCRIBING)
            .stats(new StatModifier(2, 8, Mana.getInstance(), ModType.PERCENT))
            .build();

        IngredientBuilder.of(IngredientItems.BOLETE_MUSHROOM, IRarity.COMMON_ID)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.COOKING)
            .allowedIn(PlayerSkillEnum.INSCRIBING)
            .stats(new StatModifier(1, 4, Stats.DOT_DAMAGE.get()))
            .build();

        IngredientBuilder.of(IngredientItems.MAGIC_LOG, IRarity.UNCOMMON)
            .allowedIn(PlayerSkillEnum.ALCHEMY)
            .allowedIn(PlayerSkillEnum.WEAPON_CRAFTING)
            .allowedIn(PlayerSkillEnum.INSCRIBING)
            .stats(new StatModifier(3, 6, Stats.ELEMENTAL_SPELL_DAMAGE.get(Elements.Earth)))
            .build();

        IngredientBuilder.of(IngredientItems.REDSTONE_CRYSTAL, IRarity.UNCOMMON)
            .allowedIn(PlayerSkillEnum.JEWEL_CRAFTING)
            .allowedIn(PlayerSkillEnum.ARMOR_CRAFTING)
            .allowedIn(PlayerSkillEnum.INSCRIBING)
            .stats(new StatModifier(3, 6, Stats.SPELL_CRIT_DAMAGE.get()))
            .build();

    }
}

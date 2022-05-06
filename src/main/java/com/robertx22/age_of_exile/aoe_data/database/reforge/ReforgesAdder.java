package com.robertx22.age_of_exile.aoe_data.database.reforge;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.all_keys.GearSlotKeys;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.reforge.Reforge;
import com.robertx22.age_of_exile.database.data.stats.types.loot.ItemFind;
import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class ReforgesAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        Reforge.Builder.of("aqua", "Aqua")
            .gearSlot(GearSlotKeys.RING)
            .rarity(IRarity.EPIC_ID)
            .stat(new StatModifier(2, 5, Mana.getInstance()))
            .stat(new StatModifier(1, 5, Stats.ELEMENTAL_DAMAGE.get(Elements.Water), ModType.FLAT))
            .build();

        Reforge.Builder.of("earth", "Earth")
            .gearSlot(GearSlotKeys.CHEST)
            .rarity(IRarity.EPIC_ID)
            .stat(new StatModifier(2, 6, Health.getInstance(), ModType.PERCENT))
            .stat(new StatModifier(1, 5, Stats.ELEMENTAL_DAMAGE.get(Elements.Earth), ModType.FLAT))
            .build();

        Reforge.Builder.of("nature", "Nature")
            .gearSlot(GearSlotKeys.PANTS)
            .rarity(IRarity.EPIC_ID)
            .stat(new StatModifier(2, 8, Health.getInstance(), ModType.PERCENT))
            .stat(new StatModifier(2, 6, Stats.HEAL_STRENGTH.get()))
            .build();

        Reforge.Builder.of("fortune", "Fortune")
            .gearSlot(GearSlotKeys.NECKLACE)
            .rarity(IRarity.LEGENDARY_ID)
            .stat(new StatModifier(2, 6, MagicFind.getInstance()))
            .stat(new StatModifier(2, 6, ItemFind.getInstance()))
            .build();

        Reforge.Builder.of("dragon_breath", "Dragon Breath")
            .gearSlot(GearSlotKeys.HELMET)
            .rarity(IRarity.LEGENDARY_ID)
            .stat(new StatModifier(2, 6, Health.getInstance()))
            .stat(new StatModifier(2, 10, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT))
            .build();

        Reforge.Builder.of("pride", "Pride")
            .gearSlot(GearSlotKeys.CHEST)
            .rarity(IRarity.LEGENDARY_ID)
            .stat(new StatModifier(2, 6, DatapackStats.INT))
            .stat(new StatModifier(2, 6, DatapackStats.STR))
            .stat(new StatModifier(2, 6, DatapackStats.DEX))
            .build();

        Reforge.Builder.of("nightmare", "Nightmare")
            .gearSlot(GearSlotKeys.SWORD)
            .rarity(IRarity.EPIC_ID)
            .stat(new StatModifier(2, 5, Stats.CRIT_CHANCE.get()))
            .stat(new StatModifier(2, 10, Stats.CRIT_DAMAGE.get()))
            .build();

        Reforge.Builder.of("insight", "Insight")
            .gearSlot(GearSlotKeys.SWORD)
            .rarity(IRarity.EPIC_ID)
            .stat(new StatModifier(2, 6, Mana.getInstance()))
            .stat(new StatModifier(2, 15, ManaRegen.getInstance(), ModType.PERCENT))
            .build();

        Reforge.Builder.of("shadow_bind", "Shadow Bind")
            .weapons()
            .rarity(IRarity.EPIC_ID)
            .stat(new StatModifier(1, 2, Stats.LEECH_RESOURCE.get(ResourceType.health), ModType.PERCENT))
            .stat(new StatModifier(1, 2, Stats.LEECH_RESOURCE.get(ResourceType.mana), ModType.PERCENT))
            .build();

    }
}

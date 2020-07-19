package com.robertx22.exiled_lib.registry;

import com.robertx22.exiled_lib.registry.empty_entries.EmptyAffix;
import com.robertx22.exiled_lib.registry.empty_entries.EmptyUniqueGear;
import com.robertx22.mine_and_slash.data_generation.base_gear_types.SerializableBaseGearType;
import com.robertx22.mine_and_slash.database.data.DimensionConfig;
import com.robertx22.mine_and_slash.database.data.EntityConfig;
import com.robertx22.mine_and_slash.database.data.compatible_item.CompatibleItem;
import com.robertx22.mine_and_slash.database.data.tiers.base.Tier;
import com.robertx22.mine_and_slash.database.registrators.MobAffixes;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializable;

public enum SlashRegistryType {

    NONE("none"),
    EFFECT("effect"),
    STAT("stat"),
    SPELL_SYNERGY("synergy"),
    TIER("tier") {
        @Override
        public ISerializable getSerializer() {
            return Tier.SERIALIZER;
        }
    },
    MOB_AFFIX("mob_affix") {
        @Override
        public ISerializable getSerializer() {
            return MobAffixes.EMPTY;
        }
    },
    STATMOD("stat_mod"),
    CHAOS_STAT("chaos_stat"),
    UNIQUE_GEAR("unique_gear") {
        @Override
        public ISerializable getSerializer() {
            return new EmptyUniqueGear();
        }
    },
    GEAR_TYPE("gear_type") {
        @Override
        public ISerializable getSerializer() {
            return SerializableBaseGearType.EMPTY;
        }
    },
    SPELL("spell"),
    AFFIX("affix") {
        @Override
        public ISerializable getSerializer() {
            return EmptyAffix.getInstance();
        }
    },
    WORLD_PROVIDER("world_provider"),
    EMPTY("empty"),
    DIMENSION_CONFIGS("dimension_config") {
        @Override
        public ISerializable getSerializer() {
            return DimensionConfig.EMPTY;
        }
    },
    ENTITY_CONFIGS("entity_config") {
        @Override
        public ISerializable getSerializer() {
            return EntityConfig.EMPTY;
        }
    },
    CURRENCY_ITEMS("currency_item"),
    COMPATIBLE_ITEM("compatible_item") {
        @Override
        public ISerializable getSerializer() {
            return CompatibleItem.EMPTY;
        }
    },
    SYNERGY_EFFECT("synergy_effect"),
    LOOT_CRATE("loot_crate");

    public String id;

    SlashRegistryType(String id) {
        this.id = id;
    }

    public ISerializable getSerializer() { // TODO this could be better
        return null;
    }

    public static SlashRegistryType getFromString(String str) {
        for (SlashRegistryType type : values()) {
            if (str.equals(type.id)) {
                return type;
            }
        }
        return null;
    }

}

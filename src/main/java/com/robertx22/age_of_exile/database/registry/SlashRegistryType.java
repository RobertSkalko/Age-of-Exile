package com.robertx22.age_of_exile.database.registry;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.spell_modifiers.SpellModifier;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.tiers.base.Tier;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registrators.MobAffixes;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;
import com.robertx22.age_of_exile.datapacks.loaders.BaseDataPackLoader;
import com.robertx22.age_of_exile.datapacks.seriazables.SerializableBaseGearType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.CompatibleItemUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum SlashRegistryType {

    EMPTY("none", 0, null) {
        @Override
        public BaseDataPackLoader getLoader() {
            return null;
        }
    },
    STAT("stat", 1, null) {
        @Override
        public BaseDataPackLoader getLoader() {
            return null;
        }
    },
    GEAR_SLOT("gear_slot", 3, GearSlot.SERIALIZER),
    EXILE_EFFECT("exile_effect", 3, null) {
        @Override
        public BaseDataPackLoader getLoader() {
            return null;
        }
    }, // TODO
    GEAR_TYPE("base_gear_types", 4, SerializableBaseGearType.EMPTY),
    TIER("tier", 5, Tier.SERIALIZER),
    GEM("gems", 6, Gem.SERIALIZER),
    RUNE("runes", 7, Rune.SERIALIZER),
    MOB_AFFIX("mob_affix", 8, MobAffixes.EMPTY),
    RUNEWORD("runewords", 9, RuneWord.SERIALIZER),
    AFFIX("affixes", 10, EmptyAffix.getInstance()),
    UNIQUE_GEAR("unique_gears", 11, UniqueGear.SERIALIZER),
    CURRENCY_ITEMS("currency_item", 12, null) {
        @Override
        public BaseDataPackLoader getLoader() {
            return null;
        }
    },
    DIMENSION_CONFIGS("dimension_config", 13, DimensionConfig.EMPTY),
    ENTITY_CONFIGS("entity_config", 14, EntityConfig.EMPTY),
    COMPATIBLE_ITEM("compatible_items", 15, CompatibleItem.EMPTY) {
        public List getAllForSerialization() {
            return CompatibleItemUtils.getAllForSerialization();
        }
    },
    SPELL_MODIFIER("spell_modifiers", 16, SpellModifier.SERIALIZER),
    SPELL("spells", 17, Spell.SERIALIZER),
    PERK("perk", 18, Perk.SERIALIZER),
    SPELL_SCHOOL("spell_school", 19, SpellSchool.SERIALIZER);

    public String id;
    ISerializable ser;
    int order;

    SlashRegistryType(String id, int order, ISerializable ser) {
        this.id = id;
        this.order = order;
        this.ser = ser;
    }

    public static List<SlashRegistryType> getInRegisterOrder() {
        List<SlashRegistryType> list = Arrays.stream(SlashRegistryType.values())
            .collect(Collectors.toList());
        list.sort(Comparator.comparingInt(x -> x.order));
        return list;

    }

    public static void init() {

    }

    public BaseDataPackLoader getLoader() {
        return new BaseDataPackLoader(this, this.id, x -> this.ser.fromJson((JsonObject) x)) {
            @Override
            public SlashDatapackGenerator getDataPackGenerator() {
                return new SlashDatapackGenerator<>(getAllForSerialization(), this.id);
            }
        };
    }

    public List getAllForSerialization() {
        return SlashRegistry.getRegistry(this)
            .getSerializable();
    }

    public final ISerializable getSerializer() {
        return ser;
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

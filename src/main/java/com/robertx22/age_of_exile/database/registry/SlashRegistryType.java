package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.compatible_item.CompatibleItem;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.modifiers.SpellModifier;
import com.robertx22.age_of_exile.database.data.tiers.base.Tier;
import com.robertx22.age_of_exile.database.registrators.MobAffixes;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyUniqueGear;
import com.robertx22.age_of_exile.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.datapacks.loaders.*;
import com.robertx22.age_of_exile.datapacks.seriazables.SerializableBaseGearType;

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
    EFFECT("effect", 2, null) {
        @Override
        public BaseDataPackLoader getLoader() {
            return null;
        }
    },
    GEAR_SLOT("gear_slot", 3, GearSlot.SERIALIZER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new GearSlotDatapackLoader();
        }
    },
    GEAR_TYPE("gear_type", 4, SerializableBaseGearType.EMPTY) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new BaseGearTypeDatapackLoader();
        }
    },
    TIER("tier", 5, Tier.SERIALIZER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new TierDatapackLoader();
        }
    },
    GEM("gem", 6, Gem.SERIALIZER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new GemDatapackLoader();
        }
    },
    RUNE("rune", 7, Rune.SERIALIZER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new RuneDatapackLoader();
        }
    },
    MOB_AFFIX("mob_affix", 8, MobAffixes.EMPTY) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new MobAffixDataPackLoader();
        }
    },
    RUNEWORD("runeword", 9, RuneWord.SERIALIZER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new RunewordDatapackLoader();
        }
    },
    AFFIX("affix", 10, EmptyAffix.getInstance()) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new AffixDataPackLoader();
        }
    },
    UNIQUE_GEAR("unique_gear", 11, new EmptyUniqueGear()) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new UniqueGearDatapackLoader();
        }
    },
    CURRENCY_ITEMS("currency_item", 12, null) {
        @Override
        public BaseDataPackLoader getLoader() {
            return null;
        }
    },
    DIMENSION_CONFIGS("dimension_config", 13, DimensionConfig.EMPTY) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new DimConfigsDatapackLoader();
        }
    },
    ENTITY_CONFIGS("entity_config", 14, EntityConfig.EMPTY) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new EntityConfigsDatapackLoader();
        }
    },
    COMPATIBLE_ITEM("compatible_item", 15, CompatibleItem.EMPTY) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new CompatibleItemDataPackLoader();
        }
    },
    SPELL_MODIFIER("spell_modifier", 16, SpellModifier.SERIALIZER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new SpellModifierDatapackLoader();
        }
    },
    PERK("perk", 17, Perk.SERIALIZER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new PerkDatapackLoader();
        }
    },
    SPELL("spell", 18, Spell.SERIALIZER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new SpellDatapackLoader();
        }
    },
    SPELL_SCHOOL("spell_school", 19, SpellSchool.SERIALIZER) {
        @Override
        public BaseDataPackLoader getLoader() {
            return new SpellSchoolDatapackLoader();
        }
    };

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

    public abstract BaseDataPackLoader getLoader();

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

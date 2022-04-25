package com.robertx22.age_of_exile.database.registry;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.crafting_req.CraftingReq;
import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.groups.GearRarityGroup;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.reforge.Reforge;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.StatEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.StatCondition;
import com.robertx22.age_of_exile.uncommon.utilityclasses.MapManager;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ExileDB {

    public static DimensionConfig getDimensionConfig(IWorld world) {
        String id = MapManager.getResourceLocation((World) world)
            .toString();
        return DimensionConfigs().get(id);
    }

    public static EntityConfig getEntityConfig(LivingEntity entity, EntityData data) {

        String monster_id = Registry.ENTITY_TYPE.getKey(entity.getType())
            .toString();
        String mod_id = Registry.ENTITY_TYPE.getKey(entity.getType())
            .getNamespace();

        EntityConfig config = null;

        if (EntityConfigs().isRegistered(monster_id)) {
            config = EntityConfigs().get(monster_id);
            if (config != null) {
                return config;
            }
        } else {
            if (EntityConfigs().isRegistered(mod_id)) {
                config = EntityConfigs().get(mod_id);

                if (config != null) {
                    return config;
                }

            } else {
                config = EntityConfigs().get(data.getType().id);

                if (config != null) {
                    return config;
                }
            }
        }

        return EntityConfigs().getDefault();

    }

    public static ExileRegistryContainer<GearSlot> GearSlots() {
        return Database.getRegistry(ExileRegistryTypes.GEAR_SLOT);
    }

    public static ExileRegistryContainer<UniqueGear> UniqueGears() {
        return Database.getRegistry(ExileRegistryTypes.UNIQUE_GEAR);
    }

    public static ExileRegistryContainer<GearRarityGroup> GearRarityGroups() {
        return Database.getRegistry(ExileRegistryTypes.GEAR_RARITY_GROUP);
    }

    public static ExileRegistryContainer<CurrencyItem> CurrencyItems() {
        return Database.getRegistry(ExileRegistryTypes.CURRENCY_ITEMS);
    }

    public static ExileRegistryContainer<DimensionConfig> DimensionConfigs() {
        return Database.getRegistry(ExileRegistryTypes.DIMENSION_CONFIGS);
    }

    public static ExileRegistryContainer<StatCondition> StatConditions() {
        return Database.getRegistry(ExileRegistryTypes.STAT_CONDITION);
    }

    public static ExileRegistryContainer<StatEffect> StatEffects() {
        return Database.getRegistry(ExileRegistryTypes.STAT_EFFECT);
    }

    public static ExileRegistryContainer<ExileEffect> ExileEffects() {
        return Database.getRegistry(ExileRegistryTypes.EXILE_EFFECT);
    }

    public static ExileRegistryContainer<FavorRank> FavorRanks() {
        return Database.getRegistry(ExileRegistryTypes.FAVOR_RANK);
    }

    public static ExileRegistryContainer<TalentTree> TalentTrees() {
        return Database.getRegistry(ExileRegistryTypes.TALENT_TREE);
    }

    public static ExileRegistryContainer<GearSet> Sets() {
        return Database.getRegistry(ExileRegistryTypes.GEAR_SET);
    }

    public static ExileRegistryContainer<Perk> Perks() {
        return Database.getRegistry(ExileRegistryTypes.PERK);
    }

    public static ExileRegistryContainer<Rune> Runes() {
        return Database.getRegistry(ExileRegistryTypes.RUNE);
    }

    public static ExileRegistryContainer<RuneWord> RuneWords() {
        return Database.getRegistry(ExileRegistryTypes.RUNEWORDS);
    }

    public static ExileRegistryContainer<Affix> Affixes() {
        return Database.getRegistry(ExileRegistryTypes.AFFIX);
    }

    public static RarityRegistryContainer<GearRarity> GearRarities() {
        return (RarityRegistryContainer<GearRarity>) Database.getRegistry(ExileRegistryTypes.GEAR_RARITY);
    }

    public static RarityRegistryContainer<MobRarity> MobRarities() {
        return (RarityRegistryContainer<MobRarity>) Database.getRegistry(ExileRegistryTypes.MOB_RARITY);
    }

    public static ExileRegistryContainer<Difficulty> Difficulties() {
        return Database.getRegistry(ExileRegistryTypes.TIER);
    }

    public static ExileRegistryContainer<BaseGearType> GearTypes() {
        return Database.getRegistry(ExileRegistryTypes.GEAR_TYPE);
    }

    public static ExileRegistryContainer<DungeonMobList> DungeonMobLists() {
        return Database.getRegistry(ExileRegistryTypes.DUNGEON_MOB_LIST);
    }

    public static ExileRegistryContainer<Spell> Spells() {
        return Database.getRegistry(ExileRegistryTypes.SPELL);
    }

    public static ExileRegistryContainer<MobAffix> MobAffixes() {
        return Database.getRegistry(ExileRegistryTypes.MOB_AFFIX);
    }

    public static ExileRegistryContainer<ValueCalculation> ValueCalculations() {
        return Database.getRegistry(ExileRegistryTypes.VALUE_CALC);
    }

    public static ExileRegistryContainer<EntityConfig> EntityConfigs() {
        return Database.getRegistry(ExileRegistryTypes.ENTITY_CONFIGS);
    }

    public static ExileRegistryContainer<PlayerSkill> PlayerSkills() {
        return Database.getRegistry(ExileRegistryTypes.PLAYER_SKILLS);
    }

    public static ExileRegistryContainer<SpellSchool> SpellSchools() {
        return Database.getRegistry(ExileRegistryTypes.SPELL_SCHOOL);
    }

    public static ExileRegistryContainer<Reforge> Reforges() {
        return Database.getRegistry(ExileRegistryTypes.REFORGE);
    }

    public static ExileRegistryContainer<CraftingReq> ItemCraftReq() {
        return Database.getRegistry(ExileRegistryTypes.CRAFTING_REQ);
    }

    public static ExileRegistryContainer<Stat> Stats() {
        return Database.getRegistry(ExileRegistryTypes.STAT);
    }

    public static ExileRegistryContainer<BaseStatsConfig> BaseStats() {
        return Database.getRegistry(ExileRegistryTypes.BASE_STATS);
    }

}

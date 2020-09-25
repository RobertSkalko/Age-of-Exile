package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.api.MineAndSlashEvents;
import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Mana;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.event_hooks.entity.damage.DamageEventData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.stat_calculation.CommonStatUtils;
import com.robertx22.age_of_exile.uncommon.stat_calculation.ExtraMobRarityAttributes;
import com.robertx22.age_of_exile.uncommon.stat_calculation.MobStatUtils;
import com.robertx22.age_of_exile.uncommon.stat_calculation.PlayerStatUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.EfficientMobUnitPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityUnitPacket;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.EntityStatusEffectsData;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.stream.Collectors;

// this stores data that can be lost without issue, stats that are recalculated all the time
// and mob status effects.
@Storable
public class Unit {

    @Store
    public EntityStatusEffectsData statusEffects = new EntityStatusEffectsData();

    @Store
    private StatContainer stats = new StatContainer();

    @Store
    public String GUID = UUID.randomUUID()
        .toString();

    @Store
    public String prefix;
    @Store
    public String suffix;

    @Store
    public List<String> affixes = new ArrayList<>();

    public List<MobAffix> getAffixes() {
        return affixes.stream()
            .map(x -> SlashRegistry.MobAffixes()
                .get(x))
            .collect(Collectors.toList());
    }

    public HashMap<String, StatData> getStats() {

        if (stats.stats == null) {
            this.initStats();
        }

        return stats.stats;
    }

    public StatData getCreateStat(Stat stat) {
        return getCreateStat(stat.GUID());
    }

    public boolean hasStat(String guid) {
        return stats.stats.containsKey(guid);
    }

    public StatData peekAtStat(Stat stat) {
        return peekAtStat(stat.GUID());
    }

    public StatData peekAtStat(String guid) {

        if (stats.stats == null) {
            this.initStats();
        }

        return stats.stats.getOrDefault(guid, StatData.empty());

    }

    public void randomizeAffixes(MobRarity rarity) {

        int amount = RandomUtils.roll(rarity.oneAffixChance()) ? 1 : 0;

        this.affixes.clear();

        if (amount > 0) {
            this.affixes.add(SlashRegistry.MobAffixes()
                .random()
                .GUID());
        }
    }

    public StatData getCreateStat(String guid) {

        if (stats.stats == null) {
            this.initStats();
        }

        StatData data = stats.stats.get(guid);

        if (data == null) {
            Stat stat = SlashRegistry.Stats()
                .get(guid);
            if (stat != null) {
                stats.stats.put(stat.GUID(), new StatData(stat));

                return stats.stats.get(stat.GUID());
            } else {
                return new StatData(new UnknownStat());
            }
        } else {
            return data;
        }
    }

    public Unit() {

    }

    public void initStats() {
        stats.stats = new HashMap<String, StatData>();
    }

    private void removeEmptyStats() {

        for (StatData data : new ArrayList<>(stats.stats.values())) {
            if (!data.isNotZero() || data.getId()
                .isEmpty()) {
                //System.out.println(data.Name);
                stats.stats.remove(data.getId());
            }
        }

    }

    public void removeUnregisteredStats() {

        if (stats.stats == null) {
            stats.stats = new HashMap<String, StatData>();
        }

        removeEmptyStats();

        for (Map.Entry<String, StatData> entry : new ArrayList<>(stats.stats.entrySet())) {

            StatData data = entry.getValue();

            if (!SlashRegistry.Stats()
                .isRegistered(data.getId())) {
                stats.stats.remove(entry.getKey());
            }
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Unit) {
            return ((Unit) obj).GUID.equals(this.GUID); // todo this bugfix sounds big, might mess with things!!!
        }
        return false;
    }

    @Override
    public int hashCode() {
        return GUID.hashCode();
    }

    // Stat shortcuts
    public Health health() {
        return Health.getInstance();
    }

    public Mana mana() {
        return Mana.getInstance();
    }

    public StatData healthData() {
        try {
            return getCreateStat(Health.GUID);
        } catch (Exception e) {
        }
        return StatData.empty();
    }

    public StatData magicShieldData() {
        try {
            return getCreateStat(MagicShield.GUID);
        } catch (Exception e) {

        }
        return StatData.empty();
    }

    public StatData manaData() {
        try {
            return getCreateStat(Mana.GUID);
        } catch (Exception e) {

        }
        return StatData.empty();
    }

    public int randomRarity(LivingEntity entity, UnitData data) {

        double y = entity.getY();

        List<MobRarity> rarities = SlashRegistry.MobRarities()
            .getAllRarities()
            .stream()
            .filter(x -> data.getLevel() >= x.minMobLevelForRandomSpawns() || data.getLevel() >= ModConfig.get().Server.MAX_LEVEL)
            .collect(Collectors.toList());

        if (rarities.isEmpty()) {
            rarities.add(SlashRegistry.MobRarities()
                .lowest());
        }

        MobRarity finalRarity = RandomUtils.weightedRandom(rarities);

        EntityConfig entityConfig = SlashRegistry.getEntityConfig(entity, data);

        return MathHelper.clamp(finalRarity.Rank(), entityConfig.min_rarity, entityConfig.max_rarity);

    }

    protected void ClearStats() {

        if (stats.stats == null) {
            this.initStats();
        }

        for (StatData stat : stats.stats.values()) {
            stat.Clear();
        }
    }

    protected void CalcStats(UnitData data) {
        stats.stats.values()
            .forEach((StatData stat) -> stat.CalcVal());
    }

    private static class DirtyCheck {
        public int hp;

        public boolean isDirty(DirtyCheck newcheck) {

            if (newcheck.hp != hp) {
                return true;
            }

            return false;

        }
    }

    /**
     * @return checks if it should be synced to clients. Clients currently only see
     * health and status effects
     */
    private DirtyCheck getDirtyCheck() {

        if (stats.stats == null || stats.stats.isEmpty()) {
            this.initStats();
        }

        DirtyCheck check = new DirtyCheck();

        check.hp = (int) getCreateStat(Health.GUID).getAverageValue();

        return check;
    }

    public void recalculateStats(LivingEntity entity, UnitData data, DamageEventData dmgData) {

        if (entity.world.isClient) {
            return;
        }

        data.setEquipsChanged(false);

        if (data.getUnit() == null) {
            data.setUnit(this, entity);
        }

        DirtyCheck old = getDirtyCheck();

        List<GearItemData> gears = new ArrayList<>();

        new MineAndSlashEvents.CollectGearStacksEvent(entity, gears, dmgData);

        ClearStats();

        CommonStatUtils.addPotionStats(entity);
        CommonStatUtils.addExactCustomStats(data);

        if (entity instanceof PlayerEntity) {
            PlayerStatUtils.AddPlayerBaseStats(data, this);
            Load.perks(entity)
                .applyStats(data);
            Load.statPoints((PlayerEntity) entity)
                .applyStats(data);
        } else {
            MobStatUtils.AddMobcStats(data, entity);
            MobStatUtils.addAffixStats(data);
            MobStatUtils.worldMultiplierStats(entity.world, this);
            MobStatUtils.increaseMobStatsPerTier(data, this);
            MobStatUtils.modifyMobStatsByConfig(entity, data);
            ExtraMobRarityAttributes.add(entity, data);
        }

        addGearStats(gears, entity, data);

        CommonStatUtils.CalcTraitsAndCoreStats(
            data); // has to be at end for the conditionals like if crit higher than x

        CalcStats(data);

        removeEmptyStats();

        DirtyCheck aftercalc = getDirtyCheck();

        addToVanillaHealth(entity);

        if (old.isDirty(aftercalc)) {
            if (!Unit.shouldSendUpdatePackets((LivingEntity) entity)) {
                return;
            }
            Packets.sendToTracking(getUpdatePacketFor(entity, data), entity);
        }

        if (entity instanceof PlayerEntity) {
            Packets.sendToClient((PlayerEntity) entity, new EntityUnitPacket(entity));
        }

    }

    private void addGearStats(List<GearItemData> gears, LivingEntity entity, UnitData data) {

        /*
        Add all gear stats that meet requirements
        >
        Remove the gears that added stats
        >
        Recalculate stats
        >
        Add all gear stats that meet requirements
        ...
        If recalculated already and leftover gear still doesn't meet requirements, stop and remove all.
        */

        boolean addedAny = true;

        HashMap<GearRarity, Integer> rarityMap = new HashMap<>();

        while (!gears.isEmpty()) {

            this.CalcStats(data);

            List<GearItemData> toremove = new ArrayList<>();

            for (int i = 0; i < gears.size(); i++) {

                GearItemData gear = gears.get(i);

                boolean addstats = true;

                if (entity instanceof PlayerEntity) {

                    if (!gear.isIdentified()) {
                        continue;
                    } else if (data.getLevel() < gear.level) {
                        continue;
                    } else if (!gear.meetsStatRequirements(data)) {
                        addstats = false;
                    }
                    if (addstats) {
                        GearRarity rar = (GearRarity) gear.getRarity();
                        if (rar.hasMaxWornRestriction()) {
                            if (rarityMap.get(rar) >= rar.max_worn_at_once) {
                                addstats = false;
                                continue;
                            } else {
                                rarityMap.put(rar, rarityMap.getOrDefault(rar, 0) + 1);
                            }
                        }
                    }
                }

                if (addstats) {
                    gear.GetAllStats(true, false)
                        .forEach(x -> {
                            x.applyStats(data);
                        });
                    toremove.add(gear);
                    addedAny = true;
                }

            }

            if (toremove.isEmpty()) {
                if (!addedAny) {
                    gears.clear();
                    return;
                } else {
                    addedAny = false;
                }
            }

            toremove.forEach(x -> gears.removeIf(g -> g.equals(x)));
            toremove.clear();
        }

    }

    public static UUID hpID = UUID.fromString("e926df30-c376-11ea-87d0-0242ac130003");

    private void addToVanillaHealth(LivingEntity en) {

        float hp = getCreateStat(Health.getInstance()).getAverageValue();

        EntityAttributeModifier mod = new EntityAttributeModifier(
            hpID,
            EntityAttributes.GENERIC_MAX_HEALTH.getTranslationKey(),
            hp,
            EntityAttributeModifier.Operation.ADDITION
        );

        EntityAttributeInstance atri = en.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        if (atri.hasModifier(mod)) {
            atri.removeModifier(mod);
        }
        atri.addPersistentModifier(mod);

    }

    private static List<EntityType> IGNORED_ENTITIES = null;

    public static List<EntityType> getIgnoredEntities() {

        if (IGNORED_ENTITIES == null) {
            IGNORED_ENTITIES = ModConfig.get().Server.IGNORED_ENTITIES
                .stream()
                .filter(x -> Registry.ENTITY_TYPE.getOrEmpty(new Identifier(x))
                    .isPresent())
                .map(x -> Registry.ENTITY_TYPE.get(new Identifier(x)))
                .collect(Collectors.toList());
        }

        return IGNORED_ENTITIES;

    }

    public static boolean shouldSendUpdatePackets(LivingEntity en) {
        return getIgnoredEntities().contains(en.getType()) == false;
    }

    public static MyPacket getUpdatePacketFor(LivingEntity en, UnitData data) {
        return new EfficientMobUnitPacket(en, data);
    }

    private Unit Clone() {

        Unit clone = new Unit();
        if (this.stats.stats != null) {
            clone.stats.stats = new HashMap<String, StatData>(stats.stats);
        } else {
            clone.stats.stats = new HashMap<String, StatData>();
        }

        return clone;

    }

}

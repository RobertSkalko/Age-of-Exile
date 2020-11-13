package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.api.MineAndSlashEvents;
import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ICoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.event_hooks.entity.damage.DamageEventData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAffectsStats;
import com.robertx22.age_of_exile.uncommon.stat_calculation.CommonStatUtils;
import com.robertx22.age_of_exile.uncommon.stat_calculation.ExtraMobRarityAttributes;
import com.robertx22.age_of_exile.uncommon.stat_calculation.MobStatUtils;
import com.robertx22.age_of_exile.uncommon.stat_calculation.PlayerStatUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.EfficientMobUnitPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityUnitPacket;
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
    private StatContainer stats = new StatContainer();

    @Store
    public String GUID = UUID.randomUUID()
        .toString();

    public HashMap<String, StatData> getStats() {

        if (stats.stats == null) {
            this.initStats();
        }

        return stats.stats;
    }

    public StatContainer getStatsContainer() {
        return stats;
    }

    public StatData getCalculatedStat(Stat stat) {
        return getCalculatedStat(stat.GUID());
    }

    public StatData getCalculatedStat(String guid) {

        if (stats.stats == null) {
            this.initStats();
        }

        return stats.stats.getOrDefault(guid, StatData.empty());

    }

    public InCalcStatData getStatInCalculation(Stat stat) {
        return getStatInCalculation(stat.GUID());
    }

    public InCalcStatData getStatInCalculation(String guid) {

        if (stats.stats == null) {
            this.initStats();
        }

        InCalcStatData data = stats.statsInCalc.get(guid);

        if (data == null) {
            Stat stat = SlashRegistry.Stats()
                .get(guid);
            if (stat != null) {
                stats.statsInCalc.put(stat.GUID(), new InCalcStatData(stat.GUID()));

                return stats.statsInCalc.get(stat.GUID());
            } else {
                return new InCalcStatData(new UnknownStat().GUID());
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
            return getCalculatedStat(Health.GUID);
        } catch (Exception e) {
        }
        return StatData.empty();
    }

    public StatData bloodData() {
        try {
            return getCalculatedStat(Blood.GUID);
        } catch (Exception e) {
        }
        return StatData.empty();
    }

    public StatData magicShieldData() {
        try {
            return getCalculatedStat(MagicShield.GUID);
        } catch (Exception e) {
        }
        return StatData.empty();
    }

    public StatData manaData() {
        try {
            return getCalculatedStat(Mana.GUID);
        } catch (Exception e) {

        }
        return StatData.empty();
    }

    public int randomRarity(LivingEntity entity, UnitData data) {

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

        check.hp = (int) getCalculatedStat(Health.GUID).getAverageValue();

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

        this.stats.statsInCalc.clear();
        this.stats.stats.clear();

        List<GearData> gears = new ArrayList<>();

        new MineAndSlashEvents.CollectGearStacksEvent(entity, gears, dmgData);

        CommonStatUtils.addPotionStats(entity);
        CommonStatUtils.addExactCustomStats(data);

        if (entity instanceof PlayerEntity) {
            PlayerStatUtils.AddPlayerBaseStats(data, this);
            Load.perks(entity)
                .applyStats(data);
            Load.statPoints((PlayerEntity) entity)
                .applyStats(data);
            Load.playerSkills((PlayerEntity) entity)
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

        new HashMap<>(stats.statsInCalc).entrySet()
            .forEach(x -> {
                InCalcStatData statdata = x.getValue();
                Stat stat = x.getValue()
                    .GetStat();
                if (statdata != null && stat instanceof ITransferToOtherStats) {
                    ITransferToOtherStats add = (ITransferToOtherStats) stat;
                    add.transferStats(data, statdata);
                }
            });

        new HashMap<>(stats.statsInCalc).entrySet()
            .forEach(x -> {
                InCalcStatData statdata = x.getValue();
                Stat stat = x.getValue()
                    .GetStat();
                if (statdata != null && stat instanceof ICoreStat) {
                    ICoreStat add = (ICoreStat) stat;
                    add.addToOtherStats(data, statdata);
                }
            });

        new HashMap<>(stats.statsInCalc).entrySet()
            .forEach(x -> {
                InCalcStatData statdata = x.getValue();
                Stat stat = x.getValue()
                    .GetStat();
                if (statdata != null && stat instanceof IAffectsStats) {
                    IAffectsStats add = (IAffectsStats) stat;
                    add.affectStats(data, statdata);
                }
            });

        this.stats.calculate();

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

    private void addGearStats(List<GearData> gears, LivingEntity entity, UnitData data) {

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

            List<GearItemData> toremove = new ArrayList<>();

            for (int i = 0; i < gears.size(); i++) {

                GearItemData gear = gears.get(i).gear;

                boolean addstats = true;

                if (entity instanceof PlayerEntity) {

                    if (!gear.meetsStatRequirements(data)) {
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

            toremove.forEach(x -> gears.removeIf(g -> g.gear.equals(x)));
            toremove.clear();
        }

    }

    public static UUID hpID = UUID.fromString("e926df30-c376-11ea-87d0-0242ac130003");

    private void addToVanillaHealth(LivingEntity en) {

        float hp = getCalculatedStat(Health.getInstance()).getAverageValue();

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

    private static HashMap<EntityType, Boolean> IGNORED_ENTITIES = null;

    public static HashMap<EntityType, Boolean> getIgnoredEntities() {

        if (IGNORED_ENTITIES == null) {
            IGNORED_ENTITIES = new HashMap<>();
            ModConfig.get().Server.IGNORED_ENTITIES
                .stream()
                .filter(x -> Registry.ENTITY_TYPE.getOrEmpty(new Identifier(x))
                    .isPresent())
                .map(x -> Registry.ENTITY_TYPE.get(new Identifier(x)))
                .forEach(x -> IGNORED_ENTITIES.put(x, true));
        }

        return IGNORED_ENTITIES;

    }

    public static boolean shouldSendUpdatePackets(LivingEntity en) {
        return !getIgnoredEntities().containsKey(en.getType());
    }

    public static MyPacket getUpdatePacketFor(LivingEntity en, UnitData data) {
        return new EfficientMobUnitPacket(en, data);
    }

}

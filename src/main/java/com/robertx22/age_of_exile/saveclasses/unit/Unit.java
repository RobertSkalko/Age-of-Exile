package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.IAfterStatCalc;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ICoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.event_hooks.my_events.CollectGearEvent;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.GearStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAffectsStats;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.stat_calculation.CommonStatUtils;
import com.robertx22.age_of_exile.uncommon.stat_calculation.MobStatUtils;
import com.robertx22.age_of_exile.uncommon.stat_calculation.PlayerStatUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.EfficientMobUnitPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityUnitPacket;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
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

    public InCalcStatData getStatInCalculation(Stat stat) {
        return getStats().getStatInCalculation(stat);
    }

    public InCalcStatData getStatInCalculation(String stat) {
        return getStats().getStatInCalculation(stat);
    }

    public boolean isBloodMage() {
        return getCalculatedStat(BloodUser.getInstance())
            .getValue() > 0;
    }

    public StatContainer getStats() {
        if (stats == null) {
            stats = new StatContainer();
        }
        return stats;
    }

    public StatData getCalculatedStat(Stat stat) {
        return getCalculatedStat(stat.GUID());
    }

    public StatData getCalculatedStat(String guid) {

        if (getStats().stats == null) {
            this.initStats();
        }

        return getStats().stats.getOrDefault(guid, StatData.empty());

    }

    public Unit() {

    }

    public void initStats() {
        getStats().stats = new HashMap<String, StatData>();
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

    public StatData manaData() {
        try {
            return getCalculatedStat(Mana.GUID);
        } catch (Exception e) {

        }
        return StatData.empty();
    }

    public String randomRarity(LivingEntity entity, EntityData data) {

        List<MobRarity> rarities = ExileDB.MobRarities()
            .getList()
            .stream()
            .filter(x -> data.getLevel() >= x.minMobLevelForRandomSpawns() || data.getLevel() >= GameBalanceConfig.get().MAX_LEVEL)
            .collect(Collectors.toList());

        if (rarities.isEmpty()) {
            rarities.add(ExileDB.MobRarities()
                .get(IRarity.COMMON_ID));
        }

        MobRarity finalRarity = RandomUtils.weightedRandom(rarities);

        return finalRarity.GUID();

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

        if (getStats().stats == null || getStats().stats.isEmpty()) {
            this.initStats();
        }

        DirtyCheck check = new DirtyCheck();

        check.hp = (int) getCalculatedStat(Health.GUID).getValue();

        return check;
    }

    @Store
    public HashMap<String, Integer> sets = new HashMap<>();

    private void calcSets(List<GearData> gears) {
        sets.clear();

        List<String> setUniqueids = new ArrayList<>();

        // todo possibly cache it?
        gears.forEach(x -> {
            if (x.gear != null) {
                if (x.gear.uniqueStats != null) {
                    UniqueGear uniq = x.gear.uniqueStats.getUnique(x.gear);
                    if (uniq != null) {
                        if (uniq.hasSet()) {
                            if (!setUniqueids.contains(uniq.GUID())) {
                                setUniqueids.add(uniq.GUID());
                                GearSet set = uniq.getSet();
                                String key = set
                                    .GUID();
                                int current = sets.getOrDefault(key, 0);
                                sets.put(key, current + 1);
                            }
                        }
                    }
                }
            }
        });
    }

    public void recalculateStats(LivingEntity entity, EntityData data, AttackInformation dmgData) {

        try {
            if (entity.level.isClientSide) {
                return;
            }

            //data.setEquipsChanged(false);

            if (data.getUnit() == null) {
                data.setUnit(this);
            }

            List<GearData> gears = new ArrayList<>();
            new CollectGearEvent.CollectedGearStacks(entity, gears, dmgData);

            calcSets(gears);

            stats.stats.clear();
            stats.statsInCalc.clear();

            DirtyCheck old = getDirtyCheck();

            List<StatContext> statContexts = new ArrayList<>();

            statContexts.addAll(CommonStatUtils.addPotionStats(entity));
            statContexts.addAll(CommonStatUtils.addExactCustomStats(entity));

            if (entity instanceof PlayerEntity) {

                sets.entrySet()
                    .forEach(x -> {
                        GearSet set = ExileDB.Sets()
                            .get(x.getKey());
                        statContexts.add(set.getStats(data));
                    });

                Load.playerRPGData((PlayerEntity) entity).statPoints.addStats(data);
                statContexts.addAll(PlayerStatUtils.AddPlayerBaseStats(entity));
                statContexts.addAll(Load.playerRPGData((PlayerEntity) entity).talents
                    .getStatAndContext(entity));
                statContexts.addAll(Load.playerRPGData((PlayerEntity) entity).professions
                    .getStatAndContext(entity));
                statContexts.addAll(Load.spells(entity)
                    .getStatAndContext(entity));
                statContexts.add(data.getStatusEffectsData()
                    .getStats(entity));
            } else {
                statContexts.addAll(MobStatUtils.getMobBaseStats(data, entity));
                statContexts.addAll(MobStatUtils.getAffixStats(entity));
                statContexts.addAll(MobStatUtils.getWorldMultiplierStats(entity));
                MobStatUtils.addMapStats(entity, data, this);
                statContexts.addAll(MobStatUtils.getMobConfigStats(entity, data));
                //ExtraMobRarityAttributes.add(entity, data);
            }

            statContexts.addAll(addGearStats(gears, entity, data));

            HashMap<StatContext.StatCtxType, List<StatContext>> map = new HashMap<>();
            for (StatContext.StatCtxType type : StatContext.StatCtxType.values()) {
                map.put(type, new ArrayList<>());
            }
            statContexts.forEach(x -> {
                map.get(x.type)
                    .add(x);
            });

            map.forEach((key, value) -> value
                .forEach(v -> {
                    v.stats.forEach(s -> {
                        if (s.getStat().statContextModifier != null) {
                            map.get(s.getStat().statContextModifier.getCtxTypeNeeded())
                                .forEach(c -> s.getStat().statContextModifier.modify(s, c));
                        }
                    });
                }));

            statContexts.forEach(x -> x.stats.forEach(s -> s.applyStats(data)));

            addVanillaHpToStats(entity, data);

            new HashMap<>(getStats().statsInCalc).entrySet()
                .forEach(x -> {
                    InCalcStatData statdata = x.getValue();
                    Stat stat = x.getValue()
                        .GetStat();
                    if (stat instanceof IAffectsStats) {
                        IAffectsStats add = (IAffectsStats) stat;
                        add.affectStats(data, statdata);
                    }
                });

            new HashMap<>(getStats().statsInCalc).entrySet()
                .forEach(x -> {
                    InCalcStatData statdata = x.getValue();
                    Stat stat = x.getValue()
                        .GetStat();
                    if (stat instanceof ITransferToOtherStats) {
                        ITransferToOtherStats add = (ITransferToOtherStats) stat;
                        add.transferStats(data, statdata);
                    }
                });

            new HashMap<>(getStats().statsInCalc).entrySet()
                .forEach(x -> {
                    InCalcStatData statdata = x.getValue();
                    Stat stat = x.getValue()
                        .GetStat();
                    if (stat instanceof ICoreStat) {
                        ICoreStat add = (ICoreStat) stat;
                        add.addToOtherStats(data, statdata);
                    }
                });

            stats.calculate();

            DirtyCheck aftercalc = getDirtyCheck();

            Cached.VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC.forEach(x -> {
                ModifiableAttributeInstance in = entity.getAttribute(x.left);
                if (in.getModifier(x.right) != null) {
                    in.removeModifier(x.right);
                }
            });

            this.getStats().stats.values()
                .forEach(x -> {
                    if (x.GetStat() instanceof AttributeStat) {
                        AttributeStat stat = (AttributeStat) x.GetStat();
                        stat.addToEntity(entity, x);
                    }

                });

            if (entity instanceof PlayerEntity) {

                Load.spells(entity)
                    .getSpellsData().spells.add(SpellKeys.MAGIC_PROJECTILE.id); // todo

                this.getStats().stats.values()
                    .forEach(x -> {

                        if (x.GetStat() instanceof IAfterStatCalc) {
                            IAfterStatCalc af = (IAfterStatCalc) x.GetStat();
                            af.affectUnit(data, x);
                        }
                    });

            }
            if (old.isDirty(aftercalc)) {
                if (!Unit.shouldSendUpdatePackets((LivingEntity) entity)) {
                    return;
                }
                Packets.sendToTracking(getUpdatePacketFor(entity, data), entity);
            }

            if (entity instanceof PlayerEntity) {
                Packets.sendToClient((PlayerEntity) entity, new EntityUnitPacket(entity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addVanillaHpToStats(LivingEntity entity, EntityData data) {
        if (entity instanceof PlayerEntity) {

            float maxhp = MathHelper.clamp(entity.getMaxHealth(), 0, 500);
            // all increases after this would just reduce enviro damage

            getStats().getStatInCalculation(Health.getInstance())
                .addAlreadyScaledFlat(maxhp);

            // add vanila hp to extra hp
        }
    }

    private List<StatContext> addGearStats(List<GearData> gears, LivingEntity entity, EntityData data) {

        List<StatContext> ctxs = new ArrayList<>();

        gears.forEach(x -> {
            List<ExactStatData> stats = x.gear.GetAllStats();

            if (x.percentStatUtilization != 100) {
                // multi stats like for offfhand weapons
                float multi = x.percentStatUtilization / 100F;
                stats.forEach(s -> s.multiplyBy(multi));
            }
            ctxs.add(new GearStatCtx(x.gear, stats));

        });

        return ctxs;

    }

    public static boolean shouldSendUpdatePackets(LivingEntity en) {
        if (ServerContainer.get().DONT_SYNC_DATA_OF_AMBIENT_MOBS.get()) {
            return en.getType()
                .getCategory() != EntityClassification.AMBIENT && en.getType()
                .getCategory() != EntityClassification.WATER_AMBIENT;
        }
        return true;
    }

    public static MyPacket getUpdatePacketFor(LivingEntity en, EntityData data) {
        return new EfficientMobUnitPacket(en, data);
    }

}

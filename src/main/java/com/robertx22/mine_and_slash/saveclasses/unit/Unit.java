package com.robertx22.mine_and_slash.saveclasses.unit;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.api.MineAndSlashEvents;
import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.EntityConfig;
import com.robertx22.mine_and_slash.database.data.mob_affixes.base.MobAffix;
import com.robertx22.mine_and_slash.database.data.rarities.MobRarity;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.types.UnknownStat;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Mana;
import com.robertx22.mine_and_slash.event_hooks.entity.damage.DamageEventData;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.stat_calculation.CommonStatUtils;
import com.robertx22.mine_and_slash.uncommon.stat_calculation.MobStatUtils;
import com.robertx22.mine_and_slash.uncommon.stat_calculation.PlayerStatUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.EfficientMobUnitPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.MyPacket;
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
import net.minecraft.world.dimension.DimensionType;

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

    @Store
    public String prefix;
    @Store
    public String suffix;

    public MobAffix getPrefix() {
        if (prefix == null) {
            return null;
        } else {
            return SlashRegistry.MobAffixes()
                .get(prefix);
        }
    }

    public MobAffix getSuffix() {
        if (suffix == null) {
            return null;
        } else {
            return SlashRegistry.MobAffixes()
                .get(suffix);
        }
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

    public boolean hasStat(Stat stat) {
        return hasStat(stat.GUID());
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

    public void setRandomMobAffixes(MobRarity rarity) {

        if (RandomUtils.roll(rarity.bothAffixesChance())) {
            randomizePrefix();
            randomizeSuffix();
        } else if (RandomUtils.roll(rarity.oneAffixChance())) {

            if (RandomUtils.roll(50)) {
                randomizePrefix();
            } else {
                randomizeSuffix();
            }
        }

    }

    public void randomizePrefix() {
        this.prefix = SlashRegistry.MobAffixes()
            .getFilterWrapped(x -> x.isPrefix())
            .random()
            .GUID();
    }

    public void randomizeSuffix() {
        this.suffix = SlashRegistry.MobAffixes()
            .getFilterWrapped(x -> x.isSuffix())
            .random()
            .GUID();
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
            return ((Unit) obj).GUID == this.GUID;
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

    public float getCurrentEffectiveHealth(LivingEntity entity, UnitData data) {
        float curhp = health().CurrentValue(entity, this);
        if (data.getResources() != null) {
            curhp += data.getResources()
                .getMagicShield();
        }
        return curhp;

    }

    public float getMaxEffectiveHealth() {
        float hp = healthData().getAverageValue();
        hp += magicShieldData().getAverageValue();

        return hp;

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

        List<MobRarity> rarities = Rarities.Mobs.getAllRarities();

        if (entity.world.random.nextBoolean()) {
            if (entity.dimension.equals(DimensionType.OVERWORLD)) {
                if (y < 50) {
                    rarities.removeIf(x -> x.Rank() == IRarity.Common);
                }
                if (y < 30) {
                    rarities.removeIf(x -> x.Rank() == IRarity.Magical);
                }
            }
        }

        MobRarity finalRarity = RandomUtils.weightedRandom(rarities);

        EntityConfig entityConfig = SlashRegistry.getEntityConfig(entity, Load.Unit(entity));

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

    public float getMissingHealth(LivingEntity en) {
        return healthData().getAverageValue() - health().CurrentValue(en, this);
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

        data.setEquipsChanged(false);

        if (data.getUnit() == null) {
            data.setUnit(this, entity);
        }

        DirtyCheck old = getDirtyCheck();

        List<GearItemData> gears = new ArrayList<>();

        new MineAndSlashEvents.CollectGearStacksEvent(entity, gears, dmgData);

        ClearStats();

        MobRarity rar = Rarities.Mobs.get(data.getRarity());

        CommonStatUtils.addPotionStats(entity, data);
        CommonStatUtils.addExactCustomStats(data);

        if (entity instanceof PlayerEntity) {
            PlayerStatUtils.AddPlayerBaseStats(data, this);
            Load.statPoints((PlayerEntity) entity)
                .applyStats(data);
        } else {
            MobStatUtils.AddMobcStats(data, entity);
            MobStatUtils.addAffixStats(data);
            MobStatUtils.worldMultiplierStats(entity.world, this);
            MobStatUtils.increaseMobStatsPerTier(data, this);
            MobStatUtils.modifyMobStatsByConfig(entity, data);
        }

        addGearStats(gears, entity, data);

        CommonStatUtils.CalcTraitsAndCoreStats(
            data); // has to be at end for the conditionals like if crit higher than x

        CalcStats(data);

        removeEmptyStats();

        DirtyCheck aftercalc = getDirtyCheck();

        if (old.hp != aftercalc.hp) {
            addToVanillaHealth(entity);
        }

        if (old.isDirty(aftercalc)) {
            if (!Unit.shouldSendUpdatePackets((LivingEntity) entity)) {
                return;
            }
            MMORPG.sendToTracking(getUpdatePacketFor(entity, data), entity);
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

        while (!gears.isEmpty()) {

            this.CalcStats(data);

            List<GearItemData> toremove = new ArrayList<>();

            for (int i = 0; i < gears.size(); i++) {

                GearItemData gear = gears.get(i);

                boolean addstats = true;

                if (entity instanceof PlayerEntity) {
                    if (!gear.isIdentified()) {
                        addstats = false;
                        continue;
                    } else if (data.getLevel() < gear.level) {
                        addstats = false;
                        continue;
                    } else if (!gear.meetsStatRequirements(data)) {
                        addstats = false;
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
            EntityAttributes.MAX_HEALTH.getId(),
            hp,
            EntityAttributeModifier.Operation.ADDITION
        );

        EntityAttributeInstance atri = en.getAttributeInstance(EntityAttributes.MAX_HEALTH);

        if (atri.hasModifier(mod)) {
            atri.removeModifier(mod);
        }
        atri.addModifier(mod);

    }

    private static List<EntityType> IGNORED_ENTITIES = null;

    public static List<EntityType> getIgnoredEntities() {

        if (IGNORED_ENTITIES == null) {
            IGNORED_ENTITIES = ModConfig.INSTANCE.Server.IGNORED_ENTITIES
                .stream()
                .filter(x -> Registry.ENTITY_TYPE.containsId(new Identifier(x)))
                .map(x -> Registry.ENTITY_TYPE.get(new Identifier(x)))
                .collect(Collectors.toList());
        }

        return IGNORED_ENTITIES;

    }

    public static boolean shouldSendUpdatePackets(LivingEntity en) {
        return getIgnoredEntities().contains(en.getType()) == false;
    }

    public static MyPacket getUpdatePacketFor(LivingEntity en, UnitData data) {
        return new EfficientMobUnitPacket(en, data); // todo maybe players will need extra data later on? maybe
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

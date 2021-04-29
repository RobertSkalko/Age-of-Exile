package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.capability.bases.EntityGears;
import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.capability.bases.INeededForClient;
import com.robertx22.age_of_exile.config.LevelRewardConfig;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.races.PlayerRace;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemType;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.tiers.base.Tier;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.WorldDungeonCap;
import com.robertx22.age_of_exile.event_hooks.my_events.CollectGearEvent;
import com.robertx22.age_of_exile.event_hooks.player.OnLogin;
import com.robertx22.age_of_exile.mmorpg.registers.common.ModCriteria;
import com.robertx22.age_of_exile.saveclasses.CustomExactStatsData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.*;
import com.robertx22.age_of_exile.threat_aggro.ThreatData;
import com.robertx22.age_of_exile.uncommon.datasaving.CustomExactStats;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.UnitNbt;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.utilityclasses.*;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.LootTableItem;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.EntityStatusEffectsData;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class EntityCap {

    private static final String RARITY = "rarity";
    private static final String RACE = "race";
    private static final String LEVEL = "level";
    private static final String EXP = "exp";
    private static final String HP = "hp";
    private static final String UUID = "uuid";
    private static final String MOB_SAVED_ONCE = "mob_saved_once";
    private static final String SET_MOB_STATS = "set_mob_stats";
    private static final String NEWBIE_STATUS = "is_a_newbie";
    private static final String EQUIPS_CHANGED = "EQUIPS_CHANGED";
    private static final String TIER = "TIER";
    private static final String AFFIXES = "affix";
    private static final String SHOULD_SYNC = "SHOULD_SYNC";
    private static final String ENTITY_TYPE = "ENTITY_TYPE";
    private static final String RESOURCES_LOC = "RESOURCES_LOC";
    private static final String STATUSES = "statuses";
    private static final String SCROLL_BUFF_SEED = "sb_seed";
    private static final String COOLDOWNS = "cds";
    private static final String THREAT = "th";

    public interface UnitData extends ICommonPlayerCap, INeededForClient {

        EntityStatusEffectsData getStatusEffectsData();

        void modifyResource(ResourcesData.Context ctx);

        void onDeath();

        void setType();

        EntityTypeUtils.EntityClassification getType();

        ThreatData getThreat();

        void trySync();

        GearItemData setupWeaponData();

        void setEquipsChanged(boolean bool);

        CooldownsData getCooldowns();

        boolean isNewbie();

        void setNewbieStatus(boolean bool);

        boolean needsToBeGivenStats();

        boolean increaseRarity();

        Unit getUnit();

        void setUnit(Unit unit);

        void setRarity(String rarity);

        String getRarity();

        String getUUID();

        void setUUID(UUID id);

        MutableText getName();

        void tryRecalculateStats();

        void forceRecalculateStats(AttackInformation data);

        void forceRecalculateStats();

        void forceSetUnit(Unit unit);

        Entity getEntity();

        boolean canUseWeapon(GearItemData gear);

        void onLogin(PlayerEntity player);

        int getSyncedMaxHealth();

        Tier getMapTier();

        CustomExactStatsData getCustomExactStats();

        ResourcesData getResources();

        float getCurrentMana();

        void mobStatsAreSet();

        void attackWithWeapon(AttackInformation data);

        void mobBasicAttack(AttackInformation data);

        int getLevel();

        void setLevel(int lvl);

        boolean CheckIfCanLevelUp();

        boolean LevelUp(PlayerEntity player);

        boolean CheckLevelCap();

        void SetMobLevelAtSpawn(PlayerEntity player);

        int getExp();

        void setExp(int exp);

        int GiveExp(PlayerEntity player, int i);

        int getExpRequiredForLevelUp();

        EntityGears getCurrentGears();

        MobAffixesData getAffixData();

        PlayerRace getRace();

        boolean hasRace();

        void setRace(String race);

        int getBuffSeed();

        void randomizeBuffSeed();

    }

    public static class DefaultImpl implements UnitData {

        LivingEntity entity;

        //dont save this
        EntityGears gears = new EntityGears();
        // dont

        // sync these for mobs
        Unit unit = new Unit();
        String rarity = IRarity.COMMON_ID;
        String race = "";
        int level = 1;
        int exp = 0;
        int maxHealth = 0;
        MobAffixesData affixes = new MobAffixesData();
        int buffSeed = 0;

        public EntityStatusEffectsData statusEffects = new EntityStatusEffectsData();

        CooldownsData cooldowns = new CooldownsData();
        ThreatData threat = new ThreatData();

        EntityTypeUtils.EntityClassification type = EntityTypeUtils.EntityClassification.PLAYER;
        // sync these for mobs

        boolean setMobStats = false;
        String uuid = "";
        boolean isNewbie = true;
        boolean equipsChanged = true;
        boolean shouldSync = false;

        ResourcesData resources = new ResourcesData();
        CustomExactStatsData customExactStats = new CustomExactStatsData();

        public DefaultImpl(LivingEntity entity) {
            this.entity = entity;
        }

        @Override
        public void addClientNBT(CompoundTag nbt) {

            nbt.putInt(LEVEL, level);
            nbt.putString(RARITY, rarity);
            nbt.putString(RACE, race);
            nbt.putInt(SCROLL_BUFF_SEED, buffSeed);
            nbt.putInt(HP, (int) getUnit().getCalculatedStat(Health.getInstance())
                .getAverageValue());
            nbt.putString(ENTITY_TYPE, this.type.toString());

            if (affixes != null) {
                LoadSave.Save(affixes, nbt, AFFIXES);
            }
            LoadSave.Save(statusEffects, nbt, STATUSES);
        }

        @Override
        public void loadFromClientNBT(CompoundTag nbt) {

            this.rarity = nbt.getString(RARITY);
            this.race = nbt.getString(RACE);
            this.level = nbt.getInt(LEVEL);
            this.buffSeed = nbt.getInt(SCROLL_BUFF_SEED);
            if (level < 1) {
                level = 1;
            }
            this.maxHealth = nbt.getInt(HP);

            try {
                String typestring = nbt.getString(ENTITY_TYPE);
                this.type = EntityTypeUtils.EntityClassification.valueOf(typestring);
            } catch (Exception e) {
                this.type = EntityTypeUtils.EntityClassification.OTHER;
            }

            this.affixes = LoadSave.Load(MobAffixesData.class, new MobAffixesData(), nbt, AFFIXES);
            if (affixes == null) {
                affixes = new MobAffixesData();
            }

            this.statusEffects = LoadSave.Load(EntityStatusEffectsData.class, new EntityStatusEffectsData(), nbt, STATUSES);
            if (statusEffects == null) {
                statusEffects = new EntityStatusEffectsData();
            }

            this.cooldowns = LoadSave.Load(CooldownsData.class, new CooldownsData(), nbt, COOLDOWNS);
            if (cooldowns == null) {
                cooldowns = new CooldownsData();
            }

            this.threat = LoadSave.Load(ThreatData.class, new ThreatData(), nbt, THREAT);
            if (threat == null) {
                threat = new ThreatData();
            }
        }

        @Override
        public CompoundTag toTag(CompoundTag nbt) {

            addClientNBT(nbt);

            nbt.putInt(EXP, exp);
            nbt.putString(UUID, uuid);
            nbt.putBoolean(MOB_SAVED_ONCE, true);
            nbt.putBoolean(SET_MOB_STATS, setMobStats);
            nbt.putBoolean(NEWBIE_STATUS, this.isNewbie);
            nbt.putBoolean(EQUIPS_CHANGED, equipsChanged);
            nbt.putBoolean(SHOULD_SYNC, shouldSync);

            if (unit != null) {
                UnitNbt.Save(nbt, unit);
            }

            if (customExactStats != null) {
                CustomExactStats.Save(nbt, customExactStats);
            }

            if (resources != null) {
                LoadSave.Save(resources, nbt, RESOURCES_LOC);
            }

            if (cooldowns != null) {
                LoadSave.Save(cooldowns, nbt, COOLDOWNS);
            }
            if (threat != null) {
                LoadSave.Save(threat, nbt, THREAT);
            }

            return nbt;

        }

        @Override
        public void fromTag(CompoundTag nbt) {

            loadFromClientNBT(nbt);

            this.exp = nbt.getInt(EXP);
            this.uuid = nbt.getString(UUID);
            this.setMobStats = nbt.getBoolean(SET_MOB_STATS);
            if (nbt.contains(NEWBIE_STATUS)) {
                this.isNewbie = nbt.getBoolean(NEWBIE_STATUS);
            }
            this.equipsChanged = nbt.getBoolean(EQUIPS_CHANGED);
            this.shouldSync = nbt.getBoolean(SHOULD_SYNC);

            this.unit = UnitNbt.Load(nbt);
            if (this.unit == null) {
                this.unit = new Unit();
            }

            try {
                this.resources = LoadSave.Load(ResourcesData.class, new ResourcesData(), nbt, RESOURCES_LOC);
                if (resources == null) {
                    resources = new ResourcesData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.customExactStats = CustomExactStats.Load(nbt);
            if (this.customExactStats == null) {
                this.customExactStats = new CustomExactStatsData();
            }

        }

        @Override
        public void setEquipsChanged(boolean bool) {
            this.equipsChanged = bool;
        }

        @Override
        public CooldownsData getCooldowns() {
            return cooldowns;
        }

        @Override
        public EntityStatusEffectsData getStatusEffectsData() {
            return this.statusEffects;
        }

        @Override
        public void modifyResource(ResourcesData.Context ctx) {
            this.resources.modify(ctx);
        }

        @Override
        public void onDeath() {

            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;

                int expLoss = (int) (exp * ModConfig.get().Server.EXP_LOSS_ON_DEATH);

                if (expLoss > 0) {
                    this.exp = MathHelper.clamp(exp - expLoss, 0, Integer.MAX_VALUE);
                }

                if (getLevel() >= ModConfig.get().Server.START_CORRUPT_AT_LVL) {
                    List<GearData> stacks = CollectGearEvent.getAllGear(null, entity, Load.Unit(entity));

                    int amount = 0;

                    for (GearData data : stacks) {
                        float chance = (float) (LevelUtils.getMaxLevelMultiplier(getLevel()) * ModConfig.get().Server.CHANCE_TO_CORRUPT_ITEM_AT_MAX_LEVEL);

                        if (data.gear != null && !data.gear.isCorrupted()) {
                            if (RandomUtils.roll(chance)) {
                                data.gear.is_cor = true;
                                data.gear.saveToStack(data.stack);
                                amount++;
                            }
                        }
                    }

                    if (amount > 0) {
                        player.sendMessage(new LiteralText(Formatting.RED + "As a result of death, " + amount + " worn items have been corrupted."), false);
                    }
                }

            }
        }

        @Override
        public void setType() {
            this.type = EntityTypeUtils.getType(entity);
        }

        @Override
        public EntityTypeUtils.EntityClassification getType() {
            return this.type;
        }

        @Override
        public ThreatData getThreat() {
            return threat;
        }

        @Override
        public void trySync() {
            if (this.shouldSync) {
                this.shouldSync = false;

                if (!Unit.shouldSendUpdatePackets(entity)) {
                    return;
                }
                Packets.sendToTracking(Unit.getUpdatePacketFor(entity, this), entity);
            }

        }

        @Override
        public PlayerCaps getCapType() {
            return PlayerCaps.ENTITY_DATA;
        }

        @Override
        public Unit getUnit() {
            return unit;
        }

        @Override
        public void setUnit(Unit unit) {
            this.unit = unit;
        }

        @Override
        public void setRarity(String rarity) {
            this.rarity = rarity;

            this.equipsChanged = true;
            this.shouldSync = true;
        }

        @Override
        public String getRarity() {
            return rarity;
        }

        @Override
        public String getUUID() {
            return uuid;
        }

        @Override
        public void setUUID(UUID id) {
            uuid = id.toString();
        }

        @Override
        public MutableText getName() {

            if (entity instanceof PlayerEntity) {
                return new LiteralText("")
                    .append(entity.getDisplayName());

            } else {

                MobRarity rarity = Database.MobRarities()
                    .get(getRarity());

                Formatting rarformat = rarity.textFormatting();

                MutableText name = new LiteralText("").append(entity.getDisplayName())
                    .formatted(rarformat);

                if (!rarity.name_add.isEmpty()) {
                    name = new LiteralText("[Lv." + rarity.name_add + "] ").formatted(Formatting.YELLOW)
                        .append(name);
                }

                MutableText finalName =
                    name;

                MutableText part = new LiteralText("")
                    .append(finalName)
                    .formatted(rarformat);

                MutableText tx = (part);

                return tx;

            }
        }

        @Override
        public void tryRecalculateStats() {

            if (unit == null) {
                unit = new Unit();
            }

            if (needsToRecalcStats()) {
                //Watch watch = new Watch();
                unit.recalculateStats(entity, this, null);
                //watch.print("stat calc for " + (entity instanceof PlayerEntity ? "player " : "mob "));
            }

        }

        @Override
        public void forceRecalculateStats(AttackInformation data) {
            unit.recalculateStats(entity, this, data);
        }

        @Override
        public void forceRecalculateStats() {

            if (unit == null) {
                unit = new Unit();
            }
            unit.recalculateStats(entity, this, null);
        }

        // This reduces stat calculation by about 4 TIMES!
        private boolean needsToRecalcStats() {
            return this.equipsChanged;
        }

        @Override
        public void forceSetUnit(Unit unit) {
            this.unit = unit;
        }

        @Override
        public GearItemData setupWeaponData() {
            return Gear.Load(entity.getMainHandStack());
        }

        @Override
        public boolean canUseWeapon(GearItemData weaponData) {
            return weaponData != null;
        }

        @Override
        public void onLogin(PlayerEntity player) {

            try {

                if (unit == null) {
                    unit = new Unit();
                }

                // check if newbie
                if (isNewbie()) {
                    setNewbieStatus(false);

                    if (ModConfig.get().Server.GET_STARTER_ITEMS) {
                        OnLogin.GiveStarterItems(player);
                    }

                    Load.favor(player)
                        .setFavor(ModConfig.get().Favor.STARTING_FAVOR); // newbie starting favor

                    Packets.sendToClient(player, new SyncCapabilityToClient(player, PlayerCaps.SPELLS));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean increaseRarity() {

            MobRarity rar = Database.MobRarities()
                .get(rarity);

            if (rar.hasHigherRarity()) {
                rarity = rar.getHigherRarity()
                    .GUID();
                this.equipsChanged = true;
                this.shouldSync = true;
                this.forceRecalculateStats();
                return true;
            } else {
                return false;
            }
        }

        @Override
        public int getSyncedMaxHealth() {
            return this.maxHealth;
        }

        @Override
        public Tier getMapTier() {

            int tier = 0;

            if (WorldUtils.isDungeonWorld(entity.world)) {
                WorldDungeonCap data = Load.dungeonData(entity.world);

                tier = data.data.get(entity.getBlockPos()).data.tier;
            }

            return Database.Tiers()
                .get(tier + "");
        }

        @Override
        public CustomExactStatsData getCustomExactStats() {
            return this.customExactStats;
        }

        @Override
        public ResourcesData getResources() {
            return this.resources;
        }

        @Override
        public float getCurrentMana() {
            return this.resources.getMana();
        }

        @Override
        public void mobStatsAreSet() {
            this.setMobStats = true;
        }

        @Override
        public void attackWithWeapon(AttackInformation data) {

            if (data.weaponData.GetBaseGearType()
                .getWeaponMechanic() != null) {

                if (data.weapon != null) {
                    data.weapon.damage(1, new Random(), null);
                }

                data.weaponData.GetBaseGearType()
                    .getWeaponMechanic()
                    .attack(data);

            }
        }

        @Override
        public void mobBasicAttack(AttackInformation data) {
            MobRarity rar = Database.MobRarities()
                .get(data.getAttackerEntityData()
                    .getRarity());

            float multi = (float) (ModConfig.get().Server.VANILLA_MOB_DMG_AS_EXILE_DMG + (LevelUtils.getMaxLevelMultiplier(getLevel()) * (ModConfig.get().Server.VANILLA_MOB_DMG_AS_EXILE_DMG_AT_MAX_LVL - ModConfig.get().Server.VANILLA_MOB_DMG_AS_EXILE_DMG)));

            float vanilla = data.getAmount() * multi;

            float num = vanilla * rar.DamageMultiplier() * getMapTier().dmg_multi;

            num *= Database.getEntityConfig(entity, this).dmg_multi;

            num = new AttackDamage(Elements.Physical).scale(num, getLevel());

            AttackPlayStyle style = AttackPlayStyle.MELEE;

            if (data.getSource() != null && data.getSource()
                .isProjectile()) {
                style = AttackPlayStyle.RANGED;
            }

            DamageEffect dmg = new DamageEffect(
                data, (int) num, AttackType.ATTACK, WeaponTypes.None, style
            );
            dmg.setIsBasicAttack();

            dmg.Activate();

        }

        @Override
        public boolean isNewbie() {
            return isNewbie;
        }

        @Override
        public void setNewbieStatus(boolean bool) {
            isNewbie = bool;
        }

        @Override
        public boolean needsToBeGivenStats() {
            return this.setMobStats == false;
        }

        @Override
        public int getExpRequiredForLevelUp() {
            return LevelUtils.getExpRequiredForLevel(this.getLevel() + 1);
        }

        @Override
        public EntityGears getCurrentGears() {
            return gears;
        }

        @Override
        public MobAffixesData getAffixData() {
            return affixes;
        }

        @Override
        public PlayerRace getRace() {
            return Database.Races()
                .get(race);
        }

        @Override
        public boolean hasRace() {
            return Database.Races()
                .isRegistered(race);
        }

        @Override
        public void setRace(String race) {
            this.race = race;
        }

        @Override
        public int getBuffSeed() {
            return buffSeed;
        }

        @Override
        public void randomizeBuffSeed() {
            this.buffSeed = new Random().nextInt();
        }

        @Override
        public void SetMobLevelAtSpawn(PlayerEntity nearestPlayer) {
            this.setMobStats = true;

            if (WorldUtils.isDungeonWorld(entity.world)) {
                try {
                    BlockPos pos = entity.getBlockPos();
                    DungeonData data = Load.dungeonData(entity.world).data.get(pos).data;
                    if (!data.isEmpty()) {
                        this.setLevel(data.lvl);
                        return;
                    } else {
                        System.out.print("A mob spawned in a dungeon world without a dungeon data nearby!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            setMobLvlNormally(entity, nearestPlayer);

        }

        private void setMobLvlNormally(LivingEntity entity, PlayerEntity nearestPlayer) {
            EntityConfig entityConfig = Database.getEntityConfig(entity, this);

            int lvl = LevelUtils.determineLevel(entity.world, entity.getBlockPos(),
                nearestPlayer
            );

            setLevel(MathHelper.clamp(lvl, entityConfig.min_lvl, entityConfig.max_lvl));
        }

        @Override
        public int GiveExp(PlayerEntity player, int i) {

            setExp(exp + i);

            if (exp > this.getExpRequiredForLevelUp()) {

                if (this.CheckIfCanLevelUp() && this.CheckLevelCap()) {
                    this.LevelUp(player);
                }

                return i;
            }
            return i;
        }

        @Override
        public boolean CheckIfCanLevelUp() {

            return getExp() >= getExpRequiredForLevelUp();

        }

        public int getRemainingExp() {
            int num = getExp() - getExpRequiredForLevelUp();

            if (num < 0) {
                num = 0;
            }
            return num;
        }

        @Override
        public boolean CheckLevelCap() {
            return getLevel() + 1 <= GameBalanceConfig.get().MAX_LEVEL;
        }

        @Override
        public boolean LevelUp(PlayerEntity player) {

            if (!CheckIfCanLevelUp()) {
                player.sendMessage(Chats.Not_enough_experience.locName(), false);
            } else if (!CheckLevelCap()) {
                player.sendMessage(Chats.Can_not_go_over_maximum_level.locName(), false);
            }

            if (CheckIfCanLevelUp() && CheckLevelCap()) {

                if (player instanceof ServerPlayerEntity) {
                    ModCriteria.PLAYER_LEVEL.trigger((ServerPlayerEntity) player);
                }

                // fully restore on lvlup
                getResources()
                    .modify(new ResourcesData.Context(this, player, ResourceType.MANA,
                        Integer.MAX_VALUE,
                        ResourcesData.Use.RESTORE
                    ));
                getResources()
                    .modify(new ResourcesData.Context(this, player, ResourceType.HEALTH,
                        Integer.MAX_VALUE,
                        ResourcesData.Use.RESTORE
                    ));
                getResources()
                    .modify(new ResourcesData.Context(this, player, ResourceType.BLOOD,
                        Integer.MAX_VALUE,
                        ResourcesData.Use.RESTORE
                    ));

                // fully restore on lvlup

                this.setLevel(level + 1);
                setExp(getRemainingExp());

                Load.spells(player)
                    .getSkillGemData().stacks.forEach(x -> {
                    // lvl up spell gems, not support gems
                    SkillGemData data = SkillGemData.fromStack(x);
                    if (data != null) {
                        if (data.getSkillGem().type == SkillGemType.SKILL_GEM) {

                            SkillGemData test = SkillGemData.fromStack(x);
                            test.lvl++;

                            if (test.canPlayerUse(player)) {
                                // only lvl gems if player can use them after they are lvled
                                data.tryLevel();
                                data.saveToStack(x);
                            }
                        }
                    }
                });

                Optional<LevelRewardConfig> opt = ModConfig.get().LevelRewards.levelRewards.stream()
                    .filter(x -> x.for_level == this.level)
                    .findAny();

                if (opt.isPresent()) {
                    PlayerUtils.giveItem(LootTableItem.of(new Identifier(opt.get().loot_table_id)), player);
                }

                OnScreenMessageUtils.sendLevelUpMessage(player, new LiteralText("Player"), level - 1, level);

                return true;
            }
            return false;
        }

        @Override
        public int getLevel() {

            return level;

        }

        @Override
        public void setLevel(int lvl) {

            level = MathHelper.clamp(lvl, 1, GameBalanceConfig.get().MAX_LEVEL);

            this.equipsChanged = true;
            this.shouldSync = true;

        }

        @Override
        public int getExp() {
            return exp;
        }

        @Override
        public void setExp(int exp) {
            this.exp = exp;
        }

        @Override
        public Entity getEntity() {
            return entity;
        }

    }

}

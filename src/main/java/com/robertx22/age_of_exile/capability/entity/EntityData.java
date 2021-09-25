package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.capability.bases.EntityGears;
import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.capability.bases.INeededForClient;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.WorldDungeonCap;
import com.robertx22.age_of_exile.event_hooks.player.OnLogin;
import com.robertx22.age_of_exile.saveclasses.CustomExactStatsData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.unit.MobAffixesData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.threat_aggro.ThreatData;
import com.robertx22.age_of_exile.uncommon.datasaving.CustomExactStats;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.UnitNbt;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.*;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.EntityStatusEffectsData;
import com.robertx22.library_of_exile.components.forge.BaseProvider;
import com.robertx22.library_of_exile.components.forge.BaseStorage;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Comparator;
import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber
public class EntityData implements ICommonPlayerCap, INeededForClient {

    public EntityData(LivingEntity entity) {
        this.entity = entity;
    }

    public static final ResourceLocation RESOURCE = new ResourceLocation(Ref.MODID, "entity_data");

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onEntityConstruct(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof LivingEntity) {
                event.addCapability(RESOURCE, new Provider((LivingEntity) event.getObject()));
            }
        }
    }

    public static class Storage implements BaseStorage<EntityData> {

    }

    public static class Provider extends BaseProvider<EntityData, LivingEntity> {
        public Provider(LivingEntity owner) {
            super(owner);
        }

        @Override
        public EntityData newDefaultImpl(LivingEntity owner) {
            return new EntityData(owner);
        }

        @Override
        public Capability<EntityData> dataInstance() {
            return Data;
        }
    }

    @CapabilityInject(EntityData.class)
    public static final Capability<EntityData> Data = null;

    private static final String RARITY = "rarity";
    private static final String RACE = "race";
    private static final String LEVEL = "level";
    private static final String EXP = "exp";
    private static final String HP = "hp";
    private static final String UUID = "uuid";
    private static final String SET_MOB_STATS = "set_mob_stats";
    private static final String NEWBIE_STATUS = "is_a_newbie";
    private static final String EQUIPS_CHANGED = "eq_changed";
    private static final String AFFIXES = "affix";
    private static final String SHOULD_SYNC = "do_sync";
    private static final String ENTITY_TYPE = "ENTITY_TYPE";
    private static final String RESOURCES_LOC = "res_loc";
    private static final String STATUSES = "statuses";
    private static final String SCROLL_BUFF_SEED = "sb_seed";
    private static final String COOLDOWNS = "cds";
    private static final String THREAT = "th";

    LivingEntity entity;

    transient EntityGears gears = new EntityGears();

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

    @Override
    public void addClientNBT(CompoundNBT nbt) {

        nbt.putInt(LEVEL, level);
        nbt.putString(RARITY, rarity);
        nbt.putString(RACE, race);
        nbt.putInt(SCROLL_BUFF_SEED, buffSeed);
        nbt.putInt(HP, (int) getUnit().getCalculatedStat(Health.getInstance())
            .getValue());
        nbt.putString(ENTITY_TYPE, this.type.toString());

        if (affixes != null) {
            LoadSave.Save(affixes, nbt, AFFIXES);
        }
        LoadSave.Save(statusEffects, nbt, STATUSES);
    }

    @Override
    public void loadFromClientNBT(CompoundNBT nbt) {

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

    }

    @Override
    public CompoundNBT saveToNBT() {
        CompoundNBT nbt = new CompoundNBT();

        addClientNBT(nbt);

        nbt.putInt(EXP, exp);
        nbt.putString(UUID, uuid);
        nbt.putBoolean(SET_MOB_STATS, setMobStats);
        nbt.putBoolean(NEWBIE_STATUS, this.isNewbie);
        nbt.putBoolean(EQUIPS_CHANGED, equipsChanged);
        nbt.putBoolean(SHOULD_SYNC, shouldSync);

        LoadSave.Save(cooldowns, nbt, COOLDOWNS);

        if (unit != null) {
            UnitNbt.Save(nbt, unit);
        }

        if (customExactStats != null) {
            CustomExactStats.Save(nbt, customExactStats);
        }

        if (resources != null) {
            LoadSave.Save(resources, nbt, RESOURCES_LOC);
        }

        if (threat != null) {
            LoadSave.Save(threat, nbt, THREAT);
        }

        return nbt;

    }

    @Override
    public void loadFromNBT(CompoundNBT nbt) {

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

        cooldowns = LoadSave.Load(CooldownsData.class, new CooldownsData(), nbt, COOLDOWNS);
        if (cooldowns == null) {
            cooldowns = new CooldownsData();
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

        this.threat = LoadSave.Load(ThreatData.class, new ThreatData(), nbt, THREAT);
        if (threat == null) {
            threat = new ThreatData();
        }
    }

    public void setEquipsChanged(boolean bool) {
        this.equipsChanged = bool;
    }

    public CooldownsData getCooldowns() {
        return cooldowns;
    }

    public EntityStatusEffectsData getStatusEffectsData() {
        return this.statusEffects;
    }

    public float getMaximumResource(ResourceType type) {

        if (type == ResourceType.blood) {
            return getUnit().bloodData()
                .getValue();
        } else if (type == ResourceType.mana) {
            return getUnit().manaData()
                .getValue();
        } else if (type == ResourceType.health) {
            return entity.getMaxHealth();
        } else if (type == ResourceType.energy) {
            return getUnit().energyData()
                .getValue();
        }

        return 0;

    }

    public void onDeath() {

        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            int expLoss = (int) (exp * ServerContainer.get().EXP_LOSS_ON_DEATH.get());

            if (expLoss > 0) {
                this.exp = MathHelper.clamp(exp - expLoss, 0, Integer.MAX_VALUE);
            }

        }
    }

    public void setType() {
        this.type = EntityTypeUtils.getType(entity);
    }

    public EntityTypeUtils.EntityClassification getType() {
        return this.type;
    }

    public ThreatData getThreat() {
        return threat;
    }

    public void trySync() {
        if (this.shouldSync) {
            this.shouldSync = false;

            if (!Unit.shouldSendUpdatePackets(entity)) {
                return;
            }
            Packets.sendToTracking(Unit.getUpdatePacketFor(entity, this), entity);
        }

    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;

        this.equipsChanged = true;
        this.shouldSync = true;
    }

    public String getRarity() {
        return rarity;
    }

    public String getUUID() {
        return uuid;
    }

    public MobRarity getMobRarity() {
        String rar = rarity;
        if (!ExileDB.MobRarities()
            .isRegistered(rar)) {
            rar = IRarity.COMMON_ID;
        }
        return ExileDB.MobRarities()
            .get(rar);
    }

    public void setUUID(UUID id) {
        uuid = id.toString();
    }

    public IFormattableTextComponent getName() {

        if (entity instanceof PlayerEntity) {
            return new StringTextComponent("")
                .append(entity.getDisplayName());

        } else {

            MobRarity rarity = ExileDB.MobRarities()
                .get(getRarity());

            TextFormatting rarformat = rarity.textFormatting();

            IFormattableTextComponent name = new StringTextComponent("").append(entity.getDisplayName())
                .withStyle(rarformat);

            String icons = "";

            for (MobAffix x : getAffixData().getAffixes()) {
                icons += CLOC.translate(x.locName());
            }
            if (!icons.isEmpty()) {
                icons += " ";
            }

            IFormattableTextComponent finalName = new StringTextComponent(icons).append(
                name);

            IFormattableTextComponent part = new StringTextComponent("")
                .append(finalName)
                .withStyle(rarformat);

            IFormattableTextComponent tx = (part);

            return tx;

        }
    }

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

    public void forceRecalculateStats(AttackInformation data) {
        unit.recalculateStats(entity, this, data);
    }

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

    public void forceSetUnit(Unit unit) {
        this.unit = unit;
    }

    public GearItemData setupWeaponData() {
        return Gear.Load(entity.getMainHandItem());
    }

    public boolean canUseWeapon(GearItemData weaponData) {
        return weaponData != null;
    }

    public void onLogin(PlayerEntity player) {

        try {

            if (unit == null) {
                unit = new Unit();
            }

            // check if newbie
            if (isNewbie()) {
                setNewbieStatus(false);

                if (ServerContainer.get().GET_STARTER_ITEMS.get()) {
                    OnLogin.GiveStarterItems(player);
                }

                Load.playerRPGData(player).favor
                    .setFavor(ServerContainer.get().STARTING_FAVOR.get()); // newbie starting favor

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean increaseRarity() {

        MobRarity rar = ExileDB.MobRarities()
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

    public int getSyncedMaxHealth() {
        return this.maxHealth;
    }

    public Difficulty getMapDifficulty() {

        if (WorldUtils.isMapWorldClass(entity.level)) {
            WorldDungeonCap data = Load.dungeonData(entity.level);
            return data.data.get(entity.blockPosition()).data.getDifficulty();
        }

        return ExileDB.Difficulties()
            .getList()
            .stream()
            .min(Comparator.comparingInt(x -> x.rank))
            .get();

    }

    public CustomExactStatsData getCustomExactStats() {
        return this.customExactStats;
    }

    public ResourcesData getResources() {
        return this.resources;
    }

    public float getCurrentMana() {
        return this.resources.getMana();
    }

    public void mobStatsAreSet() {
        this.setMobStats = true;
    }

    public void attackWithWeapon(AttackInformation data) {

        if (data.weaponData.GetBaseGearType()
            .getWeaponMechanic() != null) {

            GearSlot slot = data.weaponData.GetBaseGearType()
                .getGearSlot();

            float cost = Energy.getInstance()
                .scale(ModType.FLAT, slot.energy_cost, getLevel());
            SpendResourceEvent event = new SpendResourceEvent(entity, ResourceType.energy, cost);
            event.calculateEffects();

            if (event.data.getNumber() > resources.getEnergy()) {
                return;
            }

            event.Activate();

            if (data.weapon != null) {
                data.weapon.hurt(1, new Random(), null);
            }

            data.weaponData.GetBaseGearType()
                .getWeaponMechanic()
                .attack(data);

        }
    }

    public void mobBasicAttack(AttackInformation data) {
        MobRarity rar = ExileDB.MobRarities()
            .get(data.getAttackerEntityData()
                .getRarity());

        float multi = (float) (ServerContainer.get().VANILLA_MOB_DMG_AS_EXILE_DMG.get() + (LevelUtils.getMaxLevelMultiplier(getLevel()) * (ServerContainer.get().VANILLA_MOB_DMG_AS_EXILE_DMG_AT_MAX_LVL.get() - ServerContainer.get().VANILLA_MOB_DMG_AS_EXILE_DMG.get())));

        float vanilla = data.getAmount() * multi;

        float num = vanilla * rar.DamageMultiplier() * getMapDifficulty().dmg_multi;

        num *= ExileDB.getEntityConfig(entity, this).dmg_multi;

        num = new AttackDamage(Elements.Physical).scale(ModType.FLAT, num, getLevel());

        PlayStyle style = PlayStyle.melee;

        if (data.getSource() != null && data.getSource()
            .isProjectile()) {
            style = PlayStyle.ranged;
        }

        DamageEvent dmg = EventBuilder.ofDamage(data, entity, data.getTargetEntity(), num)
            .setupDamage(AttackType.attack, WeaponTypes.none, style)
            .setIsBasicAttack()
            .build();

        dmg.Activate();

    }

    public boolean isNewbie() {
        return isNewbie;
    }

    public void setNewbieStatus(boolean bool) {
        isNewbie = bool;
    }

    public boolean needsToBeGivenStats() {
        return this.setMobStats == false;
    }

    public int getExpRequiredForLevelUp() {
        return LevelUtils.getExpRequiredForLevel(this.getLevel() + 1);
    }

    public EntityGears getCurrentGears() {
        return gears;
    }

    public MobAffixesData getAffixData() {
        return affixes;
    }

    public int getBuffSeed() {
        return buffSeed;
    }

    public void randomizeBuffSeed() {
        this.buffSeed = new Random().nextInt();
    }

    public void SetMobLevelAtSpawn(PlayerEntity nearestPlayer) {
        this.setMobStats = true;

        if (WorldUtils.isMapWorldClass(entity.level)) {
            try {
                BlockPos pos = entity.blockPosition();
                DungeonData data = Load.dungeonData(entity.level).data.get(pos).data;
                if (!data.isEmpty()) {
                    this.setLevel(data.lv);
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
        EntityConfig entityConfig = ExileDB.getEntityConfig(entity, this);

        int lvl = LevelUtils.determineLevel(entity.level, entity.blockPosition(),
            nearestPlayer
        );

        setLevel(MathHelper.clamp(lvl, entityConfig.min_lvl, entityConfig.max_lvl));
    }

    public int GiveExp(PlayerEntity player, int i) {

        IFormattableTextComponent txt = new StringTextComponent("+" + (int) i + " Experience").withStyle(TextFormatting.GREEN);
        OnScreenMessageUtils.sendMessage((ServerPlayerEntity) player, txt, STitlePacket.Type.ACTIONBAR);

        setExp(exp + i);

        if (exp > this.getExpRequiredForLevelUp()) {

            if (this.CheckIfCanLevelUp() && this.CheckLevelCap()) {
                this.LevelUp(player);
            }

            return i;
        }
        return i;
    }

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

    public boolean CheckLevelCap() {
        return getLevel() + 1 <= GameBalanceConfig.get().MAX_LEVEL;
    }

    public boolean LevelUp(PlayerEntity player) {

        if (!CheckIfCanLevelUp()) {
            player.displayClientMessage(Chats.Not_enough_experience.locName(), false);
        } else if (!CheckLevelCap()) {
            player.displayClientMessage(Chats.Can_not_go_over_maximum_level.locName(), false);
        }

        if (CheckIfCanLevelUp() && CheckLevelCap()) {

            if (player instanceof ServerPlayerEntity) {
                //ModCriteria.PLAYER_LEVEL.trigger((ServerPlayerEntity) player);
            }

            // fully restore on lvlup

            getResources().restore(player, ResourceType.mana, Integer.MAX_VALUE);
            getResources().restore(player, ResourceType.health, Integer.MAX_VALUE);
            getResources().restore(player, ResourceType.blood, Integer.MAX_VALUE);

            // fully restore on lvlup

            this.setLevel(level + 1);
            setExp(getRemainingExp());

            OnScreenMessageUtils.sendLevelUpMessage(player, new StringTextComponent("Player"), level - 1, level);

            return true;
        }
        return false;
    }

    public int getLevel() {

        return level;

    }

    public void setLevel(int lvl) {

        level = MathHelper.clamp(lvl, 1, GameBalanceConfig.get().MAX_LEVEL);

        this.equipsChanged = true;
        this.shouldSync = true;

    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    @Override
    public String getCapIdForSyncing() {
        return "entity_data";
    }

}

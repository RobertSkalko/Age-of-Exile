package com.robertx22.age_of_exile.loot;

import com.robertx22.addon.infinite_dungeons.InfiniteDungeonWrapper;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.capability.player.data.ScalingPlayerDiffData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.database.data.stats.types.loot.ItemFind;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.loot.generators.BaseLootGen;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.utils.EntityUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LootInfo {

    private LootInfo(LootOrigin lootOrigin) {
        this.lootOrigin = lootOrigin;
    }

    public enum LootOrigin {
        CHEST, MOB, PLAYER, OTHER, LOOT_CRATE;
    }

    public int amount = 0;
    public Difficulty diff;
    public int level = 0;
    public int tier = 1;

    public LootOrigin lootOrigin;
    public EntityData mobData;
    public EntityData playerData;
    public LivingEntity mobKilled;
    public PlayerEntity player;
    public World world;
    public float multi = 1;
    private int minItems = 0;
    private int maxItems = 50;
    private int extraFavorItems = 0;
    public boolean isMapWorld = false;
    public FavorRank favorRank;
    public RPGPlayerData rpgData;
    public BlockPos pos;
    public DungeonData dungeon;

    public int getMinItems() {
        return minItems;

    }

    public int getExtraFavorItems() {
        return extraFavorItems;
    }

    public int getMaxItems() {
        return maxItems;
    }

    public static LootInfo ofMobKilled(PlayerEntity player, LivingEntity mob) {

        LootInfo info = new LootInfo(LootOrigin.MOB);

        try {
            info.world = mob.level;
            info.mobData = Load.Unit(mob);
            info.playerData = Load.Unit(player);
            info.mobKilled = mob;
            info.player = player;
            info.pos = mob.blockPosition();

            info.setupAllFields();

            if (ExileDB.MobRarities()
                .get(info.mobData.getRarity()).extra_hp_multi > 5 || EntityUtils.getVanillaMaxHealth(mob) > 100) {
                // is boss basically
                if (info.favorRank != null) {
                    info.extraFavorItems = info.favorRank.extra_items_per_boss;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }

    public static LootInfo ofPlayer(PlayerEntity player) {
        LootInfo info = new LootInfo(LootOrigin.PLAYER);
        info.world = player.level;
        info.pos = player.blockPosition();
        info.setupAllFields();
        return info;
    }

    public static LootInfo ofChestLoot(PlayerEntity player, BlockPos pos) {
        LootInfo info = new LootInfo(LootOrigin.CHEST);
        info.player = player;
        info.world = player.level;
        info.pos = pos;
        info.multi = 1.5F;
        info.maxItems = 7;
        info.setupAllFields();

        if (info.favorRank != null) {
            info.extraFavorItems = info.favorRank.extra_items_per_chest;
        }

        return info;
    }

    public static LootInfo ofDummyForClient(int level) {
        LootInfo info = new LootInfo(LootOrigin.OTHER);
        info.level = level;
        info.setupAllFields();
        return info;
    }

    public static LootInfo ofLevel(int level) {
        LootInfo info = new LootInfo(LootOrigin.OTHER);
        info.level = level;
        info.setupAllFields();
        return info;
    }

    public static LootInfo ofLockedChestItem(PlayerEntity player, int level) {
        LootInfo info = new LootInfo(LootOrigin.LOOT_CRATE);
        info.player = player;
        info.world = player.level;
        info.pos = player.blockPosition();
        info.level = level;
        info.multi = 5;
        info.minItems = 3;
        info.maxItems = 6;
        info.setupAllFields();

        info.isMapWorld = false;
        info.diff = null;
        info.dungeon = null;
        return info;
    }

    public static LootInfo ofSpawner(PlayerEntity player, World world, BlockPos pos) {
        LootInfo info = new LootInfo(LootOrigin.OTHER);
        info.world = world;
        info.pos = pos;
        info.player = player;
        info.setupAllFields();
        info.maxItems = 1;
        return info;
    }

    private void setupAllFields() {
        // order matters
        errorIfClient();
        setWorld();
        setDifficulty();
        setLevel();
        setFavor();

        if (player != null) {
            playerData = Load.Unit(player);
        }
    }

    private void setFavor() {
        if (player != null) {
            rpgData = Load.playerRPGData(player);
            favorRank = rpgData.favor
                .getRank();

            if (ServerContainer.get().ENABLE_FAVOR_SYSTEM.get()) {
                if (lootOrigin != LootOrigin.CHEST) {
                    if (favorRank.favor_drain_per_item > 0) {
                        this.maxItems = (int) (rpgData.favor.getFavor() * favorRank.favor_drain_per_item);
                        if (minItems > maxItems) {
                            minItems = maxItems;
                        }
                    }
                }
            }
        }
    }

    private LootInfo setDifficulty() {

        if (world != null && pos != null) {
            if (WorldUtils.isMapWorldClass(world)) {
                this.diff = Load.dungeonData(world).data.get(pos).data.getDifficulty();
            }
        }
        return this;

    }

    private void setLevel() {

        if (dungeon != null) {
            this.level = dungeon.lv;
        } else {
            if (level <= 0) {
                if (mobData != null) {
                    level = mobData.getLevel();
                } else {
                    level = LevelUtils.determineLevel(world, pos, player).level;
                }
            }
        }

        if (player != null) {
            if (MMORPG.isInfiniteDungeonsLoaded() && InfiniteDungeonWrapper.isPlayerInDungeon(player)) {
                level = Load.Unit(player)
                    .getLevel();
            }
        }

        this.tier = LevelUtils.levelToTier(level);
    }

    private void errorIfClient() {
        if (world != null && world.isClientSide) {
            throw new RuntimeException("Can't use Loot Info on client side!!!");
        }
    }

    private void setWorld() {
        if (world != null) {
            this.isMapWorld = WorldUtils.isMapWorldClass(world) && !Load.dungeonData(world).data.get(this.pos).data.isEmpty();
        }
        if (isMapWorld) {
            dungeon = Load.dungeonData(world).data.get(pos).data;
            if (dungeon == null || dungeon.isEmpty()) {
                this.level = 1; // if there's no dungeon data, dont give anything
                this.maxItems = 0;
            }
        }
    }

    public void setup(BaseLootGen gen) {

        float modifier = 1F;

        modifier += multi - 1F;

        if (mobKilled != null && mobData != null) {

            modifier += ScalingPlayerDiffData.getDMGMulti(mobData.mobScalingDiff) - 1F;

            if (this.playerData != null) {
                modifier += LootUtils.getLevelDistancePunishmentMulti(mobData.getLevel(), playerData.getLevel()) - 1F;
            }

            modifier += LootUtils.getMobHealthBasedLootMulti(mobData, mobKilled) - 1F;

            modifier += ExileDB.getEntityConfig(mobKilled, this.mobData).loot_multi - 1F;
            modifier += mobData.getUnit()
                .getCalculatedStat(ExtraMobDropsStat.getInstance())
                .getMultiplier() - 1;
            modifier += ExileDB.MobRarities()
                .get(mobData.getRarity())
                .LootMultiplier() - 1;

        }

        if (this.playerData != null) {
            if (lootOrigin != LootOrigin.LOOT_CRATE) {
                if (this.lootOrigin == LootOrigin.CHEST) {
                    modifier += playerData.getUnit()
                        .getCalculatedStat(ItemFind.getInstance())
                        .getMultiplier() - 1F;
                }
            }
        }

        if (world != null) {
            modifier += ExileDB.getDimensionConfig(world).all_drop_multi - 1F;
        }

        if (isMapWorld) {
            if (dungeon != null && !dungeon.isEmpty()) {
                modifier *= dungeon.team.lootMulti;

                modifier *= dungeon.getDifficulty().loot_multi;

            }
        }

        float chance = gen.baseDropChance() * modifier;

        chance = ExileEvents.SETUP_LOOT_CHANCE.callEvents(new ExileEvents.OnSetupLootChance(mobKilled, player, chance)).lootChance;

        amount = LootUtils.WhileRoll(chance);

    }

}

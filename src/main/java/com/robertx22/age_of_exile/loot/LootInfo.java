package com.robertx22.age_of_exile.loot;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.capability.player.PlayerFavor;
import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.loot.generators.BaseLootGen;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LootInfo {

    private LootInfo(LootOrigin lootOrigin) {
        this.lootOrigin = lootOrigin;
    }

    public enum LootOrigin {
        CHEST, MOB, PLAYER, OTHER;
    }

    public int amount = 0;
    public int tier = 0;
    public int level = 0;

    public LootOrigin lootOrigin;
    public UnitData mobData;
    public UnitData playerData;
    public LivingEntity mobKilled;
    public PlayerEntity player;
    public World world;
    public float multi = 1;
    private int minItems = 0;
    private int maxItems = 50;
    public boolean isMapWorld = false;
    public FavorRank favorRank;
    public PlayerFavor favor;
    public BlockPos pos;

    public int getMinItems() {
        return minItems;
    }

    public int getMaxItems() {
        return maxItems;
    }

    public static LootInfo ofMobKilled(PlayerEntity player, LivingEntity mob) {

        LootInfo info = new LootInfo(LootOrigin.MOB);

        info.world = mob.world;
        info.mobData = Load.Unit(mob);
        info.playerData = Load.Unit(player);
        info.mobKilled = mob;
        info.player = player;
        info.pos = mob.getBlockPos();
        info.level = info.mobData.getLevel();

        info.setupAllFields();
        return info;
    }

    public static LootInfo ofPlayer(PlayerEntity player) {
        LootInfo info = new LootInfo(LootOrigin.PLAYER);
        info.world = player.world;
        info.pos = player.getBlockPos();
        info.level = LevelUtils.determineLevel(player.world, player.getBlockPos(), player);
        info.setupAllFields();
        return info;
    }

    public static LootInfo ofChestLoot(PlayerEntity player, BlockPos pos) {
        LootInfo info = new LootInfo(LootOrigin.CHEST);
        info.player = player;
        info.world = player.world;
        info.pos = pos;
        info.level = LevelUtils.determineLevel(player.world, pos, player);
        info.multi = 2;
        info.setupAllFields();
        return info;
    }

    public static LootInfo ofSpawner(PlayerEntity player, World world, BlockPos pos) {
        LootInfo info = new LootInfo(LootOrigin.OTHER);
        info.world = world;
        info.pos = pos;
        info.player = player;
        info.level = LevelUtils.determineLevel(world, pos, PlayerUtils.nearestPlayer((ServerWorld) world, new Vec3d(pos.getX(), pos.getY(), pos.getZ())));
        info.setupAllFields();
        info.maxItems = 3;
        return info;
    }

    private void setupAllFields() {
        // order matters
        errorIfClient();
        setWorld();
        setTier();
        setLevel();
        setFavor();
    }

    private void setFavor() {
        if (player != null) {
            favor = Load.favor(player);
            favorRank = favor
                .getRank();

            if (lootOrigin != LootOrigin.CHEST) {
                if (favorRank.favor_drain_per_item > 0) {
                    this.maxItems = (int) (favor.getFavor() * favorRank.favor_drain_per_item);
                    if (minItems > maxItems) {
                        minItems = maxItems;
                    }
                }
            }
        }
    }

    private LootInfo setTier() {

        if (this.mobData != null) {
            this.tier = mobData.getTier();
        } else {
            this.tier = 0;
        }
        return this;

    }

    private void setLevel() {
        if (mobData != null) {
            level = mobData.getLevel();
        } else {
            level = LevelUtils.determineLevel(world, pos, player);
        }
    }

    private void errorIfClient() {
        if (world != null && world.isClient) {
            throw new RuntimeException("Can't use Loot Info on client side!!!");
        }
    }

    private void setWorld() {
        if (world != null) {
            this.isMapWorld = WorldUtils.isMapWorld(world);
        }
    }

    public void setup(BaseLootGen gen) {

        float modifier = 1F;

        modifier += multi - 1F;

        if (mobKilled != null && mobData != null) {

            if (this.playerData != null) {
                modifier += LootUtils.getLevelDistancePunishmentMulti(mobData, playerData) - 1F;
            }

            modifier += LootUtils.getMobHealthBasedLootMulti(mobData, mobKilled) - 1F;

            modifier += SlashRegistry.getEntityConfig(mobKilled, this.mobData).loot_multi - 1F;
            modifier += mobData.getUnit()
                .getCalculatedStat(ExtraMobDropsStat.getInstance())
                .getMultiplier() - 1;
            modifier += SlashRegistry.MobRarities()
                .get(mobData.getRarity())
                .LootMultiplier() - 1;

        }

        if (this.playerData != null) {
            if (this.lootOrigin == LootOrigin.CHEST) {
                modifier += playerData.getUnit()
                    .getCalculatedStat(TreasureQuantity.getInstance())
                    .getMultiplier() - 1F;
            }
        }

        if (world != null) {
            modifier += SlashRegistry.getDimensionConfig(world).all_drop_multi - 1F;
        }

        float chance = gen.baseDropChance() * modifier;

        chance = ExileEvents.SETUP_LOOT_CHANCE.callEvents(new ExileEvents.OnSetupLootChance(mobKilled, player, chance)).lootChance;

        amount = LootUtils.WhileRoll(chance);

        amount = MathHelper.clamp(amount, minItems, maxItems);

        if (favor != null) {
            favor.afterLootingItems(this);
        }
    }

}

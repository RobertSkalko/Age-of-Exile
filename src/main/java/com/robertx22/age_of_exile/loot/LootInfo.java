package com.robertx22.age_of_exile.loot;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.stats.types.loot.IncreasedItemQuantity;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LootInfo {

    public int amount = 0;
    public int tier = 0;
    public int level = 0;

    public UnitData mobData;
    public UnitData playerData;
    public LivingEntity victim;
    public PlayerEntity killer;
    public World world;
    public float multi = 1;
    public int minItems = 0;
    public int maxItems = 50;
    public boolean isMapWorld = false;

    public boolean isChestLoot = false;

    public BlockPos pos;

    public LootInfo setMaximum(int max) {
        this.maxItems = max;
        return this;
    }

    public LootInfo setMinimum(int min) {
        this.minItems = min;
        return this;
    }

    public LootInfo setMulti(float multi) {
        this.multi = multi;
        return this;
    }

    private LootInfo() {

    }

    public static LootInfo ofMobKilled(PlayerEntity player, LivingEntity mob) {

        LootInfo info = new LootInfo();

        info.world = mob.world;
        info.mobData = Load.Unit(mob);
        info.playerData = Load.Unit(player);
        info.victim = mob;
        info.killer = player;
        info.pos = mob.getBlockPos();
        info.level = info.mobData.getLevel();

        info.setupAllFields();
        return info;
    }

    public static LootInfo ofBlockPosition(World world, BlockPos pos) {

        LootInfo info = new LootInfo();

        info.world = world;
        info.pos = pos;
        info.level = LevelUtils.determineLevel(world, pos, PlayerUtils.nearestPlayer((ServerWorld) world, new Vec3d(pos.getX(), pos.getY(), pos.getZ())));

        info.setupAllFields();

        return info;
    }

    private void setupAllFields() {
        // order matters
        errorIfClient();
        setWorld();
        setTier();
        setLevel();
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
            level = LevelUtils.determineLevel(world, pos, killer);
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

        float modifier = 1;

        modifier += multi - 1;

        if (victim != null && mobData != null) {
            modifier += SlashRegistry.getEntityConfig(victim, this.mobData).loot_multi - 1;
            modifier += mobData.getUnit()
                .peekAtStat(ExtraMobDropsStat.getInstance())
                .getMultiplier() - 1;
            modifier += Rarities.Mobs.get(mobData.getRarity())
                .LootMultiplier() - 1;

        }

        if (this.playerData != null) {
            modifier += playerData.getUnit()
                .peekAtStat(IncreasedItemQuantity.getInstance())
                .getMultiplier() - 1;
        }

        if (world != null) {
            modifier += SlashRegistry.getDimensionConfig(world).all_drop_multi - 1;

        }

        if (mobData != null && victim != null) {
            modifier += LootUtils.getMobHealthBasedLootMulti(mobData, victim) - 1;

            if (this.playerData != null) {
                modifier += LootUtils.getLevelDistancePunishmentMulti(mobData, playerData) - 1;
            }
        }

        float chance = gen.baseDropChance() * modifier;

        chance = ExileEvents.SETUP_LOOT_CHANCE.callEvents(new ExileEvents.OnSetupLootChance(victim, killer, chance)).lootChance;

        if (minItems > 0) {
            if (chance <= 5) {
                chance += 5;
            }
        }

        amount = LootUtils.WhileRoll(chance);
    }

}

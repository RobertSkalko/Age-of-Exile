package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
import com.robertx22.age_of_exile.vanilla_mc.blocks.MNSCraftingTableBlock;
import com.robertx22.library_of_exile.main.ForgeEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ScalingDifficultyEvents {

    public static void register() {

        ForgeEvents.registerForgeEvent(PlayerInteractEvent.class, event -> {
            if (event.getWorld().isClientSide) {
                if (event.getWorld()
                    .getBlockState(event.getPos())
                    .is(SlashBlocks.MNS_CRAFTING_TABLE.get())) {
                    MNSCraftingTableBlock.IS_USING = true;
                } else {
                    MNSCraftingTableBlock.IS_USING = false;
                }
            }
        });

        ForgeEvents.registerForgeEvent(TickEvent.PlayerTickEvent.class, event -> {
            if (event.phase == TickEvent.Phase.END) {
                if (event.player.tickCount % 20 == 0) {
                    if (!event.player.level.isClientSide) {
                        Load.playerRPGData(event.player).scalingDifficulty.add(ServerContainer.get().DIFFICULTY_PER_SECOND.get()
                            .floatValue());
                        Load.playerRPGData(event.player).scalingDifficulty.tickDeathCooldown(20);
                    }
                }
            }
        });

        ForgeEvents.registerForgeEvent(LivingDeathEvent.class, event -> {
            try {
                if (event.getEntityLiving().level.isClientSide) {
                    return;
                }
                if (EntityTypeUtils.isMob(event.getEntity())) {
                    if (event.getSource() != null && event.getSource()
                        .getEntity() instanceof PlayerEntity) {
                        Load.playerRPGData((PlayerEntity) event.getSource()
                            .getEntity()).scalingDifficulty.add(ServerContainer.get().DIFFICULTY_PER_HOSTILE_KILL.get()
                            .floatValue());

                    }
                }
                if (event.getEntityLiving() instanceof PlayerEntity) {
                    if (Load.playerRPGData((PlayerEntity) event.getEntityLiving()).scalingDifficulty.canAddDeathDifficulty()) {
                        Load.playerRPGData((PlayerEntity) event.getEntityLiving()).scalingDifficulty.add(ServerContainer.get().DIFFICULTY_PER_DEATH.get()
                            .floatValue());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}

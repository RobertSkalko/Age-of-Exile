package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.capability.player.data.PlayerDeathData;
import com.robertx22.age_of_exile.dimension.dungeon_data.SingleDungeonData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class OnPlayerDeath extends EventConsumer<ExileEvents.OnPlayerDeath> {

    @Override
    public void accept(ExileEvents.OnPlayerDeath event) {
        try {

            Load.Unit(event.player)
                .onDeath();

            Load.Unit(event.player)
                .setEquipsChanged(true);

            PlayerDeathData data = ModRegistry.COMPONENTS.PLAYER_DEATH_DATA.get(event.player);
            data.deathStats.died = true;

            Packets.sendToClient(event.player, new SyncCapabilityToClient(event.player, PlayerCaps.DEATH_STATS));

            if (WorldUtils.isMapWorldClass(event.player.world)) {

                SingleDungeonData dungeon = Load.dungeonData(event.player.world).data.get(event.player.getBlockPos());

                if (!dungeon.data.isEmpty()) {

                    int favorloss = dungeon.data.getDifficulty().death_favor_penalty;

                    Load.favor(event.player)
                        .addFavor(favorloss);

                    event.player.sendMessage(new LiteralText("You lost " + Math.abs(favorloss) + " favor.").formatted(Formatting.RED), false);

                    dungeon.data.onDeath(event.player);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


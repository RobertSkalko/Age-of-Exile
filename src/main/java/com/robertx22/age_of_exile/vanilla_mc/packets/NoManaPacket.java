package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.event_hooks.ontick.OnClientTick;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;

public class NoManaPacket extends MyPacket<NoManaPacket> {

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "nomana");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {

    }

    @Override
    public void saveToData(PacketBuffer tag) {

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        if (ModConfig.get().client.SHOW_LOW_ENERGY_MANA_WARNING) {
            if (OnClientTick.canSoundNoMana()) {
                OnClientTick.setNoManaSoundCooldown();
                PlayerEntity player = ctx.getPlayer();
                player.playSound(SoundEvents.REDSTONE_TORCH_BURNOUT, 0.5F, 0);
            }
        }
    }

    @Override
    public MyPacket<NoManaPacket> newInstance() {
        return new NoManaPacket();
    }

    public NoManaPacket() {
    }

}
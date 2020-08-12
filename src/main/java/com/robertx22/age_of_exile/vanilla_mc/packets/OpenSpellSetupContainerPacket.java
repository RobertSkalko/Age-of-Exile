package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class OpenSpellSetupContainerPacket extends MyPacket<OpenSpellSetupContainerPacket> {

    public OpenSpellSetupContainerPacket() {

    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "reqopenspellpickup");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {

    }

    @Override
    public void saveToData(PacketByteBuf tag) {

    }

    @Override
    public void onReceived(PacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();
        ContainerProviderRegistry.INSTANCE.openContainer(ModRegistry.CONTAINERS.HOTBAR_SETUP, player, buf -> buf.writeInt(5));
    }

    @Override
    public MyPacket<OpenSpellSetupContainerPacket> newInstance() {
        return new OpenSpellSetupContainerPacket();
    }
}

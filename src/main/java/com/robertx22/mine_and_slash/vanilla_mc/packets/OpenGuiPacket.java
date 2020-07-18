package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.vanilla_mc.packets.proxies.OpenGuiWrapper;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class OpenGuiPacket extends MyPacket<OpenGuiPacket> {

    public static OpenGuiPacket EMPTY = new OpenGuiPacket();

    public enum GuiType {
        TALENTS,
        PICK_STATS,
        SPELLS,
        MAIN_HUB
    }

    GuiType type;

    private OpenGuiPacket() {

    }

    public OpenGuiPacket(GuiType type) {
        this.type = type;
    }

    @Override
    public OpenGuiPacket loadFromData(PacketByteBuf buf) {
        OpenGuiPacket newpkt = new OpenGuiPacket();
        newpkt.type = GuiType.valueOf(buf.readString(20));
        return newpkt;
    }

    @Override
    public void toData(OpenGuiPacket openGuiPacket, PacketByteBuf buf) {
        buf.writeString(openGuiPacket.type.name(), 20);
    }

    @Override
    public void onReceived(PacketContext ctx, OpenGuiPacket data) {
        if (data.type == GuiType.MAIN_HUB) {
            OpenGuiWrapper.openMainHub();
        }
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "opengui");
    }

}
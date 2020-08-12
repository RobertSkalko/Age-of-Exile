package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.packets.proxies.OpenGuiWrapper;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class OpenGuiPacket extends MyPacket<OpenGuiPacket> {

    public static OpenGuiPacket EMPTY = new OpenGuiPacket();

    public enum GuiType {
        TALENTS,
        PICK_STATS,
        SPELLS,
        MAIN_HUB
    }

    GuiType type;

    public OpenGuiPacket() {

    }

    public OpenGuiPacket(GuiType type) {
        this.type = type;
    }

    @Override
    public void loadFromData(PacketByteBuf buf) {
        type = GuiType.valueOf(buf.readString(20));
    }

    @Override
    public void saveToData(PacketByteBuf buf) {
        buf.writeString(type.name(), 20);
    }

    @Override
    public void onReceived(PacketContext ctx) {
        if (type == GuiType.MAIN_HUB) {
            OpenGuiWrapper.openMainHub();
        }
    }

    @Override
    public MyPacket<OpenGuiPacket> newInstance() {
        return new OpenGuiPacket();
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "opengui");
    }

}
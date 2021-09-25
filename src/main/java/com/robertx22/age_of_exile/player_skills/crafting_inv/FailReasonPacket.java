package com.robertx22.age_of_exile.player_skills.crafting_inv;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class FailReasonPacket extends MyPacket<FailReasonPacket> {

    ITextComponent text;

    public FailReasonPacket() {

    }

    public FailReasonPacket(ITextComponent text) {
        this.text = text;
    }

    @Override
    public void loadFromData(PacketBuffer buf) {
        text = buf.readComponent();
    }

    @Override
    public void saveToData(PacketBuffer buf) {
        buf.writeComponent(text);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        ProfCraftScreen.FAIL_REASON = this.text;
    }

    @Override
    public MyPacket<FailReasonPacket> newInstance() {
        return new FailReasonPacket();
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "failreason");
    }

}
package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.capability.player.data.OnePlayerCharData;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class CharSelectPackets extends MyPacket<CharSelectPackets> {

    public enum Action {DELETE, LOAD}

    public int num;
    public Action action;

    public CharSelectPackets() {

    }

    public CharSelectPackets(int charNumber, Action act) {
        this.num = charNumber;
        this.action = act;

    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "char_select");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        num = tag.readInt();
        action = tag.readEnumConstant(Action.class);
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeInt(num);
        tag.writeEnumConstant(action);
    }

    @Override
    public void onReceived(PacketContext ctx) {
        PlayerEntity p = ctx.getPlayer();

        if (action == Action.DELETE) {
            OnePlayerCharData data = Load.characters(p).data.characters.get(num);
            if (data.gearIsEmpty()) {
                Load.characters(p).data.characters.remove(num);
            } else {
                ctx.getPlayer()
                    .sendMessage(new LiteralText("You can't delete a character that is wearing gear."), false);
            }
        } else if (action == Action.LOAD) {
            Load.characters(p).data.load(num, p);
        }
    }

    @Override
    public MyPacket<CharSelectPackets> newInstance() {
        return new CharSelectPackets();
    }
}

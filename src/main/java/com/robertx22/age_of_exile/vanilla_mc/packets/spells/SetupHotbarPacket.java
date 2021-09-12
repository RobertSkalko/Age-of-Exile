package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SetupHotbarPacket extends MyPacket<SetupHotbarPacket> {

    int number;
    String spell;

    public SetupHotbarPacket(Spell spell, int number) {
        this.number = number;
        this.spell = spell.GUID();
    }

    public SetupHotbarPacket() {
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "setup_hotbar");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        this.number = tag.readInt();
        this.spell = tag.readString(111);
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeInt(number);
        tag.writeString(spell, 111);
    }

    @Override
    public void onReceived(PacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();

        EntitySpellCap.ISpellsCap spells = Load.spells(player);

        spells.getSkillGemData().hotbars.put(number, spell);

    }

    @Override
    public MyPacket<SetupHotbarPacket> newInstance() {
        return new SetupHotbarPacket();
    }
}

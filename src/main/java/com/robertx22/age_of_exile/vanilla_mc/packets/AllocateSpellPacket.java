package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class AllocateSpellPacket extends MyPacket<AllocateSpellPacket> {

    public String spell;
    AllocateSpellPacket.ACTION action;

    public enum ACTION {
        ALLOCATE, REMOVE
    }

    public AllocateSpellPacket() {

    }

    public AllocateSpellPacket(Spell spell, ACTION action) {
        this.spell = spell.GUID();
        this.action = action;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "spell_alloc");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        spell = tag.readString(100);
        action = tag.readEnumConstant(AllocateSpellPacket.ACTION.class);

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(spell, 100);
        tag.writeEnumConstant(action);

    }

    @Override
    public void onReceived(PacketContext ctx) {

        EntitySpellCap.ISpellsCap spells = Load.spells(ctx.getPlayer());

        if (spells.getFreeSpellPoints() > 0) {
            int current = spells.getSkillGemData().allocated_lvls.getOrDefault(spell, 0);
            spells.getSkillGemData().allocated_lvls.put(spell, current + 1);
        }

    }

    @Override
    public MyPacket<AllocateSpellPacket> newInstance() {
        return new AllocateSpellPacket();
    }
}


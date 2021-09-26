package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class AllocateSpellPacket extends MyPacket<AllocateSpellPacket> {

    public String spellid;
    public String schoolid;
    AllocateSpellPacket.ACTION action;

    public enum ACTION {
        ALLOCATE, REMOVE
    }

    public AllocateSpellPacket() {

    }

    public AllocateSpellPacket(SpellSchool school, Spell spell, ACTION action) {
        this.spellid = spell.GUID();
        this.schoolid = school.GUID();
        this.action = action;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "spell_alloc");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        spellid = tag.readUtf(100);
        schoolid = tag.readUtf(100);
        action = tag.readEnum(AllocateSpellPacket.ACTION.class);

    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeUtf(spellid, 100);
        tag.writeUtf(schoolid, 100);
        tag.writeEnum(action);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        EntitySpellCap.ISpellsCap spells = Load.spells(ctx.getPlayer());

        Spell spell = ExileDB.Spells()
            .get(this.spellid);
        SpellSchool school = ExileDB.SpellSchools()
            .get(this.schoolid);

        if (spells.canLearn(school, spell)) {
            spells.getSpellsData()
                .learnSpell(spell, school);
        }

        spells.syncToClient(ctx.getPlayer());

    }

    @Override
    public MyPacket<AllocateSpellPacket> newInstance() {
        return new AllocateSpellPacket();
    }
}


package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

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
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "spell_alloc");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        spellid = tag.readString(100);
        schoolid = tag.readString(100);
        action = tag.readEnumConstant(AllocateSpellPacket.ACTION.class);

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(spellid, 100);
        tag.writeString(schoolid, 100);
        tag.writeEnumConstant(action);

    }

    @Override
    public void onReceived(PacketContext ctx) {

        EntitySpellCap.ISpellsCap spells = Load.spells(ctx.getPlayer());

        Spell spell = ExileDB.Spells()
            .get(this.spellid);
        SpellSchool school = ExileDB.SpellSchools()
            .get(this.schoolid);

        if (spells.canLearn(school, spell)) {
            spells.getSpellsData()
                .learnSpell(spell, school);
        }

    }

    @Override
    public MyPacket<AllocateSpellPacket> newInstance() {
        return new AllocateSpellPacket();
    }
}


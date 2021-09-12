package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.synergy.Synergy;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class AllocateSynergyPacket extends MyPacket<AllocateSynergyPacket> {

    public String synId;
    public String schoolid;
    AllocateSynergyPacket.ACTION action;

    public enum ACTION {
        ALLOCATE, REMOVE
    }

    public AllocateSynergyPacket() {

    }

    public AllocateSynergyPacket(SpellSchool school, Synergy synergy, AllocateSynergyPacket.ACTION action) {
        this.synId = synergy.GUID();
        this.schoolid = school.GUID();
        this.action = action;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "syn_aloc");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        synId = tag.readString(100);
        schoolid = tag.readString(100);
        action = tag.readEnumConstant(AllocateSynergyPacket.ACTION.class);

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(synId, 100);
        tag.writeString(schoolid, 100);
        tag.writeEnumConstant(action);

    }

    @Override
    public void onReceived(PacketContext ctx) {

        EntitySpellCap.ISpellsCap spells = Load.spells(ctx.getPlayer());

        Synergy synergy = ExileDB.Synergies()
            .get(this.synId);
        SpellSchool school = ExileDB.SpellSchools()
            .get(this.schoolid);

        if (spells.canLearn(school, synergy)) {
            spells.getSpellsData()
                .learnSynergy(synergy, school);
        }

    }

    @Override
    public MyPacket<AllocateSynergyPacket> newInstance() {
        return new AllocateSynergyPacket();
    }
}


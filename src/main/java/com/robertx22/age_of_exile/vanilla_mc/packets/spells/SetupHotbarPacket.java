package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

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
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "setup_hotbar");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        this.number = tag.readInt();
        this.spell = tag.readUtf(111);
    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeInt(number);
        tag.writeUtf(spell, 111);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();

        EntitySpellCap.ISpellsCap spells = Load.spells(player);

        if (spells.hasSpell(ExileDB.Spells()
            .get(spell))) {
            spells.getSpellsData().hotbars.put(number, spell);
            spells.syncToClient(player);
        }
    }

    @Override
    public MyPacket<SetupHotbarPacket> newInstance() {
        return new SetupHotbarPacket();
    }
}

package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class HotbarSetupPacket extends MyPacket<HotbarSetupPacket> {

    public int number;
    public String spell;

    public HotbarSetupPacket() {

    }

    public HotbarSetupPacket(Spell spell, int num) {
        this.number = num;
        this.spell = spell.GUID();
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "hotbarsetup");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        number = tag.readInt();
        spell = tag.readString(30);

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeInt(number);
        tag.writeString(spell, 30);

    }

    @Override
    public void onReceived(PacketContext ctx) {

        PlayerEntity player = ctx.getPlayer();

        SpellCastingData data = Load.spells(player)
            .getCastingData();

        for (Map.Entry<Integer, String> entry : new HashMap<>(data.getBar()).entrySet()) {
            if (entry.getValue()
                .equals(this.spell)) {
                data.getBar()
                    .put(entry.getKey(), ""); // no duplicate spells on hotbar
            }
        }

        data.getBar()
            .put(this.number, this.spell);

        Packets.sendToClient(player, new SyncCapabilityToClient(player, PlayerCaps.SPELLS));

    }

    @Override
    public MyPacket<HotbarSetupPacket> newInstance() {
        return new HotbarSetupPacket();
    }
}

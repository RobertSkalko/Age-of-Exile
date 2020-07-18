package com.robertx22.mine_and_slash.vanilla_mc.packets.spells;

import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.saveclasses.spells.SpellCastingData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CastSpellPacket {

    public Integer hotbarNumber;
    public SpellCastingData.Hotbar hotbar;

    public CastSpellPacket() {

    }

    public CastSpellPacket(Integer hotbarNumber, SpellCastingData.Hotbar bar) {
        this.hotbarNumber = hotbarNumber;
        this.hotbar = bar;

    }

    public static CastSpellPacket decode(PacketByteBuf buf) {

        CastSpellPacket newpkt = new CastSpellPacket();

        newpkt.hotbarNumber = buf.readInt();
        newpkt.hotbar = SpellCastingData.Hotbar.valueOf(buf.readString(30));

        return newpkt;

    }

    public static void encode(CastSpellPacket packet, PacketByteBuf tag) {

        tag.writeInt(packet.hotbarNumber);
        tag.writeString(packet.hotbar.name());

    }

    public static void handle(final CastSpellPacket pkt, Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(() -> {
                try {

                    ServerPlayerEntity player = ctx.get()
                        .getSender();

                    PlayerSpellCap.ISpellsCap spells = Load.spells(player);

                    if (spells.getCastingData()
                        .canCast(pkt.hotbarNumber, pkt.hotbar, player)) {
                        spells.getCastingData()
                            .setToCast(pkt.hotbarNumber, pkt.hotbar, player, 0);

                        BaseSpell spell = spells.getCastingData()
                            .getSpellBeingCast();
                        if (spell != null) {

                            SpellCastContext c = new SpellCastContext(player, 0, spell);

                            spell.spendResources(c);
                        }

                        spells.syncToClient(player);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        ctx.get()
            .setPacketHandled(true);
    }

}

package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class TellServerToCastSpellPacket extends MyPacket<TellServerToCastSpellPacket> {

    int number;

    public TellServerToCastSpellPacket(int number) {
        this.number = number;
    }

    public TellServerToCastSpellPacket() {
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "tell_server_castspell");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        this.number = tag.readInt();
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeInt(number);
    }

    @Override
    public void onReceived(PacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();

        EntitySpellCap.ISpellsCap spells = Load.spells(player);

        if (player.isBlocking()) {
            return;
        }

        Spell spell = spells.getSpellByNumber(number);

        if (spell != null) {

            if (spells.getCastingData()
                .canCast(spell, player)) {

                spells.getCastingData()
                    .setToCast(spell, player);
                SpellCastContext c = new SpellCastContext(player, 0, spell);

                spell.spendResources(c);

            }
            spells.syncToClient(player);
        }
    }

    @Override
    public MyPacket<TellServerToCastSpellPacket> newInstance() {
        return new TellServerToCastSpellPacket();
    }
}

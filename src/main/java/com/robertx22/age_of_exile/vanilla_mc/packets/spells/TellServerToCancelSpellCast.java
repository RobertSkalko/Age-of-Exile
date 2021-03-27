package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class TellServerToCancelSpellCast extends MyPacket<TellServerToCancelSpellCast> {

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "cancelspell");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {

    }

    @Override
    public void saveToData(PacketByteBuf tag) {

    }

    @Override
    public void onReceived(PacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();

        PlayerSpellCap.ISpellsCap spells = Load.spells(player);

        if (spells.getCastingData()
            .getSpellBeingCast() != null) {

            SpellCastContext sctx = new SpellCastContext(player, spells.getCastingData().castingTicksDone, spells.getCastingData()
                .getSpellBeingCast());

            spells.getCastingData()
                .tryCast(player, spells, sctx);
            spells.getCastingData()
                .cancelCast(player);

            spells.syncToClient(player);
        }
    }

    @Override
    public MyPacket<TellServerToCancelSpellCast> newInstance() {
        return new TellServerToCancelSpellCast();
    }
}


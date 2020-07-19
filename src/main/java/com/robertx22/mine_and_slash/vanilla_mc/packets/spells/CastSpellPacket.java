package com.robertx22.mine_and_slash.vanilla_mc.packets.spells;

import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.spells.SpellCastingData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.packets.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class CastSpellPacket extends MyPacket<CastSpellPacket> {

    public Integer hotbarNumber;
    public SpellCastingData.Hotbar hotbar;

    public CastSpellPacket() {

    }

    public CastSpellPacket(Integer hotbarNumber, SpellCastingData.Hotbar bar) {
        this.hotbarNumber = hotbarNumber;
        this.hotbar = bar;

    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "castspell");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        hotbarNumber = tag.readInt();
        hotbar = SpellCastingData.Hotbar.valueOf(tag.readString(30));

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeInt(hotbarNumber);
        tag.writeString(hotbar.name());

    }

    @Override
    public void onReceived(PacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();

        PlayerSpellCap.ISpellsCap spells = Load.spells(player);

        if (spells.getCastingData()
            .canCast(hotbarNumber, hotbar, player)) {
            spells.getCastingData()
                .setToCast(hotbarNumber, hotbar, player, 0);

            BaseSpell spell = spells.getCastingData()
                .getSpellBeingCast();
            if (spell != null) {

                SpellCastContext c = new SpellCastContext(player, 0, spell);

                spell.spendResources(c);
            }

            spells.syncToClient(player);
        }
    }

    @Override
    public MyPacket<CastSpellPacket> newInstance() {
        return new CastSpellPacket();
    }
}

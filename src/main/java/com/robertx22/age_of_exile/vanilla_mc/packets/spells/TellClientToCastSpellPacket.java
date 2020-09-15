package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class TellClientToCastSpellPacket extends MyPacket<TellClientToCastSpellPacket> {

    public String spellid = "";

    public TellClientToCastSpellPacket(Spell spell) {
        this.spellid = spell.GUID();
    }

    public TellClientToCastSpellPacket() {
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "castspell");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        this.spellid = tag.readString(30);
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(spellid);
    }

    @Override
    public void onReceived(PacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();

        Spell spell = SlashRegistry.Spells()
            .get(spellid);
        SpellCastContext c = new SpellCastContext(player, 0, spell);

        spell.cast(c);

    }

    @Override
    public MyPacket<TellClientToCastSpellPacket> newInstance() {
        return new TellClientToCastSpellPacket();
    }
}

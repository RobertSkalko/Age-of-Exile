package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class TellServerToCastSpellPacket extends MyPacket<TellServerToCastSpellPacket> {

    int number;

    public TellServerToCastSpellPacket(int number) {
        this.number = number;
    }

    public TellServerToCastSpellPacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "tell_server_castspell");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        this.number = tag.readInt();
    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeInt(number);
    }

    public static boolean tryCastSpell(PlayerEntity player, Spell spell) {

        EntitySpellCap.ISpellsCap spells = Load.spells(player);

        if (player.isBlocking() || player.swinging) {
            return false;
        }

        if (spell != null) {

            if (spells.getCastingData()
                .canCast(spell, player)) {

                spells.getCastingData()
                    .setToCast(spell, player);
                SpellCastContext c = new SpellCastContext(player, 0, spell);

                spell.spendResources(c);
                spells.syncToClient(player);

                return true;
            }

        }
        return false;
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();

        EntitySpellCap.ISpellsCap spells = Load.spells(player);

        Spell spell = spells.getSpellByNumber(number);

        tryCastSpell(player, spell);

    }

    @Override
    public MyPacket<TellServerToCastSpellPacket> newInstance() {
        return new TellServerToCastSpellPacket();
    }
}

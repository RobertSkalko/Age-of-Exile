package com.robertx22.mine_and_slash.vanilla_mc.packets.spells;

import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.packets.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class CastSpellPacket extends MyPacket<CastSpellPacket> {

    public String spellid = "";

    public CastSpellPacket(PlayerEntity player) {
        BaseSpell cspell = Load.spells(player)
            .getCurrentRightClickSpell();
        if (cspell != null) {
            this.spellid = cspell.GUID();
        }
    }

    public CastSpellPacket() {
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

        PlayerSpellCap.ISpellsCap spells = Load.spells(player);

        BaseSpell spell = SlashRegistry.Spells()
            .get(this.spellid);

        if (spell != null) {

            if (spells.getCastingData()
                .canCast(spell, player)) {

                spells.getCastingData()
                    .setToCast(spell, player);

                SpellCastContext c = new SpellCastContext(player, 0, spells.getCastingData()
                    .getSkillGem(spell.GUID()));
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

package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class TellClientToCastSpellPacket extends MyPacket<TellClientToCastSpellPacket> {

    public String spellid = "";
    public int enid = 0;

    public TellClientToCastSpellPacket(LivingEntity en, Spell spell) {
        this.spellid = spell.GUID();
        this.enid = en.getId();
    }

    public TellClientToCastSpellPacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "tellclienttocastspell");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        this.spellid = tag.readUtf(30);
        this.enid = tag.readInt();
    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeUtf(spellid);
        tag.writeInt(enid);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        LivingEntity en = (LivingEntity) ctx.getPlayer().level.getEntity(enid);

        Spell spell = ExileDB.Spells()
            .get(spellid);
        SpellCastContext c = new SpellCastContext(en, 0, spell);

        spell.cast(c, true);

    }

    @Override
    public MyPacket<TellClientToCastSpellPacket> newInstance() {
        return new TellClientToCastSpellPacket();
    }
}

package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class TellClientEntityIsCastingSpellPacket extends MyPacket<TellClientEntityIsCastingSpellPacket> {

    public String spellid = "";
    public int enid = 0;

    public TellClientEntityIsCastingSpellPacket(LivingEntity en, Spell spell) {
        this.spellid = spell.GUID();
        this.enid = en.getEntityId();
    }

    public TellClientEntityIsCastingSpellPacket() {
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "tell_client_entity_is_casting_spell");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        this.spellid = tag.readString(30);
        this.enid = tag.readInt();
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(spellid);
        tag.writeInt(enid);
    }

    @Override
    public void onReceived(PacketContext ctx) {

        LivingEntity en = (LivingEntity) ctx.getPlayer().world.getEntityById(enid);

        Spell spell = Database.Spells()
            .get(spellid);
        SpellCastContext c = new SpellCastContext(en, 0, spell);

        spell.getAttached()
            .tryActivate(Spell.CASTER_NAME, SpellCtx.onTick(en, en, EntitySavedSpellData.create(c.skillGemData.lvl, en, spell)));

    }

    @Override
    public MyPacket<TellClientEntityIsCastingSpellPacket> newInstance() {
        return new TellClientEntityIsCastingSpellPacket();
    }
}

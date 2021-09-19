package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class TellClientEntityIsCastingSpellPacket extends MyPacket<TellClientEntityIsCastingSpellPacket> {

    public String spellid = "";
    public int enid = 0;

    public TellClientEntityIsCastingSpellPacket(LivingEntity en, Spell spell) {
        this.spellid = spell.GUID();
        this.enid = en.getId();
    }

    public TellClientEntityIsCastingSpellPacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "tell_client_entity_is_casting_spell");
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
    public void onReceived(Context ctx) {

        LivingEntity en = (LivingEntity) ctx.getPlayer().level.getEntity(enid);

        Spell spell = ExileDB.Spells()
            .get(spellid);
        SpellCastContext c = new SpellCastContext(en, 0, spell);

        spell.getAttached()
            .tryActivate(Spell.CASTER_NAME, SpellCtx.onTick(en, en, EntitySavedSpellData.create(Load.Unit(en)
                .getLevel(), en, spell)));

    }

    @Override
    public MyPacket<TellClientEntityIsCastingSpellPacket> newInstance() {
        return new TellClientEntityIsCastingSpellPacket();
    }
}

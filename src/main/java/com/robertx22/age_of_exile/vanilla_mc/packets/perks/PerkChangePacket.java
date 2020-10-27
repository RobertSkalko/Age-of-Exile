package com.robertx22.age_of_exile.vanilla_mc.packets.perks;

import com.robertx22.age_of_exile.capability.entity.EntityPerks;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class PerkChangePacket extends MyPacket<PerkChangePacket> {

    public String school;
    public int x;
    public int y;
    ACTION action;

    public enum ACTION {
        ALLOCATE, REMOVE
    }

    public PerkChangePacket() {

    }

    public PerkChangePacket(SpellSchool school, PointData point, ACTION action) {
        this.school = school.identifier;
        this.x = point.x;
        this.y = point.y;
        this.action = action;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "perk_change");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        school = tag.readString(30);
        x = tag.readInt();
        y = tag.readInt();
        action = tag.readEnumConstant(ACTION.class);

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(school, 30);
        tag.writeInt(x);
        tag.writeInt(y);
        tag.writeEnumConstant(action);

    }

    @Override
    public void onReceived(PacketContext ctx) {
        EntityPerks perks = Load.perks(ctx.getPlayer());
        SpellSchool sc = SlashRegistry.SpellSchools()
            .get(school);

        if (sc == null) {
            MMORPG.logError("school is null: " + this.school);
            return;
        }

        PointData point = new PointData(x, y);
        if (action == ACTION.ALLOCATE) {
            if (perks.data.canAllocate(sc, point, Load.Unit(ctx.getPlayer()), ctx.getPlayer())) {
                perks.data.allocate(ctx.getPlayer(), sc, new PointData(x, y));
            }
        } else if (action == ACTION.REMOVE) {
            if (perks.data.canRemove(sc, point)) {
                perks.data.remove(sc, new PointData(x, y));
                perks.data.reset_points--;
            }
        }

        Load.Unit(ctx.getPlayer())
            .setEquipsChanged(true);
        Load.Unit(ctx.getPlayer())
            .tryRecalculateStats(ctx.getPlayer());

        Packets.sendToClient(ctx.getPlayer(), new SyncCapabilityToClient(ctx.getPlayer(), PlayerCaps.ENTITY_PERKS));
    }

    @Override
    public MyPacket<PerkChangePacket> newInstance() {
        return new PerkChangePacket();
    }
}
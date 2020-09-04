package com.robertx22.age_of_exile.database.data.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.bases.ISerializedRegistryEntry;
import net.minecraft.advancement.Advancement;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class Perk implements ISerializedRegistryEntry<Perk>, IAutoGson<Perk> {
    public static Perk SERIALIZER = new Perk();

    public PerkType type;

    public String identifier;

    public String unlocks_spell = "";

    public String locked_under_advancement = "";

    public boolean isLockedUnderAdvancement() {
        return !locked_under_advancement.isEmpty();
    }

    public boolean didPlayerUnlockAdvancement(ServerPlayerEntity player) {
        if (!isLockedUnderAdvancement()) {
            return true;
        }
        ServerAdvancementLoader loader = player.getServer()
            .getAdvancementLoader();
        Advancement adv = loader.get(new Identifier(identifier));
        return player.getAdvancementTracker()
            .getProgress(adv)
            .isDone();
    }

    public List<OptScaleExactStat> stats = new ArrayList<>();

    public String icon = "";

    public enum PerkType {
        SPELL, STAT
    }

    @Override
    public Class<Perk> getClassForSerialization() {
        return Perk.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.PERK;
    }

    @Override
    public String GUID() {
        return identifier;
    }
}

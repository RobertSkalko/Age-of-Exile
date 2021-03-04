package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.capability.player.data.PlayerCharsData;
import com.robertx22.age_of_exile.database.data.races.PlayerRace;
import com.robertx22.age_of_exile.database.data.races.RaceLevelingPerk;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerCharCap implements ICommonPlayerCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "chars");
    private static final String LOC = "favor";

    PlayerEntity player;

    public PlayerCharCap(PlayerEntity player) {
        this.player = player;
    }

    public PlayerCharsData data = new PlayerCharsData();

    @Override
    public CompoundTag toTag(CompoundTag nbt) {
        LoadSave.Save(data, nbt, LOC);
        return nbt;
    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.CHARACTERS;
    }

    @Override
    public void fromTag(CompoundTag nbt) {
        this.data = LoadSave.Load(PlayerCharsData.class, new PlayerCharsData(), nbt, LOC);
    }

    public List<StatContext> getStats() {

        List<StatContext> list = new ArrayList<>();

        List<ExactStatData> stats = new ArrayList<>();

        for (PlayerRace race : Database.Races()
            .getList()) {
            for (RaceLevelingPerk perk : race.lvling_perks) {
                if (this.data.characters.values()
                    .stream()
                    .anyMatch(x -> x.lvl >= perk.lvl_needed)) {
                    stats.addAll(perk.stats.stream()
                        .map(s -> s.toExactStat(1))
                        .collect(Collectors.toList()));
                }
            }
        }

        list.add(new MiscStatCtx(stats));
        return list;

    }

}
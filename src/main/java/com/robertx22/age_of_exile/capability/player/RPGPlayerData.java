package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.capability.player.data.FavorData;
import com.robertx22.age_of_exile.capability.player.data.MapsData;
import com.robertx22.age_of_exile.capability.player.data.StatPointsData;
import com.robertx22.age_of_exile.capability.player.data.TeamData;
import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.DeathStatsData;
import com.robertx22.age_of_exile.saveclasses.perks.TalentsData;
import com.robertx22.age_of_exile.saveclasses.player_skills.ProfessionsData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class RPGPlayerData implements ICommonPlayerCap {

    public static final ResourceLocation RESOURCE = new ResourceLocation(Ref.MODID, "player_data");

    private static final String MAP_DATA = "maps";
    private static final String TEAM_DATA = "teams";
    private static final String FAVOR_DATA = "favor";
    private static final String TALENTS_DATA = "talents";
    private static final String STAT_POINTS = "stat_points";
    private static final String DEATH_STATS = "death_stats";
    private static final String PROFESSIONS = "profs";

    PlayerEntity player;

    public MapsData maps = new MapsData();
    public TeamData team = new TeamData();
    public FavorData favor = new FavorData();
    public TalentsData talents = new TalentsData();
    public StatPointsData statPoints = new StatPointsData();
    public DeathStatsData deathStats = new DeathStatsData();
    public ProfessionsData professions = new ProfessionsData();

    public RPGPlayerData(PlayerEntity player) {
        this.player = player;
    }

    public void createRandomDungeon(Difficulty diff) {

        this.maps = new MapsData();
        maps.isEmpty = false;

        DungeonData dun = new DungeonData();
        int lvl = Load.Unit(player)
            .getLevel();
        dun.randomize(lvl, diff);

        this.maps.dungeonData = dun;

        this.syncToClient(player);
    }

    @Override
    public CompoundNBT saveToNBT() {

        CompoundNBT nbt = new CompoundNBT();

        LoadSave.Save(maps, nbt, MAP_DATA);
        LoadSave.Save(team, nbt, TEAM_DATA);
        LoadSave.Save(favor, nbt, FAVOR_DATA);
        LoadSave.Save(talents, nbt, TALENTS_DATA);
        LoadSave.Save(statPoints, nbt, STAT_POINTS);
        LoadSave.Save(deathStats, nbt, DEATH_STATS);
        LoadSave.Save(professions, nbt, PROFESSIONS);

        return nbt;
    }

    @Override
    public void loadFromNBT(CompoundNBT nbt) {

        this.maps = loadOrBlank(MapsData.class, new MapsData(), nbt, MAP_DATA, new MapsData());
        this.team = loadOrBlank(TeamData.class, new TeamData(), nbt, TEAM_DATA, new TeamData());
        this.favor = loadOrBlank(FavorData.class, new FavorData(), nbt, FAVOR_DATA, new FavorData());
        this.talents = loadOrBlank(TalentsData.class, new TalentsData(), nbt, TALENTS_DATA, new TalentsData());
        this.statPoints = loadOrBlank(StatPointsData.class, new StatPointsData(), nbt, STAT_POINTS, new StatPointsData());
        this.deathStats = loadOrBlank(DeathStatsData.class, new DeathStatsData(), nbt, DEATH_STATS, new DeathStatsData());
        this.professions = loadOrBlank(ProfessionsData.class, new ProfessionsData(), nbt, PROFESSIONS, new ProfessionsData());

    }

    public static <OBJ> OBJ loadOrBlank(Class theclass, OBJ newobj, CompoundNBT nbt, String loc, OBJ blank) {
        try {
            OBJ data = LoadSave.Load(theclass, newobj, nbt, loc);
            if (data == null) {
                return blank;
            } else {
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blank;
    }

    @Override
    public String getCapIdForSyncing() {
        return "rpg_player_data";
    }

}

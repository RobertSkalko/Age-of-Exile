package com.robertx22.age_of_exile.database.data.spell_schools;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_schools.parser.TalentGrid;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.saveclasses.PointData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SpellSchool implements ISerializedRegistryEntry<SpellSchool>, IAutoGson<SpellSchool> {

    public static SpellSchool SERIALIZER = new SpellSchool();

    public int order = 0;
    public String text_format;
    public String identifier;
    public String school_type;
    public String icon;

    public enum SchoolType {
        TALENTS
    }

    public SchoolType getSchool_type() {
        try {
            return SchoolType.valueOf(school_type);
        } catch (Exception e) {
            e.printStackTrace();
            return SchoolType.TALENTS;
        }
    }

    // 2d grid with whitespace
    public String perks = "";

    public transient CalcData calcData = new CalcData();

    @Override
    public Class<SpellSchool> getClassForSerialization() {
        return SpellSchool.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.SPELL_SCHOOL;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    @Override
    public void onLoadedFromJson() {

        TalentGrid grid = new TalentGrid(this, perks);

        grid.loadIntoTree();

    }

    @Override
    public boolean isRegistryEntryValid() {

        for (Map.Entry<PointData, String> x : this.calcData.perks.entrySet()) {
            if (!Database.Perks()
                .isRegistered(x.getValue())) {

                String testwitbig = "big_" + x.getValue();

                if (Database.Perks()
                    .isRegistered(testwitbig)) {
                    System.out.print("\n" + testwitbig + " is REGISTERED!!!\n");
                }

                System.out.print("\n Perk of id: " + x.getValue()
                    .replaceAll("\r", "[NEWLINE]") + " doesn't exist, used in spell school: " + this.identifier + " at point: " + x.getKey()
                    .toString());
            }
        }

        return true;
    }

    @Override
    public boolean shouldGenerateJson() {
        return false; // i'll do these manually as its easier to use a program for a grid then to do it in code
    }

    public static class CalcData {

        public PointData center;

        public transient HashMap<PointData, Set<PointData>> connections = new HashMap<>();
        public transient HashMap<PointData, String> perks = new HashMap<>();

        public Perk getPerk(PointData point) {
            return Database.Perks()
                .get(perks.get(point));
        }

        public boolean isConnected(PointData one, PointData two) {

            if (!connections.containsKey(one)) {
                return false;
            }
            return connections.get(one)
                .contains(two);
        }

        public void addPerk(PointData point, String perk) {
            perks.put(point, perk);
        }

        public void addConnection(PointData from, PointData to) {

            if (from.x == to.x && from.y == to.y) {
                return;
            }

            if (!connections.containsKey(from)) {
                connections.put(from, new HashSet<>());
            }
            if (!connections.containsKey(to)) {
                connections.put(to, new HashSet<>());
            }
            connections.get(from)
                .add(to);
            connections.get(to)
                .add(from);

        }

    }

}

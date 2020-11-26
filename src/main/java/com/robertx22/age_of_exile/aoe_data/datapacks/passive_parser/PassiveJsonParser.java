package com.robertx22.age_of_exile.aoe_data.datapacks.passive_parser;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.aoe_data.datapacks.passive_parser.info.InfoJson;
import com.robertx22.age_of_exile.aoe_data.datapacks.passive_parser.info.InfoList;
import com.robertx22.age_of_exile.aoe_data.datapacks.passive_parser.info.InfoObject;
import com.robertx22.age_of_exile.saveclasses.PointData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PassiveJsonParser {

    public static void generate() {

        Gson gson = new Gson();

        PassiveTreeClass tree = gson.fromJson(PassiveJson.JSON, PassiveTreeClass.class);
        InfoList info = gson.fromJson(InfoJson.JSON, InfoList.class);

        tree.groups.values()
            .forEach(x -> {
                // flip them cus tree is upside down
                x.x = -x.x;
                x.y = -x.y;
            });

        List<ImmutablePair<Integer, PointData>> grids = new ArrayList<>();

        int smallestX = (int) tree.groups.values()
            .stream()
            .min(Comparator.comparingInt(x -> (int) x.x))
            .get().x;
        int smallestY = (int) tree.groups.values()
            .stream()
            .min(Comparator.comparingInt(x -> (int) x.y))
            .get().y;

        int biggestX = (int) tree.groups.values()
            .stream()
            .max(Comparator.comparingInt(x -> (int) x.x))
            .get().x;
        int biggestY = (int) tree.groups.values()
            .stream()
            .max(Comparator.comparingInt(x -> (int) x.y))
            .get().y;

        float MAX_SIZE = 225;

        float xMulti = MAX_SIZE / (biggestX - smallestX);
        float yMulti = MAX_SIZE / (biggestY - smallestY);

        tree.groups.values()
            .forEach(x -> {

                int xpos = (int) (x.x - smallestX);
                int ypos = (int) (x.y - smallestY);

                xpos *= xMulti;
                ypos *= yMulti;

                grids.add(ImmutablePair.of(x.n.size() == 1 ? x.n.get(0) : 0, new PointData(xpos, ypos)));
            });

        List<List<String>> grid = new ArrayList<>();

        for (int x = 0; x < 300; x++) {
            grid.add(new ArrayList<>());
        }
        for (int x = 0; x < 300; x++) {
            for (int y = 0; y < 300; y++) {
                grid.get(x)
                    .add("");
            }
        }

        grids.forEach(x -> {

            if (!grid.get(x.right.x)
                .get(x.right.y)
                .isEmpty()) {
                System.out.print("Overlapping perks! " + x.right.toString());
            }
            String field;

            if (x.left == 0) {
                field = "[CLUSTER]";
            } else {
                InfoObject infoObject = info.nodes.stream()
                    .filter(n -> n.id.equals(x.left))
                    .findAny()
                    .get();
                field = infoObject.dn;
            }

            grid.get(x.right.x)
                .set(x.right.y, field);
        });

        String result = "";

        for (List<String> x : grid) {
            result += StringUtils.join(x, ",") + "\n";
        }

        System.out.print("\n\n\n");
        System.out.print(result);
        System.out.print("\n\n\n");

        //System.out.print(tree.groups.size());

    }

}

package com.robertx22.age_of_exile.database.data.requirements;

import com.robertx22.age_of_exile.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.age_of_exile.datapacks.bases.ISerializablePart;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Requirements implements ITooltipList {

    public static Requirements EMPTY = new Requirements((TagRequirement) null);

    public List<TagRequirement> tag_requirements = new ArrayList<>();

    public Requirements(TagRequirement req) {
        this.tag_requirements.add(req);
    }

    public Requirements(TagRequirement... req1) {
        for (TagRequirement req : req1) {
            this.tag_requirements.add(req);
        }
    }

    public Requirements(List<TagRequirement> reqs) {
        this.tag_requirements.addAll(reqs);
    }

    public boolean satisfiesAllRequirements(GearRequestedFor requested) {

        for (TagRequirement req : tag_requirements) {
            if (req.meetsRequierment(requested) == false) {
                return false;
            }
        }

        return true;
    }

    public static List<ISerializablePart> possible = Arrays.asList(
        new SlotRequirement(), new TagRequirement());

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        List<Text> list = new ArrayList<>();
        this.tag_requirements.forEach(x -> {
            list.add(new SText(""));
            list.addAll(x.GetTooltipString(info));
        });
        return list;
    }
}

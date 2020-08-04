package com.robertx22.mine_and_slash.database.data.requirements;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.database.data.requirements.bases.BaseRequirement;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class TagRequirement extends BaseRequirement<TagRequirement> {

    List<String> included = new ArrayList<>();
    List<String> excluded = new ArrayList<>();

    @Override
    public boolean meetsRequierment(GearRequestedFor requested) {

        List<String> tags = requested.forSlot.getTags();

        return false;
    }

    @Override
    public String getJsonID() {
        return null;
    }

    @Override
    public JsonObject toJson() {
        return null;
    }

    @Override
    public TagRequirement fromJson(JsonObject json) {
        return null;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        return null;
    }
}

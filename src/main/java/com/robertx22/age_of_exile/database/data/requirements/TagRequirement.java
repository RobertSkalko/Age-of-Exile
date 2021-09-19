package com.robertx22.age_of_exile.database.data.requirements;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.JsonUtils;
import com.robertx22.age_of_exile.database.data.gear_types.bases.TagList;
import com.robertx22.age_of_exile.database.data.requirements.bases.BaseRequirement;
import com.robertx22.age_of_exile.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class TagRequirement extends BaseRequirement<TagRequirement> {

    public List<String> included = new ArrayList<>();
    public List<String> excluded = new ArrayList<>();

    @Override
    public boolean meetsRequierment(GearRequestedFor requested) {
        TagList list = requested.forSlot.getTags();

        if (included.stream()
            .anyMatch(x -> list.contains(x))) {
            if (excluded.stream()
                .noneMatch(y -> list.contains(y))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getJsonID() {
        return "tag_req";
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.add("included", JsonUtils.stringListToJsonArray(included));
        json.add("excluded", JsonUtils.stringListToJsonArray(excluded));
        return json;
    }

    @Override
    public TagRequirement fromJson(JsonObject json) {
        TagRequirement req = new TagRequirement();
        req.included = JsonUtils.jsonArrayToStringList(json.getAsJsonArray("included"));
        req.excluded = JsonUtils.jsonArrayToStringList(json.getAsJsonArray("excluded"));
        return req;
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info) {
        return new ArrayList<>();
    }
}

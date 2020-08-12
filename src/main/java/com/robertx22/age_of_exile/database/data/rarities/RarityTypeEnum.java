package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.database.base.Rarities;
import com.robertx22.age_of_exile.database.data.rarities.gears.CommonGear;
import com.robertx22.age_of_exile.database.data.rarities.mobs.CommonMob;
import com.robertx22.age_of_exile.database.data.rarities.skill_gems.CommonGem;
import com.robertx22.age_of_exile.datapacks.bases.ISerializable;

public enum RarityTypeEnum {

    GEAR("gear", CommonGear.getInstance(), Rarities.Gears),
    MOB("mob", CommonMob.getInstance(), Rarities.Mobs),
    SKILL_GEM("skill_gem", new CommonGem(), Rarities.SkillGems);;

    public String id;
    public ISerializable serializer;
    public BaseRaritiesContainer container;

    RarityTypeEnum(String id, ISerializable serializer, BaseRaritiesContainer container) {
        this.id = id;
        this.serializer = serializer;
        this.container = container;
    }

}

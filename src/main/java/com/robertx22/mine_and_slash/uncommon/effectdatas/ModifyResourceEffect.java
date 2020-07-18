package com.robertx22.mine_and_slash.uncommon.effectdatas;

import com.robertx22.mine_and_slash.saveclasses.unit.ResourcesData;

public class ModifyResourceEffect extends EffectData {

    public ResourcesData.Context ctx;

    public ModifyResourceEffect(ResourcesData.Context ctx) {
        super(ctx.source, ctx.target, ctx.sourceData, ctx.targetData);
        this.number = ctx.amount;
        this.ctx = ctx;
    }

    @Override
    protected void activate() {
        // already done elsewhere
    }

}

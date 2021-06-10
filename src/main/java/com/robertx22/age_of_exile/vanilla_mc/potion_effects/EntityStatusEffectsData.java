package com.robertx22.age_of_exile.vanilla_mc.potion_effects;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.inscribing.ScrollBuffData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.HashMap;

@Storable
public class EntityStatusEffectsData {

    @Store
    public HashMap<String, ExileEffectInstanceData> exileMap = new HashMap<>();

    @Store
    public ScrollBuffData sb = null;

    public ExileEffectInstanceData get(ExileStatusEffect eff) {
        return exileMap.getOrDefault(eff.GUID(), null);
    }

    public void set(ExileStatusEffect eff, ExileEffectInstanceData data) {
        exileMap.put(eff.GUID(), data);
    }

    public StatContext getStats(LivingEntity en) {

        if (sb == null) {
            return new MiscStatCtx(Arrays.asList());
        }
        if (en.hasStatusEffect(ModRegistry.POTIONS.SCROLL_BUFF)) {
            return new MiscStatCtx(sb.getStats());
        }

        return new MiscStatCtx(Arrays.asList());

    }
}

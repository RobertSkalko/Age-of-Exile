package com.robertx22.age_of_exile.database.data.spells.components.actions.vanity;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.*;

public class SoundAction extends SpellAction {

    public SoundAction() {
        super(Arrays.asList(SOUND, PITCH, VOLUME));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (!ctx.world.isClient) {

            float pitch = data.get(PITCH)
                .floatValue();
            float volume = data.get(VOLUME)
                .floatValue();
            SoundEvent sound = data.getSound();

            SoundUtils.playSound(ctx.world, ctx.pos, sound, volume, pitch);
        }
    }

    public MapHolder create(SoundEvent sound, Double volume, Double pitch) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(VOLUME, volume);
        d.put(PITCH, pitch);
        d.put(SOUND, Registry.SOUND_EVENT.getId(sound)
            .toString());
        return d;
    }

    @Override
    public String GUID() {
        return "sound";
    }
}

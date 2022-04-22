package com.robertx22.age_of_exile.uncommon;

import com.robertx22.library_of_exile.utils.SavedSound;
import net.minecraft.util.SoundEvents;

public interface SoundRefs {

    SavedSound FISHING_THROW = new SavedSound(SoundEvents.FISHING_BOBBER_THROW);
    SavedSound FISHING_THROW_LOW_PITCH = new SavedSound(SoundEvents.FISHING_BOBBER_THROW, 0);

    SavedSound DING = new SavedSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
    SavedSound DING_LOW_PITCH = new SavedSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0);

    SavedSound HURT = new SavedSound(SoundEvents.GENERIC_HURT);

    SavedSound ANVIl_NO = new SavedSound(SoundEvents.ANVIL_PLACE);

}

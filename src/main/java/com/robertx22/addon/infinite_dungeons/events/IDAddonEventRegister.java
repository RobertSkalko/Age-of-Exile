package com.robertx22.addon.infinite_dungeons.events;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISettableLevelTier;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.infinite_dungeons.exile_events.IDExileEvents;
import com.robertx22.infinite_dungeons.exile_events.OnItemGivenEvent;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.utils.AllItemStackSavers;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;

public class IDAddonEventRegister {

    public static String OVERRIDE_TIER_TAG = "override_tier";

    public static void register() {

        // todo i need to set the producer item as another field in the event, set its nbt to settier of all items produced and then use that here..
        // also set its tier here..
        // so mark it as an item producer in ID, then set its tier, then any items that it produces are that tier.

        IDExileEvents.ON_ITEM_GIVE.register(new EventConsumer<OnItemGivenEvent>() {
            @Override
            public void accept(OnItemGivenEvent event) {

                int tier = LevelUtils.levelToTier(Load.Unit(event.player)
                    .getLevel());

                if (OnItemGivenEvent.isProducer(event.stack)) {
                    event.stack.getOrCreateTag()
                        .putInt(OVERRIDE_TIER_TAG, tier);
                }

                for (ItemstackDataSaver<? extends ISettableLevelTier> saver : AllItemStackSavers.getAllOfClass(ISettableLevelTier.class)) {
                    if (saver.has(event.stack)) {
                        ISettableLevelTier inte = saver.loadFrom(event.stack);
                        if (inte != null) {

                            if (event.producerStack != null && OnItemGivenEvent.isProducer(event.producerStack)) {
                                tier = event.producerStack.getOrCreateTag()
                                    .getInt(OVERRIDE_TIER_TAG);
                            }

                            inte.setTier(tier);
                            saver.saveToObject(event.stack, inte);
                        }

                    }
                }
            }
        });

    }
}

package com.robertx22.mine_and_slash.event_hooks.TODO;

public class OnEquipChange {

    /* TODO
    // on change, notify my capability so i know stats need to be recalculated
    @SubscribeEvent
    public static void onEquipChange(LivingEquipmentChangeEvent event) {

        LivingEntity entity = event.getEntityLiving();

        if (entity != null) {

            EntityCap.UnitData data = Load.Unit(entity);
            data.setEquipsChanged(true);
            data.tryRecalculateStats(entity);

            if (entity instanceof PlayerEntity) {
                data.syncToClient((PlayerEntity) entity);
            }
        }

    }

     */

}

package com.robertx22.mine_and_slash.event_hooks.TODO;

public class OnPlayerClone {


   /* TODO
    public static void onPlayerClone(PlayerEvent.Clone event) {

        PlayerEntity original = event.getOriginal();
        PlayerEntity current = event.getPlayer();

        try {
            EntityCap.UnitData data = Load.Unit(current);
            data.loadFromNBT(Load.Unit(original)
                .saveToNBT());
            data.syncToClient(current);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Load.spells(current)
                .loadFromNBT(Load.spells(original)
                    .saveToNBT());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Load.statPoints(current)
                .loadFromNBT(Load.statPoints(original)
                    .saveToNBT());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */

}

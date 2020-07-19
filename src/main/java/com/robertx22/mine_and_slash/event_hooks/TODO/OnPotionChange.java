package com.robertx22.mine_and_slash.event_hooks.TODO;

public class OnPotionChange {

    /* TODO
    //PotionAddedEvent is called BEFORE adding the potion
    @SubscribeEvent
    public static void onAdded(PotionEvent.PotionAddedEvent event) {

        if (event.getEntityLiving().world.isClient) {
            return;
        }

        try {
            LivingEntity entity = event.getEntityLiving();

            Load.Unit(entity)
                .getUnit()
                .getStats()
                .values()
                .forEach(x -> {
                    if (x.GetStat() instanceof ImmuneToEffectStat) {
                        ImmuneToEffectStat imm = (ImmuneToEffectStat) x.GetStat();
                        if (x.getAverageValue() > 0) {
                            imm.onPotionAdded(event.getPotionEffect()
                                .getEffectType(), entity);
                        }
                    }
                });

            if (event.getPotionEffect()
                .getEffectType() instanceof IOneOfATypePotion) {
                IOneOfATypePotion one = (IOneOfATypePotion) event.getPotionEffect()
                    .getEffectType();

                List<StatusEffect> sames = entity.getStatusEffects()
                    .stream()
                    .filter(x -> {
                        if (x.getEffectType() instanceof IOneOfATypePotion) {
                            IOneOfATypePotion ot = (IOneOfATypePotion) x.getEffectType();

                            if (x.equals(event.getPotionEffect())) {
                                return false;
                            }

                            if (ot.getOneOfATypeType()
                                .equals(one.getOneOfATypeType())) {
                                return true;
                            }
                        }

                        return false;
                    })
                    .map(x -> x.getEffectType())
                    .collect(Collectors.toList());

                sames.forEach(x -> entity.removeStatusEffect(x));
            }

            if (entity != null) {
                EntityCap.UnitData data = Load.Unit(entity);
                data.setEquipsChanged(true);
                //data.tryRecalculateStats(entity); dont calc stats, PotionAddedEvent is called BEFORE adding the potion O_O
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //PotionExpiryEvent is called BEFORE removing the potion
    @SubscribeEvent
    public static void onExpired(PotionEvent.PotionExpiryEvent event) {

        LivingEntity entity = event.getEntityLiving();

        if (entity != null && !entity.world.isClient) {
            EntityCap.UnitData data = Load.Unit(entity);
            data.setEquipsChanged(true);
            //data.tryRecalculateStats(entity);
        }

    }

     */

}
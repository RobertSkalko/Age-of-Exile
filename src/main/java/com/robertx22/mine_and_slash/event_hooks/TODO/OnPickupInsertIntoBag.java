package com.robertx22.mine_and_slash.event_hooks.TODO;

public class OnPickupInsertIntoBag {

    /*
    public static boolean preventAutoActions(ItemStack stack) {
        if (stack.hasCustomName() || stack.hasEnchantments()) {
            return true; // don't pickup into bag if it has custom name or encahnted, it means its part of current gear and not a new drop
        }
        return false;
    }

    @SubscribeEvent
    public static void onPickupItem(EntityItemPickupEvent event) {

        ItemStack stack = event.getItem()
            .getStack();

        if (preventAutoActions(stack)) {
            return;
        }

        for (int i = 0; i < event.getPlayer().inventory.getInvSize(); i++) {
            if (i == event.getPlayer().inventory.selectedSlot)
                continue; // prevent item deletion

            ItemStack bag = event.getPlayer().inventory.getInvStack(i);
            if (!bag.isEmpty() && bag.getItem() instanceof BaseBagItem) {
                BaseBagItem basebag = (BaseBagItem) bag.getItem();
                BaseInventory bagInv = basebag.getInventory(bag, stack);

                if (bagInv == null) {
                    continue;
                }

                InvWrapper handler = new InvWrapper(bagInv);

                stack = loopItems(bagInv, handler, event, stack, true); // once try merge into existing
                stack = loopItems(bagInv, handler, event, stack, false); // second merge even into empty

            }

        }

    }

    private static ItemStack loopItems(BaseInventory baseInv, IItemHandler handler,
                                       EntityItemPickupEvent event, ItemStack stack,
                                       boolean searchSame) {

        if (stack.isEmpty()) {
            return stack;
        }

        for (int x = 0; x < handler.getSlots(); x++) {

            if (searchSame) {
                if (ItemHandlerHelper.canItemStacksStack(stack, handler.getStackInSlot(x)) == false) {
                    continue;
                }
            }

            ItemStack result = handler.insertItem(x, stack, false);
            int numPickedUp = stack.getCount() - result.getCount();

            if (numPickedUp > 0) {

                baseInv.writeItemStack();

                event.getItem()
                    .setStack(result);

                event.setCanceled(true);
                if (!event.getItem()
                    .isSilent()) {
                    event.getItem().world.playSound(null, event.getPlayer().x, event.getPlayer().y, event
                        .getPlayer().z, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((event
                        .getItem().world.random.nextFloat() - event.getItem().world.random
                        .nextFloat()) * 0.7F + 1.0F) * 2.0F);
                }
                ((ServerPlayerEntity) event.getPlayer()).networkHandler.sendPacket(new ItemPickupAnimationS2CPacket(event
                    .getItem()
                    .getEntityId(), event.getPlayer()
                    .getEntityId(), numPickedUp));
                event.getPlayer().container.sendContentUpdates();

                return result;
            }
        }

        return stack;
    }
TODO
     */

}

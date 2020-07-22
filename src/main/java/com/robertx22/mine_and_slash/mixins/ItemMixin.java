package com.robertx22.mine_and_slash.mixins;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin {
    /*
    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    public void on$itemUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> ci) {
        if (true || !world.isClient) {
            MMORPG.mixinLog("using item on client");
            OnItemUseCastSpell.use(world, user, hand);
        }
    }

     */
}

package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.saveclasses.item_classes.SkillGemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.CastSpellPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CancellationException;

public class OnItemUseCastSpell {
    public static void onItemUse(ItemStack stack, World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> ci) {
        try {
            if (Gear.has(stack)) {

                SkillGemData gem = Load.spells(user)
                    .getCurrentSkillGem();
                if (gem != null && gem.getSpell() != null) {
                    if (gem.getSpell()
                        .getImmutableConfigs().castingWeapon.predicate.predicate.test(user)) {

                        if (stack.getUseAction() == UseAction.NONE) {
                            Packets.sendToServer(new CastSpellPacket(user));

                        } else {
                            if (Screen.hasShiftDown()) {
                                if (world.isClient) {
                                    user.swingHand(hand);
                                } else {
                                    Packets.sendToServer(new CastSpellPacket(user));
                                    ci.setReturnValue(TypedActionResult.success(stack));
                                }
                            }
                        }
                    }
                }
            }
        } catch (CancellationException e) {
            e.printStackTrace();
        }

    }
}

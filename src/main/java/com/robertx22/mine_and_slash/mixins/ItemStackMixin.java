package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.mixin_methods.TooltipMethod;
import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.saveclasses.item_classes.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.CastSpellPacket;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ItemStack.class, priority = Integer.MAX_VALUE)
public abstract class ItemStackMixin {
    public ItemStackMixin() {
    }

    // copied from TooltipCallback fabric event
    @Inject(method = {"getTooltip"}, at = {@At("RETURN")})
    private void getTooltip(PlayerEntity entity, TooltipContext tooltipContext, CallbackInfoReturnable<List<Text>> list) {
        ItemStack stack = (ItemStack) (Object) this;
        TooltipMethod.getTooltip(stack, entity, tooltipContext, list);
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void onItemUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> ci) {
        ItemStack stack = (ItemStack) (Object) this;
        if (world.isClient) {
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
                                Packets.sendToServer(new CastSpellPacket(user));
                                ci.setReturnValue(TypedActionResult.success(stack));
                            }
                        }
                    }
                }
            }
        }
    }
}

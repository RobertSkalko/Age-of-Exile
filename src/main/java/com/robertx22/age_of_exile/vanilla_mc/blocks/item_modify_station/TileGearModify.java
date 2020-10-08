package com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TileGearModify extends BaseModificationStation {

    @Override
    public boolean isAutomatable() {
        return false;
    }

    @Override
    public boolean isItemValidInput(ItemStack stack) {
        return true;
    }

    @Override
    public int getCookTime() {
        return COOK_TIME_FOR_COMPLETION;
    }

    public enum ModifyResult {
        BREAK, SUCCESS, NONE
    }

    @Override
    public boolean isOutputSlot(int slot) {
        return false;
    }

    public ResultItem getSmeltingResultForItem() {

        LocReqContext context = getLocReqContext();

        if (context.isValid() == false) {
            return new ResultItem(ItemStack.EMPTY, ModifyResult.NONE);
        }

        if (context.effect != null && context.effect.forStation() == ICurrencyItemEffect.StationType.MODIFY) {

            if (context.effect.canItemBeModified(context)) {
                ItemStack copy = context.stack.copy();
                copy = context.effect.ModifyItem(copy, context.Currency);

                if (context.isGear()) {
                    if (context.effect.getInstability() > 0) {
                        GearItemData gear = Gear.Load(copy);

                        float chance = ModConfig.get().ItemSealing.getChanceToSeal(gear);
                        float insta = ModConfig.get().ItemSealing.getInstability(gear, context.effect);

                        gear.setInstability(gear.getInstability() + insta);
                        if (RandomUtils.roll(chance)) {
                            gear.sealed = true;
                        }
                        Gear.Save(copy, gear);
                    }
                }

                return new ResultItem(copy, ModifyResult.SUCCESS);
            } else {
                return new ResultItem(ItemStack.EMPTY, ModifyResult.NONE);
            }

        }

        return new ResultItem(ItemStack.EMPTY, ModifyResult.NONE);
    }

    public LocReqContext getLocReqContext() {

        ItemStack gearStack = this.GearSlot();
        ItemStack craftStack = this.CraftItemSlot();

        LocReqContext context = new LocReqContext(gearStack, craftStack);

        return context;

    }

    public ItemStack GearSlot() {
        return itemStacks[0];
    }

    public ItemStack CraftItemSlot() {
        return itemStacks[1];
    }

    public void setOutputSot(ItemStack stack) {
        itemStacks[0] = stack;
    }

    private static final short COOK_TIME_FOR_COMPLETION = 80; // vanilla value is 200 = 10 seconds

    public TileGearModify() {
        super(ModRegistry.BLOCK_ENTITIES.GEAR_MODIFY);
        itemStacks = new ItemStack[2];
        clear();

    }

    @Override
    public int ticksRequired() {
        return 555555;
    }

    @Override
    public void finishCooking() {

    }

    @Override
    public boolean isCooking() {
        return false;
    }

    @Override
    public int tickRate() {
        return 555;
    }

    @Override
    public void doActionEveryTime() {

    }

    public double fractionOfCookTimeComplete() {
        double fraction = cookTime / (double) getCookTime();
        return MathHelper.clamp(fraction, 0.0, 1.0);
    }

    public static class ResultItem {

        public ItemStack stack;
        public ModifyResult resultEnum;

        public ResultItem(ItemStack stack, ModifyResult resultEnum) {
            this.stack = stack;
            this.resultEnum = resultEnum;
        }
    }

    private ResultItem getResult() {

        return getSmeltingResultForItem();

    }

    private boolean canModifyItem() {

        ItemStack gear = this.GearSlot();

        if (gear.isEmpty()) {
            return false;
        }

        if (getSmeltingResultForItem().stack.isEmpty()) {
            return false;
        }

        return true;

    }

    @Override
    public boolean modifyItem() {

        if (this.canModifyItem()) {

            ResultItem result = this.getResult();

            this.GearSlot()
                .decrement(1);
            this.setOutputSot(result.stack.copy());
            result.stack = ItemStack.EMPTY;
            this.CraftItemSlot()
                .decrement(1);

            markDirty();

            if (result.resultEnum.equals(ModifyResult.BREAK)) {

                SoundUtils.playSound(world, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, 1, 1);

                ParticleEnum.sendToClients(
                    pos.up(), world, new ParticlePacketData(pos.up(), ParticleEnum.AOE).radius(0.5F)
                        .type(ParticleTypes.POOF)
                        .motion(new Vec3d(0, 0, 0))
                        .amount(5));

            } else if (result.resultEnum.equals(ModifyResult.SUCCESS)) {
                SoundUtils.ding(world, pos);

                ParticleEnum.sendToClients(
                    pos.up(), world, new ParticlePacketData(pos.up(), ParticleEnum.AOE).radius(0.5F)
                        .type(ParticleTypes.COMPOSTER)
                        .amount(15));

            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public ScreenHandler createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new ContainerGearModify(num, inventory, this, this.getPos());
    }

    @Override
    public MutableText getDisplayName() {
        return CLOC.blank("block.mmorpg.modify_station");
    }
}
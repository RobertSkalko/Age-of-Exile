package com.robertx22.age_of_exile.vanilla_mc.blocks.socket_station;

import com.robertx22.age_of_exile.database.data.currency.base.ICurrencyItemEffect;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneWordItem;
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

import java.util.ArrayList;
import java.util.List;

public class SocketStationBlockEntity extends BaseModificationStation {

    @Override
    public boolean isAutomatable() {
        return false;
    }

    @Override
    public boolean isItemValidInput(ItemStack stack) {
        return true;
    }

    public enum SocketResult {
        BREAK, SUCCESS, NONE
    }

    @Override
    public int getCookTime() {
        return COOK_TIME_FOR_COMPLETION;
    }

    @Override
    public boolean isOutputSlot(int slot) {
        return false;
    }

    public ResultItem getSmeltingResultForItem() {

        LocReqContext context = getLocReqContext();

        if (context.isValid() == false) {
            return new ResultItem(ItemStack.EMPTY, SocketResult.NONE);
        }

        if (context.effect != null && context.effect.forStation() == ICurrencyItemEffect.StationType.SOCKET) {

            if (context.effect.canItemBeModified(context)) {
                ItemStack copy = context.stack.copy();
                copy = context.effect.ModifyItem(copy, context.Currency);

                return new ResultItem(copy, SocketResult.SUCCESS);
            } else {
                return new ResultItem(ItemStack.EMPTY, SocketResult.NONE);
            }

        }

        return new ResultItem(ItemStack.EMPTY, SocketResult.NONE);
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

    public SocketStationBlockEntity() {
        super(ModRegistry.BLOCK_ENTITIES.SOCKET_STATION);
        itemStacks = new ItemStack[SocketStationContainer.TOTAL_SLOTS];
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
        return 10;
    }

    private void clearRunewordShow() {
        for (int i = 2; i < SocketStationContainer.RUNEWORD_SLOTS + 2; i++) {
            this.itemStacks[i] = ItemStack.EMPTY;
        }
    }

    @Override
    public void doActionEveryTime() {

        if (GearSlot().isEmpty() && CraftItemSlot().isEmpty()) {
            clearRunewordShow();
            return;
        }

        List<RuneWord> possible = new ArrayList<>();

        if (!GearSlot().isEmpty()) {
            GearItemData gear = Gear.Load(GearSlot());

            if (gear != null) {

                SlashRegistry.Runewords()
                    .getList()
                    .forEach(x -> {
                        if (x.canItemHave(gear)) {
                            possible.add(x);
                        }
                    });
            }

        } else if (!CraftItemSlot().isEmpty()) {
            if (CraftItemSlot().getItem() instanceof RuneItem) {
                RuneItem rune = (RuneItem) CraftItemSlot().getItem();
                SlashRegistry.Runewords()
                    .getList()
                    .forEach(x -> {
                        if (x.containsRune(rune.getRune())) {
                            possible.add(x);
                        }
                    });
            }

        }

        clearRunewordShow();

        for (int i = 2; i < SocketStationContainer.RUNEWORD_SLOTS + 2; i++) {
            int index = i - 2;
            if (possible.size() > index) {
                this.itemStacks[i] = RuneWordItem.get(possible.get(index));
            }
        }

    }

    public double fractionOfCookTimeComplete() {
        double fraction = cookTime / (double) getCookTime();
        return MathHelper.clamp(fraction, 0.0, 1.0);
    }

    static class ResultItem {

        ItemStack stack;
        SocketResult resultEnum;

        public ResultItem(ItemStack stack, SocketResult resultEnum) {
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

            if (result.resultEnum.equals(SocketResult.BREAK)) {

                SoundUtils.playSound(world, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, 1, 1);

                ParticleEnum.sendToClients(
                    pos.up(), world, new ParticlePacketData(pos.up(), ParticleEnum.AOE).radius(0.5F)
                        .type(ParticleTypes.POOF)
                        .motion(new Vec3d(0, 0, 0))
                        .amount(5));

            } else if (result.resultEnum.equals(SocketResult.SUCCESS)) {
                SoundUtils.ding(world, pos);

                ParticleEnum.sendToClients(
                    pos.up(), world, new ParticlePacketData(pos.up(), ParticleEnum.AOE).radius(0.5F)
                        .type(ParticleTypes.COMPOSTER)
                        .amount(15));

            }

            return true;
        } else {
            SoundUtils.playSound(world, pos, SoundEvents.ENTITY_VILLAGER_NO, 1, 1);

            return false;
        }
    }

    @Override
    public ScreenHandler createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new SocketStationContainer(num, inventory, this, this.getPos());
    }

    @Override
    public MutableText getDisplayName() {
        return CLOC.blank("block.mmorpg.socket_station");
    }
}

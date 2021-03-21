package com.robertx22.age_of_exile.vanilla_mc.blocks.buff_station;

import com.robertx22.age_of_exile.database.data.scroll_buff.ScrollBuff;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.inscribing.EssenceInkItem;
import com.robertx22.age_of_exile.player_skills.items.inscribing.ScrollBuffData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.BaseModificationStation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScribeBuffTile extends BaseModificationStation {

    public ScribeBuffTile() {
        super(ModRegistry.BLOCK_ENTITIES.SCRIBE_BUFF);
        this.itemStacks = new ItemStack[ScribeBuffContainer.SCRIBE_BUFF_SLOT_COUNT];
        this.clear();
    }

    public static int PAPER_SLOT = 0;
    public static int MAT_SLOT = 1;

    public static List<ScrollBuff> getCurrentSelection(PlayerEntity p) {

        int seed = Load.Unit(p)
            .getBuffSeed();
        Random ran = new Random(seed);

        List<ScrollBuff> left = Database.ScrollBuffs()
            .getList();
        List<ScrollBuff> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ScrollBuff item = RandomUtils.weightedRandom(left, ran.nextDouble());
            if (item != null) {
                left.remove(item); // no duplicates
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public boolean modifyItem(int number, PlayerEntity player) {

        ItemStack paper = itemStacks[PAPER_SLOT];
        ItemStack mat = itemStacks[MAT_SLOT];

        if (paper.getItem() == Items.PAPER) {
            if (mat.getItem() instanceof EssenceInkItem) {

                List<ScrollBuff> selection = getCurrentSelection(player);

                ScrollBuff buff = selection.get(MathHelper.clamp(number, 0, 5));

                EssenceInkItem ink = (EssenceInkItem) mat.getItem();

                ScrollBuffData data = new ScrollBuffData();

                data.lvl = ink.tier.levelRange.getMaxLevel();
                data.rar = Database.GearRarities()
                    .random()
                    .GUID();
                data.id = buff.id;

                ItemStack stack = new ItemStack(ModRegistry.MISC_ITEMS.SCROLL_BUFF);
                data.saveToStack(stack);

                itemStacks[PAPER_SLOT].decrement(1);
                itemStacks[MAT_SLOT].decrement(1);
                Load.Unit(player)
                    .randomizeBuffSeed();
                PlayerUtils.giveItem(stack, player);
                Load.Unit(player)
                    .syncToClient(player);
            }
        }

        return true;
    }

    @Override
    public boolean isItemValidInput(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean isAutomatable() {
        return false;
    }

    @Override
    public boolean isOutputSlot(int i) {
        return false;
    }

    @Override
    public Text getDisplayName() {
        return new LiteralText("");
    }

    @Override
    public void tick() {

    }

    @Override
    public ScreenHandler createMenu(int num, PlayerInventory inventory, PlayerEntity player) {
        return new ScribeBuffContainer(num, inventory, this, this.getPos());
    }
}

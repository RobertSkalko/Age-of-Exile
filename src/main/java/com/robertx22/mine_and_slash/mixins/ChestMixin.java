package com.robertx22.mine_and_slash.mixins;

import com.google.common.collect.Lists;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.MasterLootGen;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin(LootableContainerBlockEntity.class)
public abstract class ChestMixin {

    @Accessor(value = "lootTableId")
    public abstract LootTable getlootTableId();

    @Inject(method = "checkLootInteraction(Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At("HEAD"))
    public void onLootGen(PlayerEntity player, CallbackInfo ci) {
        LootableContainerBlockEntity chest = (LootableContainerBlockEntity) (Object) this;

        if (getlootTableId() != null) {
            MMORPG.mixinLog("Loottable isn't null");

            LootInfo info = LootInfo.ofBlockPosition(chest.getWorld(), chest.getPos());
            info.isChestLoot = true;

            info.minItems = RandomUtils.RandomRange(1, 3);
            List<ItemStack> items = MasterLootGen.generateLoot(info);

            List<Integer> list1 = mygetEmptySlotsRandomized(chest, chest.getWorld().random);

            for (int i = 0; i < items.size(); i++) {
                chest.setInvStack(list1.get(i), items.get(list1.get(i)));
            }

        } else {
            MMORPG.mixinLog("Loottable is null");

        }

    }

    private List<Integer> mygetEmptySlotsRandomized(Inventory inventory, Random rand) {
        List<Integer> list = Lists.newArrayList();

        for (int i = 0; i < inventory.getInvSize(); ++i) {
            if (inventory.getInvStack(i)
                .isEmpty()) {
                list.add(i);
            }
        }

        Collections.shuffle(list, rand);
        return list;
    }

}

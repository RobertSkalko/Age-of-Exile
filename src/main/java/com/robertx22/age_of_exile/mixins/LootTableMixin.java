package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.AddSpawnerExtraLootMethod;
import com.robertx22.age_of_exile.player_skills.events.OnBlockDropFarming;
import com.robertx22.age_of_exile.player_skills.events.OnBlockDropMining;
import com.robertx22.age_of_exile.player_skills.events.OnFishing;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LootTable.class)
public class LootTableMixin {

    @Inject(method = "getRandomItems(Lnet/minecraft/loot/LootContext;)Ljava/util/List;", at = @At(value = "RETURN"))
    public void hookLoot(LootContext context, CallbackInfoReturnable<List<ItemStack>> ci) {

        try {
            LootTable lootTable = (LootTable) (Object) this;

            AddSpawnerExtraLootMethod.hookLoot(context, ci);
            OnBlockDropMining.run(context, ci);
            OnBlockDropFarming.run(context, ci);
            OnFishing.run(lootTable, context, ci);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

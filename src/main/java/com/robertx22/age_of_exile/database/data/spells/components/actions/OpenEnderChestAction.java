package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;

import java.util.Arrays;
import java.util.Collection;

public class OpenEnderChestAction extends SpellAction {

    public OpenEnderChestAction() {
        super(Arrays.asList());
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {
        if (ctx.caster instanceof PlayerEntity) {
            if (!ctx.caster.level.isClientSide) {
                PlayerEntity player = (PlayerEntity) ctx.caster;

                // copied from EnderChestBlock, if bug copy again
                PlayerEnderChestContainer enderChestInventory = player.getEnderChestInventory();

                player.openMenu(new SimpleMenuProvider((i, playerInventory, playerEntity) -> {
                    return ChestMenu.threeRows(i, playerInventory, enderChestInventory);
                }, new TranslationTextComponent("container.enderchest")));
            }
        }
    }

    public MapHolder create() {
        MapHolder d = new MapHolder();
        d.type = GUID();
        return d;
    }

    @Override
    public String GUID() {
        return "open_ender_chest";
    }
}


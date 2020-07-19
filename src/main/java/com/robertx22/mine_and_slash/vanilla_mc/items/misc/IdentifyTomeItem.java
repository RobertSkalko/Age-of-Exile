package com.robertx22.mine_and_slash.vanilla_mc.items.misc;

import com.robertx22.mine_and_slash.database.base.CreativeTabs;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Gear;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.SoundUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticleEnum;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticlePacketData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class IdentifyTomeItem extends Item implements IShapedRecipe {
    public IdentifyTomeItem() {
        super(new Settings().maxCount(64)
            .group(CreativeTabs.MyModTab));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (!world.isClient) {
            try {

                ItemStack tome = player.getStackInHand(hand);

                if (tome.getItem() instanceof IdentifyTomeItem) {
                    for (ItemStack x : player.inventory.main) {
                        if (tryIdentify(x, tome)) {
                            spawnEffects(player);
                            return new TypedActionResult<ItemStack>(ActionResult.CONSUME, tome);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new TypedActionResult<ItemStack>(ActionResult.PASS, player.getStackInHand(hand));
    }

    public boolean tryIdentify(ItemStack stack, ItemStack tome) {

        GearItemData gear = Gear.Load(stack);

        if (gear != null) {
            if (!gear.isIdentified()) {

                gear.setIdentified(true);

                Gear.Save(stack, gear);

                tome.decrement(1);

                return true;
            }

        }
        return false;
    }

    private void spawnEffects(LivingEntity en) {

        ParticleEnum.sendToClients(en, new ParticlePacketData(en.getPosVector()
            .add(0, 1, 0), ParticleEnum.AOE).radius(1)
            .type(ParticleTypes.ENCHANT)
            .amount(100)
            .motion(new Vec3d(0, 0, 0)));
        SoundUtils.playSound(en, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<MutableText> tooltip, TooltipContext flagIn) {
        tooltip.add(Words.RightClickToIdentifyFirst.locName());
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(ModRegistry.MISC_ITEMS.IDENTIFY_TOME, 8)
            .input('b', Items.BOOK)
            .input('v', Items.COAL)
            .input('o', ModRegistry.MISC_ITEMS.MAGIC_ESSENCE)
            .pattern("oo")
            .pattern("bv")
            .criterion("player_level", trigger());
    }

}

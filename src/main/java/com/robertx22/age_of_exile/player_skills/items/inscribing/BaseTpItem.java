package com.robertx22.age_of_exile.player_skills.items.inscribing;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public abstract class BaseTpItem extends Item implements IAutoLocName, IAutoModel, IShapelessRecipe {

    public BaseTpItem() {
        super(new Properties().tab(CreativeTabs.Professions)
            .stacksTo(16));
    }

    public abstract ItemStack onDoneUsing(ItemStack stack, World world, ServerPlayerEntity user);

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity) {
            return onDoneUsing(stack, world, (ServerPlayerEntity) user);
        }
        return stack;
    }

    @Override
    public void onUseTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        for (int i = 0; i < 5; i++) {
            world.addParticle(ParticleTypes.REVERSE_PORTAL,
                user.getX() - 0.5F + world.random.nextDouble(),
                user.getY() + user.getBbHeight() / 2,
                user.getZ() - 0.5F + world.random.nextDouble()
                , 0, 0, 0);
        }
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 40;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return DrinkHelper.useDrink(world, user, hand);
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getKey(this)
            .toString();
    }

    @Override
    public String GUID() {
        return "";
    }
}

package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.IsSkillItemUsableUtil;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BackpackItem extends Item {
    BackpackType type;
    SkillItemTier tier;

    public BackpackItem(BackpackType type, SkillItemTier tier) {
        super(new Settings().group(CreativeTabs.Tinkering)
            .fireproof());
        this.type = type;
        this.tier = tier;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        user.setCurrentHand(hand);

        if (world != null && !world.isClient) {

            ItemStack stack = user.getStackInHand(hand);
            if (!IsSkillItemUsableUtil.canUseItem(user, stack, true)) {
                return TypedActionResult.fail(stack);
            }

            user.openHandledScreen(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
                    packetByteBuf.writeEnumConstant(hand);
                }

                @Override
                public Text getDisplayName() {
                    return new LiteralText("");
                }

                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                    ItemStack stack = player.getMainHandStack();
                    return new BackpackContainer(stack, syncId, inv);
                }
            });
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }

}
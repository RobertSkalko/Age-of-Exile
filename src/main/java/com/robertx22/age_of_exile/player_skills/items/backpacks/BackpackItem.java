package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.IsSkillItemUsableUtil;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.IReqSkillLevel;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BackpackItem extends Item implements IAutoLocName, IAutoModel, IShapedRecipe, IReqSkillLevel {
    public BackpackType type;
    public SkillItemTier tier;

    public BackpackItem(BackpackType type, SkillItemTier tier) {
        super(new Settings().group(CreativeTabs.Professions)
            .fireproof()
            .maxCount(1));
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

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey()).formatted(tier.format);
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
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String locNameForLangFile() {

        String name = type.getName();
        if (!name.isEmpty()) {
            name = " " + name;
        }
        return tier.word + name + " Bag";
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(this)
            .input('o', ModRegistry.TINKERING.LEATHER_TIER_MAP.get(tier))
            .input('m', Items.CHEST)
            .input('d', type.getCraftItem())
            .pattern("ooo")
            .pattern("omo")
            .pattern("odo")
            .criterion("player_level", trigger());
    }

    @Override
    public PlayerSkillEnum getItemSkillType() {
        return PlayerSkillEnum.TINKERING;
    }

    @Override
    public float getSkillLevelMultiNeeded() {
        return tier.lvl_req;
    }
}
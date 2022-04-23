package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.all_keys.SpellKeys;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.spells.TestSpell;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCastSpellPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class StaffWeapon extends ModWeapon implements IAutoModel {
    VanillaMaterial mat;

    public StaffWeapon(VanillaMaterial mat) {
        super(mat.toolmat, new Properties().durability(mat.toolmat.getUses())
            .tab(CreativeTabs.MyModTab), WeaponTypes.staff);
        this.mat = mat;

    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.handheld(this);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        try {

            if (!world.isClientSide) {
                GearItemData gear = Gear.Load(stack);
                if (gear != null) {
                    Spell spell = ExileDB.Spells()
                        .get(SpellKeys.MAGIC_PROJECTILE.id);

                    if (player.isCreative() && MMORPG.RUN_DEV_TOOLS) {
                        spell = TestSpell.get();
                    }

                    if (TellServerToCastSpellPacket.tryCastSpell(player, spell)) {
                        player.swing(hand);
                        return ActionResult.success(stack);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ActionResult.pass(stack);

    }
}

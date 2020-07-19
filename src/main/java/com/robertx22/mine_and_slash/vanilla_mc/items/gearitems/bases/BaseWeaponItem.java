package com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.IGearItem;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ItemUtils;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.bases.itemtiers.RarityItemTier;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.registry.Registry;

import java.util.Set;

public abstract class BaseWeaponItem extends ToolItem implements IAutoLocName, IGearItem {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet();

    public BaseWeaponItem(int rar) {

        super(
            new RarityItemTier(rar), ItemUtils.getDefaultGearProperties()
                .maxDamageIfAbsent(BaseArmorItem.GetMat(BaseArmorItem.Type.PLATE, rar)
                    .getDurability()));
        this.rarity = rar;
    }

    public float attackSpeed = -2.4F;

    public int rarity = 0;

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Items;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (entity) -> {
            entity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        Multimap<EntityAttribute, EntityAttributeModifier> map = super.getAttributeModifiers(slot);
        if (slot == EquipmentSlot.MAINHAND) {
            map.put(
                EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 5 + (this.rarity + 1),
                    EntityAttributeModifier.Operation.ADDITION
                )
            );
            map.put(
                EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier",
                    (double) this.attackSpeed, EntityAttributeModifier.Operation.ADDITION));

        }

        return map;
    }

}

package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.a_libraries.curios.MyCurioUtils;
import com.robertx22.age_of_exile.a_libraries.curios.RefCurio;
import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import java.util.Arrays;
import java.util.HashMap;

// If ANYTHING goes wrong in this class, player gear can be wiped !!!
@Storable
public class OnePlayerCharData {

    // these are just for preview on the character screen
    @Store
    public int lvl = 1;
    @Store
    public String race = "";
    // these are just for preview on the character screen

    // saved gear stacks
    @Store
    public CompoundTag wep = null;
    @Store
    public CompoundTag f = null;
    @Store
    public CompoundTag p = null;
    @Store
    public CompoundTag c = null;
    @Store
    public CompoundTag h = null;
    @Store
    public CompoundTag o = null;

    @Store
    public CompoundTag r1 = null;
    @Store
    public CompoundTag r2 = null;
    @Store
    public CompoundTag n = null;
    // saved gear stacks

    @Store
    public HashMap<PlayerCaps, CompoundTag> map = new HashMap<>();

    private void saveGear(PlayerEntity p) {

        this.wep = getNullOrTagAndDelete(EquipmentSlot.MAINHAND, p);
        this.o = getNullOrTagAndDelete(EquipmentSlot.OFFHAND, p);

        this.f = getNullOrTagAndDelete(EquipmentSlot.FEET, p);
        this.p = getNullOrTagAndDelete(EquipmentSlot.LEGS, p);
        this.c = getNullOrTagAndDelete(EquipmentSlot.CHEST, p);
        this.h = getNullOrTagAndDelete(EquipmentSlot.HEAD, p);

        this.r1 = getNullOrTagAndDeleteCurios(RefCurio.RING, 0, p);
        this.r2 = getNullOrTagAndDeleteCurios(RefCurio.RING, 1, p);
        this.n = getNullOrTagAndDeleteCurios(RefCurio.NECKLACE, 0, p);

    }

    private void loadGear(PlayerEntity p) {

        equipOrGive(EquipmentSlot.MAINHAND, wep, p);
        equipOrGive(EquipmentSlot.OFFHAND, o, p);

        equipOrGive(EquipmentSlot.FEET, f, p);
        equipOrGive(EquipmentSlot.LEGS, this.p, p);
        equipOrGive(EquipmentSlot.CHEST, c, p);
        equipOrGive(EquipmentSlot.HEAD, h, p);

        equipOrGiveCurios(RefCurio.RING, 0, r1, p);
        equipOrGiveCurios(RefCurio.RING, 1, r2, p);
        equipOrGiveCurios(RefCurio.NECKLACE, 0, n, p);

    }

    private CompoundTag getNullOrTagAndDeleteCurios(String slot, int index, PlayerEntity p) {
        try {
            ItemStack stack = MyCurioUtils.getAllSlots(Arrays.asList(slot), p)
                .get(index);

            if (Gear.has(stack)) {
                CompoundTag tag = new CompoundTag();
                stack.toTag(tag);

                MyCurioUtils.getHandlers(Arrays.asList(slot), p)
                    .forEach(x -> {
                        x.getStacks()
                            .setStack(index, ItemStack.EMPTY);

                    });
                return tag;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private CompoundTag getNullOrTagAndDelete(EquipmentSlot slot, PlayerEntity p) {
        try {
            ItemStack stack = p.getEquippedStack(slot);

            if (Gear.has(stack)) {
                CompoundTag tag = new CompoundTag();
                stack.toTag(tag);
                p.equipStack(slot, ItemStack.EMPTY);
                return tag;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void equipOrGiveCurios(String slot, int index, CompoundTag itemnbt, PlayerEntity p) {

        if (p.world.isClient) {
            return;
        }

        try {
            if (itemnbt != null) {
                ItemStack stack = ItemStack.fromTag(itemnbt);

                if (MyCurioUtils.getAllSlots(Arrays.asList(slot), p)
                    .get(index)
                    .isEmpty()) {

                    MyCurioUtils.getHandlers(Arrays.asList(slot), p)
                        .forEach(x -> {
                            x.getStacks()
                                .setStack(index, stack);

                        });
                } else {
                    PlayerUtils.giveItem(stack, p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void equipOrGive(EquipmentSlot slot, CompoundTag itemnbt, PlayerEntity p) {

        try {
            if (itemnbt != null) {
                ItemStack stack = ItemStack.fromTag(itemnbt);

                if (p.getEquippedStack(slot)
                    .isEmpty()) {
                    p.equipStack(slot, stack);
                } else {
                    PlayerUtils.giveItem(stack, p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(PlayerEntity player) {

        this.lvl = Load.Unit(player)
            .getLevel();
        try {
            this.race = Load.Unit(player)
                .getRace()
                .GUID();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (PlayerCaps cap : PlayerCaps.values()) {
            if (cap.shouldSaveToPlayerCharacter()) {
                CompoundTag nbt = new CompoundTag();
                cap.getCap(player)
                    .toTag(nbt);
                map.put(cap, nbt);
            }

        }

        saveGear(player);

    }

    public void load(PlayerEntity player) {

        for (PlayerCaps cap : PlayerCaps.values()) {
            if (cap.shouldSaveToPlayerCharacter()) {

                CompoundTag nbt = map.getOrDefault(cap, new CompoundTag());
                ICommonPlayerCap pcap = cap.getCap(player);
                pcap.fromTag(nbt);

                pcap.syncToClient(player);
            }
        }

        loadGear(player);

    }

}

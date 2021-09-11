package com.robertx22.age_of_exile.saveclasses.spells.skill_gems;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemType;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.*;

@Storable
public class SkillGemsData implements Inventory {

    @Store
    public HashMap<String, Integer> levels = new HashMap<>();

    public static int SIZE = 20;

    @Store
    public List<ItemStack> stacks = new ArrayList<>(DefaultedList.ofSize(SIZE, ItemStack.EMPTY));

    @Store
    public List<SkillGemData> gems = new ArrayList<>(DefaultedList.ofSize(SIZE, new SkillGemData())); // saved gem datas on close inventory so it's faster to get and calculate stuff

    public enum Places {

        B1(0, 0, SkillGemType.SUPPORT_GEM, 57, 9),
        B2(1, 0, SkillGemType.SKILL_GEM, 79, 9),
        B3(2, 0, SkillGemType.SUPPORT_GEM, 101, 9),

        B4(3, 1, SkillGemType.SUPPORT_GEM, 39, 32),
        B5(4, 1, SkillGemType.SUPPORT_GEM, 57, 32),
        B6(5, 1, SkillGemType.SKILL_GEM, 79, 32),
        B7(6, 1, SkillGemType.SUPPORT_GEM, 101, 32),
        B8(7, 1, SkillGemType.SUPPORT_GEM, 119, 32),

        B9(8, 2, SkillGemType.SUPPORT_GEM, 39, 55),
        B10(9, 2, SkillGemType.SUPPORT_GEM, 57, 55),
        B11(10, 2, SkillGemType.SKILL_GEM, 79, 55),
        B12(11, 2, SkillGemType.SUPPORT_GEM, 101, 55),
        B13(12, 2, SkillGemType.SUPPORT_GEM, 119, 55),

        B14(13, 3, SkillGemType.SUPPORT_GEM, 57, 78),
        B15(14, 3, SkillGemType.SKILL_GEM, 79, 78),
        B16(15, 3, SkillGemType.SUPPORT_GEM, 101, 78),

        B17(16, 4, SkillGemType.SKILL_GEM, 43, 101),
        B18(17, 5, SkillGemType.SKILL_GEM, 67, 101),
        B19(18, 6, SkillGemType.SKILL_GEM, 91, 101),
        B20(19, 7, SkillGemType.SKILL_GEM, 115, 101);

        public int index;
        public int place;
        public SkillGemType slotType;

        public int x;
        public int y;

        Places(int index, int place, SkillGemType slotType, int x, int y) {
            this.index = index;
            this.place = place;
            this.slotType = slotType;
            this.x = x;
            this.y = y;
        }
    }

    public int getIndexOfSpell(Spell spell) {

        int n = 0;
        for (SkillGemData x : gems) {
            if (x != null) {
                if (x.getSkillGem().spell_id.equals(spell.GUID())) {
                    return n;
                }
            }
            n++;
        }

        return -1;
    }

    @Override
    public void onClose(PlayerEntity player) {

        this.gems.clear();

        try {
            if (!player.world.isClient) {

                this.stacks.forEach(x -> {
                    if (x.isEmpty()) {
                        gems.add(null); // i know this is weird but when you press Q to drop item it becomes empty while keeping nbt
                    } else {
                        SkillGemData gem = StackSaving.SKILL_GEMS.loadFrom(x);
                        gems.add(gem);
                    }
                });
                Load.spells(player)
                    .syncToClient(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<SkillGemData> getSupportGemsOf(int place) {
        List<SkillGemData> list = new ArrayList<>();

        for (Places en : Places.values()) {
            if (en.slotType == SkillGemType.SUPPORT_GEM) {
                if (en.place == place) {
                    list.add(gems.get(en.index));
                }
            }
        }
        list.removeIf(x -> x == null);

        return list;
    }

    public SkillGemData getSkillGemOf(int place) {

        if (gems.size() != stacks.size()) {
            return null;
        }

        for (Places en : Places.values()) {
            if (en.place == place) {
                if (en.slotType == SkillGemType.SKILL_GEM) {
                    return gems.get(en.index);
                }
            }
        }
        System.out.print("No skill gem found for place " + place);
        return null;
    }

    @Override

    public int size() {
        return SIZE;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return stacks.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.stacks, slot, amount);
        return itemStack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return stacks.set(slot, ItemStack.EMPTY);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        stacks.set(slot, stack);
    }

    @Override
    public void markDirty() {

    }

    public Optional<SkillGemData> getSkillGemOfSpell(Spell spell) {
        Objects.requireNonNull(spell);

        Optional<SkillGemData> opt = gems.stream()
            .filter(x -> {
                return x != null && x.getSkillGem() != null && x.getSkillGem().spell_id.equals(spell.GUID());
            })
            .findAny();

        return opt;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }
}

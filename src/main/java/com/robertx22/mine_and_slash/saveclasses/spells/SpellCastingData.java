package com.robertx22.mine_and_slash.saveclasses.spells;

import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.saveclasses.item_classes.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.SkillGem;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Storable
public class SpellCastingData implements Inventory {

    @Store
    public int castingTicksLeft = 0;

    @Store
    public int castingTicksDone = 0;

    @Store
    public int lastSpellCastTimeInTicks = 0;

    public static Integer selectedSpell = 1; // this is just used on client, so client tells server which spell to cast

    @Store
    public String spellBeingCast = "";

    @Store
    private HashMap<Integer, ItemStack> hotbar = new HashMap<>();

    @Store
    private HashMap<String, SpellData> spellDatas = new HashMap<>();

    public void cancelCast(PlayerEntity player) {
        try {
            SpellCastContext ctx = new SpellCastContext(player, 0, getSkillGem(getSpellBeingCast().GUID()));

            BaseSpell spell = getSpellBeingCast();
            if (spell != null && spell.goesOnCooldownIfCastCanceled()) {
                SpellData data = spellDatas.getOrDefault(spell.GUID(), new SpellData());

                int cd = spell.getCooldownInTicks(ctx);
                data.setCooldown(cd);
            }

            spellBeingCast = "";
            castingTicksLeft = 0;
            lastSpellCastTimeInTicks = 0;
            castingTicksDone = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clear() {
        hotbar.clear();
    }

    public BaseSpell getSelectedSpell() {
        return getSpellByNumber(selectedSpell);
    }

    public boolean isCasting() {
        return castingTicksLeft > 0 && SlashRegistry.Spells()
            .isRegistered(spellBeingCast);
    }

    public void onTimePass(PlayerEntity player, PlayerSpellCap.ISpellsCap spells, int ticks) {

        try {

            if (spellBeingCast != null && spellBeingCast.length() > 0) {
                BaseSpell spell = SlashRegistry.Spells()
                    .get(spellBeingCast);

                SpellCastContext ctx = new SpellCastContext(player, castingTicksDone, getSkillGem(spellBeingCast));

                if (!player.world.isClient) {
                    if (spell != null && spells != null && SlashRegistry.Spells()
                        .isRegistered(spell)) {

                        spell.onCastingTick(ctx);

                    } else {

                    }
                }

                tryCast(player, spells, ctx);

                castingTicksLeft--;
                castingTicksDone++;

                if (castingTicksLeft < 0) {
                    this.spellBeingCast = "";
                }
            }

            spellDatas.values()
                .forEach(x -> x.tickCooldown(ticks));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<String> getSpellsOnCooldown() {
        return spellDatas.entrySet()
            .stream()
            .filter(x -> !x.getValue()
                .cooldownIsReady())
            .map(x -> x.getKey())
            .collect(Collectors.toList());

    }

    public void setToCast(BaseSpell spell, PlayerEntity player) {
        SpellCastContext ctx = new SpellCastContext(player, 0, getSkillGem(spell.GUID()));

        this.spellBeingCast = spell.GUID();
        this.castingTicksLeft = spell.useTimeTicks(ctx);
        this.lastSpellCastTimeInTicks = spell.useTimeTicks(ctx);
        this.castingTicksDone = 0;
    }

    private void tryCast(PlayerEntity player, PlayerSpellCap.ISpellsCap spells, SpellCastContext ctx) {

        if (!spellBeingCast.isEmpty()) {
            if (castingTicksLeft <= 0) {
                BaseSpell spell = SlashRegistry.Spells()
                    .get(spellBeingCast);

                int timesToCast = (int) ctx.getConfigFor(spell)
                    .get(SC.TIMES_TO_CAST)
                    .get(ctx.skillGem);

                if (timesToCast == 1) {
                    spell.cast(ctx);
                }

                player.getMainHandStack()
                    .damage(1, player, x -> {
                        player.sendToolBreakStatus(player.getActiveHand());
                    });

                onSpellCast(spell, player, spells);

                spellBeingCast = "";

            }
        }

    }

    public void setHotbar(int number, SkillGemData data) {

        if (data != null) {
            if (!SlashRegistry.Spells()
                .isRegistered(data.spell_id)) {
                try {
                    throw new Exception("Trying to setup spell that isn't registered!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        getHotbar().put(number, data);
    }

    public BaseSpell getSpellBeingCast() {
        return SlashRegistry.Spells()
            .get(spellBeingCast);
    }

    public boolean canCast(BaseSpell spell, PlayerEntity player) {

        if (isCasting()) {
            return false;
        }

        if (spell == null) {
            return false;
        }
        if (getSkillGem(spell.GUID()) == null) {
            return false;
        }

        SpellData data = getDataBySpell(spell);

        if (data.cooldownIsReady() == false) {
            return false;
        }

        SpellCastContext ctx = new SpellCastContext(player, 0, getSkillGem(spell.GUID()));

        return spell.canCast(ctx);

    }

    private void onSpellCast(BaseSpell spell, PlayerEntity player, PlayerSpellCap.ISpellsCap spells) {
        SpellData data = spellDatas.getOrDefault(spell.GUID(), new SpellData());

        SpellCastContext ctx = new SpellCastContext(player, 0, getSkillGem(spell.GUID()));

        if (spell.shouldActivateCooldown(player, spells)) {
            int cd = spell.getCooldownInTicks(ctx);
            data.setCooldown(cd);
        }

        spellDatas.put(spell.GUID(), data);

    }

    public HashMap<Integer, SkillGemData> getHotbar() {
        HashMap<Integer, SkillGemData> datamap = new HashMap<>();
        hotbar.forEach((key, value) -> {
            SkillGemData gem = SkillGem.Load(value);
            if (gem != null) {
                datamap.put(key, gem);
            }
        });
        return datamap;
    }

    public HashMap<Integer, ItemStack> getStacks() {
        return this.hotbar;
    }

    public SkillGemData getSkillGem(String id) {

        Optional<SkillGemData> opt1 = getHotbar().values()
            .stream()
            .filter(x -> x.spell_id.equals(id))
            .findFirst();

        if (opt1.isPresent()) {
            return opt1.get();
        }

        return null;

    }

    public SpellData getDataBySpell(BaseSpell spell) {

        String id = spell.GUID();

        if (spellDatas.containsKey(id) == false) {
            spellDatas.put(id, new SpellData());
        }

        return spellDatas.get(id);

    }

    public SkillGemData getSkillGemByNumber(int key) {
        return getHotbar().getOrDefault(key, new SkillGemData());
    }

    public BaseSpell getSpellByNumber(int key) {

        String id = "";
        try {
            id = getHotbar().getOrDefault(key, new SkillGemData()).spell_id;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (id != null) {
            if (SlashRegistry.Spells()
                .isRegistered(id)) {
                return SlashRegistry.Spells()
                    .get(id);
            }

        }
        return null;
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    public boolean isEmpty() {
        return hotbar.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return hotbar.getOrDefault(slot, ItemStack.EMPTY);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return hotbar.put(slot, ItemStack.EMPTY);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return hotbar.put(slot, ItemStack.EMPTY);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        hotbar.put(slot, stack);
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}

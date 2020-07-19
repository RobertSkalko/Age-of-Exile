package com.robertx22.mine_and_slash.saveclasses.spells;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.saveclasses.item_classes.SkillGemData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Storable
public class SpellCastingData {

    @Store
    public int castingTicksLeft = 0;

    @Store
    public int castingTicksDone = 0;

    @Store
    public int lastSpellCastTimeInTicks = 0;

    @Store
    public String spellBeingCast = "";

    public void cancelCast(PlayerEntity player) {
        try {
            SpellCastContext ctx = new SpellCastContext(player, 0, getSpellBeingCast());

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
        getMap(SpellCastingData.Hotbar.FIRST).clear();
        getMap(SpellCastingData.Hotbar.SECOND).clear();
    }

    public enum Hotbar {
        FIRST,
        SECOND
    }

    @Store
    private HashMap<Integer, SkillGemData> firstHotbar = new HashMap<>();

    @Store
    private HashMap<Integer, SkillGemData> secondHotbar = new HashMap<>();

    @Store
    private HashMap<String, SpellData> spellDatas = new HashMap<>();

    public boolean isCasting() {
        return castingTicksLeft > 0 && SlashRegistry.Spells()
            .isRegistered(spellBeingCast);
    }

    public void onTimePass(PlayerEntity player, PlayerSpellCap.ISpellsCap spells, int ticks) {

        try {

            BaseSpell spell = SlashRegistry.Spells()
                .get(spellBeingCast);

            SpellCastContext ctx = new SpellCastContext(player, castingTicksDone, spell);

            if (!player.world.isClient) {
                if (spell != null && spells != null && SlashRegistry.Spells()
                    .isRegistered(spell)) {

                    spell.onCastingTick(ctx);
                    addCastingMoveDebuff(player);
                } else {
                    removeCastingMoveDebuff(player);
                }
            }

            tryCast(player, spells, ctx);

            castingTicksLeft--;
            castingTicksDone++;

            if (!player.world.isClient) {

                if (spell == null || !SlashRegistry.Spells()
                    .isRegistered(spell)) {
                    removeCastingMoveDebuff(player);
                }
            }

            spellDatas.values()
                .forEach(x -> x.tickCooldown(ticks));

            if (castingTicksLeft < 0) {
                this.spellBeingCast = "";
            }

        } catch (Exception e) {

        }

    }

    public static EntityAttributeModifier CASTING_SPEED_DEBUFF = new EntityAttributeModifier(UUID.fromString("d6d3dc82-9787-4722-9a33-924b94490e2a"), EntityAttributes.MOVEMENT_SPEED.getId(), -0.25F, EntityAttributeModifier.Operation.MULTIPLY_BASE);

    public void addCastingMoveDebuff(PlayerEntity player) {
        EntityAttributeInstance atri = player.getAttributes()
            .get(EntityAttributes.MOVEMENT_SPEED);

        if (!atri.hasModifier(CASTING_SPEED_DEBUFF)) {
            atri.addModifier(CASTING_SPEED_DEBUFF);
        }
    }

    public void removeCastingMoveDebuff(PlayerEntity player) {
        EntityAttributeInstance atri = player.getAttributes()
            .get(EntityAttributes.MOVEMENT_SPEED);

        if (atri.hasModifier(CASTING_SPEED_DEBUFF)) {
            atri.removeModifier(CASTING_SPEED_DEBUFF);
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

    public void setToCast(int key, Hotbar hotbar, PlayerEntity player, int ticks) {
        BaseSpell spell = getSpellByKeybind(key, hotbar);

        SpellCastContext ctx = new SpellCastContext(player, 0, spell);

        this.spellBeingCast = spell.GUID();

        this.castingTicksLeft = spell.useTimeTicks(ctx);
        this.lastSpellCastTimeInTicks = spell.useTimeTicks(ctx);
        this.castingTicksDone = 0;

    }

    public void setToCast(BaseSpell spell, PlayerEntity player) {
        SpellCastContext ctx = new SpellCastContext(player, 0, spell);

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
                    .get(ctx.spellsCap, spell);

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

    public void setHotbar(int number, Hotbar hotbar, SkillGemData data) {

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

        getMap(hotbar).put(number, data);
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

        SpellData data = getDataBySpell(spell);

        if (data.cooldownIsReady() == false) {
            return false;
        }

        SpellCastContext ctx = new SpellCastContext(player, 0, spell);

        return spell.canCast(ctx);

    }

    public boolean canCast(int key, Hotbar hotbar, PlayerEntity player) {

        if (isCasting()) {
            return false;
        }

        BaseSpell spell = getSpellByKeybind(key, hotbar);

        if (spell == null) {
            return false;
        }

        SpellData data = getDataBySpell(spell);

        if (data.cooldownIsReady() == false) {
            return false;
        }
        SpellCastContext ctx = new SpellCastContext(player, 0, spell);

        return spell.canCast(ctx);

    }

    private void onSpellCast(BaseSpell spell, PlayerEntity player, PlayerSpellCap.ISpellsCap spells) {
        SpellData data = spellDatas.getOrDefault(spell.GUID(), new SpellData());

        SpellCastContext ctx = new SpellCastContext(player, 0, spell);

        if (spell.shouldActivateCooldown(player, spells)) {
            int cd = spell.getCooldownInTicks(ctx);
            data.setCooldown(cd);
        }

        spellDatas.put(spell.GUID(), data);

    }

    public HashMap<Integer, SkillGemData> getMap(Hotbar hotbar) {
        if (hotbar == Hotbar.FIRST) {
            return this.firstHotbar;
        } else {
            return this.secondHotbar;
        }
    }

    public SkillGemData getSkillGem(String id) {

        Optional<SkillGemData> opt1 = getMap(Hotbar.FIRST).values()
            .stream()
            .filter(x -> x.spell_id.equals(id))
            .findFirst();
        Optional<SkillGemData> opt2 = getMap(Hotbar.SECOND).values()
            .stream()
            .filter(x -> x.spell_id.equals(id))
            .findFirst();

        if (opt1.isPresent()) {
            return opt1.get();
        }
        if (opt2.isPresent()) {
            return opt2.get();
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

    public SpellData getDataByKeybind(int key, Hotbar hotbar) {

        String id = getMap(hotbar).get(key).spell_id;

        if (spellDatas.containsKey(id) == false) {
            spellDatas.put(id, new SpellData());
        }

        return spellDatas.get(id);

    }

    public BaseSpell getSpellByKeybind(int key, Hotbar hotbar) {
        String id = getMap(hotbar).getOrDefault(key, new SkillGemData()).spell_id;

        if (id != null) {
            if (SlashRegistry.Spells()
                .isRegistered(id)) {
                return SlashRegistry.Spells()
                    .get(id);
            }

        }
        return null;
    }

}

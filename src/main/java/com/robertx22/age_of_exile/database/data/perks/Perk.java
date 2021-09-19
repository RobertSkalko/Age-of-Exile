package com.robertx22.age_of_exile.database.data.perks;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.tooltips.StatTooltipType;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AdvancementUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientTextureUtils;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.registry.serialization.IByteBuf;
import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class Perk implements JsonExileRegistry<Perk>, IAutoGson<Perk>, ITooltipList, IByteBuf<Perk>, IAutoLocName {
    public static Perk SERIALIZER = new Perk();

    public PerkType type;
    public String identifier;
    public String lock_under_adv = "";
    public String icon = "";
    public String one_of_a_kind = null;

    public transient String locname = "";

    public boolean is_entry = false;
    public int lvl_req = 1;

    public List<OptScaleExactStat> stats = new ArrayList<>();

    @Override
    public Perk getFromBuf(PacketBuffer buf) {
        Perk data = new Perk();

        data.type = PerkType.valueOf(buf.readUtf(50));
        data.identifier = buf.readUtf(100);
        data.lock_under_adv = buf.readUtf(100);
        data.icon = buf.readUtf(150);
        data.one_of_a_kind = buf.readUtf(80);
        if (data.one_of_a_kind.isEmpty()) {
            data.one_of_a_kind = null;
        }

        data.is_entry = buf.readBoolean();
        data.lvl_req = buf.readInt();

        int s = buf.readInt();

        for (int i = 0; i < s; i++) {
            data.stats.add(OptScaleExactStat.SERIALIZER.getFromBuf(buf));
        }

        return data;
    }

    @Override
    public void toBuf(PacketBuffer buf) {
        buf.writeUtf(type.name(), 100);
        buf.writeUtf(identifier, 100);
        buf.writeUtf(lock_under_adv, 100);
        buf.writeUtf(icon, 150);

        buf.writeUtf(one_of_a_kind != null ? one_of_a_kind : "", 80);

        buf.writeBoolean(is_entry);
        buf.writeInt(lvl_req);

        buf.writeInt(stats.size());
        stats.forEach(x -> x.toBuf(buf));

    }

    transient ResourceLocation cachedIcon = null;

    public ResourceLocation getIcon() {
        if (cachedIcon == null) {
            ResourceLocation id = new ResourceLocation(icon);
            if (ClientTextureUtils.textureExists(id)) {
                cachedIcon = id;
            } else {
                cachedIcon = Stat.DEFAULT_ICON;
            }
        }
        return cachedIcon;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info) {
        List<ITextComponent> list = new ArrayList<>();

        try {

            if (this.type != PerkType.STAT) {
                list.add(locName().withStyle(type.format));
            }

            info.statTooltipType = StatTooltipType.BASE_LOCAL_STATS;

            stats.forEach(x -> list.addAll(x.GetTooltipString(info)));

            Advancement adv = AdvancementUtils.getAdvancement(info.player.level, new ResourceLocation(lock_under_adv));

            if (adv != null) {
                list.add(new StringTextComponent("Needs advancement: ").append(adv.getChatComponent()));
            }

            if (this.one_of_a_kind != null) {
                list.add(new StringTextComponent("Can only have one Perk of this type: ").withStyle(TextFormatting.GREEN));

                list.add(new TranslationTextComponent(Ref.MODID + ".one_of_a_kind." + one_of_a_kind).withStyle(TextFormatting.GREEN));
            }

            if (lvl_req > 1) {
                list.add(Words.RequiresLevel.locName()
                    .append(": " + lvl_req)
                    .withStyle(TextFormatting.YELLOW));
            }

            if (this.type == PerkType.MAJOR) {

                list.add(new StringTextComponent("Game changer talent.").withStyle(TextFormatting.RED));
            }

            list.add(Words.PressAltForStatInfo.locName()
                .withStyle(TextFormatting.BLUE));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Talents;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".talent." + identifier;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    public enum Connection {
        LINKED, BLOCKED, POSSIBLE
    }

    public enum PerkType {
        STAT(2, 24, 24, 39, TextFormatting.WHITE),
        SPECIAL(3, 28, 28, 77, TextFormatting.LIGHT_PURPLE),
        MAJOR(1, 33, 33, 1, TextFormatting.RED),
        START(4, 35, 35, 115, TextFormatting.YELLOW),
        SPELL_MOD(5, 26, 26, 153, TextFormatting.BLACK);

        int order;

        public int width;
        public int height;
        private int xoff;
        public TextFormatting format;

        PerkType(int order, int width, int height, int xoff, TextFormatting format) {
            this.order = order;
            this.width = width;
            this.height = height;
            this.xoff = xoff;
            this.format = format;
        }

        public int getXOffset() {
            return xoff;
        }

    }

    public boolean isLockedUnderAdvancement() {
        return !lock_under_adv.isEmpty();
    }

    public PerkType getType() {
        return type;
    }

    public boolean isLockedToPlayer(PlayerEntity player) {

        if (Load.Unit(player)
            .getLevel() < lvl_req) {
            return true;
        }

        if (one_of_a_kind != null) {
            if (!one_of_a_kind.isEmpty()) {

                if (Load.playerRPGData(player).talents
                    .getAllAllocatedPerks()
                    .values()
                    .stream()
                    .filter(x -> {
                        return this.one_of_a_kind.equals(x.one_of_a_kind);
                    })
                    .count() > 0) {
                    return true;
                }
            }
        }
        if (isLockedUnderAdvancement()) {
            if (!didPlayerUnlockAdvancement(player)) {
                return true;
            }
        }

        return false;

    }

    public boolean didPlayerUnlockAdvancement(PlayerEntity player) {
        ResourceLocation id = new ResourceLocation(this.lock_under_adv);
        return AdvancementUtils.didPlayerFinish(player, id);
    }

    @Override
    public Class<Perk> getClassForSerialization() {
        return Perk.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.PERK;
    }

    @Override
    public String GUID() {
        return identifier;
    }
}

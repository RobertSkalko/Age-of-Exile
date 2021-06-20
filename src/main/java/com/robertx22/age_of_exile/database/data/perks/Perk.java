package com.robertx22.age_of_exile.database.data.perks;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.JsonExileRegistry;
import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
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
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

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
    public Perk getFromBuf(PacketByteBuf buf) {
        Perk data = new Perk();

        data.type = PerkType.valueOf(buf.readString(50));
        data.identifier = buf.readString(100);
        data.lock_under_adv = buf.readString(100);
        data.icon = buf.readString(150);
        data.one_of_a_kind = buf.readString(80);
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
    public void toBuf(PacketByteBuf buf) {
        buf.writeString(type.name(), 100);
        buf.writeString(identifier, 100);
        buf.writeString(lock_under_adv, 100);
        buf.writeString(icon, 150);

        buf.writeString(one_of_a_kind != null ? one_of_a_kind : "", 80);

        buf.writeBoolean(is_entry);
        buf.writeInt(lvl_req);

        buf.writeInt(stats.size());
        stats.forEach(x -> x.toBuf(buf));

    }

    transient Identifier cachedIcon = null;

    public Identifier getIcon() {
        if (cachedIcon == null) {
            Identifier id = new Identifier(icon);
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
    public List<Text> GetTooltipString(TooltipInfo info) {
        List<Text> list = new ArrayList<>();

        try {

            if (this.type != PerkType.STAT) {
                list.add(locName().formatted(type.format));
            }

            info.statTooltipType = StatTooltipType.BASE_LOCAL_STATS;

            stats.forEach(x -> list.addAll(x.GetTooltipString(info)));

            Advancement adv = AdvancementUtils.getAdvancement(info.player.world, new Identifier(lock_under_adv));

            if (adv != null) {
                list.add(new LiteralText("Needs advancement: ").append(adv.toHoverableText()));
            }

            if (this.one_of_a_kind != null) {
                list.add(new LiteralText("Can only have one Perk of this type: ").formatted(Formatting.GREEN));

                list.add(new TranslatableText(Ref.MODID + ".one_of_a_kind." + one_of_a_kind).formatted(Formatting.GREEN));
            }

            if (lvl_req > 1) {
                list.add(Words.RequiresLevel.locName()
                    .append(": " + lvl_req)
                    .formatted(Formatting.YELLOW));
            }

            if (this.type == PerkType.MAJOR) {

                list.add(new LiteralText("Game changer talent.").formatted(Formatting.RED));
            }

            list.add(Words.PressAltForStatInfo.locName()
                .formatted(Formatting.BLUE));

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
        STAT(2, 24, 24, 39, Formatting.WHITE),
        SPECIAL(3, 28, 28, 77, Formatting.LIGHT_PURPLE),
        MAJOR(1, 33, 33, 1, Formatting.RED),
        START(4, 35, 35, 115, Formatting.YELLOW),
        SPELL_MOD(5, 26, 26, 153, Formatting.BLACK);

        int order;

        public int width;
        public int height;
        private int xoff;
        public Formatting format;

        PerkType(int order, int width, int height, int xoff, Formatting format) {
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

                if (Load.perks(player)
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
        Identifier id = new Identifier(this.lock_under_adv);
        return AdvancementUtils.didPlayerFinish(player, id);
    }

    @Override
    public Class<Perk> getClassForSerialization() {
        return Perk.class;
    }

    @Override
    public ExileRegistryTypes getExileRegistryType() {
        return ExileRegistryTypes.PERK;
    }

    @Override
    public String GUID() {
        return identifier;
    }
}

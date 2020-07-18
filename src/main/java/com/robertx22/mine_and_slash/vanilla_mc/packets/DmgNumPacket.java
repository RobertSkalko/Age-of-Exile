package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.OnDisplayDamage;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class DmgNumPacket extends MyPacket<DmgNumPacket> {

    public String element;
    public String string;
    public double x;
    public double y;
    public double z;
    public float height;
    public boolean isExp;
    public float number;

    private DmgNumPacket() {

    }

    public DmgNumPacket(LivingEntity entity, Elements ele, String str, float number) {

        element = ele.toString();
        string = str;
        x = entity.getX();
        y = entity.getY();
        z = entity.getZ();
        height = entity.getHeight();
        this.number = number;

    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "dmgnum");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        element = tag.readString();
        x = tag.readDouble();
        y = tag.readDouble();
        z = tag.readDouble();
        height = tag.readFloat();
        isExp = tag.readBoolean();
        string = tag.readString();
        number = tag.readFloat();
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(element);
        tag.writeDouble(x);
        tag.writeDouble(y);
        tag.writeDouble(z);
        tag.writeFloat(height);
        tag.writeBoolean(isExp);
        tag.writeString(string);
        tag.writeFloat(number);
    }

    @Override
    public void onReceived(PacketContext ctx, DmgNumPacket data) {
        if (isExp && ClientConfigs.INSTANCE.dmgParticleConfig.ENABLE_CHAT_EXP_MSG.get()) {
            ClientOnly.getPlayer()
                .sendMessage(new SText(Formatting.GREEN + "" + Formatting.BOLD + "+" + number + " EXP"));

        } else if (isExp == false && ClientConfigs.INSTANCE.dmgParticleConfig.ENABLE_FLOATING_DMG.get()) {
            OnDisplayDamage.displayParticle(element, string, x, y, z, height);
        }
    }

    @Override
    public MyPacket<DmgNumPacket> newInstance() {
        return new DmgNumPacket();
    }
}

package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.data_generation.DimConfigsDatapackManager;
import com.robertx22.mine_and_slash.data_generation.EntityConfigsDatapackManager;
import com.robertx22.mine_and_slash.data_generation.affixes.AffixDataPackManager;
import com.robertx22.mine_and_slash.data_generation.base_gear_types.BaseGearTypeDatapackManager;
import com.robertx22.mine_and_slash.data_generation.compatible_items.CompatibleItemDataPackManager;
import com.robertx22.mine_and_slash.data_generation.mob_affixes.MobAffixDataPackManager;
import com.robertx22.mine_and_slash.data_generation.rarities.GearRarityManager;
import com.robertx22.mine_and_slash.data_generation.rarities.SkillGemRarityManager;
import com.robertx22.mine_and_slash.data_generation.tiers.TierDatapackManager;
import com.robertx22.mine_and_slash.data_generation.unique_gears.UniqueGearDatapackManager;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.command.CommandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerResourceManager.class)
public abstract class ServerResourceManagerMixin {

    @Inject(method = "<init>*", at = @At("RETURN"))
    public void my$onConstruct(CommandManager.RegistrationEnvironment registrationEnvironment, int i, CallbackInfo ci) {

        ServerResourceManager m = (ServerResourceManager) (Object) this;
        ReloadableResourceManager manager = (ReloadableResourceManager) m.getResourceManager();

        manager.registerListener(new BaseGearTypeDatapackManager());
        manager.registerListener(new TierDatapackManager());
        manager.registerListener(new AffixDataPackManager());
        manager.registerListener(new MobAffixDataPackManager());
        manager.registerListener(new UniqueGearDatapackManager());
        manager.registerListener(new CompatibleItemDataPackManager());
        manager.registerListener(new GearRarityManager());
        manager.registerListener(new SkillGemRarityManager());
        manager.registerListener(new DimConfigsDatapackManager());
        manager.registerListener(new EntityConfigsDatapackManager());
    }
}

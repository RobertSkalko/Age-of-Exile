package com.robertx22.age_of_exile.vanilla_mc.items.misc;

public class ProjectileItem extends AutoItem {
    String id;

    public ProjectileItem(String id) {
        super(new Settings());
        this.id = id;
    }

    @Override
    public String locNameForLangFile() {
        return "Projectile";
    }

    @Override
    public String GUID() {
        return "projectile/" + id;
    }
}

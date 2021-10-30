package com.robertx22.age_of_exile.database.data.gear_slots;

import com.robertx22.age_of_exile.a_libraries.curios.RefCurio;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.StaffWeapon;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashMap;

public class GearSlot implements JsonExileRegistry<GearSlot>, IAutoGson<GearSlot>, IAutoLocName {

    public static GearSlot SERIALIZER = new GearSlot("", "", SlotFamily.NONE, 1, -1, 0);
    private static HashMap<String, HashMap<Item, Boolean>> CACHED_GEAR_SLOTS = new HashMap<>();
    static HashMap<Item, GearSlot> CACHED = new HashMap<>();

    public String id;
    public int weight;
    public int energy_cost;
    public int model_num = -1;
    public transient String locname = "";
    public SlotFamily fam = SlotFamily.Armor;

    public GearSlot(String id, String name, SlotFamily fam, int energy_cost, int modelnnum, int weight) {
        this.id = id;
        this.fam = fam;
        this.energy_cost = energy_cost;
        this.locname = name;
        this.model_num = modelnnum;
        this.weight = weight;
    }

    public static GearSlot getSlotOf(Item item) {

        if (CACHED.containsKey(item)) {
            return CACHED.get(item);
        }

        if (ServerContainer.get()
            .getCompatMap()
            .containsKey(item)) {
            CACHED.put(item, ServerContainer.get()
                .getCompatMap()
                .get(item));
            return CACHED.get(item);
        }

        for (GearSlot slot : ExileDB.GearSlots()
            .getList()) {
            if (isItemOfThisSlot(slot, item)) {
                CACHED.put(item, slot);
                return slot;
            }
        }

        CACHED.put(item, null);

        return null;
    }

    // has to use ugly stuff like this cus datapacks.
    public static boolean isItemOfThisSlot(GearSlot slot, Item item) {
        if (item == Items.AIR) {
            return false;
        }
        String id = slot.GUID();

        if (id.isEmpty()) {
            return false;
        }

        if (!CACHED_GEAR_SLOTS.containsKey(id)) {
            CACHED_GEAR_SLOTS.put(id, new HashMap<>());
        }
        if (CACHED_GEAR_SLOTS.get(id)
            .containsKey(item)) {
            return CACHED_GEAR_SLOTS.get(id)
                .get(item);
        }

        boolean bool = false;

        try {

            if (ServerContainer.get()
                .getCompatMap()
                .containsKey(item)) {
                if (ServerContainer.get()
                    .getCompatMap()
                    .get(item)
                    .GUID()
                    .equals(slot.GUID())) {
                    bool = true;
                }
            } else {

                if (item instanceof ArmorItem) {
                    EquipmentSlotType eqslot = ((ArmorItem) item).getSlot();
                    if (eqslot == EquipmentSlotType.CHEST && id.equals(GearSlots.CHEST)) {
                        bool = true;
                    } else if (eqslot == EquipmentSlotType.LEGS && id.equals(GearSlots.PANTS)) {
                        bool = true;
                    } else if (eqslot == EquipmentSlotType.HEAD && id.equals(GearSlots.HELMET)) {
                        bool = true;
                    } else if (eqslot == EquipmentSlotType.FEET && id.equals(GearSlots.BOOTS)) {
                        bool = true;
                    }

                } else if (id.equals(GearSlots.SWORD)) {
                    bool = item instanceof SwordItem;
                } else if (id.equals(GearSlots.BOW)) {
                    bool = item instanceof BowItem;
                } else if (id.equals(GearSlots.AXE)) {
                    bool = item instanceof AxeItem;
                } else if (id.equals(GearSlots.SHIELD)) {
                    bool = item instanceof ShieldItem;
                } else if (id.equals(GearSlots.CROSBOW)) {
                    bool = item instanceof CrossbowItem;
                } else if (id.equals(GearSlots.SCEPTER) || id.equals(GearSlots.STAFF)) {
                    bool = item instanceof StaffWeapon;
                } else if (id.equals(GearSlots.NECKLACE)) {
                    bool = CuriosApi.getCuriosHelper()
                        .getCurioTags(item)
                        .contains(RefCurio.NECKLACE);
                } else if (id.equals(GearSlots.RING)) {
                    bool = CuriosApi.getCuriosHelper()
                        .getCurioTags(item)
                        .contains(RefCurio.RING);
                }
            }

            CACHED_GEAR_SLOTS.get(id)
                .put(item, bool);

            return bool;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GEAR_SLOT;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Slots;
    }

    @Override
    public String locNameLangFileGUID() {
        return "mmorpg.gearslot." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public Class<GearSlot> getClassForSerialization() {
        return GearSlot.class;
    }
}

package bluefirephoenix.brokennametags;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokenNameTags implements ModInitializer {
    public static final String MOD_ID = "brokennametags";

    public static final Item BROKEN_NAMETAG = new BrokenNameTagItem(new FabricItemSettings().maxCount(1));

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void addItemstoItemGroups(){
        addToItemGroup(ItemGroups.INGREDIENTS, BROKEN_NAMETAG);
    }

    public static void addToItemGroup (ItemGroup group, Item item){
        ItemGroupEvents.modifyEntriesEvent(group).register(entries ->
                entries.add(item));
    }

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, new Identifier("brokennametags", "broken_nametag"),  BROKEN_NAMETAG);
        addItemstoItemGroups();
    }
}
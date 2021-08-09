package com.nyfaria.nyfseoka.init;

import com.nyfaria.nyfseoka.NyfsEoka;
import com.nyfaria.nyfseoka.items.Eoka;
import com.nyfaria.nyfseoka.items.Slug;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemInit {

    public static final Item SLUG_ITEM = new Slug(new Item.Settings().group(ItemGroup.COMBAT).maxCount(9));
    public static final Item BARREL_ITEM = new Item(new Item.Settings().group(ItemGroup.COMBAT).maxCount(1));
    public static final Item HANDLE_ITEM = new Item(new Item.Settings().group(ItemGroup.COMBAT).maxCount(1));
    public static final Eoka EOKA_ITEM = new Eoka(new Item.Settings().group(ItemGroup.COMBAT).maxCount(1).maxDamage(200));

    public static void init(){
        Registry.register(Registry.ITEM, new Identifier(NyfsEoka.MOD_ID, "eoka"), EOKA_ITEM);
        Registry.register(Registry.ITEM, new Identifier(NyfsEoka.MOD_ID, "slug"), SLUG_ITEM);
        Registry.register(Registry.ITEM, new Identifier(NyfsEoka.MOD_ID, "barrel"), BARREL_ITEM);
        Registry.register(Registry.ITEM, new Identifier(NyfsEoka.MOD_ID, "handle"), HANDLE_ITEM);
    }
}

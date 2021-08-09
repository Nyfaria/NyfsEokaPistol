package com.nyfaria.nyfseoka;

import com.nyfaria.nyfseoka.init.ItemInit;
import com.nyfaria.nyfseoka.items.Slug;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NyfsEoka implements ModInitializer {

	public static final String MOD_ID = "nyfseoka";
	public static final RecipeSerializer<SlugRecipe> SLUG_RECIPE_RECIPE_SERIALIZER;
	public static final RecipeType<SlugRecipe> SLUG_RECIPE;
	static {
		SLUG_RECIPE_RECIPE_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("nyfseoka:slug_recipe"), new CookingRecipeSerializer<>(SlugRecipe::new, 200));
		SLUG_RECIPE = Registry.register(Registry.RECIPE_TYPE, new Identifier("nyfseoka:slug_recipe"), new RecipeType<SlugRecipe>() {
			@Override
			public String toString(){return "blast_furnace";}
		});
	}

	@Override
	public void onInitialize() {
		ItemInit.init();

	}
}

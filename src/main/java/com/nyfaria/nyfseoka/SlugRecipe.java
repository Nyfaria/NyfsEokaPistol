package com.nyfaria.nyfseoka;

import com.nyfaria.nyfseoka.items.Slug;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.BlastingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;

public class SlugRecipe extends BlastingRecipe {
    public SlugRecipe(Identifier id, String group, Ingredient input, ItemStack output, float experience, int cookTime){
        super(id,group,input,output,experience,cookTime);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return super.getSerializer();
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        this.output.setCount(8);
        return output.copy();
    }
}

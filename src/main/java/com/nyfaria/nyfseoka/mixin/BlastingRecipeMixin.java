package com.nyfaria.nyfseoka.mixin;

import com.nyfaria.nyfseoka.init.ItemInit;
import com.nyfaria.nyfseoka.items.Slug;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.BlastingRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCookingRecipe.class)
public class BlastingRecipeMixin {


    @Shadow @Final protected ItemStack output;

    @Inject(method = "craft", at=@At("HEAD"), cancellable = true)
    public void slugCount(Inventory inventory, CallbackInfoReturnable<ItemStack> cir){

        if(this.output.getItem() instanceof Slug){
            this.output.setCount(8);
        }

    }


}

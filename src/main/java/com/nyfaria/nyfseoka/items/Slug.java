package com.nyfaria.nyfseoka.items;

import com.nyfaria.nyfseoka.enitities.SlugEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Slug extends ArrowItem {

    public Slug(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        SlugEntity arrowEntity = new SlugEntity(world, shooter);
        return arrowEntity;

    }
}

package com.nyfaria.nyfseoka.init;

import com.nyfaria.nyfseoka.NyfsEoka;
import com.nyfaria.nyfseoka.enitities.SlugEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityInit {

    public static final EntityType<SlugEntity> SLUG = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(NyfsEoka.MOD_ID, "slug"),
            FabricEntityTypeBuilder.<SlugEntity>create(SpawnGroup.MISC, SlugEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                    .build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
    );
}

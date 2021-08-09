package com.nyfaria.nyfseoka.enitities;

import com.google.common.collect.Sets;
import com.nyfaria.nyfseoka.NyfsEokaClient;
import com.nyfaria.nyfseoka.init.EntityInit;
import com.nyfaria.nyfseoka.init.ItemInit;
import com.nyfaria.nyfseoka.items.Eoka;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potions;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SlugEntity extends PersistentProjectileEntity implements FlyingItemEntity {

    public Vec3d shooterPos;

    public SlugEntity(World world) {
        super(EntityInit.SLUG, world);
    }

    public SlugEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public SlugEntity(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    public SlugEntity(EntityType<? extends PersistentProjectileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
    }

    public SlugEntity(World world, LivingEntity shooter) {
        super(EntityInit.SLUG, shooter, world);
        this.setOwner(shooter);
         shooterPos = shooter.getPos();
    }


    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ItemInit.SLUG_ITEM, 1);
    }

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() { // Not entirely sure, but probably has do to with the snowball's particles. (OPTIONAL)
        ItemStack itemStack = this.asItemStack();
        return (ParticleEffect) (itemStack.isEmpty() ? ParticleTypes.EXPLOSION : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }

    @Override
    public Packet<?> createSpawnPacket() {

        return SlugSpawnPacket.create(this, NyfsEokaClient.PACKETID);
    }


    @Override
    public ItemStack getStack() {
        return new ItemStack(ItemInit.SLUG_ITEM, 1);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return false;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if(entityHitResult.getEntity().world.isClient) {
            return;
        }
        if (entityHitResult.getEntity() instanceof LivingEntity) {
            PlayerEntity playerIn = (PlayerEntity) this.getOwner();
            ItemStack itemStack = playerIn.getStackInHand(Hand.MAIN_HAND);
            Eoka eoka = (Eoka) itemStack.getItem();
            int range = eoka.getRange();
            double distance = this.getPos().distanceTo(shooterPos);
            float damage = distance <= range ? 10.0f : (float)(range / distance) * 10.0f;
            if(random.nextInt(10) == 0){
                damage*=2;
            }
            entityHitResult.getEntity().damage(DamageSource.player((PlayerEntity) this.getOwner()), damage);
        }
    }
}

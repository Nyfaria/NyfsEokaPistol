package com.nyfaria.nyfseoka;

import com.nyfaria.nyfseoka.enitities.SlugEntity;
import com.nyfaria.nyfseoka.enitities.SlugSpawnPacket;
import com.nyfaria.nyfseoka.init.EntityInit;
import com.nyfaria.nyfseoka.init.ItemInit;
import com.nyfaria.nyfseoka.items.Eoka;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import java.util.UUID;

public class NyfsEokaClient implements ClientModInitializer {
	public static final Identifier PACKETID = new Identifier(NyfsEoka.MOD_ID, "spawn_packet");

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.INSTANCE.register(EntityInit.SLUG, FlyingItemEntityRenderer::new);
		predicateInit();
		receiveEntityPacket();
	}
	public void receiveEntityPacket() {
		ClientSidePacketRegistry.INSTANCE.register(PACKETID, (ctx, byteBuf) -> {
			EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
			UUID uuid = byteBuf.readUuid();
			int entityId = byteBuf.readVarInt();
			Vec3d pos = SlugSpawnPacket.PacketBufUtil.readVec3d(byteBuf);
			float pitch = SlugSpawnPacket.PacketBufUtil.readAngle(byteBuf);
			float yaw = SlugSpawnPacket.PacketBufUtil.readAngle(byteBuf);
			ctx.getTaskQueue().execute(() -> {
				if (MinecraftClient.getInstance().world == null)
					throw new IllegalStateException("Tried to spawn entity in a null world!");
				Entity e = et.create(MinecraftClient.getInstance().world);
				if (e == null)
					throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
				e.updateTrackedPosition(pos);
				e.setPos(pos.x, pos.y, pos.z);
				e.setPitch(pitch);
				e.setYaw(yaw);
				e.setId(entityId);
				e.setUuid(uuid);
				MinecraftClient.getInstance().world.addEntity(entityId, e);
			});
		});
	}

	public void predicateInit(){
		FabricModelPredicateProviderRegistry.register(ItemInit.EOKA_ITEM, new Identifier("pull"), (itemStack, clientWorld, livingEntity, i) -> {
			if (livingEntity == null) {
				return 0.0F;
			}
			return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
		});

		FabricModelPredicateProviderRegistry.register(ItemInit.EOKA_ITEM, new Identifier("pulling"), (itemStack, clientWorld, livingEntity, i) -> {
			if (livingEntity == null) {
				return 0.0F;
			}
			return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
		});

		FabricModelPredicateProviderRegistry.register(ItemInit.EOKA_ITEM, new Identifier("charged"), (itemStack, clientWorld, livingEntity, i) -> Eoka.isCharged(itemStack) ? 1.0F : 0.0F);

	}
}

package dev.sterner.book_of_the_dead.common.registry;

import dev.sterner.book_of_the_dead.common.entity.BloodSlimeEntity;
import dev.sterner.book_of_the_dead.common.entity.OldManEntity;
import dev.sterner.book_of_the_dead.common.entity.PlayerCorpseEntity;
import dev.sterner.book_of_the_dead.common.util.Constants;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class BotDEntityTypes {
    private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();




	public static final EntityType<OldManEntity> OLD_MAN_ENTITY =
			register(
					"old_man",
					QuiltEntityTypeBuilder.<OldManEntity>create()
							.spawnGroup(SpawnGroup.AMBIENT)
							.spawnableFarFromPlayer()
							.entityFactory(OldManEntity::new)
							.setDimensions(EntityDimensions.fixed(0.6F, 1.95F))
							.build());

	public static final EntityType<BloodSlimeEntity> BLOOD_SLIME_ENTITY =
			register(
					"blood_slime",
					QuiltEntityTypeBuilder.<BloodSlimeEntity>create()
							.spawnGroup(SpawnGroup.MONSTER)
							.spawnableFarFromPlayer()
							.entityFactory(BloodSlimeEntity::new)
							.setDimensions(EntityDimensions.fixed(0.8F, 0.8F))
							.build());

	public static final EntityType<PlayerCorpseEntity> PLAYER_CORPSE_ENTITY =
			register(
					"corpse_player",
					QuiltEntityTypeBuilder.<PlayerCorpseEntity>create()
							.spawnGroup(SpawnGroup.MISC)
							.entityFactory(PlayerCorpseEntity::new)
							.setDimensions(EntityDimensions.fixed(0.6F, 1.85F))
							.build());


	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
		ENTITY_TYPES.put(type, Constants.id(name));
		return type;
	}

	public static void init() {
		FabricDefaultAttributeRegistry.register(OLD_MAN_ENTITY, OldManEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(BLOOD_SLIME_ENTITY, BloodSlimeEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(PLAYER_CORPSE_ENTITY, PlayerCorpseEntity.createMobAttributes());

		ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
	}
}

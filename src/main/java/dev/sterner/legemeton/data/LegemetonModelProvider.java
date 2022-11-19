package dev.sterner.legemeton.data;

import dev.sterner.legemeton.common.registry.LegemetonObjects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.Models;

public class LegemetonModelProvider extends FabricModelProvider {

	public LegemetonModelProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerDoor(LegemetonObjects.REINFORCED_DOOR);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(LegemetonObjects.LEGEMETON, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.SIGNED_CONTRACT, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.CONTRACT, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.PACKET, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.BUTCHER_KNIFE, Models.HANDHELD);
		itemModelGenerator.register(LegemetonObjects.BLOODY_BUTCHER_KNIFE, Models.HANDHELD);
		itemModelGenerator.register(LegemetonObjects.CAGE, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.HOOK, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.METAL_HOOK, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.FLESH, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.COOKED_FLESH, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.EMERALD_TABLET, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.FAT, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.SKIN, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.BOTTLE_OF_BLOOD, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.ROPE.asItem(), Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.QUICKSILVER, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.SOUL_GEM, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.CINNABAR, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.CELLAR_KEY, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.OLD_LETTER, Models.GENERATED);
		itemModelGenerator.register(LegemetonObjects.RETORT_FLASK, Models.GENERATED);
	}
}

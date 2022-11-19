package dev.sterner.legemeton.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public class ReinforcedBlock extends Block {
	public static final EnumProperty<ReinforcedType> REINFORCED_TYPE = EnumProperty.of("reinforced_type", ReinforcedType.class);
	public ReinforcedBlock(Settings settings) {
		super(settings.requiresTool().strength(55.0F, 1200.0F));
		this.setDefaultState(this.stateManager.getDefaultState().with(REINFORCED_TYPE, ReinforcedType.DEEPSLATE_TILES));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(REINFORCED_TYPE);
	}

	public enum ReinforcedType implements StringIdentifiable {
		DEEPSLATE_TILES,
		SPRUCE_PLANKS;

		@Override
		public String asString() {
			return switch (this) {
				case DEEPSLATE_TILES -> "deepslate_tiles";
				case SPRUCE_PLANKS -> "spruce_planks";
			};
		}
	}
}

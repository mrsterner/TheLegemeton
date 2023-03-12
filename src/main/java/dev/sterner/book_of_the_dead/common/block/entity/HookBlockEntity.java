package dev.sterner.book_of_the_dead.common.block.entity;

import dev.sterner.book_of_the_dead.api.BotDApi;
import dev.sterner.book_of_the_dead.api.block.entity.BaseButcherBlockEntity;
import dev.sterner.book_of_the_dead.api.interfaces.IHauler;
import dev.sterner.book_of_the_dead.common.component.BotDComponents;
import dev.sterner.book_of_the_dead.common.component.PlayerDataComponent;
import dev.sterner.book_of_the_dead.common.registry.BotDBlockEntityTypes;
import dev.sterner.book_of_the_dead.common.registry.BotDEnchantments;
import dev.sterner.book_of_the_dead.common.registry.BotDObjects;
import dev.sterner.book_of_the_dead.common.util.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class HookBlockEntity extends BaseButcherBlockEntity {
	public int hookedAge = 0;

	public HookBlockEntity(BlockPos pos, BlockState state) {
		super(BotDBlockEntityTypes.HOOK, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState tickerState, HookBlockEntity blockEntity) {
		boolean mark = false;

		if (world != null && !world.isClient) {
			if (world.getTime() % 20 == 0 && !blockEntity.storedCorpseNbt.isEmpty()) {
				mark = true;
				if(blockEntity.hookedAge < Constants.Values.BLEEDING){
					blockEntity.hookedAge++;
				}else{
					blockEntity.hookedAge = Constants.Values.BLEEDING;
				}
			}
			if(blockEntity.storedCorpseNbt.isEmpty()){
				blockEntity.hookedAge = 0;
			}
		}
		if(mark){
			markDirty(world, pos, tickerState);
		}
	}

	public ActionResult onUse(World world, BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
		if(hand == Hand.MAIN_HAND){
			boolean isItem = (player.getMainHandStack().isOf(BotDObjects.BUTCHER_KNIFE) || player.getMainHandStack().isOf(BotDObjects.BLOODY_BUTCHER_KNIFE) || EnchantmentHelper.getLevel(BotDEnchantments.BUTCHERING, player.getMainHandStack()) != 0);
			if(isItem){
				List<ItemStack> nonEmptyOutput = this.outputs.stream().filter(item -> !item.isEmpty() || !item.isOf(Items.AIR) || item.getCount() != 0).toList();
				List<Float> nonEmptyChance = this.chances.stream().filter(chance -> chance != 0).toList();
				if(nonEmptyOutput.size() > 0){
					double butcherModifier = BotDComponents.PLAYER_COMPONENT.maybeGet(player).map(PlayerDataComponent::getButcheringModifier).orElse(1D);
					if(world.getRandom().nextFloat() < (0.25f + (hookedAge > 0 ? (float)(Constants.Values.BLEEDING / hookedAge) : 1) * butcherModifier)) {
						if (nonEmptyChance.size() > 0) {
							if (world.getRandom().nextFloat() < nonEmptyChance.get(0)) {
								ItemScatterer.spawn(world, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, nonEmptyOutput.get(0));
							}
						} else {
							ItemScatterer.spawn(world, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, nonEmptyOutput.get(0));
						}
					}
					this.outputs.set(0, ItemStack.EMPTY);
					this.chances.set(0, 0F);
					world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_HONEY_BLOCK_BREAK, SoundCategory.PLAYERS, 2,1);
					world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1,1);
					nonEmptyOutput = this.outputs.stream().filter(item -> !item.isEmpty() || !item.isOf(Items.AIR) || item.getCount() != 0).toList();
					if(nonEmptyOutput.isEmpty()){
						reset();
					}
				}else {
					reset();
				}
			}else{
				IHauler.of(player).ifPresent(hauler -> {
					if(hauler.getCorpseEntity() != null){
						NbtCompound nbtCompound = hauler.getCorpseEntity();
						if(!nbtCompound.isEmpty()){
							setCorpse(nbtCompound);
							hauler.clearCorpseData();
							markDirty();
						}
					}
				});

				refreshButcheringRecipe();
			}
			return ActionResult.CONSUME;
		}
		return ActionResult.PASS;
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.putInt(Constants.Nbt.HOOKED_AGE, this.hookedAge);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.hookedAge = nbt.getInt(Constants.Nbt.HOOKED_AGE);
	}
}

package dev.sterner.legemeton.common.registry;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.villager.api.TradeOfferHelper;


public class LegemetonTrades {
	public static final Int2ObjectMap<TradeOffers.Factory[]> OLD_MAN_TRADES = new Int2ObjectArrayMap<>();

	public static final TradeOffers.Factory LEGEMETON_OFFER = new ItemToItemOffer(new ItemStack(LegemetonObjects.LEGEMETON), LegemetonObjects.OLD_LETTER, 1,5, 0.2F);

	static {
		OLD_MAN_TRADES.put(1, new TradeOffers.Factory[]{LEGEMETON_OFFER});
	}

	public static void init() {
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 1, factories -> {
			factories.add(new EmeraldToItemOffer(new ItemStack(LegemetonObjects.OLD_LETTER, 1), 1, 20 ,5, 0.2F));
		});
	}

	public static class ItemToItemOffer implements TradeOffers.Factory {
		private final Item buy;
		private final ItemStack sell;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public ItemToItemOffer(ItemStack stack, Item buy, int maxUses, int experience, float multiplier) {
			this.sell = stack;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = multiplier;
			this.buy = buy;
		}

		@Nullable
		@Override
		public TradeOffer create(Entity entity, RandomGenerator random) {
			return new TradeOffer(new ItemStack(buy, 1), sell, this.maxUses, this.experience, this.multiplier);
		}
	}

	public static class EmeraldToItemOffer implements TradeOffers.Factory {

		private final ItemStack sell;
		private final int price;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public EmeraldToItemOffer(ItemStack stack, int price, int maxUses, int experience, float multiplier) {
			this.sell = stack;
			this.price = price;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = multiplier;
		}

		@Nullable
		@Override
		public TradeOffer create(Entity entity, RandomGenerator random) {
			return new TradeOffer(new ItemStack(Items.EMERALD, this.price + random.nextInt(3)), sell, this.maxUses, this.experience,
					this.multiplier);
		}
	}
}

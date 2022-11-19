package dev.sterner.legemeton.mixin;

import dev.sterner.legemeton.LegemetonClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Shadow
	public abstract boolean isPaused();

	@Shadow
	private float pausedTickDelta;

	@Shadow
	public abstract float getTickDelta();

	@Shadow
	@Nullable
	public ClientPlayerEntity player;

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;render(FJZ)V"))
	private void onFrameStart(boolean tick, CallbackInfo ci) {
		LegemetonClient.ClientTickHandler.renderTick(isPaused() ? pausedTickDelta : getTickDelta());
	}
}
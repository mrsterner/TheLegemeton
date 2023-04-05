package dev.sterner.book_of_the_dead.client.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sterner.book_of_the_dead.BotDClient;
import dev.sterner.book_of_the_dead.client.screen.BookOfTheDeadScreen;
import dev.sterner.book_of_the_dead.client.screen.tab.BotDTab;
import dev.sterner.book_of_the_dead.common.util.RenderUtils;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.ChatNarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class PageWidget extends ClickableWidget {
	public int h;
	public int w;
	public int u;
	public int v;
	public float x;
	public float y;
	public BookOfTheDeadScreen screen;
	public BotDTab tab;
	public int hoverTick = 0;

	public PageWidget(int x, int y, int u, int v, int w, int h, BotDTab tab, BookOfTheDeadScreen screen) {
		super(x, y, w, h, ChatNarratorManager.NO_TITLE);
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		this.u = u;
		this.v = v;
		this.tab = tab;
		this.screen = screen;
	}

	@Override
	public void drawWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {

		float pulseAlpha = (MathHelper.sin(hoverTick / 20f) + 1) / 2;

		RenderSystem.setShaderTexture(0, BookOfTheDeadScreen.BOOK_TEXTURE);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, isHovered() ? this.alpha : 0.85F);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		// Render the screen texture

		matrices.push();
		if(isHovered()){
			matrices.translate(-pulseAlpha/2, -pulseAlpha/2,0);
			matrices.scale(1 + pulseAlpha * 0.001f,1 + pulseAlpha * 0.001f,1);
		}
		RenderUtils.drawTexture(matrices, x, y, u, v, w, h, 512, 256);
		matrices.pop();
	}

	@Override
	public boolean isHovered() {
		hoverTick++;
		return super.isHovered();
	}

	public void tick(){
		if(!isHovered()){
			hoverTick = 0;
		}
	}

	@Override
	protected void updateNarration(NarrationMessageBuilder builder) {

	}
}
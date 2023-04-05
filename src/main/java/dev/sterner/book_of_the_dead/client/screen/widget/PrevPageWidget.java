package dev.sterner.book_of_the_dead.client.screen.widget;

import dev.sterner.book_of_the_dead.client.screen.BookOfTheDeadScreen;
import dev.sterner.book_of_the_dead.client.screen.tab.BotDTab;
import dev.sterner.book_of_the_dead.client.screen.tab.BookTab;

public class PrevPageWidget extends PageWidget {

	public PrevPageWidget(int x, int y, BotDTab tab, BookOfTheDeadScreen screen) {
		super(x, y, 273 + 19, 0, 18, 9, tab, screen);
	}

	@Override
	public void onClick(double mouseX, double mouseY) {
		if (isValidClickButton(0)) {
			if(tab instanceof BookTab bookTab){
				screen.clearChildren();
				bookTab.previousPage();
				screen.initialize();
			}
			if(tab != null && tab.getPrevTab() != null){
				screen.clearChildren();
				screen.tab = tab.getPrevTab();
				screen.initialize();
			}
		}
	}
}
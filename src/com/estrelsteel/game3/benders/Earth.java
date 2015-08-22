package com.estrelsteel.game3.benders;

import com.estrelsteel.game3.inventory.Inventory;
import com.estrelsteel.game3.item.Item;
import com.estrelsteel.game3.character.Player;

public class Earth extends BasicBender {
	
	public Earth(int maxMaterial, int minMaterial, int materialValue) {
		super(maxMaterial, minMaterial, materialValue, "EARTH");
	}
	
	public Inventory configureInventory(Player player) {
		Inventory inventory = player.getInventory();
		inventory.addInventoryItem(Item.EARTH_REMOVE);
		inventory.addInventoryItem(Item.EARTH_RESTORE);
		inventory.addInventoryItem(Item.EARTH_PLACE);
		return inventory;
	}
}

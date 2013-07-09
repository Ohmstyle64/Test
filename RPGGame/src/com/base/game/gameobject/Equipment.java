package com.base.game.gameobject;

import com.base.game.gameobject.item.EquipableItem;

public class Equipment {
	
	private EquipableItem[] items;
	private Inventory inv;
	
	public Equipment(Inventory inv) {
		
		this.inv = inv;
		items = new EquipableItem[EquipableItem.NUM_SLOTS];
	}
	
	public boolean Equip(EquipableItem item) {
		
		int index = item.getSlot();
		
		inv.remove(item);
		
		if(items[index] != null) {
			if(!DeEquip(index))
				return false;
		}
		
		items[index] = item;
		return true;
	}
	
	public boolean DeEquip(int slot) {
		
		if(inv.add(items[slot])) {
			items[slot] = null;
			return true;
		}
		return false;
	}
	
	
}

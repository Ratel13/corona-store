--[[
  Copyright (C) 2014 Soomla Inc.
 
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
]]--


--[[
	Bruno Pinheiro:

	This not another tavern in city of Berlock. In fact, since the new laws to purchase Armors and Weapons 
	have entered into force, many of the establishments are not what they appears to be. The establishments owners
	are trying to sell as many illegal weapons and armors as possible. But, specifically this one, is special. There 
	are a lot of unimaginable things being sold: Potions, Magical Carpets, Magical Scrolls, Baby dragons and so on.

	Be careful. This tavern attracts not only the eyes of the law, but the eyes of unknown creatures that live in the 
	shadows and in the Deadly Fire Mountains of Berlock.
]]--

local soomla = require "plugin.soomla"
local AppId = "com.soom.la.TheTavern"

local TheTavern = {}
TheTavern.version = 1
TheTavern.CUSTOM_SECRET = "The_Darkness_is_comming"
TheTavern.GOOGLE_PLAY_KEY = "Testing"

-- Currencies

TheTavern.CURRENCY_GOLD_ID = soomla.createCurrency({
	name = "Golden Coins",
	description = "\"You don't know what this is, do ye? This is Aztec Gold\" - Captain Barbossa",
	itemId = "currency_gold"	
})

TheTavern.CURRENCY_SKILLPOINTS_ID = soomla.createCurrency({
	name = "Skill Points",
	description = "",
	itemId = "currency_skillpoints"
})

-- Currency Packs
TheTavern.CURRENCYPACK_GOLD_100_ID = soomla.createCurrencyPack({
	name = "100 Golden Coins",
	description = "A pack of 100 Golden Coins",
	itemId = "currencypack_gold_100",
	currency = TheTavern.CURRENCY_GOLD_ID,
	amount = 100,
	purchase = {
		purchaseType = "market",
		product = {
			id = AppId .. ".currencypack_gold_100",
			consumption = "consumable",
			price = 0.99
		}
	}
})

TheTavern.CURRENCYPACK_GOLD_500_ID = soomla.createCurrencyPack({
	name = "500 Golden Coins",
	description = "A pack of 500 Golden Coins",
	itemId = "currencypack_gold_500",
	currency = TheTavern.CURRENCY_GOLD_ID,
	amount = 500,
	purchase = {
		purchaseType = "market",
		product = {
			id = AppId .. ".currencypack_gold_500",
			consumption = "consumable",
			price = 1.99
		}
	}
})

TheTavern.CURRENCYPACK_GOLD_2000_ID = soomla.createCurrencyPack({
	name = "2000 Golden Coins",
	description = "A pack of 2000 Golden Coins",
	itemId = "currencypack_gold_2000",
	currency = TheTavern.CURRENCY_GOLD_ID,
	amount = 2000,
	purchase = {
		purchaseType = "market",
		product = {
			id = AppId .. ".currencypack_gold_2000",
			consumption = "consumable",
			price = 2.99
		}
	}
})

TheTavern.CURRENCYPACK_GOLD_5000_ID = soomla.createCurrencyPack({
	name = "5000 Golden Coins",
	description = "A pack of 5000 Golden Coins",
	itemId = "currencypack_gold_5000",
	currency = TheTavern.CURRENCY_GOLD_ID,
	amount = 5000,
	purchase = {
		purchaseType = "market",
		product = {
			id = AppId .. ".currencypack_gold_5000",
			consumption = "consumable",
			price = 3.99
		}
	}
})


-- Single Use
TheTavern.SINGLEUSE_MUGOFBEER_ID = soomla.createSingleUseVG({
	name = "A mug of beer",
	description = "Restores 20 of HP",
	itemId = "singleuse_mugofbeer",
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_GOLD_ID,
			amount = 5
		}
	}
})

TheTavern.SINGLEUSE_BOTTLEOFBEER_ID = soomla.createSingleUseVG({
	name = "A bottle of beer",
	description = "Restores 50 of HP",
	itemId = "singleuse_bottleofbeer",
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_GOLD_ID,
			amount = 10
		}
	}
})

TheTavern.SINGLEUSE_MAGICALPOTION_ID = soomla.createSingleUseVG({
	name = "A magical potion",
	description = "Restores 100 of HP",
	itemId = "singleuse_magicalpotion",
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_GOLD_ID,
			amount = 15
		}
	}
})

TheTavern.SINGLEUSE_MAGICALANTIDOTE_ID = soomla.createSingleUseVG({
	name = "A magical antidote",
	description = "Restores 100 of HP and cures Poison",
	itemId = "singleuse_magicalantidote",
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_GOLD_ID,
			amount = 20
		}
	}
})

TheTavern.SINGLEUSE_GOBLINGRENADE_ID = soomla.createSingleUseVG({
	name = "A Goblin Grenade",
	description = "Deals 10 of damage to target criature",
	itemId = "singleuse_goblingrenade",
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_GOLD_ID,
			amount = 20
		}
	}
})

TheTavern.SINGLEUSE_POISONEDARROW_ID = soomla.createSingleUseVG({
	name = "A poisoned arrow",
	description = "Deals 2 of damage and causes poison",
	itemId = "singleuse_poisonedarrow",
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_GOLD_ID,
			amount = 20
		}
	}
})

-- Upgrades
TheTavern.UPGRADE_MAGICALPOTION_1_ID = "upgrade_potion_1"
TheTavern.UPGRADE_MAGICALPOTION_2_ID = "upgrade_potion_2"
TheTavern.UPGRADE_MAGICALPOTION_3_ID = "upgrade_potion_3"
TheTavern.UPGRADE_POISONEDARROW_1_ID = "upgrade_poisonedarrow_1"
TheTavern.UPGRADE_POISONEDARROW_2_ID = "upgrade_poisonedarrow_2"
TheTavern.UPGRADE_POISONEDARROW_3_ID = "upgrade_poisonedarrow_3"
TheTavern.UPGRADE_POISONEDARROW_4_ID = "upgrade_poisonedarrow_4"
TheTavern.UPGRADE_GOBLINGRENADE_1_ID = "upgrade_goblingrenade_1"
TheTavern.UPGRADE_GOBLINGRENADE_2_ID = "upgrade_goblingrenade_2"

soomla.createUpgradeVG({
	name = "Potion Level 1",
	description = "Restores 120 of HP",
	itemId = TheTavern.UPGRADE_MAGICALPOTION_1_ID,
	linkedGood = TheTavern.SINGLEUSE_MAGICALPOTION_ID,
	previousUpgrade = "",
	nextUpgrade = TheTavern.UPGRADE_MAGICALPOTION_2_ID,
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_SKILLPOINTS_ID,
			amount = 150
		}
	}
})

soomla.createUpgradeVG({
	name = "Potion Level 2",
	description = "Restores 150 of HP",
	itemId = TheTavern.UPGRADE_MAGICALPOTION_2_ID,
	linkedGood = TheTavern.SINGLEUSE_MAGICALPOTION_ID,
	previousUpgrade = TheTavern.UPGRADE_MAGICALPOTION_1_ID,
	nextUpgrade = TheTavern.UPGRADE_MAGICALPOTION_3_ID,
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_SKILLPOINTS_ID,
			amount = 200
		}
	}
})

soomla.createUpgradeVG({
	name = "Potion Level 3",
	description = "Restores 250 of HP",
	itemId = TheTavern.UPGRADE_MAGICALPOTION_3_ID,
	linkedGood = TheTavern.SINGLEUSE_MAGICALPOTION_ID,
	previousUpgrade = TheTavern.UPGRADE_MAGICALPOTION_2_ID,
	nextUpgrade = "",
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_SKILLPOINTS_ID,
			amount = 250
		}
	}
})

soomla.createUpgradeVG({
	name = "Poisoned Arrow Level 2",
	description = "Deals 5 of damage and causes poison",
	itemId = TheTavern.UPGRADE_POISONEDARROW_1_ID,
	linkedGood = TheTavern.SINGLEUSE_POISONEDARROW_ID,
	previousUpgrade = "",
	nextUpgrade = TheTavern.UPGRADE_POISONEDARROW_2_ID,
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_SKILLPOINTS_ID,
			amount = 50
		}
	}
})

soomla.createUpgradeVG({
	name = "Poisoned Arrow Level 3",
	description = "Deals 10 of damage and causes poison",
	itemId = TheTavern.UPGRADE_POISONEDARROW_2_ID,
	linkedGood = TheTavern.SINGLEUSE_POISONEDARROW_ID,
	previousUpgrade = TheTavern.UPGRADE_POISONEDARROW_1_ID,
	nextUpgrade = TheTavern.UPGRADE_POISONEDARROW_3_ID,
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_SKILLPOINTS_ID,
			amount = 100
		}
	}
})

soomla.createUpgradeVG({
	name = "Poisoned Arrow Level 4",
	description = "Deals 20 of damage and causes poison",
	itemId = TheTavern.UPGRADE_POISONEDARROW_3_ID,
	linkedGood = TheTavern.SINGLEUSE_POISONEDARROW_ID,
	previousUpgrade = TheTavern.UPGRADE_POISONEDARROW_2_ID,
	nextUpgrade = TheTavern.UPGRADE_POISONEDARROW_4_ID,
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_SKILLPOINTS_ID,
			amount = 150
		}
	}
})

soomla.createUpgradeVG({
	name = "Poisoned Arrow Level 5",
	description = "Deals 50 of damage and causes poison",
	itemId = TheTavern.UPGRADE_POISONEDARROW_4_ID,
	linkedGood = TheTavern.SINGLEUSE_POISONEDARROW_ID,
	previousUpgrade = TheTavern.UPGRADE_POISONEDARROW_3_ID,
	nextUpgrade = "",
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_SKILLPOINTS_ID,
			amount = 350
		}
	}
})

soomla.createUpgradeVG({
	name = "Goblin Grenade Level 2",
	description = "Deals 20 of damage",
	itemId = TheTavern.UPGRADE_GOBLINGRENADE_1_ID,
	linkedGood = TheTavern.SINGLEUSE_GOBLINGRENADE_ID,
	previousUpgrade = "",
	nextUpgrade = TheTavern.UPGRADE_GOBLINGRENADE_2_ID,
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_SKILLPOINTS_ID,
			amount = 50
		}
	}
})

soomla.createUpgradeVG({
	name = "Goblin Grenade Level 3",
	description = "Deals 60 of damage",
	itemId = TheTavern.UPGRADE_GOBLINGRENADE_2_ID,
	linkedGood = TheTavern.SINGLEUSE_GOBLINGRENADE_ID,
	previousUpgrade = TheTavern.UPGRADE_GOBLINGRENADE_1_ID,
	nextUpgrade = "",
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_SKILLPOINTS_ID,
			amount = 120
		}
	}
})


-- Single Use Pack
TheTavern.SINGLEUSEPACK_MUGSOFBEER_ID = soomla.createSingleUsePackVG({
	name = "10 mugs of beer",
	description = "A pack of 10 mugs of beer",
	itemId = "singleusepack_mugsofbeer",
	singleUseGood = TheTavern.SINGLEUSE_MUGOFBEER_ID,
	amount = 10,
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_GOLD_ID,
			amount = 35
		}
	}
})

TheTavern.SINGLEUSEPACK_GOBLINGRENADES_ID = soomla.createSingleUsePackVG({
	name = "10 goblin grenades",
	description = "A pack of 10 goblin grenades",
	itemId = "singleusepack_goblingrenades",
	singleUseGood = TheTavern.SINGLEUSE_GOBLINGRENADE_ID,
	amount = 10,
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_GOLD_ID,
			amount = 150
		}
	}
})


-- Equippable
TheTavern.EQUIPPABLE_IRONSWORD_ID = soomla.createEquippableVG({
    name = "Iron Sword",
    description = "+ 5 Strength / + 1 Attack Range",
    itemId = "equippable_ironsword",
    equipModel = "category",
    purchase = {
    	purchaseType = "virtualItem",
    	exchangeCurrency = {
    		itemId = TheTavern.CURRENCY_GOLD_ID,
    		amount = 70
    	}
	}
})

TheTavern.EQUIPPABLE_BRONZESPEAR_ID = soomla.createEquippableVG({
    name = "Bronze Spear",
    description = "+ 7 Strength / + 2 Attack Range",
    itemId = "equippable_bronzespear",
    equipModel = "category",
    purchase = {
    	purchaseType = "virtualItem",
    	exchangeCurrency = {
    		itemId = TheTavern.CURRENCY_GOLD_ID,
    		amount = 120
    	}
	}
})

TheTavern.EQUIPPABLE_IRONHELMET_ID = soomla.createEquippableVG({
    name = "Iron Helmet",
    description = "+ 3 Defense",
    itemId = "equippable_ironhelmet",
    equipModel = "category",
    purchase = {
    	purchaseType = "virtualItem",
    	exchangeCurrency = {
    		itemId = TheTavern.CURRENCY_GOLD_ID,
    		amount = 50
    	}
	}
})

TheTavern.EQUIPPABLE_NECKLACE_ID = soomla.createEquippableVG({
    name = "Necklace",
    description = "+ 8 Magic",
    itemId = "equippable_necklace",
    equipModel = "local",
    purchase = {
    	purchaseType = "virtualItem",
    	exchangeCurrency = {
    		itemId = TheTavern.CURRENCY_GOLD_ID,
    		amount = 80
    	}
	}
})

TheTavern.EQUIPPABLE_IRONSET_ID = soomla.createEquippableVG({
    name = "Complete Iron Set",
    description = "+ 3 Defense / + 5 Attack / + 1 Attack Range",
    itemId = "equippable_ironset",
    equipModel = "global",
    purchase = {
    	purchaseType = "virtualItem",
    	exchangeCurrency = {
    		itemId = TheTavern.CURRENCY_GOLD_ID,
    		amount = 50
    	}
	}
})

TheTavern.EQUIPPABLE_IRONCHEST_ID = soomla.createEquippableVG({
	name = "Iron Chest",
    description = "+ 5 Defense",
    itemId = "equippable_ironchest",
    equipModel = "category",
    purchase = {
    	purchaseType = "virtualItem",
    	exchangeCurrency = {
    		itemId = TheTavern.CURRENCY_GOLD_ID,
    		amount = 60
    	}
	}
})

TheTavern.EQUIPPABLE_BRONZECHEST_ID = soomla.createEquippableVG({
	name = "Bronze Chest",
    description = "+ 10 Defense",
    itemId = "equippable_bronzechest",
    equipModel = "category",
    purchase = {
    	purchaseType = "virtualItem",
    	exchangeCurrency = {
    		itemId = TheTavern.CURRENCY_GOLD_ID,
    		amount = 150
    	}
	}
})

-- Categories
TheTavern.CATEGORY_EQUIPPABLE_HAND = soomla.createCategory({
	name = "Hand",
	items = {
		TheTavern.EQUIPPABLE_BRONZESPEAR_ID,
		TheTavern.EQUIPPABLE_IRONSWORD_ID
	}
})

TheTavern.CATEGORY_EQUIPPABLE_CHEST = soomla.createCategory({
	name = "Chest",
	items = {
		TheTavern.EQUIPPABLE_IRONCHEST_ID,
		TheTavern.EQUIPPABLE_BRONZECHEST_ID
	}
})

TheTavern.CATEGORY_EQUIPPABLE_HELMET = soomla.createCategory({
	name = "Head",
	items = {
		TheTavern.EQUIPPABLE_IRONHELMET_ID
	}
})

TheTavern.CATEGORY_EQUIPPABLE_ACCESSORIES = soomla.createCategory({
	name = "Accessories",
	items = {
		TheTavern.EQUIPPABLE_NECKLACE_ID
	}
})

TheTavern.CATEGORY_EQUIPPABLE_SET = soomla.createCategory({
	name = "Set",
	items = {
		TheTavern.EQUIPPABLE_IRONSET_ID
	}
})

-- Lifetime
TheTavern.LIFETIME_BLESSING_ID = soomla.createLifetimeVG({
	name = "Blessing",
	description = "Reduces 10 of Shadow damages",
	itemId = "lifetime_blessing",
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_SKILLPOINTS_ID,
			amount = 100
		}
	}
})

TheTavern.LIFETIME_FIREBALL_ID = soomla.createLifetimeVG({
	name = "Fireball",
	description = "Deals 20 of damage",
	itemId = "lifetime_fireball",
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_SKILLPOINTS_ID,
			amount = 50
		}
	}
})

TheTavern.LIFETIME_DOUBLEJUMP_ID = soomla.createLifetimeVG({
	name = "Double Jump",
	description = "Enables Double Jump",
	itemId = "lifetime_doublejump",
	purchase = {
		purchaseType = "virtualItem",
		exchangeCurrency = {
			itemId = TheTavern.CURRENCY_SKILLPOINTS_ID,
			amount = 80
		}
	}
})

-- Non Consumable Item
TheTavern.NONCONSUMABLE_NOADS_ID = soomla.createNonConsumableItem({
	name = "Ads-Free",
	description = "Hide the Game Ads",
	itemId = "nonconsumable_noads",
	purchase = {
		purchaseType = "market",
		product = {
			id = AppId .. ".noads",
			consumption = "nonConsumable",
			price = 1.99
		}
	}
})



-- Making all the items available
TheTavern.virtualGoods = {
	-- single use
	TheTavern.SINGLEUSE_MUGOFBEER_ID, 
	TheTavern.SINGLEUSE_BOTTLEOFBEER_ID, 
	TheTavern.SINGLEUSE_MAGICALPOTION_ID, 
	TheTavern.SINGLEUSE_MAGICALANTIDOTE_ID,
	TheTavern.SINGLEUSE_POISONEDARROW_ID, 
	TheTavern.SINGLEUSE_GOBLINGRENADE_ID, 

	-- single use pack
	TheTavern.SINGLEUSEPACK_GOBLINGRENADES_ID,
	TheTavern.SINGLEUSEPACK_MUGSOFBEER_ID,

	-- equipment
	TheTavern.EQUIPPABLE_IRONSWORD_ID, 
	TheTavern.EQUIPPABLE_BRONZESPEAR_ID, 
	TheTavern.EQUIPPABLE_IRONSET_ID, 
	TheTavern.EQUIPPABLE_NECKLACE_ID, 
	TheTavern.EQUIPPABLE_IRONHELMET_ID,
	TheTavern.EQUIPPABLE_IRONCHEST_ID,
	TheTavern.EQUIPPABLE_BRONZECHEST_ID,

	-- lifetime
	TheTavern.LIFETIME_BLESSING_ID, 
	TheTavern.LIFETIME_FIREBALL_ID, 
	TheTavern.LIFETIME_DOUBLEJUMP_ID,

	-- Upgrades
  TheTavern.UPGRADE_MAGICALPOTION_1_ID,
  TheTavern.UPGRADE_MAGICALPOTION_2_ID,
  TheTavern.UPGRADE_MAGICALPOTION_3_ID,
  TheTavern.UPGRADE_POISONEDARROW_1_ID,
  TheTavern.UPGRADE_POISONEDARROW_2_ID,
  TheTavern.UPGRADE_POISONEDARROW_3_ID,
  TheTavern.UPGRADE_POISONEDARROW_4_ID,
  TheTavern.UPGRADE_GOBLINGRENADE_1_ID,
  TheTavern.UPGRADE_GOBLINGRENADE_2_ID
}

TheTavern.virtualCurrencies = {
	TheTavern.CURRENCY_GOLD_ID, 
	TheTavern.CURRENCY_SKILLPOINTS_ID
}

TheTavern.virtualCurrencyPacks = {
	TheTavern.CURRENCYPACK_GOLD_100_ID, 
	TheTavern.CURRENCYPACK_GOLD_500_ID, 
	TheTavern.CURRENCYPACK_GOLD_2000_ID, 
	TheTavern.CURRENCYPACK_GOLD_5000_ID
}

TheTavern.virtualCategories = {
	TheTavern.CATEGORY_EQUIPPABLE_HAND, 
	TheTavern.CATEGORY_EQUIPPABLE_CHEST,
	TheTavern.CATEGORY_EQUIPPABLE_HELMET,
	TheTavern.CATEGORY_EQUIPPABLE_SET,
	TheTavern.CATEGORY_EQUIPPABLE_ACCESSORIES
}

TheTavern.nonConsumableItems = {
	TheTavern.NONCONSUMABLE_NOADS_ID
}

soomla.initializeStore(TheTavern)

return TheTavern
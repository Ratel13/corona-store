//
//  LuaLoader.java
//  TemplateApp
//
//  Copyright (c) 2014 Soomla. All rights reserved.
//

package plugin.soomla;

import android.app.Activity;
import android.util.Log;
import android.view.ViewDebug;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.NamedJavaFunction;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.ansca.corona.CoronaLua;
import com.ansca.corona.CoronaRuntime;
import com.ansca.corona.CoronaRuntimeListener;

import com.soomla.store.billing.google.*;
import com.soomla.corona.Map_Lua;
import com.soomla.corona.ArrayList_Lua;
import com.soomla.corona.store.domain.LuaJSON;
import com.soomla.corona.store.CoronaSoomlaStore;
import com.soomla.store.domain.*;
import com.soomla.store.purchaseTypes.*;
import com.soomla.store.SoomlaStore;
import com.soomla.store.StoreInventory;
import com.soomla.store.data.StoreInfo;
import com.soomla.store.domain.virtualCurrencies.VirtualCurrency;
import com.soomla.store.domain.virtualCurrencies.VirtualCurrencyPack;
import com.soomla.store.domain.virtualGoods.EquippableVG;
import com.soomla.store.domain.virtualGoods.LifetimeVG;
import com.soomla.store.domain.virtualGoods.SingleUsePackVG;
import com.soomla.store.domain.virtualGoods.SingleUseVG;
import com.soomla.store.domain.virtualGoods.UpgradeVG;

import org.json.JSONObject;

import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * Implements the Lua interface for a Corona plugin.
 * <p>
 * Only one instance of this class will be created by Corona for the lifetime of the application.
 * This instance will be re-used for every new Corona activity that gets created.
 */
public class LuaLoader implements JavaFunction, CoronaRuntimeListener {

	public LuaLoader() {
		CoronaEnvironment.addRuntimeListener(this);
	}

    /// Creating Models
    private static void addVirtualItemForState(VirtualItem virtualItem,LuaState L) {
        CoronaSoomlaStore.getInstance().addVirtualItem(virtualItem);
        L.pushString(virtualItem.getName());
    }

    private static void handleModelFailure(LuaState L,String modelName) {
        System.out.println("SOOMLA: " + modelName + " couldn't be created.");
        L.pushNil();
    }

    private static Map<String,Object> getMapFromLua(LuaState L) {
        return Map_Lua.mapFromLua(L,L.getTop());
    }

    public static int getVirtualItem(LuaState L) {
        String itemId = L.toString(-1);
        try {
            VirtualItem virtualItem = StoreInfo.getVirtualItem(itemId);
            Map<String,Object> map = null;
            if(virtualItem instanceof SingleUseVG) map = LuaJSON.singleUseMap((SingleUseVG)virtualItem);
            else if(virtualItem instanceof SingleUsePackVG) map = LuaJSON.singleUsePackMap((SingleUsePackVG)virtualItem);
            else if(virtualItem instanceof EquippableVG) map = LuaJSON.equippableMap((EquippableVG)virtualItem);
            else if(virtualItem instanceof UpgradeVG) map = LuaJSON.upgradeMap((UpgradeVG)virtualItem);
            else if(virtualItem instanceof LifetimeVG) map = LuaJSON.lifetimeMap((LifetimeVG)virtualItem);
            else if(virtualItem instanceof NonConsumableItem) map = LuaJSON.nonConsumableItemMap((NonConsumableItem)virtualItem);
            else if(virtualItem instanceof VirtualCurrency) map = LuaJSON.currencyMap((VirtualCurrency) virtualItem);
            else if(virtualItem instanceof VirtualCurrencyPack) map = LuaJSON.currencyPackMap((VirtualCurrencyPack)virtualItem);
            Map_Lua.mapToLua(map,L);
        } catch (Exception e) {
            System.out.println("SOOMLA: VirtualItem with itemId=" + itemId + " couldn't be found!");
            L.pushNil();
        }
        return 1;
    }

    /// Wrappers
    private class GetVirtualItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "getVirtualItem"; }
        @Override public int invoke(LuaState L) { return getVirtualItem(L); }
    }

    private class CreateCurrencyWrapper implements NamedJavaFunction {
        @Override public String getName() { return "createCurrency"; }
        @Override public int invoke(LuaState L) {
            try {
                Map<String,Object> map = LuaLoader.getMapFromLua(L);
                JSONObject json = LuaJSON.currencyJSON(map);
                VirtualCurrency currency = new VirtualCurrency(json);
                LuaLoader.addVirtualItemForState(currency,L);
            } catch(Exception e) {
                LuaLoader.handleModelFailure(L,"Currency");
                System.out.println(e.getMessage());
            }
            return 1;
        }
    }

    private class GetCurrencyWrapper implements NamedJavaFunction {
        @Override public String getName() { return "getCurrency"; }
        @Override public int invoke(LuaState L) { return LuaLoader.getVirtualItem(L); }
    }

    private class CreateCurrencyPackWrapper implements NamedJavaFunction {
        @Override public String getName() { return "createCurrencyPack"; }
        @Override public int invoke(LuaState L) {
            try {
                Map<String,Object> map = LuaLoader.getMapFromLua(L);
                JSONObject json = LuaJSON.currencyPackJSON(map);
                VirtualCurrencyPack currencyPack = new VirtualCurrencyPack(json);
                LuaLoader.addVirtualItemForState(currencyPack,L);
            } catch(Exception e) {
                LuaLoader.handleModelFailure(L,"CurrencyPack");
                System.out.println(e.getMessage());
            }
            return 1;
        }
    }

    private class GetCurrencyPackWrapper implements NamedJavaFunction {
        @Override public String getName() { return "getCurrencyPack"; }
        @Override public int invoke(LuaState L) { return LuaLoader.getVirtualItem(L); }
    }

    private class CreateSingleUseVGWrapper implements NamedJavaFunction {
        @Override public String getName() { return "createSingleUseVG"; }
        @Override public int invoke(LuaState L) {
            try {
                Map<String,Object> map = LuaLoader.getMapFromLua(L);
                JSONObject json = LuaJSON.singleUseJSON(map);
                SingleUseVG singleUse = new SingleUseVG(json);
                LuaLoader.addVirtualItemForState(singleUse,L);
            } catch(Exception e) {
                LuaLoader.handleModelFailure(L,"SingleUseVG");
                System.out.println(e.getMessage());
            }
            return 1;
        }
    }

    private class GetSingleUseVGWrapper implements NamedJavaFunction {
        @Override public String getName() { return "getSingleUseVG"; }
        @Override public int invoke(LuaState L) { return LuaLoader.getVirtualItem(L); }
    }

    private class CreateLifetimeVGWrapper implements NamedJavaFunction {
        @Override public String getName() { return "createLifetimeVG"; }
        @Override public int invoke(LuaState L) {
            try {
                Map<String,Object> map = LuaLoader.getMapFromLua(L);
                JSONObject json = LuaJSON.lifetimeJSON(map);
                LifetimeVG lifetime = new LifetimeVG(json);
                LuaLoader.addVirtualItemForState(lifetime,L);
            } catch(Exception e) {
                LuaLoader.handleModelFailure(L,"LifetimeVG");
                System.out.println(e.getMessage());
            }
            return 1;
        }
    }

    private class GetLifetimeVGWrapper implements NamedJavaFunction {
        @Override public String getName() { return "getLifetimeVG"; }
        @Override public int invoke(LuaState L) { return LuaLoader.getVirtualItem(L); }
    }

    private class CreateEquippableVGWrapper implements NamedJavaFunction {
        @Override public String getName() { return "createEquippableVG"; }
        @Override public int invoke(LuaState L) {
            try {
                Map<String,Object> map = LuaLoader.getMapFromLua(L);
                JSONObject json = LuaJSON.equippableJSON(map);
                EquippableVG equippable = new EquippableVG(json);
                LuaLoader.addVirtualItemForState(equippable,L);
            } catch(Exception e) {
                LuaLoader.handleModelFailure(L,"EquippableVG");
                System.out.println(e.getMessage());
            }
            return 1;
        }
    }

    private class GetEquippableVGWrapper implements NamedJavaFunction {
        @Override public String getName() { return "getEquippableVG"; }
        @Override public int invoke(LuaState L) { return LuaLoader.getVirtualItem(L); }
    }

    private class CreateSingleUsePackVGWrapper implements NamedJavaFunction {
        @Override public String getName() { return "createSingleUsePackVG"; }
        @Override public int invoke(LuaState L) {
            try {
                Map<String,Object> map = LuaLoader.getMapFromLua(L);
                JSONObject json = LuaJSON.singleUsePackJSON(map);
                SingleUsePackVG singleUsePack = new SingleUsePackVG(json);
                LuaLoader.addVirtualItemForState(singleUsePack,L);
            } catch(Exception e) {
                LuaLoader.handleModelFailure(L,"SingleUsePackVG");
                System.out.println(e.getMessage());
            }
            return 1;
        }
    }

    private class GetSingleUsePackVGWrapper implements NamedJavaFunction {
        @Override public String getName() { return "getSingleUsePackVG"; }
        @Override public int invoke(LuaState L) { return LuaLoader.getVirtualItem(L); }
    }

    private class CreateUpgradeVGWrapper implements NamedJavaFunction {
        @Override public String getName() { return "createUpgradeVG"; }
        @Override public int invoke(LuaState L) {
            try {
                Map<String,Object> map = LuaLoader.getMapFromLua(L);
                JSONObject json = LuaJSON.upgradeJSON(map);
                UpgradeVG upgrade = new UpgradeVG(json);
                LuaLoader.addVirtualItemForState(upgrade,L);
            } catch(Exception e) {
                LuaLoader.handleModelFailure(L,"UpgradeVG");
                System.out.println(e.getMessage());
            }
            return 1;
        }
    }

    private class GetUpgradeVGWrapper implements NamedJavaFunction {
        @Override public String getName() { return "getUpgradeVG"; }
        @Override public int invoke(LuaState L) { return LuaLoader.getVirtualItem(L); }
    }

    private class CreateNonConsumableItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "createNonConsumableItem"; }
        @Override public int invoke(LuaState L) {
            try {
                Map<String,Object> map = LuaLoader.getMapFromLua(L);
                JSONObject json = LuaJSON.nonConsumableItemJSON(map);
                NonConsumableItem nonConsumable = new NonConsumableItem(json);
                LuaLoader.addVirtualItemForState(nonConsumable,L);
            } catch(Exception e) {
                LuaLoader.handleModelFailure(L,"NonConsumableItem");
                System.out.println(e.getMessage());
            }
            return 1;
        }
    }

    private class GetNonConsumableItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "getNonConsumableItem"; }
        @Override public int invoke(LuaState L) { return LuaLoader.getVirtualItem(L); }
    }

    private class CreateVirtualCategoryWrapper implements NamedJavaFunction {
        @Override public String getName() { return "createCategory"; }
        @Override public int invoke(LuaState L) {
            try {
                Map<String,Object> map = LuaLoader.getMapFromLua(L);
                JSONObject json = LuaJSON.categoryJSON(map);
                VirtualCategory category = new VirtualCategory(json);
                CoronaSoomlaStore.getInstance().addVirtualCategory(category);
                L.pushString(category.getName());
            } catch(Exception e) {
                LuaLoader.handleModelFailure(L,"Category");
                System.out.println(e.getMessage());
            }
            return 1;
        }
    }

    private class GetVirtualCategoryWrapper implements NamedJavaFunction {
        @Override public String getName() { return "getCategory"; }
        @Override public int invoke(LuaState L) {
            String name = L.toString(-1);
            try {
                VirtualCategory category = CoronaSoomlaStore.getInstance().getCategory(name);
                Map<String,Object> map = LuaJSON.categoryMap(category);
                Map_Lua.mapToLua(map,L);
            } catch (Exception e) {
                System.out.println("SOOMLA: VirtualCategory with name=" + name + " couldn't be find!");
                L.pushNil();
            }
            return 1;
        }
    }

    private class InitializeStoreWrapper implements NamedJavaFunction {
        @Override public String getName() { return "initializeStore"; }
        @Override public int invoke(LuaState L) {
            System.out.println("SOOMLA: Trying to initialize the store...");
            Map<String,Object> map = LuaLoader.getMapFromLua(L);
            try {
                CoronaSoomlaStore.getInstance().initialize(map);
            } catch (Exception e) {
                System.out.println("SOOMLA: It was not possible to initialize the store.");
            }
            return 0;
        }
    }

    private class CanBuyWrapper implements NamedJavaFunction {
        @Override public String getName() { return "canBuyItem"; }
        @Override public int invoke(LuaState L) {
            boolean canBuy = false;
            String itemId = L.toString(-1);
            try {
                PurchasableVirtualItem virtualItem = (PurchasableVirtualItem)StoreInfo.getVirtualItem(itemId);
                if(virtualItem.getPurchaseType() instanceof PurchaseWithVirtualItem) {
                    PurchaseWithVirtualItem purchase = (PurchaseWithVirtualItem)virtualItem.getPurchaseType();
                    int balance = StoreInventory.getVirtualItemBalance(purchase.getTargetItemId());
                    canBuy = balance >= purchase.getAmount();
                }
                else canBuy = true;
            } catch(Exception e) { canBuy = false; }
            L.pushBoolean(canBuy);
            return 1;
        }
    }

    private class GetItemBalanceWrapper implements NamedJavaFunction {
        @Override public String getName() { return "getItemBalance"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                int balance = StoreInventory.getVirtualItemBalance(itemId);
                L.pushInteger(new Integer(balance));
            }
            catch (Exception e) { L.pushInteger(0); }
            return 1;
        }
    }

    private class IsItemEquippedWrapper implements NamedJavaFunction {
        @Override public String getName() { return "isItemEquipped"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                boolean equipped = StoreInventory.isVirtualGoodEquipped(itemId);
                L.pushBoolean(equipped);
            }
            catch (Exception e) { L.pushBoolean(false); }
            return 1;
        }
    }

    private class CategoryForItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "categoryForItem"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                VirtualCategory category = StoreInfo.getCategory(itemId);
                Map<String,Object> map = LuaJSON.categoryMap(category);
                Map_Lua.mapToLua(map,L);
            } catch (Exception e) { L.pushNil(); }
            return 1;
        }
    }

    private class FirstUpgradeForItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "firstUpgradeForItem"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                UpgradeVG upgrade = StoreInfo.getGoodFirstUpgrade(itemId);
                Map<String,Object> map = LuaJSON.upgradeMap(upgrade);
                Map_Lua.mapToLua(map,L);
            } catch(Exception e) { L.pushNil(); }
            return 1;
        }
    }

    private class LastUpgradeForItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "lastUpgradeForItem"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                UpgradeVG upgrade = StoreInfo.getGoodLastUpgrade(itemId);
                Map<String,Object> map = LuaJSON.upgradeMap(upgrade);
                Map_Lua.mapToLua(map,L);
            } catch (Exception e) { L.pushNil(); }
            return 1;
        }
    }

    private class ItemUpgradeLevelWrapper implements NamedJavaFunction {
        @Override public String getName() { return "itemUpgradeLevel"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                int level = StoreInventory.getGoodUpgradeLevel(itemId);
                L.pushInteger(new Integer(level));
            } catch (Exception e) { L.pushInteger(0); }
            return 1;
        }
    }


    private class ItemCurrentUpgradeWrapper implements NamedJavaFunction {
        @Override public String getName() { return "itemCurrentUpgrade"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                String currentUpgrade = StoreInventory.getGoodCurrentUpgrade(itemId);
                L.pushString(currentUpgrade);
            } catch (Exception e) { L.pushNil(); }
            return 1;
        }
    }

    private class UpgradesForItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "upgradesForItem"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                ArrayList<Object> returnUpgrades = new ArrayList<Object>();
                List<UpgradeVG> upgrades = StoreInfo.getGoodUpgrades(itemId);
                for(UpgradeVG upgrade : upgrades)
                    returnUpgrades.add(LuaJSON.upgradeMap(upgrade));
                ArrayList_Lua.arrayToLua(returnUpgrades,L);
            } catch (Exception e) { L.pushNil(); }
            return 1;
        }
    }

    private class ItemHasUpgradesWrapper implements NamedJavaFunction {
        @Override public String getName() { return "itemHasUpgrades"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                boolean hasUpgrades = StoreInfo.hasUpgrades(itemId);
                L.pushBoolean(hasUpgrades);
            } catch (Exception e) { L.pushBoolean(false); }
            return 1;
        }
    }

    private class NonConsumableItemExistsWrapper implements NamedJavaFunction {
        @Override public String getName() { return "nonConsumableItemExists"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                boolean exists = StoreInventory.nonConsumableItemExists(itemId);
                L.pushBoolean(exists);
            } catch (Exception e) { L.pushBoolean(false); }
            return 1;
        }
    }

    private class BuyItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "buyItem"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                StoreInventory.buy(itemId);
            } catch(Exception e) {}
            return 0;
        }
    }

    private class GiveItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "giveItem"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-2);
            Integer amount = L.toInteger(-1);
            try {
                StoreInventory.giveVirtualItem(itemId,amount.intValue());
            } catch(Exception e) {}
            return 0;
        }
    }

    private class TakeItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "takeItem"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-2);
            Integer amount = L.toInteger(-1);
            try {
                StoreInventory.takeVirtualItem(itemId,amount.intValue());
            } catch (Exception e) {}
            return 0;
        }
    }

    private class EquipItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "equipItem"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                StoreInventory.equipVirtualGood(itemId);
            } catch (Exception e) {}
            return 0;
        }
    }

    private class UnequipItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "unequipItem"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                StoreInventory.unEquipVirtualGood(itemId);
            } catch (Exception e){}
            return 0;
        }
    }

    private class UpgradeItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "upgradeItem"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                StoreInventory.upgradeVirtualGood(itemId);
            } catch (Exception e){}
            return 0;
        }
    }

    private class ForceUpgradeWrapper implements NamedJavaFunction {
        @Override public String getName() { return "forceUpgrade"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                StoreInventory.forceUpgrade(itemId);
            } catch (Exception e){}
            return 0;
        }
    }

    private class RemoveUpgradesWrapper implements NamedJavaFunction {
        @Override public String getName() { return ""; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                StoreInventory.removeUpgrades(itemId);
            } catch (Exception e) {}
            return 0;
        }
    }

    private class AddNonConsumableItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "addNonConsumableItem"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                StoreInventory.addNonConsumableItem(itemId);
            } catch (Exception e) {}
            return 0;
        }
    }

    private class RemoveNonConsumableItemWrapper implements NamedJavaFunction {
        @Override public String getName() { return "removeNonConsumableItem"; }
        @Override public int invoke(LuaState L) {
            String itemId = L.toString(-1);
            try {
                StoreInventory.removeNonConsumableItem(itemId);
            } catch (Exception e) {}
            return 0;
        }
    }


	@Override public int invoke(LuaState L) {
        NamedJavaFunction[] luaFunctions = new NamedJavaFunction[] {
                new GetVirtualItemWrapper(),
                new CreateCurrencyWrapper(),
                new GetCurrencyWrapper(),
                new CreateCurrencyPackWrapper(),
                new GetCurrencyPackWrapper(),
                new CreateEquippableVGWrapper(),
                new GetEquippableVGWrapper(),
                new CreateLifetimeVGWrapper(),
                new GetLifetimeVGWrapper(),
                new CreateNonConsumableItemWrapper(),
                new GetNonConsumableItemWrapper(),
                new CreateSingleUsePackVGWrapper(),
                new GetSingleUsePackVGWrapper(),
                new CreateSingleUseVGWrapper(),
                new GetSingleUseVGWrapper(),
                new CreateUpgradeVGWrapper(),
                new GetUpgradeVGWrapper(),
                new CreateVirtualCategoryWrapper(),
                new GetVirtualCategoryWrapper(),

                //Store Actions
                new BuyItemWrapper(),
                new GiveItemWrapper(),
                new TakeItemWrapper(),
                new EquipItemWrapper(),
                new UnequipItemWrapper(),
                new UpgradeItemWrapper(),
                new ForceUpgradeWrapper(),
                new RemoveUpgradesWrapper(),
                new AddNonConsumableItemWrapper(),
                new RemoveNonConsumableItemWrapper(),

                //Store Informations
                new CanBuyWrapper(),
                new GetItemBalanceWrapper(),
                new IsItemEquippedWrapper(),
                new CategoryForItemWrapper(),
                new FirstUpgradeForItemWrapper(),
                new LastUpgradeForItemWrapper(),
                new ItemUpgradeLevelWrapper(),
                new ItemCurrentUpgradeWrapper(),
                new UpgradesForItemWrapper(),
                new ItemHasUpgradesWrapper(),
                new NonConsumableItemExistsWrapper(),

                //Initialization
                new InitializeStoreWrapper()
        };
        String libName = L.toString(1);
        L.register(libName,luaFunctions);
        return 1;
    }

    /// Corona Events
    @Override public void onLoaded(CoronaRuntime runtime)       { _runtime = runtime; }
    @Override public void onStarted(CoronaRuntime runtime)      { _runtime = runtime; }
    @Override public void onSuspended(CoronaRuntime runtime)    { _runtime = runtime; }
    @Override public void onResumed(CoronaRuntime runtime)      { _runtime = runtime; }
    @Override public void onExiting(CoronaRuntime runtime)      { _runtime = runtime; }

    public static void throwEvent(Map<String,Object> map) {
        LuaState L = _runtime.getLuaState();
        String eventTable = Map_Lua.mapToLuaString(map);
        System.out.println("eventTable = " + eventTable);
        L.load("function soomlaThrowEvent() Runtime:dispatchEvent(" + eventTable + ") end","=simple");
        L.call(0,0);
        L.getGlobal("soomlaThrowEvent");
        L.call(0,0);
    }

    private static CoronaRuntime _runtime;
}

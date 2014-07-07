package com.soomla.corona.store;

import com.soomla.store.domain.*;
import com.soomla.BusProvider;
import com.soomla.store.domain.virtualCurrencies.*;
import com.soomla.store.domain.virtualGoods.*;
import com.soomla.store.events.*;
import com.squareup.otto.Subscribe;
import com.soomla.corona.store.domain.LuaJSON;

import java.lang.Boolean;
import java.lang.Object;
import java.lang.String;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class EventListener {

    /// Singleton
    private EventListener() {}

    public static EventListener getInstance() {
        if(_instance == null) _instance = new EventListener();
        return _instance;
    }

    private static EventListener _instance;

    @Subscribe public void onMarketPurchase(MarketPurchaseEvent marketPurchaseEvent) {
        PurchasableVirtualItem purchasableVirtualItem = marketPurchaseEvent.getPurchasableVirtualItem();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_MarketPurchase");
        map.put("purchasableItem",LuaJSON.purchasableItemMap(purchasableVirtualItem));
        map.put("payload",marketPurchaseEvent.getPayload());
        map.put("token",marketPurchaseEvent.getToken());
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onMarketItemsRefreshFinishedEvent(MarketItemsRefreshFinishedEvent marketEvent) {
        ArrayList<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        for(MarketItem marketItem : marketEvent.getMarketItems()) items.add(LuaJSON.marketItemMap(marketItem));
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_MarketItemsRefreshed");
        map.put("items",items);
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onMarketRefund(MarketRefundEvent marketRefundEvent) {
        PurchasableVirtualItem purchasableVirtualItem = marketRefundEvent.getPurchasableVirtualItem();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_MarketRefund");
        map.put("purchasableItem",LuaJSON.purchasableItemMap(purchasableVirtualItem));
        map.put("payload",marketRefundEvent.getPayload());
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onItemPurchased(ItemPurchasedEvent itemPurchasedEvent) {
        PurchasableVirtualItem purchasableVirtualItem = itemPurchasedEvent.getPurchasableVirtualItem();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_ItemPurchased");
        map.put("purchasableItem",LuaJSON.purchasableItemMap(purchasableVirtualItem));
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onGoodEquipped(GoodEquippedEvent virtualGoodEquippedEvent) {
        EquippableVG equippableVG = virtualGoodEquippedEvent.getGood();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_VirtualGoodEquipped");
        map.put("equippableVG",LuaJSON.equippableMap(equippableVG));
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onGoodUnEquipped(GoodUnEquippedEvent virtualGoodUnEquippedEvent) {
        EquippableVG equippableVG = virtualGoodUnEquippedEvent.getGood();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_VirtualGoodUNEQUIPPED");
        map.put("equippableVG",LuaJSON.equippableMap(equippableVG));
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onGoodUpgrade(GoodUpgradeEvent goodUpgradeEvent) {
        UpgradeVG upgradeVG = goodUpgradeEvent.getCurrentUpgrade();
        VirtualItem good = goodUpgradeEvent.getGood();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_VirtualGoodUpgrade");
        map.put("virtualGood",LuaJSON.virtualItemMap(good));
        map.put("upgradeVG",LuaJSON.upgradeMap(upgradeVG));
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onBillingSupported(BillingSupportedEvent billingSupportedEvent) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_BillingSupported");
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onBillingNotSupported(BillingNotSupportedEvent billingNotSupportedEvent) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_BillingNotSupported");
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onMarketPurchaseStarted(MarketPurchaseStartedEvent marketPurchaseStartedEvent) {
        PurchasableVirtualItem purchasableVirtualItem = marketPurchaseStartedEvent.getPurchasableVirtualItem();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_MarketPurchaseStarted");
        map.put("purchasableItem",LuaJSON.purchasableItemMap(purchasableVirtualItem));
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onMarketPurchaseCancelled(MarketPurchaseCancelledEvent marketPurchaseCancelledEvent) {
        PurchasableVirtualItem purchasableVirtualItem = marketPurchaseCancelledEvent.getPurchasableVirtualItem();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_MarketPurchaseCancelled");
        map.put("purchasableItem",LuaJSON.purchasableItemMap(purchasableVirtualItem));
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onItemPurchaseStarted(ItemPurchaseStartedEvent itemPurchaseStartedEvent) {
        PurchasableVirtualItem purchasableVirtualItem = itemPurchaseStartedEvent.getPurchasableVirtualItem();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_ItemPurchaseStarted");
        map.put("purchasableItem",LuaJSON.purchasableItemMap(purchasableVirtualItem));
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onUnexpectedStoreError(UnexpectedStoreErrorEvent unexpectedStoreErrorEvent) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_UnexpectedErrorInStore");
        map.put("description",unexpectedStoreErrorEvent.getMessage());
        map.put("code",new Double(0));
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onIabServiceStarted(IabServiceStartedEvent iabServiceStartedEvent) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_IabServiceStarted");
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onIabServiceStopped(IabServiceStoppedEvent iabServiceStoppedEvent) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_IabServiceStopped");
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onCurrencyBalanceChanged(CurrencyBalanceChangedEvent currencyBalanceChangedEvent) {
        VirtualCurrency currency = currencyBalanceChangedEvent.getCurrency();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_ChangedCurrencyBalance");
        map.put("balance",new Double(currencyBalanceChangedEvent.getBalance()));
        map.put("amountAdded",new Double(currencyBalanceChangedEvent.getAmountAdded()));
        map.put("currency",LuaJSON.currencyMap(currency));
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onGoodBalanceChanged(GoodBalanceChangedEvent goodBalanceChangedEvent) {
        VirtualItem virtualGood = goodBalanceChangedEvent.getGood();
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_ChangedGoodBalance");
        map.put("balance",new Double(goodBalanceChangedEvent.getBalance()));
        map.put("amountAdded",new Double(goodBalanceChangedEvent.getAmountAdded()));
        map.put("virtualGood",LuaJSON.virtualItemMap(virtualGood));
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onRestoreTransactionsFinished(RestoreTransactionsFinishedEvent restoreTransactionsFinishedEvent) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_TransactionRestored");
        map.put("success",new Boolean(restoreTransactionsFinishedEvent.isSuccess()));
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onRestoreTransactionsStarted(RestoreTransactionsStartedEvent restoreTransactionsStartedEvent) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_TransactionRestoreStarted");
        plugin.soomla.LuaLoader.throwEvent(map);
    }

    @Subscribe public void onSoomlaStoreInitialized(SoomlaStoreInitializedEvent soomlaStoreInitializedEvent) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("name","soomla_SoomlaStoreInitialized");
        plugin.soomla.LuaLoader.throwEvent(map);
    }

}
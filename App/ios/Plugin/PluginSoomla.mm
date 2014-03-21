//
//  PluginSoomla.mm
//  Soomla for Corona
//
//  Copyright (c) 2014 Soomla. All rights reserved.

#import "PluginSoomla.h"
#import <UIKit/UIKit.h>
#import "SoomlaStore.h"

#import "NSDictionary+Lua.h"
#import "VirtualItem+Lua.h"
#import "PurchasableVirtualItem+Lua.h"
#import "SoomlaStore.h"
#import "VirtualCurrency.h"
#import "VirtualCurrencyPack+Lua.h"
#import "SingleUseVG.h"
#import "LifetimeVG.h"
#import "EquippableVG+Lua.h"
#import "SingleUsePackVG+Lua.h"
#import "UpgradeVG+Lua.h"
#import "NonConsumableItem.h"
#import "VirtualCategory+Lua.h"
#import "EventManager.h"
#import "EventListener.h"

PluginSoomla::PluginSoomla() {

}

PluginSoomla * PluginSoomla::getLibrary(lua_State * L) {
    PluginSoomla * soomla = (PluginSoomla *) CoronaLuaToUserdata(L,lua_upvalueindex(1));
    return soomla;
}

NSDictionary * PluginSoomla::getDictionaryFromLuaState(lua_State * L) {
    return [NSDictionary dictionaryFromLua:L tableIndex:lua_gettop(L)];
}

#pragma mark - Creating Models
int PluginSoomla::createCurrency(lua_State * L) {
    VirtualCurrency * currency = [[VirtualCurrency alloc] initFromLua:PluginSoomla::getDictionaryFromLuaState(L)];
    PluginSoomla::addVirtualItemForLuaState(currency,L);
    return 1;
}

int PluginSoomla::createCurrencyPack(lua_State * L) {
    VirtualCurrencyPack * currencyPack = [[VirtualCurrencyPack alloc] initFromLua:PluginSoomla::getDictionaryFromLuaState(L)];
    PluginSoomla::addVirtualItemForLuaState(currencyPack,L);
    return 1;
}

int PluginSoomla::createSingleUseVG(lua_State * L) {
    SingleUseVG * singleUseVG = [[SingleUseVG alloc] initFromLua:PluginSoomla::getDictionaryFromLuaState(L)];
    PluginSoomla::addVirtualItemForLuaState(singleUseVG,L);
    return 1;
}

int PluginSoomla::createLifetimeVG(lua_State * L) {
    LifetimeVG * lifetimeVG = [[LifetimeVG alloc] initFromLua:PluginSoomla::getDictionaryFromLuaState(L)];
    PluginSoomla::addVirtualItemForLuaState(lifetimeVG,L);
    return 1;
}

int PluginSoomla::createEquippableVG(lua_State * L) {
    EquippableVG * equippableVG = [[EquippableVG alloc] initFromLua:PluginSoomla::getDictionaryFromLuaState(L)];
    PluginSoomla::addVirtualItemForLuaState(equippableVG,L);
    return 1;
}

int PluginSoomla::createSingleUsePackVG(lua_State * L) {
    SingleUsePackVG * singleUsePackVG = [[SingleUsePackVG alloc] initFromLua:PluginSoomla::getDictionaryFromLuaState(L)];
    PluginSoomla::addVirtualItemForLuaState(singleUsePackVG,L);
    return 1;
}

int PluginSoomla::createUpgradeVG(lua_State * L) {
    UpgradeVG * upgradeVG = [[UpgradeVG alloc] initFromLua:PluginSoomla::getDictionaryFromLuaState(L)];
    PluginSoomla::addVirtualItemForLuaState(upgradeVG,L);
    return 1;
}

int PluginSoomla::createNonConsumableItem(lua_State * L) {
    NonConsumableItem * nonConsumableItem = [[NonConsumableItem alloc] initFromLua:PluginSoomla::getDictionaryFromLuaState(L)];
    PluginSoomla::addVirtualItemForLuaState(nonConsumableItem,L);
    return 1;
}

void PluginSoomla::addVirtualItemForLuaState(VirtualItem * virtualItem,lua_State * L) {
    if(virtualItem == nil) {
        lua_pushstring(L,[[NSString stringWithFormat:@"invalid!"] cStringUsingEncoding:NSUTF8StringEncoding]);
        return;
    }
    [[SoomlaStore sharedInstance] addVirtualItem:virtualItem];
    lua_pushstring(L,[virtualItem.itemId cStringUsingEncoding:NSUTF8StringEncoding]);
}

int PluginSoomla::createVirtualCategory(lua_State * L) {
    VirtualCategory * virtualCategory = [[VirtualCategory alloc] initFromLua:PluginSoomla::getDictionaryFromLuaState(L)];
    if(virtualCategory.name == nil) {
        NSLog(@"SOOMLA: name shouldn't be empty for a Virtual Category!");
        lua_pushstring(L,[[NSString stringWithFormat:@"invalid!"] cStringUsingEncoding:NSUTF8StringEncoding]);
        return 1;
    }
    [[SoomlaStore sharedInstance] addVirtualCategory:virtualCategory];
    lua_pushstring(L,[virtualCategory.name cStringUsingEncoding:NSUTF8StringEncoding]);
    return 1;
}

#pragma mark - Retrivieng Models Data
int PluginSoomla::getCurrency(lua_State * L) { return PluginSoomla::getVirtualItem(L); }
int PluginSoomla::getCurrencyPack(lua_State * L) { return PluginSoomla::getVirtualItem(L); }
int PluginSoomla::getSingleUseVG(lua_State * L) { return PluginSoomla::getVirtualItem(L); }
int PluginSoomla::getLifetimeVG(lua_State * L) { return PluginSoomla::getVirtualItem(L); }
int PluginSoomla::getSingleUsePackVG(lua_State * L) { return PluginSoomla::getVirtualItem(L); }
int PluginSoomla::getEquippableVG(lua_State * L) { return PluginSoomla::getVirtualItem(L); }
int PluginSoomla::getNonConsumableItem(lua_State * L) { return PluginSoomla::getVirtualItem(L); }
int PluginSoomla::getUpgradeVG(lua_State * L) { return PluginSoomla::getVirtualItem(L); }

int PluginSoomla::getVirtualItem(lua_State * L) {
    const int itemIdParameterIndex = -1;
    NSString * itemId = [NSString stringWithFormat:@"%s",lua_tostring(L,itemIdParameterIndex)];
    VirtualItem * virtualItem = [[SoomlaStore sharedInstance] virtualItemWithId:itemId];
    NSDictionary * virtualItemData = [virtualItem toLuaDictionary];
    [virtualItemData toLuaTable:L];
    return 1;
}


int PluginSoomla::getVirtualCategory(lua_State * L){
    const int nameParameterIndex = -1;
    NSString * name = [NSString stringWithFormat:@"%s",lua_tostring(L,nameParameterIndex)];
    VirtualCategory * category = [[SoomlaStore sharedInstance] categoryWithName:name];
    NSDictionary * categoryData = [category toLuaDictionary];
    [categoryData toLuaTable:L];
    return 1;
}

#pragma mark - Store initialization
int PluginSoomla::initializeStore(lua_State * L) {
    [[SoomlaStore sharedInstance] initializeWithData:PluginSoomla::getDictionaryFromLuaState(L)];
    return 0;
}

#pragma mark - Events
int PluginSoomla::addEventListener(lua_State * L) {
    const int eventNameParameterIndex = -2;
    const int listenerParameterIndex = -1;
    NSString * eventName = [NSString stringWithFormat:@"%s",lua_tostring(L,eventNameParameterIndex)];
    CoronaLuaRef listener = CoronaLuaNewRef(L,listenerParameterIndex);
    [[EventManager sharedInstance] addListener:listener toEvent:eventName];
    return 0;
}

int PluginSoomla::removeEventListener(lua_State * L) {
    const int eventNameParameterIndex = -2;
    const int listenerParameterIndex = -1;
    NSString * eventName = [NSString stringWithFormat:@"%s",lua_tostring(L,eventNameParameterIndex)];
    CoronaLuaRef listener = CoronaLuaNewRef(L,listenerParameterIndex);
    [[EventManager sharedInstance] removeListener:listener fromEvent:eventName];
    return 0;
}


#pragma mark - Corona Export
const char PluginSoomla::kName[] = "plugin.soomla";

int PluginSoomla::Finalizer(lua_State * L) {
    PluginSoomla * soomla = (PluginSoomla *) CoronaLuaToUserdata(L,1);
    [[EventManager sharedInstance] deleteAllReferences:L];
    [[EventListener sharedInstance] stopListeningSoomlaEvents];
    delete soomla;
    return 0;
}

int PluginSoomla::Export(lua_State * L) {
    const char kMetatableName[] = __FILE__;
    CoronaLuaInitializeGCMetatable(L,kMetatableName,Finalizer);
    
    const luaL_Reg exportTable[] = {
        { "createCurrency", createCurrency },
        { "createCurrencyPack", createCurrencyPack },
        { "createSingleUseVG", createSingleUseVG },
        { "createLifetimeVG", createLifetimeVG },
        { "createEquippableVG", createEquippableVG },
        { "createSingleUsePackVG", createSingleUsePackVG },
        { "createUpgradeVG", createUpgradeVG },
        { "createNonConsumableItem", createNonConsumableItem },
        { "createCategory", createVirtualCategory },

        { "getCurrency", getCurrency },
        { "getCurrencyPack", getCurrencyPack },
        { "getSingleUseVG", getSingleUseVG },
        { "getLifetimeVG", getLifetimeVG },
        { "getEquippableVG", getEquippableVG },
        { "getSingleUsePackVG", getSingleUsePackVG },
        { "getUpgradeVG", getUpgradeVG },
        { "getNonConsumableItem", getNonConsumableItem },
        { "getCategory", getVirtualCategory },
        
        { "addEventListener", addEventListener },
        { "removeEventListener", removeEventListener },
        
        { "initializeStore", initializeStore },
        
        { NULL, NULL }
    };
    
    PluginSoomla * soomla = new PluginSoomla();
    CoronaLuaPushUserdata(L,soomla,kMetatableName);
    
    luaL_openlib(L,kName,exportTable,1);
    return 1;
}

CORONA_EXPORT int luaopen_plugin_soomla(lua_State * L) {
    int results = PluginSoomla::Export(L);
    [[EventListener sharedInstance] startListeningSoomlaEvents];
    return results;
}
package com.fabledzx.agelock;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

/**
 * 客户端入口：注册“进入游戏强制年龄验证”和“5 分钟锁定计时”。
 */
public class AgeLockClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 每次进入世界 -> 强制弹出年龄输入界面
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) ->
                AgeManager.getInstance().onJoin(client));

        // 离开世界 / 断线 -> 重置状态
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) ->
                AgeManager.getInstance().onDisconnect());

        // 每 tick 驱动：界面兜底 + 受限游玩计时
        ClientTickEvents.END_CLIENT_TICK.register(AgeManager.getInstance()::tick);
    }
}

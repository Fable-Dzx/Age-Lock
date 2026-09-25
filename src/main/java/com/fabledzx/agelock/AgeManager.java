package com.fabledzx.agelock;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * 模组核心状态机：
 * <ul>
 *   <li>进入游戏 -> 强制弹年龄输入界面（INPUT_REQUIRED）</li>
 *   <li>验证通过 -> 未满 18 岁进入受限游玩（PLAYING_LIMITED），5 分钟后锁定；成年则自由游玩（PLAYING_FREE）</li>
 *   <li>锁定（LOCKED）-> 只能点击「我长大了」重新输入年龄</li>
 * </ul>
 * 任何试图关闭强制界面的行为（ESC、按键等）都会被 tick 强制拉回。
 */
public final class AgeManager {

    public enum Mode {
        IDLE,
        INPUT_REQUIRED,
        PLAYING_LIMITED,
        PLAYING_FREE,
        LOCKED
    }

    private static final AgeManager INSTANCE = new AgeManager();

    private Mode mode = Mode.IDLE;
    private long lastVerifiedAge = -1;
    private int playTicks = 0;
    private int lockSeconds = 300; // 默认 5 分钟
    private boolean enabled = true;
    private boolean warned60 = false;
    private boolean warned10 = false;

    private AgeManager() {
        loadConfig();
    }

    public static AgeManager getInstance() {
        return INSTANCE;
    }

    /** 从 config/age-lock.properties 读取配置，不存在则生成默认配置 */
    private void loadConfig() {
        try {
            Path file = FabricLoader.getInstance().getConfigDir().resolve("age-lock.properties");
            Properties props = new Properties();
            if (Files.exists(file)) {
                try (InputStream in = Files.newInputStream(file)) {
                    props.load(in);
                }
            }
            this.lockSeconds = Integer.parseInt(props.getProperty("lock-seconds", "300"));
            this.enabled = Boolean.parseBoolean(props.getProperty("enabled", "true"));
            props.setProperty("lock-seconds", String.valueOf(this.lockSeconds));
            props.setProperty("enabled", String.valueOf(this.enabled));
            try (OutputStream out = Files.newOutputStream(file)) {
                props.store(out, "Age Lock 配置：lock-seconds=受限游玩时长(秒)；enabled=false 可完全关闭本模组");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 进入游戏：强制打开年龄输入界面 */
    public void onJoin(MinecraftClient client) {
        if (!enabled) {
            return;
        }
        mode = Mode.INPUT_REQUIRED;
        client.execute(() -> client.setScreen(new AgeInputScreen()));
    }

    /** 离开服务器 / 退出世界：重置状态 */
    public void onDisconnect() {
        mode = Mode.IDLE;
        playTicks = 0;
        warned60 = false;
        warned10 = false;
    }

    /** 当前是否处于“必须展示强制界面”的状态（用于阻止玩家关掉界面） */
    public boolean canForceScreen() {
        return mode == Mode.INPUT_REQUIRED || mode == Mode.LOCKED;
    }

    /** 记录本次输入的年龄（尚未点击“好的”） */
    public void verify(long age) {
        this.lastVerifiedAge = age;
    }

    /** 点击“好的”：根据年龄进入受限或自由游玩 */
    public void confirm() {
        warned60 = false;
        warned10 = false;
        if (lastVerifiedAge < 18) {
            mode = Mode.PLAYING_LIMITED;
            playTicks = 0;
        } else {
            mode = Mode.PLAYING_FREE;
        }
    }

    /** 点击“我长大了”：重新要求输入年龄 */
    public void onGrowUp() {
        mode = Mode.INPUT_REQUIRED;
    }

    /** 每 tick 驱动：强制界面兜底 + 受限游玩计时 */
    public void tick(MinecraftClient client) {
        if (!enabled) {
            return;
        }
        if (client.player == null || client.world == null) {
            return;
        }

        switch (mode) {
            case INPUT_REQUIRED -> {
                if (!(client.currentScreen instanceof AgeInputScreen)
                        && !(client.currentScreen instanceof AgeResultScreen)) {
                    client.setScreen(new AgeInputScreen());
                }
            }
            case LOCKED -> {
                if (!(client.currentScreen instanceof AgeLockScreen)
                        && !(client.currentScreen instanceof AgeInputScreen)
                        && !(client.currentScreen instanceof AgeResultScreen)) {
                    client.setScreen(new AgeLockScreen());
                }
            }
            case PLAYING_LIMITED -> {
                playTicks++;
                int remaining = lockSeconds - playTicks / 20;
                if (remaining == 60 && !warned60) {
                    warned60 = true;
                    client.inGameHud.setOverlayMessage(Text.literal("距离锁定还有 1 分钟！"), true);
                } else if (remaining == 10 && !warned10) {
                    warned10 = true;
                    client.inGameHud.setOverlayMessage(Text.literal("还有 10 秒就要锁定啦！"), true);
                }
                if (playTicks >= lockSeconds * 20) {
                    mode = Mode.LOCKED;
                    warned60 = false;
                    warned10 = false;
                    client.setScreen(new AgeLockScreen());
                }
            }
            default -> {
                // IDLE / PLAYING_FREE 无需处理
            }
        }
    }
}

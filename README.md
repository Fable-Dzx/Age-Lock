# Age Lock · 年龄验证锁定模组

**Forced age verification every time you join a world. Under 18? Only 5 minutes of play — then the game locks. Enter an absurd age? Enjoy Steam-style easter eggs.**
**每次进入游戏强制验证年龄：未满 18 岁只能玩 5 分钟，到点游戏锁定；输入离谱年龄，触发 Steam 风格彩蛋。**

![Mod Loader](https://img.shields.io/badge/Mod%20Loader-Fabric-blueviolet?style=for-the-badge)
![Environment](https://img.shields.io/badge/Environment-Client-informational?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

---

**Languages / 语言：** [中文](#中文) · [English](#english)

---

## 中文

一个为 Minecraft **Fabric** 制作的纯客户端模组。每次加入世界（单人 / 多人）都会强制弹出年龄验证界面，不可用 ESC 或快捷键跳过。支持的 Minecraft 版本请查看 GitHub Releases。

### 功能特性

- **强制验证**：每次进入游戏都弹出「请输入您的年龄」，按 ESC 或按 E 都无法绕过；
- **未满 18 岁**：验证通过后只能游玩 **5 分钟**，锁定前 1 分钟 / 10 秒屏幕顶部会有倒计时提示，到点游戏锁定；
- **「我长大了」**：锁定界面只有这一个按钮，点击后重新要求输入年龄（再次输入未成年年龄会再获得 5 分钟，输入成年年龄则永久放行）；
- **Steam 风格彩蛋**：输入超大年龄会触发各种搞笑提示；
- **验证弹窗**：输入年龄后先弹出结果框，点击「好的」才正式进入正常游玩；
- **可配置**：锁定时长、开关均可通过配置文件调整；
- **多语言支持**：内置简体中文 / English，全部文案来自语言 JSON 文件，新增语言只需添加一个文件；
- **纯客户端**：不影响服务端，单机 / 局域网 / 联机服务器通用。

### 年龄区间彩蛋

| 年龄 Age | 提示语 Message | 结果 Result |
|---|---|---|
| 1–6 | 小朋友快去写作业！你只能玩 5 分钟哦~ | 5 分钟受限 |
| 7–12 | 小学生玩家你好呀~未满 18 岁只能玩 5 分钟哦！ | 5 分钟受限 |
| 13–17 | 未成年哦~只能玩 5 分钟，时间到了要乖乖下线！ | 5 分钟受限 |
| 18–99 | 已成年，验证通过！祝你玩得开心！ | 正常游玩 |
| 100–999 | 百岁老人，老当益壮！欢迎加入 Minecraft！ | 正常游玩 |
| 1000–99999 | 千年老妖现身！这块大陆因你而颤抖！ | 正常游玩 |
| 100000–999999 | 十万年！您这算是活化石了吧？ | 正常游玩 |
| 66000000 | 玩去吧，霸王龙先生！| 正常游玩 |
| 4600000000 | 地球诞生时您就在了？地球母亲您好！ | 正常游玩 |
| 13800000000 | 宇宙大爆炸先生，欢迎来到 Minecraft！ | 正常游玩 |
| 更大 | 您比宇宙还老？！时间管理局需要您！ | 正常游玩 |
| 其他 ≥1000000 | 百万年传奇！这游戏配得上您！ | 正常游玩 |

### 安装

1. 安装 **Fabric Loader**（与你的 Minecraft 版本对应）：<https://fabricmc.net/use/>
2. 将 `age-lock-1.0.0.jar` 放入 `.minecraft/mods/` 文件夹；
3. 同时放入与游戏版本匹配的 **Fabric API**：<https://modrinth.com/mod/fabric-api>
4. 启动游戏即可，进入世界后会自动弹出年龄验证。

### 配置

首次启动后会在 `.minecraft/config/` 下生成 `age-lock.properties`：

```properties
# 受限游玩时长（秒），默认 300 秒 = 5 分钟
lock-seconds=300
# false 可完全关闭本模组
enabled=true
```

### 从源码构建

需要 **JDK 17** 与 **Gradle 7.6+**：

```bash
gradle build
# 产物：build/libs/age-lock-1.0.0.jar
```

### 技术说明

- 使用 Fabric API 事件：`ClientPlayConnectionEvents.JOIN/DISCONNECT`、`ClientTickEvents.END_CLIENT_TICK`；
- 强制界面通过每 tick 兜底检测实现，ESC / 快捷键均无法绕过；
- 锁定计时基于游戏 tick（20 tick/秒），单机暂停菜单中暂停计时；
- 主包：`com.fabledzx.agelock`；支持的 Minecraft 版本见 GitHub Releases。

### 许可

[MIT](LICENSE) © Age Lock contributors

---

## English

A client-side mod for Minecraft **Fabric**. Every time you join a world (singleplayer or multiplayer), a forced age-verification screen pops up — ESC and hotkeys can't skip it. Supported Minecraft versions are listed on GitHub Releases.

### Features

- **Forced verification** — the age input dialog appears on every world join and cannot be bypassed with ESC or the inventory key;
- **Under 18** — you get only **5 minutes** of play; countdown warnings appear on-screen at 1 minute and 10 seconds before the lock, then the game locks;
- **“I've grown up!”** — the lock screen has a single button; clicking it re-prompts for age (re-entering a minor age grants another 5 minutes; entering an adult age unlocks permanently);
- **Steam-style easter eggs** — absurd ages trigger funny messages;
- **Result popup** — after entering an age, a result dialog appears; click **“OK”** to actually start playing;
- **Configurable** — lock duration and an on/off switch via a config file;
- **i18n** — Simplified Chinese and English built in; all text comes from lang JSON files, so adding a language is just dropping in one more file;
- **Client-only** — works in singleplayer, LAN and on online servers without touching the server.

### Age Range Easter Eggs

| Age | Message | Result |
|---|---|---|
| 1–6 | Go do your homework, little one! You can only play for 5 minutes~ | 5-min limit |
| 7–12 | Hello there, elementary schooler~ Under 18 means only 5 minutes! | 5-min limit |
| 13–17 | Still a minor~ Only 5 minutes, then it's bedtime! | 5-min limit |
| 18–99 | Adult verified! Have fun! | Play freely |
| 100–999 | A centenarian, still going strong! Welcome to Minecraft! | Play freely |
| 1000–99999 | A thousand-year-old spirit appears! The land trembles before you! | Play freely |
| 100000–999999 | A hundred thousand years?! Are you a living fossil? | Play freely |
| 66000000 | Go play, Mr. Tyrannosaurus Rex! | Play freely |
| 4600000000 | You were there when Earth was born? Hello, Mother Earth! | Play freely |
| 13800000000 | Mr. Big Bang, welcome to Minecraft! | Play freely |
| Even larger | Older than the universe?! The Time Bureau needs you! | Play freely |
| Other ≥1000000 | A million-year legend! This game is worthy of you! | Play freely |

### Installation

1. Install **Fabric Loader** (matching your Minecraft version): <https://fabricmc.net/use/>
2. Put `age-lock-1.0.0.jar` into your `.minecraft/mods/` folder;
3. Also install **Fabric API** matching your Minecraft version: <https://modrinth.com/mod/fabric-api>
4. Launch the game — the age verification will appear automatically when you enter a world.

### Configuration

On first launch, `age-lock.properties` is generated in `.minecraft/config/`:

```properties
# Play time limit for minors (seconds). Default 300 = 5 minutes
lock-seconds=300
# Set to false to disable the mod entirely
enabled=true
```

### Building from Source

Requires **JDK 17** and **Gradle 7.6+**:

```bash
gradle build
# Output: build/libs/age-lock-1.0.0.jar
```

### Technical Notes

- Powered by Fabric API events: `ClientPlayConnectionEvents.JOIN/DISCONNECT` and `ClientTickEvents.END_CLIENT_TICK`;
- The forced screens are re-checked every tick, so ESC and hotkeys cannot bypass them;
- The lock timer counts game ticks (20 ticks/second) and pauses in the singleplayer pause menu;
- Main package: `com.fabledzx.agelock`; supported Minecraft versions are listed on GitHub Releases.

### License

[MIT](LICENSE) © Age Lock contributors

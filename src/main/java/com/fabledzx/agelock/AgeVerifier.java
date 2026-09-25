package com.fabledzx.agelock;

/**
 * 年龄验证规则：按年龄区间返回提示语，未满 18 岁进入受限模式（5 分钟锁定）。
 */
public final class AgeVerifier {

    /** 成年年龄界限 */
    public static final long ADULT_AGE = 18L;
    /** 6600 万年：霸王龙灭绝的年代（彩蛋） */
    public static final long TYRANNOSAURUS_AGE = 66_000_000L;
    /** 46 亿年：地球年龄（彩蛋） */
    public static final long EARTH_AGE = 4_600_000_000L;
    /** 138 亿年：宇宙年龄（彩蛋） */
    public static final long UNIVERSE_AGE = 13_800_000_000L;

    private AgeVerifier() {
    }

    public static VerifyResult check(long age) {
        if (age <= 0) {
            return new VerifyResult("年龄必须大于 0", false);
        }

        // 未满 18 岁：只能玩 5 分钟，到点锁定
        if (age < ADULT_AGE) {
            if (age <= 6) {
                return new VerifyResult("小朋友快去写作业！你只能玩 5 分钟哦~", true);
            } else if (age <= 12) {
                return new VerifyResult("小学生玩家你好呀~未满 18 岁只能玩 5 分钟哦！", true);
            } else {
                return new VerifyResult("未成年哦~只能玩 5 分钟，时间到了要乖乖下线！", true);
            }
        }

        // 超大年龄彩蛋（Steam 风格）
        if (age == TYRANNOSAURUS_AGE) {
            return new VerifyResult("玩去吧，霸王龙先生！", false);
        }
        if (age == EARTH_AGE) {
            return new VerifyResult("地球诞生时您就在了？地球母亲您好！", false);
        }
        if (age == UNIVERSE_AGE) {
            return new VerifyResult("宇宙大爆炸先生，欢迎来到 Minecraft！", false);
        }
        if (age > UNIVERSE_AGE) {
            return new VerifyResult("您比宇宙还老？！时间管理局需要您！", false);
        }

        // 普通成年区间
        if (age <= 99) {
            return new VerifyResult("已成年，验证通过！祝你玩得开心！", false);
        }
        if (age < 1000) {
            return new VerifyResult("百岁老人，老当益壮！欢迎加入 Minecraft！", false);
        }
        if (age < 100000) {
            return new VerifyResult("千年老妖现身！这块大陆因你而颤抖！", false);
        }
        if (age < 1000000) {
            return new VerifyResult("十万年！您这算是活化石了吧？", false);
        }
        return new VerifyResult("百万年传奇！这游戏配得上您！", false);
    }
}

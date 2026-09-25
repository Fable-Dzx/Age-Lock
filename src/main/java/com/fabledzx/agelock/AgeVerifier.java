package com.fabledzx.agelock;

/**
 * 年龄验证规则：按年龄区间返回提示语翻译键，未满 18 岁进入受限模式（5 分钟锁定）。
 * 文案本身不写死在代码里，全部通过翻译键引用，便于多语言支持。
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
            return new VerifyResult("agelock.age.invalid", false);
        }

        // 未满 18 岁：只能玩 5 分钟，到点锁定
        if (age < ADULT_AGE) {
            if (age <= 6) {
                return new VerifyResult("agelock.age.child", true);
            } else if (age <= 12) {
                return new VerifyResult("agelock.age.pupil", true);
            } else {
                return new VerifyResult("agelock.age.teen", true);
            }
        }

        // 超大年龄彩蛋（Steam 风格）
        if (age == TYRANNOSAURUS_AGE) {
            return new VerifyResult("agelock.age.trex", false);
        }
        if (age == EARTH_AGE) {
            return new VerifyResult("agelock.age.earth", false);
        }
        if (age == UNIVERSE_AGE) {
            return new VerifyResult("agelock.age.universe", false);
        }
        if (age > UNIVERSE_AGE) {
            return new VerifyResult("agelock.age.older_than_universe", false);
        }

        // 普通成年区间
        if (age <= 99) {
            return new VerifyResult("agelock.age.adult", false);
        }
        if (age < 1000) {
            return new VerifyResult("agelock.age.centenarian", false);
        }
        if (age < 100000) {
            return new VerifyResult("agelock.age.millennium", false);
        }
        if (age < 1000000) {
            return new VerifyResult("agelock.age.fossil", false);
        }
        return new VerifyResult("agelock.age.legend", false);
    }
}

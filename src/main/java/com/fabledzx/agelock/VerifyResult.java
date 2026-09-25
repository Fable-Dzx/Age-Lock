package com.fabledzx.agelock;

/**
 * 年龄校验结果。
 *
 * @param message 校验通过后展示的提示语（含各种年龄区间的彩蛋）
 * @param locked  是否属于受限状态（未满 18 岁，5 分钟后锁定）
 */
public record VerifyResult(String message, boolean locked) {
}

package com.fabledzx.agelock;

/**
 * 年龄校验结果。
 *
 * @param messageKey 提示语的翻译键（定义于 assets/age-lock/lang/*.json）
 * @param locked     是否属于受限状态（未满 18 岁，5 分钟后锁定）
 */
public record VerifyResult(String messageKey, boolean locked) {
}

package com.acongfly.studyjava.javaStudy.other;

import java.security.SecureRandom;

import com.google.common.base.Preconditions;

/**
 * 红包分配算法
 *
 * @version 2018-12-20 13:02
 */
public final class EnvelopeUtils {
    private EnvelopeUtils() {/**/}

    /**
     * 返回一次抽奖在指定中奖概率下是否中奖
     *
     * @param rate
     *            中奖概率
     * @return
     */
    public static boolean canReward(double rate) {
        return Math.random() <= rate;
    }

    /**
     * 返回min~max区间内随机数，含min和max
     *
     * @param min
     * @param max
     * @return
     */
    private static int getRandomVal(int min, int max) {
        SecureRandom random = new SecureRandom();
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 带概率偏向的随机算法，概率偏向subMin~subMax区间 返回boundMin~boundMax区间内随机数（含boundMin和boundMax），同时可以指定子区间subMin~subMax的优先概率
     * 例：传入参数(10, 50, 20, 30, 0.8)，则随机结果有80%概率从20~30中随机返回，有20%概率从10~50中随机返回
     *
     * @param boundMin
     *            边界
     * @param boundMax
     * @param subMin
     * @param subMax
     * @param subRate
     * @return
     */
    private static int getRandomValWithSpecifySubRate(int boundMin, int boundMax, int subMin, int subMax,
        double subRate) {
        if (canReward(subRate)) {
            return getRandomVal(subMin, subMax);
        }
        return getRandomVal(boundMin, boundMax);
    }

    /**
     * 随机分配第n个红包
     *
     * @param totalBonus
     *            总红包量
     * @param totalNum
     *            总份数
     * @param sendedBonus
     *            已发送红包量
     * @param sendedNum
     *            已发送份数
     * @param rdMin
     *            随机下限
     * @param rdMax
     *            随机上限
     * @return
     */
    public static int randomBonusWithSpecifyBound(Integer totalBonus, Integer totalNum, Integer sendedBonus,
        Integer sendedNum, Integer rdMin, Integer rdMax, double bigRate) {
        Integer avg = totalBonus / totalNum; // 平均值
        Integer leftLen = avg - rdMin;
        Integer rightLen = rdMax - avg;
        Integer boundMin = 0, boundMax = 0;

        // 大范围设置小概率
        if (leftLen.equals(rightLen)) {
            boundMin = Math.max((totalBonus - sendedBonus - (totalNum - sendedNum - 1) * rdMax), rdMin);
            boundMax = Math.min((totalBonus - sendedBonus - (totalNum - sendedNum - 1) * rdMin), rdMax);
        } else if (rightLen.compareTo(leftLen) > 0) {
            // 上限偏离
            Integer standardRdMax = avg + leftLen; // 右侧对称上限点
            Integer _rdMax = canReward(bigRate) ? rdMax : standardRdMax;
            boundMin = Math.max((totalBonus - sendedBonus - (totalNum - sendedNum - 1) * standardRdMax), rdMin);
            boundMax = Math.min((totalBonus - sendedBonus - (totalNum - sendedNum - 1) * rdMin), _rdMax);
        } else {
            // 下限偏离
            Integer standardRdMin = avg - rightLen; // 左侧对称下限点
            Integer _rdMin = canReward(bigRate) ? rdMin : standardRdMin;
            boundMin = Math.max((totalBonus - sendedBonus - (totalNum - sendedNum - 1) * rdMax), _rdMin);
            boundMax = Math.min((totalBonus - sendedBonus - (totalNum - sendedNum - 1) * standardRdMin), rdMax);
        }

        // 已发平均值偏移修正-动态比例
        if (boundMin.equals(boundMax)) {
            return getRandomVal(boundMin, boundMax);
        }
        double currAvg = sendedNum == 0 ? (double)avg : (sendedBonus / (double)sendedNum); // 当前已发平均值
        double middle = (boundMin + boundMax) / 2.0;
        Integer subMin = boundMin, subMax = boundMax;
        // 期望值
        double exp = avg - (currAvg - avg) * sendedNum / (double)(totalNum - sendedNum);
        if (middle > exp) {
            subMax = (int)Math.round((boundMin + exp) / 2.0);
        } else {
            subMin = (int)Math.round((exp + boundMax) / 2.0);
        }
        Integer expBound = (boundMin + boundMax) / 2;
        Integer expSub = (subMin + subMax) / 2;
        double subRate = (exp - expBound) / (double)(expSub - expBound);
        return getRandomValWithSpecifySubRate(boundMin, boundMax, subMin, subMax, subRate);
    }

    /**
     * @param totalBonus
     *            总红包量
     * @param totalNum
     *            总份数
     * @param rdMin
     *            随机下限
     * @param rdMax
     *            随机上限
     */
    public static void checkEnvelopUnpack(EnvelopPrizeTypeEnum priceType, int totalBonus, int totalNum, int rdMin,
        int rdMax) {
        if (priceType != EnvelopPrizeTypeEnum.RANDOM_WITH_RANGE) {
            return;
        }
        Preconditions.checkArgument(rdMin > 0, "随机下限设置不正确");
        Preconditions.checkArgument(rdMax >= 1, "随机上限设置不正确");
        Preconditions.checkArgument(rdMax >= rdMin, "随机界限设置不正确");
        int maxNum = totalNum * rdMax;
        Preconditions.checkArgument(maxNum >= totalBonus, "总红包量设置不正确");
    }

    /**
     * 红包随机分配算法 1, 2
     *
     * @param remainMoney
     *            剩余金额
     * @param remainCount
     *            剩余红包数量
     * @param priceType
     * @return
     */
    public static int getUnpackAmount(int remainMoney, int remainCount, EnvelopPrizeTypeEnum priceType) {
        if (remainCount == 1) {
            return remainMoney;
        }
        if (priceType == EnvelopPrizeTypeEnum.RANDOM) {
            int between = (remainMoney / remainCount) << 1;
            int boundNum = between - 2;
            if (boundNum <= 0) {
                return 1;
            } else {
                return new SecureRandom().nextInt(boundNum) + 1;
            }
        } else {
            return remainMoney / remainCount;
        }
    }
}

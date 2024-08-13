package com.phaeris.rolex.util;

import com.phaeris.rolex.exception.ExternalException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wyh
 * @since 2024/8/6
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unused")
public class CronUtil {

    private static final String CRON_FORMAT = "%s %s %s %s %s %s";

    private static final String EVERY = "*";

    private static final String DAY_WEEK_EVERY = "?";

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * 日期转cron
     *
     * @param timestamp 时间戳
     * @param deep      获取日期元素深度, 按顺序获取{@code 秒-分-时-日-月-周}
     * @return cron表达式
     */
    public static String date2cron(long timestamp, int deep) {
        return date2cron(new Date(timestamp), deep);
    }

    /**
     * 日期转cron
     *
     * @param date 日期
     * @param deep 获取日期元素深度, 按顺序获取{@code 秒-分-时-日-月-周}
     * @return cron表达式
     */
    public static String date2cron(Date date, int deep) {
        return getCron(CronParser.deepBy(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), deep));
    }

    /**
     * 日期转cron
     *
     * @param dateStr 日期字符串(yyyy-MM-dd HH:mm:ss)
     * @param deep    获取日期元素深度, 按顺序获取{@code 秒-分-时-日-月-周}
     * @return cron表达式
     */
    public static String date2cron(CharSequence dateStr, int deep) {
        return getCron(CronParser.deepBy(LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)), deep));
    }

    /**
     * 生成6位cron表达式
     *
     * @param dateEle 日期元素, 按顺序设置{@code 秒-分-时-日-月-周}, 缺少元素默认后面追加周期
     * @return cron
     */
    public static String getCron(String... dateEle) {
        String[] eleArr = dateEle;
        if (dateEle.length < 6) {
            eleArr = Arrays.copyOf(dateEle, 6);
        }
        return getCron(eleArr[0], eleArr[1], eleArr[2], eleArr[3], eleArr[4], eleArr[5]);
    }

    /**
     * 生成6位cron表达式
     *
     * @param s   秒
     * @param m   分
     * @param h   时
     * @param d   日
     * @param mon 月
     * @param w   周(周天=1 周一=2)
     * @return cron
     */
    public static String getCron(String s, String m, String h, String d, String mon, String w) {
        if (d != null && w != null) {
            throw new ExternalException("日与周不能同时指定");
        }
        String second = getEleFormat(s, EVERY);
        String minute = getEleFormat(m, EVERY);
        String hour = getEleFormat(h, EVERY);
        String day = getEleFormat(d, w == null ? EVERY : DAY_WEEK_EVERY);
        String month = getEleFormat(mon, EVERY);
        String week = getEleFormat(w, DAY_WEEK_EVERY);
        return String.format(CRON_FORMAT, second, minute, hour, day, month, week);
    }

    /**
     * 获取时间元素
     *
     * @param dateEle 日期元素
     * @param every   默认值
     * @return 日期元素
     */
    private static String getEleFormat(String dateEle, String every) {
        return Optional.ofNullable(dateEle).orElse(every);
    }

    @Getter
    @AllArgsConstructor
    public enum CronParser {

        SECOND(1, LocalDateTime::getSecond),

        MINUTE(2, LocalDateTime::getMinute),

        HOUR(3, LocalDateTime::getHour),

        DAY(4, LocalDateTime::getDayOfMonth),

        MONTH(5, x -> x.getMonth().getValue()),

        WEEK(6, x -> (x.getDayOfWeek().getValue() % 7) + 1);

        private final int deep;

        private final Function<LocalDateTime, Integer> function;

        public static String[] deepBy(LocalDateTime date, int deep) {
            List<CronParser> filter = Arrays.stream(CronParser.values())
                    .filter(x -> x.getDeep() <= deep)
                    .collect(Collectors.toList());
            String[] ans = new String[deep];
            for (int i = 0; i < deep; i++) {
                if (deep == 6 && i == 3) {
                    //如果深度到周了, 设置日为*
                    ans[i] = null;
                } else {
                    ans[i] = String.valueOf(filter.get(i).getFunction().apply(date));
                }
            }
            return ans;
        }
    }
}

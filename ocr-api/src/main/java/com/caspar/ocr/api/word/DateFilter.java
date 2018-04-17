package com.caspar.ocr.api.word;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:匹配文本日期
 *
 * @author Caspar
 * @Date 2018-04-14
 */
@Slf4j
public class DateFilter {

    // yyyy-MM-dd HH:mm:ss
    private static final String REGEX_1 = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}:\\d{2}:\\d{2})";
    private static final String REGEX_1_PATTERN = "yyyy-MM-dd HH:mm:ss";

    // yyyy/MM/dd HH:mm:ss
    private static final String REGEX_2 = "(\\d{4})/(\\d{1,2})/(\\d{1,2}) (\\d{2}:\\d{2}:\\d{2})";
    private static final String REGEX_2_PATTERN = "yyyy/MM/dd HH:mm:ss";

    // yyyy年MM月dd日 HH:mm:ss
    private static final String REGEX_3 = "(\\d{4})年(\\d{2})月(\\d{2})日 (\\d{2}:\\d{2}:\\d{2})";
    private static final String REGEX_3_PATTERN = "yyyy年MM月dd日 HH:mm:ss";

    // yyyy.MM.dd HH:mm:ss
    private static final String REGEX_4 = "(\\d{4}).(\\d{2}).(\\d{2}) (\\d{2}:\\d{2}:\\d{2})";
    private static final String REGEX_4_PATTERN = "yyyy.MM.dd HH:mm:ss";

    // dd/MM/yyyy HH:mm:ss
    private static final String REGEX_5 = "(\\d{2})/(\\d{2})/(\\d{4}) (\\d{2}:\\d{2}:\\d{2})";
    private static final String REGEX_5_PATTERN = "dd/MM/yyyy HH:mm:ss";

    //-----------------------------------------------------------------------------------------------------

    // yyyy-MM-dd HH:mm
    private static final String REGEX_11 = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}:\\d{2})";
    private static final String REGEX_11_PATTERN = "yyyy-MM-dd HH:mm";

    // yyyy/MM/dd HH:mm
    private static final String REGEX_21 = "(\\d{4})/(\\d{1,2})/(\\d{1,2}) (\\d{2}:\\d{2})";
    private static final String REGEX_21_PATTERN = "yyyy/MM/dd HH:mm";

    // yyyy年MM月dd日 HH:mm
    private static final String REGEX_31 = "(\\d{4})年(\\d{2})月(\\d{2})日 (\\d{2}:\\d{2})";
    private static final String REGEX_31_PATTERN = "yyyy年MM月dd日 HH:mm";

    // yyyy.MM.dd HH:mm
    private static final String REGEX_41 = "(\\d{4}).(\\d{2}).(\\d{2}) (\\d{2}:\\d{2})";
    private static final String REGEX_41_PATTERN = "yyyy.MM.dd HH:mm";

    // dd/MM/yyyy HH:mm
    private static final String REGEX_51 = "(\\d{2})/(\\d{2})/(\\d{4}) (\\d{2}:\\d{2})";
    private static final String REGEX_51_PATTERN = "dd/MM/yyyy HH:mm";

    //-----------------------------------------------------------------------------------------------------

    // yyyy-MM-dd
    private static final String REGEX_12 = "(\\d{4})-(\\d{2})-(\\d{2})";
    private static final String REGEX_12_PATTERN = "yyyy-MM-dd";

    // yyyy/MM/dd
    private static final String REGEX_22 = "(\\d{4})/(\\d{1,2})/(\\d{1,2})";
    private static final String REGEX_22_PATTERN = "yyyy/MM/dd";

    // yyyy年MM月dd日
    private static final String REGEX_32 = "(\\d{4})年(\\d{2})月(\\d{2})日";
    private static final String REGEX_32_PATTERN = "yyyy年MM月dd日";

    // yyyy.MM.dd
    private static final String REGEX_42 = "(\\d{4}).(\\d{2}).(\\d{2})";
    private static final String REGEX_42_PATTERN = "yyyy.MM.dd";

    // dd/MM/yyyy
    private static final String REGEX_52 = "(\\d{2})/(\\d{2})/(\\d{4})";
    private static final String REGEX_52_PATTERN = "dd/MM/yyyy";

    //-----------------------------------------------------------------------------------------------------

    // HH:mm:ss
    private static final String REGEX_TIME_1 = "(\\d{2}:\\d{2}:\\d{2})";
    private static final String REGEX_TIME_1_PATTERN = "HH:mm:ss";

    // HH:mm
    private static final String REGEX_TIME_2 = "(\\d{2}:\\d{2})";
    private static final String REGEX_TIME_2_PATTERN = "HH:mm";

    private static Map<String, String> pattern;

    static {
        pattern = new LinkedHashMap<>();
        pattern.put(REGEX_1, REGEX_1_PATTERN);
        pattern.put(REGEX_2, REGEX_2_PATTERN);
        pattern.put(REGEX_3, REGEX_3_PATTERN);
        pattern.put(REGEX_4, REGEX_4_PATTERN);
        pattern.put(REGEX_5, REGEX_5_PATTERN);

        pattern.put(REGEX_11, REGEX_11_PATTERN);
        pattern.put(REGEX_21, REGEX_21_PATTERN);
        pattern.put(REGEX_31, REGEX_31_PATTERN);
        pattern.put(REGEX_41, REGEX_41_PATTERN);
        pattern.put(REGEX_51, REGEX_51_PATTERN);

        pattern.put(REGEX_12, REGEX_12_PATTERN);
        pattern.put(REGEX_22, REGEX_22_PATTERN);
        pattern.put(REGEX_32, REGEX_32_PATTERN);
        pattern.put(REGEX_42, REGEX_42_PATTERN);
        pattern.put(REGEX_52, REGEX_52_PATTERN);

        pattern.put(REGEX_TIME_1, REGEX_TIME_1_PATTERN);
        pattern.put(REGEX_TIME_2, REGEX_TIME_2_PATTERN);
    }

    public static long matchDate(String text) {
        for (Map.Entry<String, String> entry : pattern.entrySet()) {
            try {
                String key = entry.getKey();
                String value = entry.getValue();

                Pattern pattern = Pattern.compile(key);
                Matcher matcher = pattern.matcher(text);

                if (matcher.find()) {
                    String dateStr = matcher.group(0);

                    if (REGEX_TIME_1.equals(key) || REGEX_TIME_2.equals(key)) {
                        StringBuilder matchTime = new StringBuilder();
                        matchTime.append(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(System.currentTimeMillis()))
                                .append(" ")
                                .append(dateStr);

                        if (REGEX_TIME_2.equals(key)) {
                            matchTime.append(":00");
                        }

                        return FastDateFormat.getInstance(REGEX_1_PATTERN).parse(matchTime.toString()).getTime();
                    } else {
                        return FastDateFormat.getInstance(value).parse(dateStr).getTime();
                    }
                }
            } catch (Exception e) {

            }
        }

        return 0L;
    }

}

package com.caspar.ocr.api.word;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-14
 */
@Component
public class WordFilter {

    /**
     * 最小匹配规则
     */
    public static final int MIN_MATCH_TYPE = 1;

    /**
     * 最大匹配规则
     */
    public static final int MAX_MATCH_TYPE = 2;

    /**
     * 匹配汉字、字母、数字正则表达式
     */
    private static final Pattern LEGAL_COMMON_CHAR_REG = Pattern.compile("^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$");

    @Autowired
    private WordInitializer wordInitializer;

    /**
     * 获取匹配到的词汇
     *
     * @param wordType  词汇类型
     * @param text      文本
     * @param matchType 匹配模式,当前为:最大匹配和最小匹配
     * @return
     */
    public Set<String> getWords(int wordType, String text, int matchType) {
        Set<String> words = new HashSet<>();

        for (int i = 0, j = text.length(); i < j; i++) {
            int length = checkWord(wordType, text, i, matchType);

            // 存在,存入集合中
            if (length > 0) {
                words.add(text.substring(i, i + length));
                // 减1,因为for自增
                i = i + length - 1;
            }
        }

        return words;
    }

    /**
     * 获取第一个匹配到的词
     *
     * @param wordType
     * @param text
     * @param matchType
     * @return
     */
    public String getFirstMatch(int wordType, String text, int matchType) {
        for (int i = 0, j = text.length(); i < j; i++) {
            int length = checkWord(wordType, text, i, matchType);

            if (length > 0) {
                return text.substring(i, i + length);
            }
        }
        return "";
    }

    /**
     * 检查文本是否包含词库中的词汇,存在返回长度,不存在返回0
     *
     * @param wordType   词汇类型
     * @param text       文本
     * @param beginIndex 开始索引
     * @param matchType  匹配模式:当前为,最大和最小匹配
     * @return
     */
    private int checkWord(int wordType, String text, int beginIndex, int matchType) {
        // 敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;

        // 匹配标识数默认为0
        int matchFlag = 0;
        //特殊字符后缀长度，目的在于去除特殊字符后缀
        int specialCharSuffixCount = 0;

        Map currentMap = wordInitializer.getWordsMap(wordType);
        for (int i = beginIndex; i < text.length(); i++) {
            char word = text.charAt(i);

            // 若为特殊符号，跳过并判断下一个字符
            boolean isMatched = LEGAL_COMMON_CHAR_REG.matcher(String.valueOf(word)).matches();
            if (!isMatched) {
                /*isMatched == false && currentMap == sensitiveWordMap
                此时情况为特殊符号重新开始匹配，忽律这种情况。以防发生'@#关键字'这种情况*/

                //特殊符号不是重新匹配
                matchFlag++;
                //累加特殊字符后缀长度
                specialCharSuffixCount++;
                continue;
            }

            // 获取指定key
            currentMap = (Map) currentMap.get(word);
            // 存在，则判断是否为最后一个
            if (currentMap != null) {
                // 找到相应key，匹配标识+1
                matchFlag++;

                //1、不是以纯特殊字符为后缀
                //2、特殊字符后面的汉字（数字、字母）存在subMap
                //那么，清零特殊字符后缀长度
                specialCharSuffixCount = 0;

                // 如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1".equals(currentMap.get("isEnd"))) {
                    // 结束标志位为true
                    flag = true;
                    if (MIN_MATCH_TYPE == matchType) {// 最小规则，直接返回
                        break;
                    } else if (MAX_MATCH_TYPE == matchType) {//最大规则还需继续查找
                        continue;
                    }
                }
            } else {// 不存在，直接返回
                break;
            }
        }
        // 长度必须大于等于1，为词
        if (matchFlag < 2 || !flag) {
            matchFlag = 0;
        }
        //去除特殊字符后缀
        if (matchFlag - specialCharSuffixCount >= 2) {
            matchFlag = matchFlag - specialCharSuffixCount;
        }
        return matchFlag;
    }

}

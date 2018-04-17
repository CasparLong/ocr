package com.caspar.ocr.api.word;

import com.caspar.ocr.persistent.entity.TextWord;
import com.caspar.ocr.service.service.TextWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-14
 */
@Component
@Slf4j
public class WordInitializer implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * 结束标志位：已结束
     */
    private static final String END_FLAG_TRUE = "1";

    /**
     * 结束标志位：
     */
    private static final String END_FLAG_FALSE = "0";

    /**
     * 商城词类型
     */
    public static final int MALL_TEXT_WORD_TYPE = 0;

    /**
     * 金额相关词类型
     */
    public static final int AMOUNT_TEXT_WORD_TYPE = 1;

    @Autowired
    private TextWordService textWordService;

    private Map mallWordsMap;

    private Map amountWordsMap;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        launchInit();
    }

    /**
     * 初始化词库
     */
    private synchronized void launchInit() {
        launchIni(MALL_TEXT_WORD_TYPE);
        launchIni(AMOUNT_TEXT_WORD_TYPE);
    }

    /**
     * 初始化词库
     *
     * @param wordType
     */
    private synchronized void launchIni(int wordType) {
        long l1 = System.currentTimeMillis();
        log.info("### TextWords initializing.");

        Set<TextWord> originalWords = loadRawWords(wordType);

        switch (wordType) {
            case MALL_TEXT_WORD_TYPE:
                mallWordsMap = addTextWordToHashMap(originalWords);
                break;
            case AMOUNT_TEXT_WORD_TYPE:
                amountWordsMap = addTextWordToHashMap(originalWords);
        }

        long l2 = System.currentTimeMillis();
        log.info("### TextWords initialized, type:{}, deal: {}ms", wordType, (l2 - l1));
    }

    /**
     * 刷新词库
     *
     * @param wordType
     */
    public void refreshWords(int wordType) {
        switch (wordType) {
            case MALL_TEXT_WORD_TYPE:
                refreshWords(mallWordsMap, wordType);
                break;
            case AMOUNT_TEXT_WORD_TYPE:
                refreshWords(amountWordsMap, wordType);
        }
    }

    /**
     * 刷新词库
     *
     * @param wordsMap
     * @param wordType
     */
    public void refreshWords(Map wordsMap, int wordType) {
        log.info("### Refresh TextWord, type:{}", wordType);

        if (wordsMap != null) {
            wordsMap.clear();
        }
        launchIni(wordType);

        log.info("### Refresh TextWord success");
    }

    /**
     * 读取原始词
     *
     * @param wordType 类型
     * @return
     */
    private Set<TextWord> loadRawWords(int wordType) {
        TextWord textWord = new TextWord();
        textWord.setWordType(wordType);
        List<TextWord> textWords = textWordService.select(textWord);

        CopyOnWriteArraySet<TextWord> originalTextWords = new CopyOnWriteArraySet<>(textWords);
        log.info("Original words,type={},size={}", wordType, originalTextWords.size());
        return originalTextWords;
    }

    /**
     * 读取词库,构建DFA算法模型
     *
     * @param originalTextWords
     * @return
     */
    private Map addTextWordToHashMap(Set<TextWord> originalTextWords) {
        // 初始化词库容器，减少扩容操作
        Map wordMap = new HashMap(originalTextWords.size());

        for (TextWord textWord : originalTextWords) {
            String word = textWord.getWord();
            Map nowMap = wordMap;
            for (int i = 0, j = word.length(); i < j; i++) {
                char keyChar = word.charAt(i);
                Object tempMap = nowMap.get(keyChar);

                // 如果存在该key，直接赋值
                if (tempMap != null) {
                    nowMap = (Map) tempMap;
                }
                // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                else {
                    // 设置标志位
                    Map<String, String> newMap = new HashMap<String, String>();
                    newMap.put("isEnd", END_FLAG_FALSE);

                    // 添加到集合
                    nowMap.put(keyChar, newMap);
                    nowMap = newMap;
                }
                // 最后一个
                if (i == word.length() - 1) {
                    nowMap.put("isEnd", END_FLAG_TRUE);
                }
            }
        }
        return wordMap;
    }

    public Map getWordsMap(int wordType) {
        switch (wordType) {
            case MALL_TEXT_WORD_TYPE:
                return getMallWordsMap();
            case AMOUNT_TEXT_WORD_TYPE:
                return getAmountWordsMap();
            default:
                return null;
        }
    }

    public Map getMallWordsMap() {
        return mallWordsMap;
    }

    public Map getAmountWordsMap() {
        return amountWordsMap;
    }
}

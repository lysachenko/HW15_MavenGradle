package com.lysachenko.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class WordService {

    public Map<String, Integer> getCountOfWords(List<String> textSong, int limit) {
        Map<String, Integer> wordCountMap = new TreeMap<>();
        Integer count;
        for (String word : textSong) {
            count = wordCountMap.get(word);
            if (count == null) {
                wordCountMap.put(word, 1);
            } else {
                wordCountMap.put(word, count + 1);
            }
        }
        wordCountMap = wordCountMap
                .entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
        return wordCountMap;
    }

    public List<String> removeBadWords(List<String> songText, List<String> badWords) {
        return songText.stream()
                .filter(word -> !badWords.contains(word))
                .collect(Collectors.toList());
    }

    public int getWordsCount(List<String> stringList) {
        return stringList.size();
    }

    public List<String> toLowerCaseSymbols(List<String> stringList) {
        return stringList.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public List<String> removeWordsLengthLessThan(List<String> stringList, int length) {
        return stringList.stream()
                .filter(s -> s.length() > length)
                .collect(Collectors.toList());
    }

    public List<String> removeUnnecessarySymbols(List<String> stringList) {
        return stringList.stream()
                .map(s -> s.replace(",", ""))
                .map(s -> s.replace(" ", ""))
                .map(s -> s.replace("(", ""))
                .map(s -> s.replace(")", ""))
                .map(s -> s.replace("!", ""))
                .collect(Collectors.toList());
    }
}

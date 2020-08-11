package com.lysachenko;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Executor {

    private final String SOURCE_FILE_NAME = "data.txt";
    private final String BAD_WORD_FILE_NAME = "bad_words.txt";

    public void run() {
        List<String> songText = removeUnnecessarySymbols(readFile(SOURCE_FILE_NAME));
        List<String> badWordsList = removeUnnecessarySymbols(readFile(BAD_WORD_FILE_NAME));

        int totalWordCount = getWordsCount(songText);
        List<String> textSongWithoutBadWords = removeBadWords(songText, badWordsList);
        int totalWordCountWithoutBadWords = getWordsCount(removeBadWords(songText, badWordsList));
        List<String> textSongWithoutBadWordsAndWordsLengthLessThree =
                removeWordsLengthLessThan(removeBadWords(songText, badWordsList), 3);
        int totalWordCountWithoutBadWordsAndWordsLengthLessThree =
                getWordsCount(removeWordsLengthLessThan(removeBadWords(songText, badWordsList), 3));

        System.out.println("Total count of words = " + totalWordCount);
        System.out.println("Word list without bad words: " + textSongWithoutBadWords);
        System.out.println("Total count of words without bad words = " + totalWordCountWithoutBadWords);
        System.out.println("Word list without bad words and word length less 3: "
                + textSongWithoutBadWordsAndWordsLengthLessThree);
        System.out.println("Total count of words without bad words and word length less 3 = "
                + totalWordCountWithoutBadWordsAndWordsLengthLessThree);

        System.out.println("First 6 words that are most often repeated:");
        getCountOfWords(songText, 6).forEach((word, count) -> System.out.println(word + " = " + count));
    }

    private Map<String, Integer> getCountOfWords(List<String> textSong, int limit) {
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

    private List<String> removeBadWords(List<String> songText, List<String> badWords) {
        return songText.stream()
                .filter(word -> !badWords.contains(word))
                .collect(Collectors.toList());
    }

    private int getWordsCount(List<String> stringList) {
        return stringList.size();
    }

    private List<String> toLowerCaseSymbols(List<String> stringList) {
        return stringList.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    private List<String> removeWordsLengthLessThan(List<String> stringList, int length) {
        return stringList.stream()
                .filter(s -> s.length() > length)
                .collect(Collectors.toList());
    }

    private List<String> removeUnnecessarySymbols(List<String> stringList) {
        return stringList.stream()
                .map(s -> s.replace(",", ""))
                .map(s -> s.replace(" ", ""))
                .map(s -> s.replace("(", ""))
                .map(s -> s.replace(")", ""))
                .map(s -> s.replace("!", ""))
                .collect(Collectors.toList());
    }

    public List<String> readFile(String fileName) {
        List<String> strings = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                strings.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return strings;
    }
}

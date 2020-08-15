package com.lysachenko;

import com.lysachenko.service.WordService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Executor {

    private static final String SOURCE_FILE_NAME = "data.txt";
    private static final String BAD_WORD_FILE_NAME = "bad_words.txt";
    private static final int WORD_LENGTH = 3;
    private static final int COUNT_OF_REPEATED_WORDS = 6;

    public void run() {
        WordService wordService = new WordService();

        List<String> songText = wordService.removeUnnecessarySymbols(readFileFromResources(SOURCE_FILE_NAME));
        List<String> badWordsList = wordService.removeUnnecessarySymbols(readFileFromResources(BAD_WORD_FILE_NAME));

        int totalWordCount = wordService.getWordsCount(songText);
        List<String> textSongWithoutBadWords = wordService.removeBadWords(songText, badWordsList);
        int totalWordCountWithoutBadWords = wordService.getWordsCount(wordService.removeBadWords(songText, badWordsList));
        List<String> textSongWithoutBadWordsAndWordsLengthLessSpecified =
                wordService.removeWordsLengthLessThan(wordService.removeBadWords(songText, badWordsList), WORD_LENGTH);
        int totalWordCountWithoutBadWordsAndWordsLengthLessThree =
                wordService.getWordsCount(wordService.removeWordsLengthLessThan(wordService.removeBadWords(songText, badWordsList), 3));

        System.out.println("Total count of words = " + totalWordCount);
        System.out.println("Word list without bad words: " + textSongWithoutBadWords);
        System.out.println("Total count of words without bad words = " + totalWordCountWithoutBadWords);
        System.out.println("Word list without bad words and word length less " + WORD_LENGTH + ": "
                + textSongWithoutBadWordsAndWordsLengthLessSpecified);
        System.out.println("Total count of words without bad words and word length less " + WORD_LENGTH + " = "
                + totalWordCountWithoutBadWordsAndWordsLengthLessThree);

        System.out.println("First " + COUNT_OF_REPEATED_WORDS + " words that are most often repeated:");
        wordService.getCountOfWords(songText, COUNT_OF_REPEATED_WORDS).forEach((word, count) -> System.out.println(word + " = " + count));
    }

    public List<String> readFileFromResources(String fileName) {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile());
        List<String> strings = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                strings.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return strings;
    }
}

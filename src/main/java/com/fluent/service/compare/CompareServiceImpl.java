package com.fluent.service.compare;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompareServiceImpl implements CompareService {

    @Override
    public List<Integer> getDifferenceIndexes(String correctSentence, String givenSentence) {
        int len1 = correctSentence.length();
        int len2 = givenSentence.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            for (int j = 0; j <= len2; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (correctSentence.charAt(i - 1) == givenSentence.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], // Replace
                            Math.min(dp[i - 1][j], // Remove
                                    dp[i][j - 1])); // Insert
                }
            }
        }

        return getDiffIndexes(correctSentence, givenSentence, dp);
    }

    private List<Integer> getDiffIndexes(String correctSentence, String givenSentence, int[][] dp) {
        List<Integer> diffIndexes = new ArrayList<>();
        int i = correctSentence.length();
        int j = givenSentence.length();

        while (i > 0 && j > 0) {
            if (correctSentence.charAt(i - 1) == givenSentence.charAt(j - 1)) {
                i--;
                j--;
            } else {
                diffIndexes.add(j - 1); // 'j' instead of 'i'
                if (dp[i][j] == dp[i - 1][j - 1] + 1) {
                    i--;
                    j--;
                } else if (dp[i][j] == dp[i - 1][j] + 1) {
                    i--;
                } else if (dp[i][j] == dp[i][j - 1] + 1) {
                    j--;
                }
            }
        }

        while (j > 0) { // 'j' instead of 'i'
            diffIndexes.add(j - 1);
            j--;
        }

        return diffIndexes;
    }
}

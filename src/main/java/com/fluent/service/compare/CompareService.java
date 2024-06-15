package com.fluent.service.compare;

import java.util.List;

public interface CompareService {
    List<Integer> getDifferenceIndexes(String correctSentence, String givenSentence);
}
package com.fluent.controller;

import com.fluent.dto.SentenceDTO;
import com.fluent.service.compare.CompareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CompareController {

    private final CompareService compareService;

    public CompareController(CompareService compareService) {
        this.compareService = compareService;
    }

    @PostMapping("/compare")
    public List<Integer> compareSentences(@RequestBody SentenceDTO sentenceDTO) {
        return compareService.getDifferenceIndexes(sentenceDTO.getCorrectSentence(), sentenceDTO.getGivenSentence());
    }
}
package com.thowl.vocabulary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thowl.vocabulary.entity.Vocabulary;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {

}

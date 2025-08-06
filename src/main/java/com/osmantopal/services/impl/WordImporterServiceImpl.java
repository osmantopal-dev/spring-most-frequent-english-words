package com.osmantopal.services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.osmantopal.entities.EnglishWord;
import com.osmantopal.repository.EnglishWordRepository;
import com.osmantopal.services.IWordImporterService;

@Service
public class WordImporterServiceImpl implements IWordImporterService {

    @Autowired
    private EnglishWordRepository englishWordRepository;

	@Override
	public void importWordsFromFile() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource("words.txt").getInputStream(), "UTF-8"))) {
            
            List<EnglishWord> words = new ArrayList<>();
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) { // Başlık satırını atla
                    isHeader = false;
                    continue;
                }

                String[] columns = line.split(",");
                if (columns.length == 3) {
                    String englishWord = columns[0].trim();
                    String turkishMeaning = columns[1].trim();
                    String level = columns[2].trim().toUpperCase();
                    
                    System.out.println("Kelime: " + englishWord + ", Anlam: " + turkishMeaning + ", Seviye: " + level);
                    if (englishWord.isEmpty() || turkishMeaning.isEmpty() || level.isEmpty()) {
                        System.err.println("Boş değer bulundu, atlanıyor: " + line);
                        continue; // Boş değerleri atla
                    }

                    if (!englishWordRepository.existsByWord(englishWord)) {
                        EnglishWord word = new EnglishWord();
                        word.setId(null); // Yeni bir ID ataması için null yap
                        word.setEnglishWord(englishWord);
                        word.setTurkishMeaning(turkishMeaning);
                        word.setWordLevel(level);
                        englishWordRepository.save(word);
                    }
                    
                    
                }
            }

            englishWordRepository.saveAll(words);
            System.out.println(words.size() + " kelime başarıyla yüklendi!");

        } catch (Exception e) {
            System.err.println("Hata: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
}
package org.mycompany;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

public class Main {
    public static void main(String[] args) {

        Translate translate = TranslateOptions.getDefaultInstance().getService();
        MyTranslationService translationService = new MyTranslationService(translate);

        // Пример использования сервиса
        String sentence = "Hello, how are you?";
        String targetLanguage = "ru"; // Целевой язык - русский

        try {
            String translatedText = translationService.translateWithGoogle(sentence, targetLanguage);
            System.out.println("Translated text: " + translatedText);
        } catch (MyTranslationServiceException e) {
            System.err.println("Translation failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument: " + e.getMessage());
        }
    }
}
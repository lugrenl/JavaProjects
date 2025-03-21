package org.mycompany;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MyTranslationServiceTest {

    @Mock
    private Translate translate;

    @Mock
    private Translation translation;

    private MyTranslationService myTranslationService;

    @BeforeEach
    void setUp() {
        myTranslationService = new MyTranslationService(translate);
    }

    /**
     * 1. Happy case test.
     * <p>
     * When `MyTranslationService::translateWithGoogle` method is called with any sentence and target language is equal to "ru",
     * `googleTranslate` dependency should be called and `translation.getTranslatedText()` returned.
     * No other interactions with `googleTranslate` dependency should be invoked apart from a single call to `googleTranslate.translate()`.
     */
    @Test
    void translateWithGoogle_anySentenceAndTargetLanguageIsRu_success() {
        String sentence = "Test sentence";
        String targetLanguage = "ru";
        String expectedResult = "Тестовое предложение";

        when(translate.translate(eq(sentence), any())).thenReturn(translation);
        when(translation.getTranslatedText()).thenReturn(expectedResult);

        String actualResult = myTranslationService.translateWithGoogle(sentence, targetLanguage);

        assertEquals(expectedResult, actualResult);

        verify(translate).translate(eq(sentence), any());
        verifyNoMoreInteractions(translate);

        verify(translation).getTranslatedText();
        verifyNoMoreInteractions(translation);
    }

    /**
     * 2. Unhappy case test when target language is not supported.
     * <p>
     * When `MyTranslationService::translateWithGoogle` method is called with any sentence and target language is not equal to "ru",
     * `IllegalArgumentException` should be thrown. `googleTranslate` dependency should not be called at all.
     */
    @Test
    void translateWithGoogle_anySentenceAndTargetLanguageIsNotRu_failure() {
        String sentence = "Test sentence";
        String targetLanguage = "br";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> myTranslationService.translateWithGoogle(sentence, targetLanguage));

        assertEquals("only translation to Russian is currently supported!", exception.getMessage());

        verifyNoInteractions(translate);
        verifyNoInteractions(translation);
    }

    /**
     * 3. Unhappy case test when Google Translate call throws exception.
     * <p>
     * When `MyTranslationService::translateWithGoogle` method is called with any sentence and target language is equal to "ru",
     * `googleTranslate` dependency should be called. When `googleTranslate` dependency throws exception, it should be
     * wrapped into `MyTranslationServiceException` and the latter should be thrown from our method.
     */
    @Test
    void translateWithGoogle_googleTranslateThrowsException_failure() {
        String sentence = "Test sentence";
        String targetLanguage = "ru";

        when(translate.translate(eq(sentence), any())).thenThrow(new RuntimeException());

        MyTranslationServiceException exception = assertThrows(MyTranslationServiceException.class,
                () -> myTranslationService.translateWithGoogle(sentence, targetLanguage));

        assertEquals("Exception while calling Google Translate API", exception.getMessage());

        verify(translate).translate(eq(sentence), any());
        verifyNoMoreInteractions(translate);

        verifyNoInteractions(translation);
    }
}
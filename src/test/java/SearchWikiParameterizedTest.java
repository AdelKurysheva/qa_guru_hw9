import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.$;

public class SearchWikiParameterizedTest {
    @BeforeEach
    void setup() {
        Configuration.browserSize = "1920x1080";
        Configuration.browser = "edge";
        Selenide.open("https://ru.wikipedia.org/wiki/");
        Configuration.holdBrowserOpen = true;
    }

    @ValueSource(strings = {
            "Samsung", "Sony"
    })
    @ParameterizedTest(name = "При поиске на Wiki {0} на странице должен быть текст {0}")
    @Tags({
            @Tag("BLOCKER"),
            @Tag("WEB")
    })
    void openWikiPagesTest(String testData) {
        $("#searchInput").setValue(testData);
        $("#searchButton").click();
        $(".mw-page-title-main").shouldHave(Condition.text(testData));
    }


    @CsvSource(value = {
            "Samsung, Samsung Group",
            "Sony, Sony Group Corporation"
    })
    @ParameterizedTest(name = "При поиске на Wiki {0} на странице должен быть текст {1}")
    @Tags({
            @Tag("BLOCKER"),
            @Tag("WEB")
    })
    void openWikiPagesTest2(String testData, String expectedResult) {
        $("#searchInput").setValue(testData);
        $("#searchButton").click();
        $(".infobox-above").shouldHave(Condition.text(expectedResult));
    }

    static Stream<String> argumentsStream() {
        return Stream.of("Samsung", "Sony");
    }

    @MethodSource("argumentsStream")
    @ParameterizedTest(name = "При поиске на Wiki {0} на странице должен быть текст {0}")
    @Tags({
            @Tag("BLOCKER"),
            @Tag("WEB")
    })
    void openWikiPagesTest3(String testData) {
        $("#searchInput").setValue(testData);
        $("#searchButton").click();
        $(".mw-page-title-main").shouldHave(Condition.text(testData));
    }
}

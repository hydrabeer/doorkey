package search;

import entity.AbstractVaultItem;
import entity.PasswordVaultItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.search.SearchDataAccessInterface;
import service.search.SearchInputBoundary;
import service.search.SearchInteractor;
import service.search.SearchOutputBoundary;
import service.search.SearchOutputData;
import service.search.SearchInputData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SearchInteractorTest {

    private SearchInputBoundary searchInteractor;
    private TestSearchOutputBoundary testOutputBoundary;

    @BeforeEach
    void setUp() {
        testOutputBoundary = new TestSearchOutputBoundary();
        TestSearchDataAccessObject testDataAccess = new TestSearchDataAccessObject();
        searchInteractor = new SearchInteractor(testOutputBoundary, testDataAccess);
    }

    @Test
    void testExecuteWithResults() {
        SearchInputData inputData = new SearchInputData("Google");
        searchInteractor.execute(inputData);

        assertEquals(2, testOutputBoundary.searchOutputData.getResults().size());
        assertEquals("Google",
                testOutputBoundary.searchOutputData.getResults().get(0).getTitle());
        assertEquals("Google Alt",
                testOutputBoundary.searchOutputData.getResults().get(1).getTitle());
    }

    @Test
    void testExecuteWithNoResults() {
        SearchInputData inputData = new SearchInputData("noresults");
        searchInteractor.execute(inputData);

        assertNull(testOutputBoundary.searchOutputData);
        assertEquals("No results found for \"noresults\".",
                testOutputBoundary.noResultsMessage);
    }

    private static class TestSearchOutputBoundary implements SearchOutputBoundary {

        private SearchOutputData searchOutputData;
        private String noResultsMessage;

        @Override
        public void prepareResultsView(SearchOutputData searchOutputData) {
            this.searchOutputData = searchOutputData;
        }

        @Override
        public void prepareNoResultsView(String message) {
            this.noResultsMessage = message;
        }
    }

    private static class TestSearchDataAccessObject implements SearchDataAccessInterface {

        @Override
        public List<AbstractVaultItem> getItems() {
            return List.of(new PasswordVaultItem(
                            "Google",
                            "user1",
                            "password1",
                            "https://example.com"),
                    new PasswordVaultItem(
                            "Google Alt",
                            "user2",
                            "password2",
                            "https://example.com"),
                    new PasswordVaultItem(
                            "result3",
                            "user3",
                            "password3",
                            "https://example.com"));
        }
    }
}

package service.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.AbstractVaultItem;
import entity.NoteVaultItem;
import entity.PasswordVaultItem;
import exception.AuthException;
import mock.MockRepositoryProvider;
import mock.MockUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchInteractorTest {
    private MockRepositoryProvider repositoryProvider;
    private UserRepository userRepository;
    private SearchInputBoundary searchInteractor;
    private TestSearchOutputBoundary testOutputBoundary;

    @BeforeEach
    void setUp() throws AuthException, IOException {
        testOutputBoundary = new TestSearchOutputBoundary();
        userRepository = new MockUserRepository();
        repositoryProvider = new MockRepositoryProvider(userRepository);
        userRepository.signupUser("h@gmail.com", "password123");

        userRepository.addVaultItem(new PasswordVaultItem(
                        "Google",
                        "hydrabeer",
                        "password",
                        "https://accounts.google.com/"
                )
        );
        userRepository.addVaultItem(new PasswordVaultItem(
                        "Google Alt",
                        "drake",
                        "password",
                        "https://accounts.google.com/"
                )
        );
        userRepository.addVaultItem(new PasswordVaultItem(
                        "CSC207",
                        "Zach",
                        "password",
                        "https://accounts.google.com/"
                )
        );
        userRepository.addVaultItem(new NoteVaultItem("Recipe", "Recipe for cookies"));
        searchInteractor = new SearchInteractor(repositoryProvider, testOutputBoundary);
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
    void testExecuteWithUsernameResults() {
        SearchInputData inputData = new SearchInputData("Zach");
        searchInteractor.execute(inputData);

        assertEquals(1, testOutputBoundary.searchOutputData.getResults().size());
        assertEquals("CSC207",
                testOutputBoundary.searchOutputData.getResults().get(0).getTitle());
    }

    @Test
    void testExecuteWithNoteResults() {
        SearchInputData inputData = new SearchInputData("Rec");
        searchInteractor.execute(inputData);

        assertEquals(1, testOutputBoundary.searchOutputData.getResults().size());
        assertEquals("Recipe",
                testOutputBoundary.searchOutputData.getResults().get(0).getTitle());
    }

    @Test
    void testExecuteWithNoResults() {
        SearchInputData inputData = new SearchInputData("noresults");
        searchInteractor.execute(inputData);

        List<AbstractVaultItem> results = testOutputBoundary.searchOutputData.getResults();
        assertEquals(new ArrayList<>(), results);
    }

    @Test
    void testRepositoryProvider_isEmpty() {
        repositoryProvider.clearRepository();

        SearchInputData inputData = new SearchInputData("SearchItems");
        searchInteractor.execute(inputData);

        List<AbstractVaultItem> results = testOutputBoundary.searchOutputData.getResults();
        assertEquals(new ArrayList<>(), results);
    }

    private static class TestSearchOutputBoundary implements SearchOutputBoundary {

        private SearchOutputData searchOutputData;

        @Override
        public void prepareResultsView(SearchOutputData searchOutputData) {
            this.searchOutputData = searchOutputData;
        }
    }
}

package service.search.interface_adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.search.SearchInputBoundary;
import service.search.SearchInputData;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchControllerTest {

    private SearchController searchController;
    private MockSearchInputBoundary mockSearchInputBoundary;

    // Simple mock implementation of SearchInputBoundary
    private static class MockSearchInputBoundary implements SearchInputBoundary {
        private boolean executeCalled = false;

        @Override
        public void execute(SearchInputData inputData) {
            executeCalled = true;
        }

        public boolean isExecuteCalled() {
            return executeCalled;
        }
    }

    @BeforeEach
    public void setUp() {
        mockSearchInputBoundary = new MockSearchInputBoundary();
        searchController = new SearchController(mockSearchInputBoundary);
    }

    @Test
    public void testExecute() {
        String query = "test query";
        searchController.execute(query);

        assertTrue(mockSearchInputBoundary.isExecuteCalled());
    }
}
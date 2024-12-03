package service.search.interface_adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ViewManagerModel;
import service.search.SearchOutputData;
import entity.AbstractVaultItem;
import entity.NoteVaultItem;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchPresenterTest {

    private SearchPresenter searchPresenter;
    private SearchViewModel searchViewModel;
    private ViewManagerModel viewManagerModel;

    @BeforeEach
    public void setUp() {
        searchViewModel = new SearchViewModel();
        viewManagerModel = new ViewManagerModel();
        searchPresenter = new SearchPresenter(searchViewModel, viewManagerModel);
    }

    @Test
    public void testPrepareResultsView() {
        List<AbstractVaultItem> results = new ArrayList<>();
        results.add(new NoteVaultItem("Recipe", "Recipe for cookies"));
        SearchOutputData searchOutputData = new SearchOutputData(results);

        searchPresenter.prepareResultsView(searchOutputData);

        assertEquals(results, searchViewModel.getState().getItems());
        assertEquals(searchViewModel.getViewName(), viewManagerModel.getState());
    }
}
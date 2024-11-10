package use_cases.search;

/**
 * The output boundary for the Search use case.
 */
public interface SearchOutputBoundary {

    /**
     * Prepares the results view for the Search use case.
     *
     * @param searchResponseModel the output data
     */
    void prepareResultsView(SearchResponseModel searchResponseModel);

    /**
     * Prepares the no results view for the Search use case.
     *
     * @param message the message indicating no results were found
     */
    void prepareNoResultsView(String message);
}

package service.search;

/**
 * The output boundary for the Search use case.
 */
public interface SearchOutputBoundary {
    /**
     * Prepares the results view for the Search use case.
     *
     * @param searchOutputData the output data
     */
    void prepareResultsView(SearchOutputData searchOutputData);
}

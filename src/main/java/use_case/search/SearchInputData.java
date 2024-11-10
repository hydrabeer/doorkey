package use_case.search;

/**
 * The Input Data for the Search use case.
 */
public class SearchInputData {
    private final String query;

    public SearchInputData(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}

package com.akinkemer.newsmanagementapp.utilities.results;

public class EmptyDataResults extends DataResult {
    public EmptyDataResults(String message) {
        super(null, true ,message);
    }

    public EmptyDataResults() {
        super(null, true,"There is no data");
    }
}

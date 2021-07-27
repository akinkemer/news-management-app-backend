package com.akinkemer.newsmanagementapp.utilities.results;

public class EmptyDataResult extends DataResult {
    public EmptyDataResult(String message) {
        super(null, true ,message);
    }

    public EmptyDataResult() {
        super(null, true,"There is no data");
    }
}

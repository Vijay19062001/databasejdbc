package springdemo.databasejdbc.enums;

public enum RetentionStatus {

    EARLY_RETURN("early_return"),
    LATE_RETURN("late_return"),
    ON_TIME("on_time");

    private final String returns;

    RetentionStatus(String returns) {
        this.returns = returns;
    }

    public String getReturns() {
        return returns;
    }

}



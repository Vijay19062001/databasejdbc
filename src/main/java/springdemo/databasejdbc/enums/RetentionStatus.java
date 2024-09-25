package springdemo.databasejdbc.enums;

public enum RetentionStatus {

    ENTRY_DATE("entry_date"),
    RETURN_DATE("return_date"),
    EARLY_RETURN_DATE("early_return_date");

    private final String status;

    RetentionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}



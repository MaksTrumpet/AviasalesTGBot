package MTCompany.request;

public enum RequestCommands {
    INFO("/info"),
    REGISTRATION("/registration"),
    CANCEL("/cancel"),
    START("/start"),
    SEARCH_TICKETS_ONE_DAY_ONE_CITY("/1");
    private final String cmd;

    RequestCommands(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
    }

    public boolean equals(String cmd) {
        return this.toString().equals(cmd);
    }
}

package MTCompany.request;

public enum RequestCommands {
    INFO("/info"),
    REGISTRATION("/registration"),
    CANCEL("/cancel"),
    START("/start"),
    SEARCH_TICKETS_ONE_DAY_ONE_CITY("/1"),
    INPUT_DATA("input_data");
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

    public static RequestCommands fromString(String command) {
        for (RequestCommands requestCommand : RequestCommands.values()) {
            if (requestCommand.toString().equals(command)) {
                return requestCommand;
            }
        }
        return null;
    }

}

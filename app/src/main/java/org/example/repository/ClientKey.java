package org.example.repository;

public enum ClientKey {
    API_KEY("API_KEY", ""),
    CONNECTED_ACCOUNT_ID("CONNECTED_ACCOUNT_ID", "");

    private final String key;
    private final String value;

    ClientKey(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}

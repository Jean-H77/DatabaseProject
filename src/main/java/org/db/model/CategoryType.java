package org.db.model;

public enum CategoryType {
    BASE("CATEGORY_ID", "SELECT CATEGORY_ID FROM categories WHERE CATEGORY_NAME = ?"),
    TYPE("TYPE_ID", "SELECT TYPE_ID FROM categoryTypes WHERE TYPE_NAME = ?"),
    MAKER("MAKER_ID", "SELECT MAKER_ID FROM categoryMakers WHERE MAKER_NAME = ?");

    private final String primaryKeyName;
    private final String query;

    CategoryType(String primaryKeyName, String query) {
        this.primaryKeyName = primaryKeyName;
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }
}

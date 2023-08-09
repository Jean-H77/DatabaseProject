package org.db.model;

public class Category {

    private final int baseCategoryId;
    private final int makerCategoryId;
    private final int typeCategoryId;

    public Category(int baseCategoryId, int makerCategoryId, int typeCategoryId) {
        this.baseCategoryId = baseCategoryId;
        this.makerCategoryId = makerCategoryId;
        this.typeCategoryId = typeCategoryId;
    }

    public int getBaseCategoryId() {
        return baseCategoryId;
    }

    public int getMakerCategoryId() {
        return makerCategoryId;
    }

    public int getTypeCategoryId() {
        return typeCategoryId;
    }
}

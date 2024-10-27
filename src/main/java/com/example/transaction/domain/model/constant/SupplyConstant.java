package com.example.transaction.domain.model.constant;
public class SupplyConstant {
    private SupplyConstant() {
        throw new IllegalStateException("Utility class");
    }
    public static final String TASK_NOT_FOUND_MESSAGE_ERROR = "No found Supply with id %s";
    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";
    public static final String DETAILS = "details";
    public static final int ZERO_CONSTANT = 0;
    public static final String ARTICLE_MANDATORY = "id article is mandatory";
    public static final String ARTICLE_MESSAGE_MIN_ERROR = "id article must  be greater than zero";
    public static final String QUANTITY_MANDATORY = "quantity is mandatory";
    public static final String QUANTITY_MESSAGE_MIN_ERROR = "Quantity must be greater than zero";
    public static final int MAXIMUM_ALLOW_LETTERS = 50;
    public static final String MESSAGE_MANDATORY = "state is mandatory";
    public static final String MESSAGE_MAX_BIGGER = "state don't be bigger than 10 characters";
    public static final String MESSAGE_ERROR_ADD = "Supply Exist";
    public static final String LIST_EMPTY = "List Empty";
    public static final String PRICE_MANDATORY = "price is mandatory";
    public static final String PRICE_MIN_ZERO = "price article must  be greater than zero";
    public static final String LIST_ARTICLES_SALE_EMPTY = "List articles sale empty";
    public static final String TRASACTION_FAILED = "Transaction failed";
}

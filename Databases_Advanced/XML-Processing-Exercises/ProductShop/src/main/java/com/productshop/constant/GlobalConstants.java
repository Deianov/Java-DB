package com.productshop.constant;

public final class GlobalConstants {

    private static final String RESOURCES_PATH = System.getProperty("user.dir");

    public static final String FILE_PATH_CATEGORIES =
            "src/main/resources/files/xml/categories.xml";
    public static final String FILE_PATH_PRODUCTS =
            "src/main/resources/files/xml/products.xml";
    public static final String FILE_PATH_USERS =
            "src/main/resources/files/xml/users.xml";

//    public static final String FILE_QUERY1 = RESOURCES_PATH + "\\src\\main\\resources\\output\\query1.json";
//    public static final String FILE_QUERY2 = RESOURCES_PATH + "\\src\\main\\resources\\output\\query2.json";
//    public static final String FILE_QUERY3 = RESOURCES_PATH + "\\src\\main\\resources\\output\\query3.json";
//    public static final String FILE_QUERY4 = RESOURCES_PATH + "\\src\\main\\resources\\output\\query4.json";

    public static final String FILE_QUERY1 = "src/main/resources/output/query1.xml";
    public static final String FILE_QUERY2 = "src/main/resources/output/query2.xml";
    public static final String FILE_QUERY3 = "src/main/resources/output/query3.xml";
    public static final String FILE_QUERY4 = "src/main/resources/output/query4.xml";

    public static final double QUERY1_PRODUCT_RANGE_FROM = 500;
    public static final double QUERY1_PRODUCT_RANGE_TO = 1000;
}
package com.automation.data;

public final class TestData {

    private TestData() {}

    public static final class Users {
        public static final String STANDARD_USERNAME = "standard_user";
        public static final String LOCKED_OUT_USERNAME = "locked_out_user";
        public static final String PROBLEM_USERNAME = "problem_user";
        public static final String PERFORMANCE_GLITCH_USERNAME = "performance_glitch_user";
        public static final String ERROR_USERNAME = "error_user";
        public static final String VISUAL_USERNAME = "visual_user";
        // saucedemo's demo accounts all share this password.
        public static final String PASSWORD = "secret_sauce";

        private Users() {}
    }

    public static final class Products {
        public static final String BACKPACK = "Sauce Labs Backpack";
        public static final String BIKE_LIGHT = "Sauce Labs Bike Light";

        private Products() {}
    }

    public static final class Shipping {
        public static final String FIRST_NAME = "Damla";
        public static final String LAST_NAME = "Pinar";
        public static final String POSTAL_CODE = "10001";

        private Shipping() {}
    }
}

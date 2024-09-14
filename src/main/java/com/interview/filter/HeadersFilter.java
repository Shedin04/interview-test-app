package com.interview.filter;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.util.Date;

public class HeadersFilter implements Filter {

    private static final String X_REQUEST_ID = "X_REQUEST_ID";
    private static final String X_REQUEST_ID_PREFIX = "Autotest-";

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext fc) {
        requestSpec.header(new Header(X_REQUEST_ID, X_REQUEST_ID_PREFIX + getCurrentTimestamp()));
        return fc.next(requestSpec, responseSpec);
    }

    private String getCurrentTimestamp() {
        return String.valueOf(new Date().getTime());
    }
}
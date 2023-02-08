package ru.netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
public class APIHelper {
    private static final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static String sendForm(DataGenerator.UserInfo user, String param, int statusCode) {
        return given()
                .spec(requestSpecification)
                .body(user)
                .when()
                .post(param)
                .then()
                .log().all()
                .statusCode(statusCode)
                .extract().response().asString();
    }
}
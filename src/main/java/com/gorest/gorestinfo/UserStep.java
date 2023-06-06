package com.gorest.gorestinfo;

import com.gorest.constants.EndPoints;
import com.gorest.model.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class UserStep {
    @Step("Creating student with name : {0}, email : {1}, gender : {2}, status : {3}")
    public ValidatableResponse createUser (String name, String email, String gender, String status){
        UserPojo userPojo = new UserPojo();
         userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization"," Bearer 88df011bb43ef6f82191f4ee040dc88e67ecc8511770380a8126b1624919a1f4")
                .body(userPojo)
                .when()
                .post()
                .then();
    }

    @Step("Getting user information from the email : {0}")
    public HashMap<String,Object> getInfoByEmail(String email){
        String p1 = "findAll{it.email == '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .header("Authorization","Bearer 88df011bb43ef6f82191f4ee040dc88e67ecc8511770380a8126b1624919a1f4")
                .get()
                .then().log().all()
                .statusCode(200)
                .extract()
                .path("findAll{it.email == '" + email + "'}.get(0)");
    }
    @Step("Updating user with user_id: {0}, name : {1}, email : {2}, gender : {3}, status : {4}")

    public ValidatableResponse updateUser(int userId, String name, String email, String gender, String status){
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .header("Authorization","Bearer 88df011bb43ef6f82191f4ee040dc88e67ecc8511770380a8126b1624919a1f4")
                .header("Content-Type", "application/json")
                .pathParam("user_id",userId)
                .body(userPojo)
                .when()
                .put(EndPoints.UPDATE_A_USER_BY_ID)
                .then();
    }
    @Step("Deleting user information with id: {0}")
    public ValidatableResponse deleteUser(int userId) {
        return SerenityRest.given().log().all()
                .header("Authorization","Bearer 88df011bb43ef6f82191f4ee040dc88e67ecc8511770380a8126b1624919a1f4")
                .pathParam("user_id", userId)
                .when()
                .delete(EndPoints.DELETE_A_USER_BY_ID)
                .then();
    }

    @Step("Getting user information with studentId: {0}")
    public ValidatableResponse getUserById(int userId){
        return SerenityRest.given().log().all()
                .header("Authorization","Bearer 88df011bb43ef6f82191f4ee040dc88e67ecc8511770380a8126b1624919a1f4")
                .pathParam("user_id", userId)
                .when()
                .get(EndPoints.GET_USER_BY_ID)
                .then();
    }

}

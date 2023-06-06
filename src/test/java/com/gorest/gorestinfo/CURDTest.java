package com.gorest.gorestinfo;

import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class CURDTest extends TestBase {
    static int userId;
    static String name = "Riya" + TestUtils.getRandomValue();
    static String email = "Riya" + TestUtils.getRandomValue() + "@gmail.com";
    static String gender = "female";
    static String status = "Active";

    @Steps
    UserStep  userSteps;

    @Title("Creating new user.")
    @Test
    public void test001() {
        ValidatableResponse response =userSteps.createUser(name, email, gender, status);
        response.log().all().statusCode(201);
    }

    @Title("Verifying user was created.")
    @Test
    public void test002() {
        HashMap<String, Object> userMap = userSteps.getInfoByEmail(email);
        Assert.assertThat(userMap, hasValue(email));
        userId = (int) userMap.get("id");
    }

    @Title("Updating the user and verifying updated information.")
    @Test
    public void test003() {
        name = name + "-Updated";
        userSteps.updateUser(userId, name, email, gender, status)
                .log().all().statusCode(200);
        HashMap<String, Object> userMap = userSteps.getInfoByEmail(email);
        Assert.assertThat(userMap, hasValue(email));
    }

    @Title("Deleting the user and verifying user was deleted.")
    @Test
    public void test004() {
        userSteps.deleteUser(userId).statusCode(204);
        userSteps.getUserById(userId).statusCode(404);
    }


}

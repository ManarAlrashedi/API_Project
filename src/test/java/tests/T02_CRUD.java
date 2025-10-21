package tests;

import base_urls.CRUDActiviteUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.FakePostPojo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class T02_CRUD extends CRUDActiviteUrl {

    /*
        Task: Write code that performs all CRUD operations on "activities"
        using the Fake REST API at https://fakerestapi.azurewebsites.net
        Requirements:
        1. Use POJO classes for all operations
        2. Implement CREATE (POST) - Add new activity
        3. Implement READ (GET) - Retrieve activity details
        4. Implement UPDATE (PUT) - Modify existing activity
        5. Implement DELETE - Remove activity
        6. Add appropriate assertions for each operation
*/

    FakePostPojo expectedData;

    //post
    @Test(priority = 1)
    void createUserTest() {
        expectedData = new FakePostPojo(2, "Activity 2", "2025-10-21T13:11:44.8085893+00:00", true);

        //Send the request
        Response response = given(spec).body(expectedData).post("/api/v1/Activities/");
        response.prettyPrint();

        response
                .then()
                .statusCode(200)
                .body("id", equalTo(expectedData.getId()),
                        "title", equalTo(expectedData.getTitle()),
                        "dueDate", equalTo(expectedData.getDueDate()),
                        "completed", equalTo(expectedData.getCompleted())
                );
        FakePostPojo actualData = response.as(FakePostPojo.class);
        assertEquals(response.statusCode(), 200);
        assertEquals(actualData.getId(), expectedData.getId());
        assertEquals(actualData.getTitle(), expectedData.getTitle());
        assertEquals(actualData.getDueDate(), expectedData.getDueDate());
        assertEquals(actualData.getCompleted(), expectedData.getCompleted());

    }

    //get
    @Test(priority = 2)
    void readActivityTest() {
        int activityId = 2;
        Response response = given(spec).get("/api/v1/Activities/" + activityId);
        response.prettyPrint();
        FakePostPojo actualData = response.as(FakePostPojo.class);
        assertEquals(response.statusCode(), 200);
    }


    //put
    @Test(priority = 3)
    void updateActivityTest() {
        FakePostPojo updatedData = new FakePostPojo(222, "Coffee", "2025-11-11T00:00:00", false);
        Response response = given(spec).body(updatedData).put("/api/v1/Activities/2934");
        response.prettyPrint();
        FakePostPojo actualData = response.as(FakePostPojo.class);
        assertEquals(response.statusCode(), 200);
        assertEquals(actualData.getTitle(), "Coffee");
        assertEquals(actualData.getDueDate(), "2025-11-11T00:00:00");
        assertEquals(actualData.getCompleted(), false);
    }

    //delete
    @Test(priority = 4)
    void deleteActivityTest() {
        int activityId = 2;
        Response deleteResponse = given(spec).delete("/api/v1/Activities/" + activityId);
        deleteResponse.prettyPrint();
        assertEquals(deleteResponse.statusCode(), 200);

    }

    }







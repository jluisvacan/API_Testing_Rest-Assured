package practice;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Basics {

    public static void main(String[] args) throws IOException {

        //validate if Add Place API is working as expected
        //Given - All inputs details
        //When - Submit the API
        //Then - validate the response


        /*
            ADD PLACE - POST
        */
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String response = given().log().all()
                            .queryParam("key", "qaclick123")
                            .header("Content-Type", "application/json")
                            .body(payload.AddPlace())
                            .when().post("maps/api/place/add/json")
                            .then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)")
                            .extract().response().asString();

        System.out.println(response);



         /*
            ADD PLACE - POST
            Using content file
            Content file convert to Byte with Files.readAllBytes
            Byte data convert to String decoding with new String
        */
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String response1 = given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(new String (Files.readAllBytes(Paths.get("src/main/resources/addPlace.json"))))
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();

        System.out.println("---> Read file response: "+response1);

        //String parsing json
//        JsonPath js = new JsonPath(response);
        JsonPath js = ReUsableMethods.rawToJson(response);

        String placeId = js.getString("place_id");
        System.out.println(placeId);




        /*
            UPDATE PLACE - PUT
        */

        String newAddress = "031, side layout, shoen 170";
        given().queryParam("key", "qaclick123").queryParam("place_id",placeId)
                .header("Content-Type", "application/json").body("{\n" +
                        "    \"place_id\":\""+placeId+"\",\n" +
                        "    \"address\": \""+newAddress+"\",\n" +
                        "    \"key\": \"qaclick123\"\n" +
                        "}")
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));





        /*
           GET PLACE - GET
        */
        String getResponse = given().queryParam("place_id", placeId).queryParam("key", "qaclick123")
                                .when().get("maps/api/place/get/json")
                                .then().assertThat().statusCode(200).extract().response().asString();

//        JsonPath js1 = new JsonPath(getResponse);
        JsonPath js1 = ReUsableMethods.rawToJson(getResponse);
        String actualAddress = js1.getString("address");

        System.out.println(actualAddress);

        //Assert with TestNG
        Assert.assertEquals(actualAddress, newAddress);




    }
}

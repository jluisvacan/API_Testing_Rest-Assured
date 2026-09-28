package demo.GoogleMaps;

import demo.pojo.GoogleMaps.AddPlace;
import demo.pojo.GoogleMaps.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilder {
    public static void main(String[] args) {

        //Create request body with POJO class
        AddPlace p =new AddPlace();
        p.setAccuracy(50);
        p.setAddress("30, side layout, cohen 12");
        p.setLanguage("Spanish");
        p.setName("Luis");
        p.setPhone_number("+1 111 111 1111");
        p.setWebsite("http://google.com");
        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        p.setTypes(myList);
        Location l = new Location();
        l.setLat(101.0098);
        l.setLng(203.1212);
        p.setLocation(l);


        RestAssured.baseURI = "";

        //Spec Builder
        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key","qaclick123")
                .setContentType(ContentType.JSON)
                .build();


        ResponseSpecification resSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build()
                ;

        RequestSpecification res = given()
                .spec(req)
                .body(p);

        Response response = res
                                .when().post("/maps/api/place/add/json")
                                .then().spec(resSpec)
                                .extract().response();

        String resposeString = response.asString();
        System.out.println("-> Response: "+resposeString);


    }
}

package demo.GoogleMaps;

import demo.pojo.GoogleMaps.AddPlace;
import demo.pojo.GoogleMaps.Location;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.*;

public class Serialize {
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


        RestAssured.baseURI = "https://rahulshettyacademy.com";

        Response response = given()
                .queryParam("key","qaclick123")
                .body(p)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .extract().response();

        String resposeString = response.asString();
        System.out.println("-> Response: "+resposeString);


    }
}

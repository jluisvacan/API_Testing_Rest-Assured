package demo.ExcelDriven;

import files.ReUsableMethods;
import files.dataDriven;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import static io.restassured.RestAssured.given;

public class ExcelDriven {

    public static void main(String[] args) throws IOException {

        /*
            ADD PLACE - POST
        */
        //Get data request from Excel file
        dataDriven d = new dataDriven();
        ArrayList<String> data = d.getData("RestAddbook", "restassured");

        //Hashmap to Json
        HashMap<String,Object> map = new HashMap<>();
        map.put("name", data.get(1));
        map.put("isbn", data.get(2));
        map.put("aisle", data.get(3));
        map.put("author", data.get(4));
        /*
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("lat", "12.0098");
        map2.put("lng", "-103.0032");
        map.put("location", map2);
        */

        RestAssured.baseURI = "http://216.10.245.166/";

        String response = given().log().all()
                            .header("Content-Type", "application/json")
                            .body(map)
                            .when().post("Library/Addbook.php")
                            .then().assertThat().statusCode(200)
                            .extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);

        String id = js.get("ID");
        System.out.println("-> ID: "+id);




    }
}

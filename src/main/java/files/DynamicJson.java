package files;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle){

        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type","application/json").body(payload.AddBook(isbn,aisle))
                            .when().post("/Library/Addbook.php")
                            .then().log().all().assertThat().statusCode(200)
                            .extract().response().body().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);

        String identifier = js.getString("ID");
        System.out.println("-> ID Book: " + identifier);
    }


    @DataProvider(name = "BooksData")
    public Object[][] getData(){
        return new Object[][]{
                    {"aqqqw","5443"},
                    {"qwwaa","0954"},
                    {"pprt","61213"}
        };
    };
}

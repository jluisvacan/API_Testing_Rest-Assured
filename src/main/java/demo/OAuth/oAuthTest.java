package demo.OAuth;

import demo.pojo.OAuth.Api;
import demo.pojo.OAuth.GetCourse;
import demo.pojo.OAuth.WebAutomation;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class oAuthTest {

    public static void main(String[] args) {

        String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};

        String response = given()
                .formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println("-> Response: " + response);
        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");


        //Using POJO to serialization and deserialization
        GetCourse gc = given()
                .queryParam("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .as(GetCourse.class);


        System.out.println("-> Instructor: "+gc.getInstructor());
        System.out.println("-> Expertise: "+gc.getExpertise());
        System.out.println("-> Course Title: "+gc.getCourses().getApi().get(0).getCourseTitle());

        List<Api> apiList = gc.getCourses().getApi();
        for(int i = 0; i < apiList.size(); i++){
            if (apiList.get(i).getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java")){
                System.out.println("-> Course Price: "+apiList.get(i).getPrice());
            }
        }

        //Get the course names of WebAutomation
        ArrayList<String> a = new ArrayList<>();

        List<WebAutomation> wa = gc.getCourses().getWebAutomation();
        for(int i =-0; i< wa.size(); i++){
            a.add(wa.get(i).getCourseTitle());
        }

        List<String> expectedList = Arrays.asList(courseTitles);
        Assert.assertTrue(a.equals(expectedList));



    }
}

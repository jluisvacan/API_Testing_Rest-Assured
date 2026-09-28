package demo.Jira;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.*;

public class BugTestJira {

    public static void main(String[] args) {

        RestAssured.baseURI="https://lv-academy-team.atlassian.net/";

        String createIssueResponse = given().log().all()
                .header("Content-Type","application/json")
                .header("Authorization", "Basic amx1aXMudmFjYW42NEBnbWFpbC5jb206QVRBVFQzeEZmR0YwaEVpNUtwSThzU3hBSXl2TzR0d1hGZ1A0c28xZ19ZVmhYcHBnMHlVUi1jaFU0MHRfRGRoUFdranNKSldBdmZIVmtmbmxmNGFoNGE1Q1ZZaHZrX29pbzgxNVNOU1hJbHJoWGhnQmtSWmVWSTk0djR5a2pLNTF4MWZaT05vQmc1YTBjSVlocHJhcjJoWHlLOVNLS3B3bzJWV1dPVWozNTNlaU5jRkdXbGZwS0lZPUQwODlCQkVF")
                .body("\n" +
                        "{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"SCRUM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Checkbox are not working\",\n" +
                        "       \"description\": {\n" +
                        "            \"content\": [\n" +
                        "                {\n" +
                        "                \"content\": [\n" +
                        "                    {\n" +
                        "                    \"text\": \"The checkbox in Home page are not working.\",\n" +
                        "                    \"type\": \"text\"\n" +
                        "                    }\n" +
                        "                ],\n" +
                        "                \"type\": \"paragraph\"\n" +
                        "                }\n" +
                        "            ],\n" +
                        "            \"type\": \"doc\",\n" +
                        "            \"version\": 1\n" +
                        "        },\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .when()
                .post("rest/api/3/issue")
                .then().log().all()
                .assertThat().statusCode(201)
                .extract().response().asString();

        JsonPath js = new JsonPath(createIssueResponse);
        String issueId = js.get("id");
        System.out.println("-> Issue ID: "+issueId);


        // Add attachment
        String addAttachmentResponse =  given().log().all()
                                            .pathParam("key",issueId)
                                            .header("X-Atlassian-Token", "no-check")
                                            .header("Authorization", "Basic amx1aXMudmFjYW42NEBnbWFpbC5jb206QVRBVFQzeEZmR0YwaEVpNUtwSThzU3hBSXl2TzR0d1hGZ1A0c28xZ19ZVmhYcHBnMHlVUi1jaFU0MHRfRGRoUFdranNKSldBdmZIVmtmbmxmNGFoNGE1Q1ZZaHZrX29pbzgxNVNOU1hJbHJoWGhnQmtSWmVWSTk0djR5a2pLNTF4MWZaT05vQmc1YTBjSVlocHJhcjJoWHlLOVNLS3B3bzJWV1dPVWozNTNlaU5jRkdXbGZwS0lZPUQwODlCQkVF")
                                            .multiPart("file",new File("src/main/resources/DropdownErrorJira.png"))
                                            .when()
                                            .post("rest/api/3/issue/{key}/attachments")
                                            .then().log().all()
                                            .assertThat().statusCode(200)
                                            .extract().response().asString();

        JsonPath js1 = new JsonPath(addAttachmentResponse);
        String idAttached = js1.getString("id");
        System.out.println("-> Id attached: "+idAttached);
    }
}

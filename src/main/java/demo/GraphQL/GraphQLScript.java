package demo.GraphQL;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class GraphQLScript {

    public static void main(String[] args) {

        //Query
        int characterId = 6229;
        String response = given().log().all().header("Content-Type", "application/json")
                .body("{\"query\":\"query($characterId : Int!, $episodeId : Int!)\\n{\\n  character(characterId: $characterId) \\n  {\\n    name\\n    gender\\n    status\\n    type\\n    id\\n  }\\n  location(locationId: 7113) \\n  {\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId) \\n  {\\n    id\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name:\\\"ankur\\\"}) \\n  {\\n    info {count}\\n    result {name, type}\\n  }\\n  episodes(filters: {name: \\\"Amazon\\\"})\\n  {\\n   result{\\n    id\\n    name\\n    air_date\\n    episode\\n  } \\n}\\n  \\n  \\n\\n}\\n\",\"variables\":{\"characterId\":"+characterId+",\"episodeId\":5111}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println("Query Response: -> "+response);

        JsonPath js = new JsonPath(response);
        String characterName = js.getString("data.character.name");
        Assert.assertEquals(characterName, "Robin");


        //Mutations
        String newCharacterName = "Baskin Robin";
        String mutationResponse = given().log().all().header("Content-Type", "application/json")
                .body("{\"query\":\"mutation($locationName: String!, $episodeName: String!, $characterName: String!)\\n{\\n  createLocation(location: {name: $locationName type: \\\"Southzone\\\", dimension: \\\"234\\\"}) \\n  {\\n   id\\n  }\\n  createCharacter(character: {name: $characterName, type: \\\"test\\\", status: \\\"dead\\\", species:\\\"fantasy\\\", gender: \\\"male\\\", image: \\\"jpg\\\", originId:100, locationId:150})\\n  {\\n    id\\n  }\\n  createEpisode(episode: {name: $episodeName, air_date: \\\"August 2021\\\", episode: \\\"Netflix\\\"})\\n  {\\n    id\\n  }\\n  deleteLocations(locationIds: [7112,7108])\\n  {\\n    locationsDeleted\\n  }\\n}\",\"variables\":{\"characterName\":\"Newzealand\",\"episodeName\":\""+newCharacterName+"\",\"locationName\":\"Manifest\"}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println("Mutation Response: -> "+mutationResponse);



    }
}

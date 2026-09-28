package files;

public class payload {

    public static String AddPlace(){

        return "{\n" +
                "    \"location\": {\n" +
                "        \"lat\": -30.001132,\n" +
                "        \"lng\": 31.112223\n" +
                "    },\n" +
                "    \"accuracy\": 51,\n" +
                "    \"name\": \"Luis V\",\n" +
                "    \"phone_number\": \"(+11) 111 222 3333\",\n" +
                "    \"address\": \"30, side layout, choen 10\",\n" +
                "    \"types\": [\n" +
                "        \"shoe park\",\n" +
                "        \"shop\"\n" +
                "    ],\n" +
                "    \"website\": \"http://rahulshettyacademy.com\",\n" +
                "    \"language\": \"Spanish-MX\"\n" +
                "}";
    }

    public static String CoursePrice(){

        return "{\n" +
                "    \"dashboard\": {\n" +
                "        \"purchaseAmount\": 3660,\n" +
                "        \"website\": \"https://rahulshettyacademy.com\"\n" +
                "    },\n" +
                "    \"courses\": [\n" +
                "        {\n" +
                "            \"title\": \"FastAPI Python\",\n" +
                "            \"price\": 40,\n" +
                "            \"copies\": 5\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"Playwright Java\",\n" +
                "            \"price\": 100,\n" +
                "            \"copies\": 25\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"Karate Java\",\n" +
                "            \"price\": 45,\n" +
                "            \"copies\": 10\n" +
                "        },\n" +
                "        {\n" +
                "            \"title\": \"Spring Boot Java\",\n" +
                "            \"price\": 30,\n" +
                "            \"copies\": 17\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }


    public static String AddBook(String isbn, String aisle){

        return "{\n" +
                "    \"name\":\"Learn Rest Assured Automation with Java\",\n" +
                "    \"isbn\": \""+isbn+"\",\n" +
                "    \"aisle\": \""+aisle+"\",\n" +
                "    \"author\": \"Johon Val\"\n" +
                "}";
    }
}

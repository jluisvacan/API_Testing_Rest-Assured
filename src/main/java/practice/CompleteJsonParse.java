package practice;

import files.payload;
import io.restassured.path.json.JsonPath;

public class CompleteJsonParse {

    public static void main(String[] args) {

        JsonPath js = new JsonPath(payload.CoursePrice());


        // Print No. of courses returned by API
        int countCourses = js.getInt("courses.size()");
        System.out.println("-> No. Courses: " + countCourses);


        // Print Purchase amount
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("-> Total amount: " + totalAmount);


        // Print the title of the first course
        String titleFirstCourse = js.getString("courses[0].title");
        System.out.println("-> Title 1st course: "+ titleFirstCourse);


        // Print all course titles and their respective prices
        for(int i =0; i <countCourses; i++){
            String courseTitle = js.getString("courses["+i+"].title");
            String coursePrice = js.get("courses["+i+"].price").toString();
            System.out.println("-> Course: " + courseTitle + ", price: " + coursePrice);
        }


        // Print No. of copies sold by Playwright course
        System.out.println("No of copies by Playwright course");

        for(int i=0; i < countCourses; i++){
            String titleCourse = js.getString("courses["+i+"].title");

            if(titleCourse.equalsIgnoreCase("Playwright Java")){
                int copies = js.get("courses["+i+"].copies");
                System.out.println("-> No. copies: " + copies);
                break;
            }

        }


        //Verify if sum of all course prices matches with purchase amount


    }
}

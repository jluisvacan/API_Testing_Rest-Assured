package demo.ECommerce;

import static io.restassured.RestAssured.*;

import demo.pojo.ECommerce.LoginRequest;
import demo.pojo.ECommerce.LoginResponse;
import demo.pojo.ECommerce.OrderDetail;
import demo.pojo.ECommerce.Orders;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ECommerceAPI {

    public static void main(String[] args) throws IOException {

        // Load credentials
        Properties prop = new Properties();
        FileInputStream fis =new FileInputStream(".env");
        prop.load(fis);


        // Login
        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON)
                .build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail(prop.getProperty("user"));
        loginRequest.setUserPassword(prop.getProperty("pass"));

        RequestSpecification reqLogin = given().log().all()
                .spec(req)
                .body(loginRequest);

        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login")
                .then().extract().response().as(LoginResponse.class);

        String token = loginResponse.getToken();
        System.out.println("-> Token: "+loginResponse.getToken());
        System.out.println("-> User Id: "+loginResponse.getUserId());
        String userId = loginResponse.getUserId();


        // Add Product
        RequestSpecification addProductReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token)
                .build();

        RequestSpecification reqAddProduct = given().spec(addProductReq)
                .param("productName", "Laptop")
                .param("productAddedBy", userId)
                .param("productCategory", "Tech")
                .param("productSubCategory", "Computer")
                .param("productPrice", 2020)
                .param("productDescription", "Lenovo S91")
                .param("productFor", "male")
                .multiPart("productImage",new File("src/main/resources/laptop.jpg"));

        String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
                .then().log().all().extract().response().asString();

        JsonPath js = new JsonPath(addProductResponse);
        String productId = js.getString("productId");
        System.out.println("-> Status: " + js.getString("message"));

        //Create Order
        RequestSpecification createOrderRequest = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token)
                .setContentType(ContentType.JSON)
                .build();


        // Create Order request
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("Mexico");
        orderDetail.setProductOrderedId(productId);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);

        Orders orders = new Orders();
        orders.setOrders(orderDetailList);

        RequestSpecification createOrderReq = given().spec(createOrderRequest)
                .body(orders);

        String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order")
                .then().log().all()
                .extract().response().asString();



        // Delete Product

        RequestSpecification deleteProductReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token)
                .setContentType(ContentType.JSON)
                .build();

        RequestSpecification deleteProdReq = given().log().all()
                .spec(deleteProductReq)
                .pathParam("productId", productId);

        String deleteProductResponse = deleteProdReq
                .when().delete("https://rahulshettyacademy.com/api/ecom/product/delete-product/{productId}")
                .then().log().all()
                .extract().response().asString();

        JsonPath jsDelete = new JsonPath(deleteProductResponse);
        String messageDelete = jsDelete.getString("message");

        System.out.println("-> Delete status: "+messageDelete);


    }
}

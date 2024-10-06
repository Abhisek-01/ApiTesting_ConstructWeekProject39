package CommentApi;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.Assert;

public class Comments {

	@Test
	public void allComment() {

		RestAssured.baseURI = "https://dummyjson.com";
		given().contentType("application/json").get("/comments").then().statusCode(200).log().all();

	}

	@Test
	public void singleComment() {

		RestAssured.baseURI = "https://dummyjson.com";
		given().contentType("application/json").get("/comments/1").then().statusCode(200).log().all();

	}

	@Test
	public void skipComment() {
		RestAssured.baseURI = "https://dummyjson.com";

		Response response = given().contentType("application/json").queryParam("limit", 10).queryParam("skip", 10)
				.get("/comments").then().statusCode(200).extract().response();
		System.out.print(response.asPrettyString());

	}

	@Test
	public void commentByPostid() {
		RestAssured.baseURI = "https://dummyjson.com";

		Response response = given().contentType("application/json").get("comments/post/6").then().extract().response();
		System.out.println(response.asPrettyString());
	}

	@Test
	public void AddNewComment() {

		RestAssured.baseURI = "https://dummyjson.com";
		JSONObject json = new JSONObject();
		json.put("body", "This makes all sense to me!");
		json.put("postId", 3);
		json.put("userId", 5);

		Response response = given().contentType("application/json").get("comments/add").then().extract().response();
		System.out.println(response.asPrettyString());

	}

	@Test
	public void UpdateComment() {
		RestAssured.baseURI = "https://dummyjson.com";

		JSONObject json = new JSONObject();
		json.put("body", "I think I should shift to the moon");

		Response response = given().contentType("application/json").get("/comments/1").then().extract().response();

		System.out.println(response.asPrettyString());

	}

	@Test
	public void Delete() {

		RestAssured.baseURI = "https://dummyjson.com";

		Response response = given().when().delete("/comments/1").then().statusCode(200).extract().response();

		System.out.println(response.asPrettyString());

		String deletedOn = response.jsonPath().getString("deletedOn");

		Assert.assertNotNull(deletedOn, "The 'deletedOn' timestamp should be present");

	}

}

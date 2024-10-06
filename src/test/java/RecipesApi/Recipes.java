package RecipesApi;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.Assert;

public class Recipes {

	@Test
	public void GetAllRecipes() {

		given().get("https://dummyjson.com/recipes").then().statusCode(200).log().all();

	}

	@Test
	public void SingleRecipes() {

		given().get("https://dummyjson.com/recipes/1").then().statusCode(200).log().all();

	}

	@Test
	public void SearchRecipes() {

		given().contentType("application/json").get("https://dummyjson.com/recipes/search?q=Margherita").then()
				.statusCode(200).log().all();

	}

	@Test
	public void LimitSkip() {
		RestAssured.baseURI = "https://dummyjson.com";

		given().contentType("application/json").queryParam("limit", 10).queryParam("skip", 10)
				.queryParam("select", "name,image").when().get("/recipes").then().statusCode(200).log().all();

	}

	@Test
	public void ShortRecipes() {

		RestAssured.baseURI = "https://dummyjson.com";
		given().queryParam("sortBy", "name").queryParam("order", "asc").when().get("/recipes").then().statusCode(200)
				.log().all();

	}

	@Test
	public void Getall() {
		RestAssured.baseURI = "https://dummyjson.com";
		Response response = given().when().get("/recipes/tags").then().statusCode(200) // Validate the status code
				.extract().response();

		// Log response for debugging purposes (optional)
		System.out.println(response.asPrettyString());
	}

	@Test
	public void RecipesByTag() {

		RestAssured.baseURI = "https://dummyjson.com";

		// Fetch the recipes by tag (e.g., "Pakistani")
		Response response = given().when().get("/recipes/tag/Pakistani").then().statusCode(200).extract().response();

		// Log response for debugging purposes (optional)
		System.out.println(response.asPrettyString());

	}

	@Test
	public void RecipesByMeal() {
		RestAssured.baseURI = "https://dummyjson.com";

		// Fetch the recipes by tag (e.g., "Pakistani")
		Response response = given().when().get("/recipes/meal-type/snack").then().statusCode(200).extract().response();

		// Log response for debugging purposes (optional)
		System.out.println(response.asPrettyString());
		String firstRecipeName = response.jsonPath().getString("recipes[0].name");
		Assert.assertNotNull(firstRecipeName, "Chocolate Chip Cookies");
	}

}

package br.com.intelipost.api;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

import br.com.intelipost.api.steps.Hooks;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

/**
 * @author duekita
 *
 */
public class RESTAssured {
	private String bodyData;
	private String HTTPMethod;
	private String URL;
	private Map<String, String> headers;
	private Response response;
	private ValidatableResponse responseBody;

	public RESTAssured() {
		headers = new HashMap<String, String>();
	}

	public void addHeader(String name, String value) {
		headers.put(name, value);
	}

	public void send() {
		switch (HTTPMethod) {
		case "GET":
			response = given().headers(headers).and().get(URL);
			break;
		case "POST":
			response = given().headers(headers).and().body(bodyData).and().post(URL);
			break;
		}
		// TODO implementar outros métodos HTTP

		Hooks.write("Response body:");
		Hooks.write(response.asString());
		Hooks.write("Response headers:");
		Hooks.write(response.headers().toString());
	}

	public String getBodyData() {
		return bodyData;
	}

	public RESTAssured setBodyData(String bodyData) {
		this.bodyData = bodyData;
		return this;
	}

	public String getHTTPMethod() {
		return HTTPMethod;
	}

	public RESTAssured setHTTPMethod(String hTTPMethod) {
		HTTPMethod = hTTPMethod.toUpperCase();
		return this;
	}

	public String getURL() {
		return URL;
	}

	public RESTAssured setURL(String URL) {
		this.URL = URL;
		return this;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public ValidatableResponse getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(ValidatableResponse responseBody) {
		this.responseBody = responseBody;
	}

	public RESTAssured assertStatusCode(int statusCode) {
		responseBody = response.then().statusCode(statusCode);
		return this;
	}

	/**
	 * Extrai uma valor do JSON de resposta e verifica se é igual ao esperado
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public RESTAssured validateThatReponseObjectEqualTo(String name, String value) {
		responseBody.assertThat().body(name, equalTo(value));
		return this;
	}

	/**
	 * Extrai uma lista de valores do JSON de resposta e verifica se o valor não é
	 * igual ao esperado
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public RESTAssured validateThatResponseAllObjectsNotEqualsTo(String name, String value) {
		ArrayList<Object> allObjects = response.path(name);
		for (int i = 0; i < allObjects.size(); i++) {
			if (StringUtils.isNumeric(allObjects.get(i).toString())) {
				Assert.assertFalse("Object '" + name + "' should not be equals to '" + value + "', but was '"
						+ allObjects.get(i) + "'.", allObjects.get(i).toString().equals(value));
			} else {
				Assert.assertFalse("Object '" + name + "' should not be equals to '" + value + "', but was '"
						+ allObjects.get(i) + "'.", allObjects.get(i).equals(value));
			}
		}
		return this;
	}

	/**
	 * Extrai uma lista de valores do JSON de resposta e verifica se o valor é igual
	 * ao esperado
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public RESTAssured validateThatResponseAllObjectsEqualsTo(String name, String value) {
		ArrayList<Object> allObjects = response.path(name);
		for (int i = 0; i < allObjects.size(); i++) {
			if (StringUtils.isNumeric(allObjects.get(i).toString())) {
				Assert.assertTrue("Object '" + name + "' should be equals to '" + value + "', but was '"
						+ allObjects.get(i) + "'.", allObjects.get(i).toString().equals(value));
			} else {
				Assert.assertTrue("Object '" + name + "' should be equals to '" + value + "', but was '"
						+ allObjects.get(i) + "'.", allObjects.get(i).equals(value));
			}
		}
		return this;
	}

	public RESTAssured validateJSONSchema(String JSON) {
		responseBody.assertThat().body(matchesJsonSchema(JSON));
		return this;
	}
}

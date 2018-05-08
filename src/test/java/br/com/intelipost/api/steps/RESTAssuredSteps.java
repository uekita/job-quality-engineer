package br.com.intelipost.api.steps;

import java.util.List;

import br.com.intelipost.api.RESTAssured;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;

public class RESTAssuredSteps {

	private static RESTAssured request;

	public RESTAssuredSteps() {
		request = new RESTAssured();
	}

	@Given("Eu adiciono as headers da request$")
	public void eu_adiciono_as_headers_da_request(DataTable data) {
		List<List<String>> headers = data.raw();
		for (int i = 1; i < headers.size(); i++) {
			request.addHeader(headers.get(i).get(0), headers.get(i).get(1));
		}
	}

	@Given("Eu adiciono a request body$")
	public void eu_adiciono_a_request_body(String body) {
		request.setBodyData(body);
	}

	@Given("Eu envio a request do tipo \"([^\"]*)\" com a URL \"([^\"]*)\"$")
	public void eu_envio_a_request_do_tipo_com_a_url(String method, String URL) {
		request.setHTTPMethod(method).setURL(URL).send();
	}

	@Given("O codigo de resposta deve ser \"([^\"]*)\"$")
	public void eu_codigo_de_resposta_deve_ser(int responseCode) {
		request.assertStatusCode(responseCode);
	}

	@Given("Eu valido as informacoes na response body$")
	public void eu_valido_as_informacoes_na_response_body(DataTable data) {
		List<List<String>> objetos = data.raw();
		for (int i = 1; i < objetos.size(); i++) {
			request.validateThatReponseObjectEqualTo(objetos.get(i).get(0), objetos.get(i).get(1));
		}
	}

	@Given("Eu valido que na response body, o objeto \"([^\"]*)\" não deve ter o valor \"([^\"]*)\"$")
	public void eu_valido_que_na_response_body_o_objeto_nao_deve_ter_o_valor(String name, String value) {
		request.validateThatResponseAllObjectsNotEqualsTo(name, value);
	}

	@Given("Eu valido que na response body, o objeto \"([^\"]*)\" deve ter o valor \"([^\"]*)\"$")
	public void eu_valido_que_na_response_body_o_objeto_deve_ter_o_valor(String name, String value) {
		request.validateThatResponseAllObjectsEqualsTo(name, value);
	}
	
	@Given("Eu valido o JSON schema da resposta$")
	public void eu_valido_JSON_schema_da_resposta(String JSON) {
		request.validateJSONSchema(JSON);
	}
	
	@Given("Eu adiciono a descrição da request \"([^\"]*)\"$")
	public void eu_adiciono_a_descricao_da_request(String descricao) {
		Hooks.write(descricao);
	}
}

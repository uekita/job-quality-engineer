
# Intelipost: Teste prático para analista de qualidade e testes

Teste prático para o cargo de analista de qualidade e testes criado pela Intelispost.
Principais dificuldades:
* Validação do respostas. Foi necessário um pouco de pesquisa para, por exemplo, extrair uma lista de valores. Não existem muitos exemplos de uso para extrair valores de JSON usando o GPATH. Na verdade, existem diversas formas de validar e extrair valores de um JSON usando outras bibliotecas, o problema foi encontrar uma que apresentasse a forma mais simples para ser parametrizável numa step definition.
* Validação de JSON schema. (método criado, mas não implantado, pois nenhum campo no JSON de retorno é obrigatório para a api )
* Criação de step definitions reaproveitáveis. Criar métodos genéricos implica um pouco mais de trabalho do que implementar uma step definition mais específica.

## Análise do teste

Para criar o framework de teste automatizado em java escolhi criar um projeto do tipo maven por:
* Ser uma ferramenta padrão para aplicações java;
* Facilidade de se trabalhar com depedências, basta adicionar a dependencia ao *pom.xml*;
* Ao criar um projeto do tipo maven, já é montado automaticamente uma estrutura de pacotes (incluindo uma de testes).

### Testando RESTful APIs

Para testar as APIs utilizei a biblioteca [REST-assured](http://rest-assured.io/). A sua implementação é simples, possui verbos de BDD (when, given, etc), e possui métodos para validar respostas em JSON.

### Estrutura

O projeto foi estruturado da seguinte forma:
```
|---src
|	\---test
|	|	|---\java
|	|	|	\---\br
|	|	|		\---com\
|	|	|			\---intelipost
|	|	|				|---api
|	|	|				|	|---steps
|	|	|				|	|	|---Hooks.java
|	|	|				|	|	\---RESTAssuredSteps.java
|	|	|				|	\---RESTAssured.java	
|	|	|				\---support
|	|	|					|---ConfigurationProperties.java
|	|	|					|---CucumberRerport.java
|	|	|					\---CucumberRunner.java
|	\---\resources
|		\---\features
|			\---\api
|		 		\---CriarCotacaoPorProduto.feature
|---jmeter
|	\---SC01
|		|---\dados
|		|	\---SC01.csv
|		\---CriarCotacaoPorProduto.jmx
|---Automation.rar	
|---config.properties
\---pom.xml
```

* Um pacote com os fontes do framework
	* classes de suporte (Runner, Report, Configuration)
	*  implemetação de steps e a Hooks
* Uma pasta chamada 'resources' para armazenamento das features
* Um pasta para armazenar os scripts do Jmeter

Foi criado um pacote independete para APIs, pois caso seja necessário criar um framework para automação de UI, basta adicionar um outro pacote 'ui', aproveitando a mesma estrutura. Caso exista um teste integrado a implementação já estará organizado num mesmo projeto.

## Relatório de execução

Foi utilizado o [Cucumber Reporting](https://github.com/damianszczepanik/cucumber-reporting), pois ele é organiza os cenários em features, tags, steps e separa os cenários falhos, além de ser visualmente mais agradável e intuitivo que o report html do plugin do cucumber-jvm. Ele possui também um plugin para no Jenkins.

## Como executar
### Configurando a execução
O teste é parametrizado através de um arquivo de configurações -*Configuration.properties*- que é apresentado da seguinte forma:
```
feature=src/test/resources/features/api/criarCotacaoPorProduto.feature
steps=br.com.intelipost.api.steps
tag=@SC05
projectName=Teste API Intelipost
openReportAfterTest=true
reportPath=C:/Reports
```
* **feature** - obrigatório - Caminho da feature relativo a pasta do framework
* **steps** - obrigatório - Caminho do pacote das classes com as implemetações das steps da feature
* **tag** - opcional - Anotação a ser procurada na feature. Se utilizar mais de uma tag, adicionar vírgula. Ex.: @positivo,@negativo
* **projectName** - obrigatório - Nome do projeto que irá ser apresentado no relatório de execução
* **openReportAfterTest** - opcional - Abre o relatório de execução automaticamente após a execução. (Só funciona em Windows)
*  **reportPath** - opcional - Define o caminho do relatório de execução. Se deixado vazio será criado uma pasta Reports na raiz da pasta do projeto.

Por que utilizar um arquivo de configuração?
Para ser simples a execução e configurável para qualquer pessoa com um mínimo de instrução de utilização. Os parâmetros precisam ficar em código. E o arquivo pode ser lido, por exemplo, pelo Jenkins num job do tipo Maven.

### Executando
Formas:
1. Através de uma IDE - Importar o projeto, configurar os parâmetros de execução do *Configuration.properties*, que está na raiz do projeto e executar o método main da classe CucumberRunner;
2. Extrair o arquivo *Automation.rar* para qualquer diretório, configurar os parâmetros de execução no *Configuration.properties* e executar o arquivo executar_windows.bat ou executar_linux.sh de acordo com o sistema operacional.

Após a execução o relatório será gerado na raiz do projeto dentro da pasta *Reports* com a data e hora da execução. Obs: Ao abir a pasta do relatório, selecionar o arquivo 'overview-features.html', pois ele apresenta uma visão geral da execução.

## Criação de cenários
Para criar os cenários de teste utilizei os principais os seguintes recursos do cucumber:
1. **Background** - Permite a execução de steps antes de cada cenário como uma pré-condição de cada tete. Utilizei para incluir as headers.
2. **Scenario Outline** - Para executar um mesmo cenário utilizando parâmetros diferentes, evitando duplicação de cenários. Seu uso foi essêncial para todos os cenários.

## Classe executora
Crie uma classe executora utilizando o CLI Runner. Dessa forma é possível realizar ações antes (como ler o arquivo de configurações) e depois do teste (como gerar o relatório, rodar scripts, etc). A execução via cucumber-Junit não possui uma implementação funcional de @beforeAll e @afterAll.
## Links que me ajudaram :thumbsup: 

* Some examples of REST-assured - https://blog.jayway.com/2013/04/12/whats-new-in-rest-assured-1-8/
* A brief explanation how Gpath works - http://groovy-lang.org/processing-xml.html#_gpath
* A brief intro to Json https://dzone.com/articles/understanding-basic-json
* Usage examples of groovy Json gpath - http://james-willett.com/2017/05/rest-assured-gpath-json/
* Examples of rest-Assured examples - https://www.joecolantonio.com/2014/04/24/rest-assured-how-to-post-a-json-request/
* A great example of bdd for web api testing with REST-Assured - http://angiejones.tech/rest-assured-with-cucumber-using-bdd-for-web-services-automation/
* A overview of rest-Assured - https://gorillalogic.com/blog/automation-testing-part-2-api-testing-rest-assured/
* Rest-assured guide - https://github.com/rest-assured/rest-assured/wiki/Usage#specifying-request-data
* Rest-assured usage - https://www.swtestacademy.com/api-testing-with-rest-assured/

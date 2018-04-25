# Intelipost: Teste prático para analista de qualidade e testes

Este é o teste usado por nós aqui da Intelipost para avaliar tecnicamente os candidatos a nossas vagas de analista de testes. Se você estiver participando de um processo seletivo para nossa equipe, certamente em algum momento receberá este link, mas caso você tenha chego aqui "por acaso", sinta-se convidado a desenvolver nosso teste e enviar uma mensagem para nós nos e-mails `stefan.rehm@intelipost.com.br` e `gustavo.hideyuki@intelipost.com.br`.

Aqui na Intelipost nós aplicamos este mesmo teste para as vagas em todos os níveis, ou seja, um candidato a uma vaga de analista de testes junior fará o mesmo teste de um outro candidato a uma vaga sênior, mudando obviamente o nosso critério de avaliação do resultado do teste.

Nós fazemos isso esperando que as pessoas mais iniciantes entendam qual o modelo de profissional que temos por aqui e que buscamos para o nosso time. Portanto, se você estiver se candidatando a uma vaga mais iniciante, não se assuste, e faça o melhor que você puder!

## Instruções

Você deverá criar um fork deste projeto, e desenvolver em cima do seu fork. Use o `README` principal do seu repositório para nos contar como foi resolver seu teste, as decisões tomadas, como você organizou e separou seus testes, e principalmente as instruções de como rodar seu projeto, afinal a primeira pessoa que irá rodar seu projeto será um programador de nossa equipe, e se você conseguir explicar para ele como fazer isso, você já começou bem!

Lembre-se que este é um teste técnico e não um concurso público, portanto, não existe apenas uma resposta correta. Mostre que você é bom e nos impressione, mas não esqueça do objetivo do projeto. Nós não definimos um tempo limite para resolução deste teste, o que vale para nós e o resultado final e a evolução da criação do projeto até se atingir este resultado.

## O desafio

Você será o responsável pelo desenvolvimento dos testes automatizados da nossa API de cotação a fim de cobrir alguns requisitos da `Loja Intelipost` (loja fictícia), temos uma documentação publica (https://docs.intelipost.com.br/v1/cotacao) para que voce consiga entender como vai realizar as requisições. Use esta documentação para ler sobre os parâmetros de entrada e saída, afinal serão importantíssimos para a validação dos seus testes. Nessa fase voce automatizará as cotações de frete por produto descritas no link https://docs.intelipost.com.br/v1/cotacao/criar-cotacao-por-produto .

Para as requisições que você for fazer na API, utilize a api key **4aa90b1087807b5fb8e52b01584f84e416ddb8ab8e5b800ae5d0f075a2d1e379**

 ## Para conhecimento

A `Loja Intelipost` possui diversos canais de vendas, recebendo milhares de cotações de frete em todo o País. Para garantir que o cliente receba sua encomenda rapidamente, a Loja Intelipost possui quatro endereços de origem:

| Origem | Estado | CEP |
| ------------- | ------------- | ------------- |
| Origem 1  | Espirito Santo  | 29010-120 |
| Origem 2  | Tocantins  | 77001-054 |
| Origem 3  | Mato Grosso  | 78005-170 |
| Origem 4  | Rio Grande do Sul  | 94090-720 |

## Cobertura dos testes

* A Loja Intelipost deixou de atuar nos canais de vendas **CN1** e **CN2**, portanto se houver uma requisição oriunda dos canais de vendas (_sales_channel_) **CN1** ou **CN2**, é esperado que não haja opções de entrega no resultado da cotação.

* A Loja Intelipost espera que não haja opções de entrega caso, na cotação de frete, o cep de origem seja de **Tocantins** e o cep de destino esteja localizado na **Região Sudeste** do País.

* O canal de vendas **CN123**, não deve disponibilizar a opção de entrega **Correios PAC** caso o cep de destino esteja entre as faixas de cep _22710-010_ e _22710-990_.

* Todas as cotações de frete, independente do canal de vendas, cujo o destino seja o estado do **Pará**, o prazo de entrega esperado é de 20 dias.

* Cotações cujo o SKU (identificador) do produto seja **SKU123** não deve disponibilizar a opção de entrega **Correios PAC**.

Com as informações acima, sua atividade será a criação de um conjunto de testes a fim de garantir que tais situações estão ocorrendo conforme o esperado.

### O que nós esperamos do seu teste:

* Possua um bom nível de cobertura
* Seja feito o uso de BDD
* Possua clareza na escrita dos testes
* Possua um report simples onde possamos entender as possíveis falhas ocorridas na execução dos testes
* Seja produzido em _Java_ (Java 8 de preferência)
* Possua informações para que possamos entender o que e como foi desenvolvido
* Possua informações de como executar os testes
* Escolha ao menos um dos seus testes e crie-o utilizando Jmeter

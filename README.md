# SogniSport Exchange Rate API

Esta API REST consulta a taxa de câmbio entre duas moedas usando um serviço externo e armazena os dados em um banco H2 embutido.


# Tecnologias

	- Java com Quarkus
	- Banco H2 embutido
	- Docker
	- Swagger

# Executando a aplicação

	 Verificar a exchange e cadastrar uma chave: 
	 https://app.exchangerate-api.com/dashboard
	 
	 Configurar o arquivo `application.properties` com sua chave de API na variavel exchange.api.url.
	
	 
	 Compilar e rodar com Maven:  
	   ./mvnw clean compile quarkus:dev
   
# Verificar se subiu
 	
	http://localhost:8090/q/dev-ui/welcome
     
# Docker

	compilar:
		docker build -t sognisport-challenge-api .
	executar:
		docker run --name sognisport-challenge-api -p 8090:8090 sognisport-challenge-api
	
# Docs

	http://localhost:8090/swagger-ui

# Database h2

	http://localhost:8090/h2-console
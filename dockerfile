# Usando uma imagem base OpenJDK 23
FROM openjdk:23-jdk-slim AS build

# Configura o diretório de trabalho dentro do container
WORKDIR /app

# Instala dependências necessárias para o Maven
RUN apt-get update && apt-get install -y curl tar maven

# Copia apenas o arquivo pom.xml primeiro para instalar as dependências
COPY pom.xml /app/

# Baixa as dependências do Maven
RUN mvn dependency:go-offline

# Agora copia o restante do código
COPY . /app

# Compila o projeto
RUN mvn clean package -DskipTests

# Segundo estágio, menor e apenas para execução
FROM openjdk:23-jdk-slim AS runtime

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado no estágio de build para o estágio de execução
COPY --from=build /app/target/sognisport-challenge-api.jar /app/sognisport-challenge-api.jar

# Expõe a porta 8090 para que a aplicação esteja acessível externamente
EXPOSE 8090

# Comando para iniciar a aplicação
#CMD ["java", "-jar", "/app/sognisport-challenge-api.jar", "-Dquarkus.http.port=8090"]

# Use uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo JAR da aplicação para o diretório de trabalho no contêiner
COPY target/api-livros-0.0.1-SNAPSHOT.jar /app/api-livros.jar

# Exponha a porta em que a aplicação vai rodar
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "api-livros.jar"]

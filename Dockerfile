FROM openjdk:17-jdk-slim-buster
WORKDIR /app

# Créez un répertoire pour le fichier JAR
RUN mkdir -p target

# Copiez tous les fichiers du répertoire target vers le répertoire de travail de l'image Docker
COPY target/ /app/

EXPOSE 8082

ENTRYPOINT java -jar app.jar
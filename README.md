# Java_auth_Spring_JWT_tocken

C'est une application complete pour l'authentification de l'utilisateur
avec Spring Boot et Spring Security .

Il génère un token pour chaque utilisateur avec le nom d'utilisateur ou l'email et le mot de passe
avec un temps de validité de 7 jours.

Le token est utilisé pour authentifier l'utilisateur avec Spring Security

Le JWT est utilisé pour authentifier l'utilisateur avec Spring Boot et Spring Security est genre automatiquement les tokens


## Pour utiliser cette application il faut :
### - clonner le code github
### - faire un mvn clean install
- configurer le fichier application.properties avec les informations de connexion a la base
notée :
- Créé votre base de donnee 
- spring.datasource.driver-class-name= DRIVER Utilisé pour la connexion
- spring.datasource.url=jdbc: L'url de la base
- spring.datasource.username= Nom d'utilisateur
- spring.datasource.password= Mot de passe
- spring.jpa.hibernate.ddl-auto=update   => pour (mettre a jour la base au changement)


- app.jwt.secret= Secret du token
- app.jet-expiration-milliseconds= Temps de validité du token
# - faire un mvn spring-boot:run



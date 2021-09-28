# tutos-enit-soa
Exemples rapides pour le module SOA
Prérequis:
- java 11 ou plus
- il faut avoir JAVA_HOME configurée
- maven récent avec MAVEN_HOME configurée
- installer docker (avec WSL sur windows)
- VS code avec les extensions java quarkus docker redhat
- démarrer dans le terminal (interne à vscode) en mode developpement avec la commande mvn clean compile quarkus:dev
- la configuration est dans le fichier application.properties
- par defaut l'application tourne sur localhost:8080 (l'interface qui s'affiche contient un lien vers dev-ui qui liste les extensions/modules installés
- dev-ui donne l'accès à swagger pour tester les APIs
- pour lancer l'infra sur docker click droit dans le fichier docker-compose et faire docker compose up

si besoin de voir la BD avec pgadmin faire:
- pg admin est configuré sur docker et se trouve sur le port 5050
- première utilisation de pgadmin il faut configurer un nouveau serveur avec les paramètres par defaut et dont l'adresse est db

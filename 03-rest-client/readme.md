c'est un exemple de deux microservices où l'un utilise un web service de l'autre.

Le microservice de la banque propose un web service pour convertir un montant en une devise (USD, TNS ou EUR) en une autre devise. Pour celà, il utilise le microservice de taux de change pour connaitre les taux actuels.

L'intégration se fait par la spécification microprofile rest client .

Le microservice de la banque fait le mapping d'une méthode d'une interface au web service distant. Le lien entre la méthode et le web service se font par annotation ou par configuration.

pour tester l'exemple, ouvrir le microservice de taux de change et le démarrer par la commande mvn quarkus:dev dans le terminal de vscode. le projet utilise le port 8081. cliquer sur le lien dev ui, puis sur [swagger-ui](http://localhost:8081/q/swagger-ui/) et tester les web services exposés.

Ensuite, ouvrir et exécuter le projet banque et aller vers  [swagger-ui](http://localhost:8080/q/swagger-ui/). Tester le web service exposé en passant un montant à convertir. Le résultat calculé et retourné vient de la multiplication du montant par la valeur du taux de change retournée par l'autre microservice.
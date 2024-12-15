
# Historydle



Historydle est un site de jeu historique où l'utilisateur peut tenter de trouver un personnage historique chaque jour selon
différents jeux :

- **Historydle :** Devinez un personnage à partir de ses attributs spécfiques.
- **Citation :** Trouvez qui a dit une citation célèbre.
- **Portrait :** Identifiez un personnage à partir de son portrait. 

Chaque jeu possède **chaque jour** un nouveau personnage à trouver. 

## Lancement

Afin de lancer le projet comme il se doit veuillez suivre les indications suivantes : 

- Exporter le projet github sur une IDE
- Une fois le projet lancé, déplacez vous dans le dossier démo depuis un terminal à l' aide de : 
```bash
cd demo
``` 
- Pour lancer le projet utilisez la commande : 
```bash
mvn spring-boot:run
```
- Une fois le projet correctement lancé veuillez vous rendre sur : **localhost:8080**. Si ce port est déjà utilisé vous pouvez le changer **ligne 18** du fichier : `demo/main/ressources/application.properties` 
```bash 
server.port=8080
``` 
- **Attention : Il est impératif d'être connecté à internet pour profiter du site à 100%. Sans cela, il pourrait notamment manquer une partie du CSS et du JS.**

## Auto-Evaluation
#### Qualité 
Ce projet a été réalisé à l'aide d'un framework CSS. Nous avons utilisé tailwind CSS pour ce faire. 
Bien que la prise en main fut un petit peu déconcertante au début,l'utilisation de ce framework nous a permis de gagner beaucoup de temps dans la
réalisation du css que l'on trouve efficace et agréable. De plus afin de rendre le travail de groupe plus pratique nous avons utilisé github dés le début du projet. Cela nous a permis de pouvoir nous organiser correctement avec de nombreux commit et merge. Nous avons fait le choix de travailler chacun sur une branche différente. Tout ceci dans le but que chacun implémente une fonctionnalité différente afin d'avancer le plus rapidement possible.

#### Technique

1 . Pour toutes nos fonctionnalités, nous avons utilisé le design pattern MVC tout au long de notre projet. 
Afin d'implementer celui-ci nous avons séparé chaque fonctionnalités sous trois composantes distinctes :

- **Le model** : Regroupe tout ce qui concerne la gestion des données et leur logique métier. Dans notre application web, ceci représente les entités et repositories qui collaborent pour gérer les interactions avec la base de données et les règles définies 
- **La vue** : Regroupe toutes les pages HTML. A noter que l'on a utilisé l'outils Thymeleaf.
- **Le Controller** : Gère les requêtes utilisateur et transfère les données entre le modèle et la vue. Nous avons un Controller pour toutes les vues et fonctionnalités existantes sur le site. 

2 . Le projet utilise les méthodes HTTP appropriées pour chaque type d'opération. On a notamment utilisé, la méthode GET lorsque l'on souhaite récupérer tous les personnages. La méthode POST a pu être utilisé pour récupérer la réponse de l'utilisateur, la comparer, enregistrer la réponse et rénvoyer vers la nouvelle page actualiser. Enfin les méthodes PUT et DELETE ont pu être utilisé **A rajouter**

Il est important de noter que toutes les vues manipules des données transmises par son controlleur tels que la réponse de l'utilisateur ou encore l'affichage de personnage à trouver.

#### fonctionnalité
L'application permet d'insérer une entité dans la BDD avec l'utilisation de la méthode `save` qui permet de sauvegarder un compte d'un utilisateur dans la BDD (fichier CSV). Cette méthode est disponible grâce à `Spring Data JPA`. Pour la modification **A rajouter**

#### Détails des Entités

1 . **Utilisateur**
   - **Champs :** id, nom, email, motDePasse.
   - **Relations :**
     - 1-N avec "Partie" (un utilisateur peut avoir plusieurs parties enregistrées).
     - N-N avec "Personnage" (un utilisateur peut aimer plusieurs personnages et un personnage peut être aimé par plusieurs utilisateurs.).

2 . **Personnage**
   - **Champs :** id, nom, description, portraitURL, citations.
   - **Relations :**
     - 1-N avec "Citation" (un personnage peut avoir plusieurs citations).
     - N-N avec "Utilisateur" (un personnage peut être aimé par plusieurs utilisateurs et un utilisateur peut aimer plusieurs personnages.

3 . **Citation**
   - **Champs :** id, texte, personnageId.
   - **Relations :**
     - N-1 avec "Personnage" (chaque citation appartient à un personnage).

4 . **Partie**
   - **Champs :** id, date, utilisateurId, resultat.
   - **Relations :**
     - N-1 avec "Utilisateur".
     - N-1 avec "Personnage" (chaque partie est associée à un personnage).

5 . **Proposition**
   - **Champs :** id, textePropose, estCorrect, partieId.
   - **Relations :**
     - N-1 avec "Partie" (chaque proposition appartient à une partie)

#### Pour le Futur
Finalement, après avoir réalisé ce projet, nous avons envie de déployer ce site. En effet, nous pensons que cela peut être plaisant de proposer à tout le monde la possibilité de tester notre site et de pourquoi pas l'utiliser de manière quotidienne.
## Authors 👩‍💻

- [@Pierre NICHELE](https://github.com/pierre200326)
- [@Antonin CUYALA](https://github.com/AntoCu)
- [@Baptiste PLAUT-AUBRY](https://github.com/BaptistePlautA)
- [@Romain CORRAL](https://github.com/RomainCrrl)


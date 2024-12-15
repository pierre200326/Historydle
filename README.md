
# Historydle



Historydle est un site de jeu sur lequel l'utilisateur devra tenter de trouver un personnage historique chaque jour pour différents jeux :

- **Historydle :** Devinez un personnage à partir de ses attributs spécfiques.
- **Citation :** Trouvez qui a dit une citation célèbre.
- **Portrait :** Identifiez un personnage à partir de son portrait. 

Pour ces 3 jeux, un personnage aléatoire est généré **chaque jour**. A vous de le trouver !. 

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

#### Fonctionnalités
1 . L'application permet d'insérer une entité dans la BDD avec l'utilisation de la méthode `save` qui permet de sauvegarder un compte d'un utilisateur dans la BDD (fichier CSV). Cette méthode est disponible grâce à `Spring Data JPA`.

2 . L'application d'insérer, mettre à jour et supprimer un utilisateur dans la bdd, chercher une entité personnage dans la BDD

3 & 4 . L'application permet de lier 2 entités en BDD (User et partie, User et personnage likés par exemple)

#### Qualité 
1 . Ce projet a été réalisé à l'aide d'un framework CSS : tailwind.
Bien que la prise en main fut un petit peu déconcertante au début,l'utilisation de ce framework nous a permis de gagner beaucoup de temps dans la
réalisation du css que l'on trouve efficace et agréable. 

2 & 3 . Afin de rendre le travail de groupe plus pratique nous avons utilisé github dés le début du projet.
Cela nous a permis de pouvoir nous organiser correctement et efficacement malgré les multiples commit et merge. Nous avons fait le choix de travailler chacun sur une branche différente.
Tout ces éléments ont permis à chacun d'mplémenter et de tester des fonctionnalités différentes afin d'avancer le plus rapidement possible.

#### Technique

1 . Pour toutes nos fonctionnalités, nous avons utilisé le design pattern MVC tout au long de notre projet. 
Afin d'implementer celui-ci nous avons séparé chaque fonctionnalités sous trois composantes distinctes :

- **Modèle** : Regroupe tout ce qui concerne la gestion des données et leur logique métier. Dans notre application web, ceci représente les entités et repositories qui collaborent pour gérer les interactions avec la base de données et les règles définies 
- **Vue** : Regroupe toutes les pages HTML. A noter que l'on a utilisé l'outils Thymeleaf.
- **Contrôleur** : Gère les requêtes utilisateur et transfère les données entre le modèle et la vue. Nous avons un Controller pour toutes les vues et fonctionnalités existantes sur le site. 

2 . Le projet utilise toutes les méthodes HTTP demandées (GET,POST,PUT,DELETE). Nous avons notamment utilisé, la méthode GET lorsque l'on souhaite récupérer tous les personnages. La méthode POST a pu être utilisé pour récupérer la réponse de l'utilisateur, la comparer, enregistrer la réponse et renvoyer vers la nouvelle page actualiser. Enfin les méthodes PUT et DELETE sont utilisées dans l'admin pour modifier ou supprimer un utilisateur.

3 . Chaque que toutes les vues manipulent des données transmises par leur contrôleur tels que la réponse de l'utilisateur ou encore l'affichage du personnage à trouver.

#### Détails des Entités

1 . **Utilisateur**
   - **Champs :** id, pseudo, mdp, statut, liste de parties, liste de personnages likés.
   - **Relations :**
     - 1-N avec "Partie" (L'utilisateur possède un historique de parties)
     - N-N avec "Personnage" (un utilisateur peut liker plusieurs personnages, un personnage peut être liké par plusieurs utilisateurs)

2 . **Personnage**
   - **Champs :** id, nom, genre,pays,continent, domaine, periode imageURL, citation, liste d'indices, liste d'utilisateurs qui ont likés.
   - **Relations :**
     - 1-N avec "Indice" (un personnage peut avoir plusieurs indices, en l'occurence on lui en donne 2 dans les différents jeux actuellement).
     - N-N avec "Utilisateur" (un personnage peut être liké par plusieurs utilisateurs et un utilisateur peut liker plusieurs personnages)

3,4,5 . **ReponseCitation, ReponseDevinette, ReponsePortrait**
   - **Champs :** personnage, date.
   - **Relations :**
     - 1-1 avec "Personnage" (chaque citation/devinette/portrait du jour est associé à un personnage)

6 . **Partie**
   - **Champs :** id, jeu, personnageTrouvé,utilisateur.
   - **Relations :**
     - N-1 avec "Utilisateur" (L'utilisateur possède un historique de parties)

7 . **Indice**
   - **Champs :** id, personnage, indice.
   - **Relations :**
     - N-1 avec "Personnage" (chaque indice appartient à un personnage)

#### Futur de Historydle
Après avoir réalisé ce projet, nous envisageons de déployer ce site, afin de donner à tout le monde la possibilité de tester notre site et de tester ses connaissances quotidiennement.
Nous pourrions rajouter des fonctionnalités supplémentaires comme un top des joueurs du jour (classement du plus faible nombre d'essais par exemple), un classement des joueurs les plus réguliers, de nouveaux modes de jeux etc.

## Auteurs 👩‍💻

- [@Pierre NICHELE](https://github.com/pierre200326)
- [@Antonin CUYALA](https://github.com/AntoCu)
- [@Baptiste PLAUT-AUBRY](https://github.com/BaptistePlautA)
- [@Romain CORRAL](https://github.com/RomainCrrl)


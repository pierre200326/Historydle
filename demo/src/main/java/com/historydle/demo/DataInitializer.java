package com.historydle.demo;

import org.springframework.core.io.ClassPathResource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final PersonnageRepository personnageRepository;
    private final ReponseDevinetteController reponseDevinetteController;
    private final ReponseCitationController reponseCitationController;
    private final ReponsePortraitController reponsePortraitController;
    private final IndiceRepository indiceRepository;
    private final UtilisateurService utilisateurService;

    public DataInitializer(PersonnageRepository personnageRepository, ReponseDevinetteController reponseDevinetteController, ReponseCitationController reponseCitationController,ReponsePortraitController reponsePortraitController,IndiceRepository indiceRepository,UtilisateurService utilisateurService) {
        this.personnageRepository = personnageRepository;
        this.reponseDevinetteController = reponseDevinetteController;
        this.reponseCitationController=reponseCitationController;
        this.reponsePortraitController=reponsePortraitController;
        this.indiceRepository=indiceRepository;
        this.utilisateurService=utilisateurService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadUsersFromCsv();

        createPersonnage("Napoléon Bonaparte", "Homme", "France", "Europe", "Politicien", 19,"“Les hommes de génie sont des météores destinés à brûler pour éclairer leur siècle”","51 ans","Empereur des Français");
        createPersonnage("Leonard de Vinci", "Homme", "Italie", "Europe", "Artiste", 16,"“Nul conseil n'est plus loyal que celui qui se donne sur un navire en péril”","67 ans","Maître de la Renaissance");
        createPersonnage("Cléopâtre", "Femme", "Égypte", "Afrique", "Dirigeant", -1,"Je suis une personne qui ne possède pas citation connue","39 ans","Reine d'Égypte");
        createPersonnage("Galilée", "Homme", "Italie", "Europe", "Scientifique", 17,"“On ne peut rien apprendre aux gens. On peut seulement les aider à découvrir qu’ils possèdent déjà en eux tout ce qui est à apprendre”","77 ans","Père de la science Moderne");
        createPersonnage("Albert Einstein", "Homme", "Allemagne", "Europe", "Scientifique", 20,"“Que chacun raisonne en son âme et conscience, qu'il se fasse une idée fondée sur ses propres lectures et non d'après les racontars des autres”","71 ans","Fondateur de la relativité");
        createPersonnage("Abraham Lincoln", "Homme", "États-Unis", "Amérique", "Dirigeant", 19, "“Le gouvernement du peuple, par le peuple, pour le peuple, ne disparaîtra pas de la Terre.”", "56 ans", "Président des États-Unis");
        createPersonnage("Alexandre Le Grand", "Homme", "Macédoine", "Europe", "Dirigeant Militaire", -6, "“Il n'y a rien d'impossible à celui qui essaie.”", "32 ans", "Conquérant");
        createPersonnage("Vercingétorix", "Homme", "Gaule", "Europe", "Dirigeant/Militaire", -1, "“Je suis ici pour défendre la liberté de la Gaule.”", "Inconnu", "Chef de la Gaule");
        createPersonnage("Hô Chi Minh", "Homme", "Vietnam", "Asie", "Dirigeant", 20, "“Rien n'est plus précieux que l'indépendance et la liberté.”", "79 ans", "Père de l'indépendance vietnamienne");
        createPersonnage("Catherine De Medicis", "Femme", "France/Italie", "Europe", "Dirigeant", 16, "“Divise, afin de régner”", "69 ans", "Reine de France");
        createPersonnage("Cyrus Le Grand", "Homme", "Perse", "Asie", "Dirigeant", 6, "“Je ne crains qu'une seule chose, perdre mon honneur.”", "Inconnu", "Roi de Perse");
        createPersonnage("Didon", "Femme", "Phénicie", "Afrique", "Dirigeant", 9, "“Un homme sans foi ne mérite pas mon amour.”", "Inconnu", "Reine de Carthages");
        createPersonnage("Alienor d'Aquitaine", "Femme", "France/Angleterre", "Europe", "Dirigeant", 12, "“Je suis née pour le pouvoir et la majesté.”", "82 ans", "Duchesse d'Aquitaine");
        createPersonnage("Elisabeth II", "Femme", "Angleterre", "Europe", "Dirigeant", 21, "“Je déclare devant vous tous que ma vie entière sera consacrée à votre service.”", "96 ans", "Reine du Royaume-Uni");
        createPersonnage("Otto von Bismarck", "Homme", "Allemagne", "Europe", "Dirigeant", 19, "“La politique n'est pas une science exacte.”", "83 ans", "Chancelier de l'empire allemand");
        createPersonnage("Gandhi", "Homme", "Inde", "Asie", "Philosophe/Dirigeant/Religieux", 20, "“Soyez le changement que vous voulez voir dans le monde.”", "78 ans", "Père de la nation Indienne");
        createPersonnage("Genghis Khan", "Homme", "Mongolie", "Asie", "Dirigeant/Militaire", 18, "“La conquête du monde repose sur le contrôle de soi.”", "Inconnu", "Empereur Mongol");
        createPersonnage("Gilgamesh", "Homme", "Sumer", "Asie", "Dirigeant", -27, "“Ce que nous faisons dans la vie résonne pour l'éternité.”", "Inconnu", "Roi d'Uruk");
        createPersonnage("Nabuchodonosor", "Homme", "Babylone", "Asie", "Dirigeant", -6, "“Je suis le roi de Babylone, celui qui a reconstruit le temple des dieux.”", "Inconnu", "Roi de Babylone");
        createPersonnage("Canute Le Grand", "Homme", "Angleterre/Danemark", "Europe", "Dirigeant", 11, "“La puissance d'un roi a ses limites.”", "45", "Roi d'Angleterre et du Danemark");
        createPersonnage("Oda Nobunaga", "Homme", "Japon", "Asie", "Dirigeant", 16, "“Si l'oiseau ne chante pas, tue-le.”", "47", "Daimyo du Japon");
        createPersonnage("Marie Curie", "Femme", "France/Pologne", "Europe", "Scientifique", 20, "“Dans la vie, rien n'est à craindre, tout est à comprendre.”", "66 ans", "Pionnière de la radioactivité");
        createPersonnage("Jules Cesar", "Homme", "Italie", "Europe", "Dirigeant", -1, "“Veni, vidi, vici.”", "56 ans", "Empereur de Rome");
        createPersonnage("Louis II de Bavière", "Homme", "Allemagne", "Europe", "Dirigeant", 19,  "“J'ai voulu rester un éternel mystère pour moi-même et pour les autres.”", "40 ans", "Roi de Bavière");
        createPersonnage("Tim Berners-Lee", "Homme", "Angleterre", "Europe", "Scientifique", 21, "“Le Web est plus une création sociale que technique.”", "Vivant", "Inventeur du world wide web");
        createPersonnage("Brendan Eich", "Homme", "États-Unis", "Amérique", "Scientifique", 21, "“Si le code est ouvert, il est libre d’être modifié.”", "Vivant", "Créateur de JavaScript");
        createPersonnage("Moctezuma", "Homme", "Aztèque", "Amérique", "Dirigeant", 16, "“Les dieux nous ont laissé un royaume magnifique.”", "Inconnu", "Empereur Azteque");
        createPersonnage("Pachacutec", "Homme", "Inca", "Amérique", "Dirigeant", 15, "“Nous vivons sous la protection de notre dieu Soleil.”", "Inconnu", "Empereur Inca");
        createPersonnage("Ramses II", "Homme", "Égypte", "Afrique", "Dirigeant", -13, "“Je suis le pharaon du peuple.”", "90 ans", "Pharaon d'Egypte");
        createPersonnage("Saladin", "Homme", "Égypte/Ayyoubides", "Asie/Afrique", "Dirigeant", 12, "“La paix est plus difficile à faire que la guerre.”", "55 ans ", "Sultant d'Egypte et de Syrie");
        createPersonnage("Sejong Le Grand", "Homme", "Corée", "Asie", "Dirigeant", 15, "“L'éducation est la clé de la prospérité.”", "52 ans", "Roi de Corée");
        createPersonnage("Shaka Zulu", "Homme", "Afrique du Sud", "Afrique", "Dirigeant/Militaire", 19, "“Le zoulou est un guerrier invincible.”", "Inconnu", "Roi des Zouloux");
        createPersonnage("Simon Bolivar", "Homme", "Venezuela/Colombie/Équateur/Pérou/Bolivie", "Amérique", "Dirigeant/Militaire", 19, "“La liberté est le seul objectif digne du sacrifice de la vie.”", "47 ans", "Libérateur de l'Amérique Latine");
        createPersonnage("Soliman Le Magnifique", "Homme", "Empire ottoman", "Europe/Asie", "Dirigeant/Militaire", 16, "“Mon empire ne connaît pas de frontières.”", "71 ans", "Sultan de l'Empire ottoman");
        createPersonnage("Theodore Roosevelt", "Homme", "États-Unis", "Amérique", "Dirigeant", 20, "“Parlez doucement et portez un gros bâton.”", "60 ans", "Président des Etats-Unis");
        createPersonnage("Newton", "Homme", "Angleterre", "Europe", "Scientifique", 18, "“Si j'ai vu plus loin, c'est en montant sur les épaules de géants.”", "84 ans", "Mathématicien et physicien");
        createPersonnage("Michel-Ange", "Homme", "Italie", "Europe", "Artiste", 16, "“La sculpture consiste à enlever ce qui est de trop.”", "88 ans", "Maître de la Renaissance");
        createPersonnage("Platon", "Homme", "Grèce", "Europe", "Philosophe", -4, "“Connais-toi toi-même.”", "80 ans", "Philosophe grec");
        createPersonnage("Socrate", "Homme", "Grèce", "Europe", "Philosophe", -5, "“Je sais que je ne sais rien.”", "70 ans", "Philosophe grec");
        createPersonnage("Bouddha", "Homme", "Inde", "Asie", "Religieux/Philosophe", -6, "“La paix vient de l'intérieur. Ne la cherchez pas à l'extérieur.”", "Inconnu", "Fondateur du bouddhisme");
        createPersonnage("Confucius", "Homme", "Chine", "Asie", "Philosophe", -5, "“Apprends à vivre comme si tu devais mourir demain.”", "Inconnu", "Philosophe chinois");
        createPersonnage("Vincent Van Gogh", "Homme", "Pays-Bas", "Europe", "Artiste", 19, "“Je rêve mes tableaux, et je peins mes rêves.”", "37 ans", "Peintre post-impressionniste");
        createPersonnage("Aristote", "Homme", "Grèce", "Europe", "Philosophe/Scientifique", -4, "“L'ignorant affirme, le savant doute, le sage réfléchit.”", "62 ans", "Philosophe et scientifique");
        createPersonnage("Nikola Tesla", "Homme", "Serbie/États-Unis", "Europe/Amérique", "Scientifique/Ingénieur", 20, "“Les scientifiques d’aujourd’hui pensent profondément au lieu de clairement. Pour penser clairement, il faut avoir de la clarté dans l’esprit.”", "86 ans", "Inventeur et ingénieur");
        createPersonnage("Sigmund Freud", "Homme", "Autriche", "Europe", "Scientifique/Philosophe", 20, "“Le moi n’est pas maître dans sa propre maison.”", "83 ans", "Fondateur de la psychanalyse");
        createPersonnage("Jean-Jacques Rousseau", "Homme", "Suisse/France", "Europe", "Philosophe", 18, "“L'homme est né libre, et partout il est dans les fers.”", "66 ans", "Philosohe des Lumières");
        createPersonnage("William Shakespeare", "Homme", "Angleterre", "Europe", "Artiste", 17, "“Être ou ne pas être, telle est la question.”", "52 ans", "Dramaturge et Poete");
        createPersonnage("Charles Darwin", "Homme", "Angleterre", "Europe", "Scientifique", 19, "“Ce n’est pas le plus fort de l’espèce qui survit, ni le plus intelligent, mais celui qui s’adapte le mieux au changement.”", "73 ans", "Naturaliste et biologiste");
        createPersonnage("Thomas Edison", "Homme", "États-Unis", "Amérique", "Scientifique/Inventeur", 20, "“Le génie est un pour cent d’inspiration et quatre-vingt-dix-neuf pour cent de transpiration.”", "84 ans", "Inventeur et entrepreneur");
        createPersonnage("Victor Hugo", "Homme", "France", "Europe", "Écrivain", 19, "“La liberté commence où l’ignorance finit.”", "83 ans", "Ecrivain et Poete");
        createPersonnage("Jeanne d'Arc", "Femme", "France", "Europe", "Militaire/Religieuse", 15, "“Allez de l’avant et Dieu sera avec vous.”", "19 ans", "Héroïne et sainte de France");
        createPersonnage("Charles De Gaulle", "Homme", "France", "Europe", "Dirigeant/Militaire", 20, "“La grandeur d’un pays ne se mesure pas à son territoire, mais à sa volonté.”", "79 ans", "Président de la France et Père de la Libération");
        createPersonnage("Clovis", "Homme", "France", "Europe", "Dirigeant", 6, "“Par ce signe, tu vaincras.”", "Inconnu", "Premier roi des Francs");
        createPersonnage("Charlemagne", "Homme", "France", "Europe", "Dirigeant", 9, "“J'aimerais savoir lire.”", "Inconnu", "Empereur d'Occident");
        createPersonnage("Angela Merkel", "Femme", "Allemagne", "Europe", "Dirigeante", 21, "“Nous réussirons.”", "Vivante", "Chancelière Allemande");
        createPersonnage("Frida Kahlo", "Femme", "Mexique", "Amérique", "Artiste", 20, "“Je peins des fleurs pour qu'elles ne meurent pas.”", "47 ans", "Peintre Mexicaine");
        personnageRepository.findAll().forEach(p -> System.out.println("Personnage : " + p.getNom()));

        //Afficher les réponses du jour
        Personnage reponseDevinette = reponseDevinetteController.getReponseDuJour();
        System.out.println("La réponse du jour est : " + reponseDevinette.getNom() + ", Domaine: " + reponseDevinette.getDomaine() + ", Genre: " + reponseDevinette.getGenre());
        Personnage reponseCitation = reponseCitationController.getReponseDuJour();
        System.out.println("La réponse du jour est : " + reponseCitation.getNom() + ", Domaine: " + reponseCitation.getDomaine() + ", Genre: " + reponseCitation.getGenre());
        Personnage reponsePortrait = reponsePortraitController.getReponseDuJour();
        System.out.println("La réponse du jour est : " + reponsePortrait.getNom() + ", Domaine: " + reponsePortrait.getDomaine() + ", Genre: " + reponsePortrait.getGenre());
    }

    private void createPersonnage(String nom, String genre, String pays, String continent, String domaine, int periode, String citation, String indiceDescription,String indiceDescription2) {
    Personnage personnage = new Personnage(nom, genre, pays, continent, domaine, periode, citation);
    Indice indice = new Indice(personnage, indiceDescription);
    Indice indice2 = new Indice(personnage, indiceDescription2);
    personnage.addIndice(indice);
    personnage.addIndice(indice2);
        personnageRepository.save(personnage);
        indiceRepository.save(indice);
        indiceRepository.save(indice2);

        logger.info("Personnage {} avec indice {} ajouté à la base de données", nom, indiceDescription,indiceDescription2);
    }

    private void loadUsersFromCsv() {
        // Chemin du fichier CSV dans le dossier externe "data"
        String cheminFichier = "./data/utilisateurs.csv";

        try (FileReader fileReader = new FileReader(cheminFichier)) {
            // Lecture du fichier CSV avec Apache Commons CSV
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader("pseudo", "mdp")
                .withSkipHeaderRecord()
                .parse(fileReader);

            // Parcours des enregistrements CSV
            for (CSVRecord record : records) {
                String pseudo = record.get("pseudo");
                String mdp = record.get("mdp");

                // Vérifie si l'utilisateur existe déjà via le service
                if (!utilisateurService.existeParPseudo(pseudo)) {
                    // Ajoute l'utilisateur via le service
                    utilisateurService.inscrireUtilisateur(pseudo, mdp);
                }
            }

            System.out.println("Utilisateurs chargés depuis le fichier CSV !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
        }
    }

}

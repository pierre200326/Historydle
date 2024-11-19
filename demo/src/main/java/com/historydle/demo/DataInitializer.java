package com.historydle.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final PersonnageRepository personnageRepository;
    private final ReponseDevinetteController reponseDevinetteController;
    private final ReponseCitationController reponseCitationController;
    private final ReponsePortraitController reponsePortraitController;
    private final IndiceRepository indiceRepository;

    public DataInitializer(PersonnageRepository personnageRepository, ReponseDevinetteController reponseDevinetteController, ReponseCitationController reponseCitationController,ReponsePortraitController reponsePortraitController,IndiceRepository indiceRepository) {
        this.personnageRepository = personnageRepository;
        this.reponseDevinetteController = reponseDevinetteController;
        this.reponseCitationController=reponseCitationController;
        this.reponsePortraitController=reponsePortraitController;
        this.indiceRepository=indiceRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        createPersonnage("Napoléon Bonaparte", "Homme", "France", "Europe", "Dirigeant / Militaire", 19,"“Les hommes de génie sont des météores destinés à brûler pour éclairer leur siècle”","Empereur des Français");
        createPersonnage("Leonard de Vinci", "Homme", "Italie", "Europe", "Artiste / Scientifique", 16,"“Nul conseil n'est plus loyal que celui qui se donne sur un navire en péril”","Maître de la Renaissance");
        createPersonnage("Cléopâtre", "Femme", "Égypte", "Afrique", "Dirigeant", -1,"Je suis une personne qui ne possède pas citation connue","Reine d'Égypte");
        createPersonnage("Galilée", "Homme", "Italie", "Europe", "Scientifique", 17,"“On ne peut rien apprendre aux gens. On peut seulement les aider à découvrir qu’ils possèdent déjà en eux tout ce qui est à apprendre”","Père de la science Moderne");
        createPersonnage("Einstein", "Homme", "Allemagne", "Europe", "Scientifique", 20,"“Que chacun raisonne en son âme et conscience, qu'il se fasse une idée fondée sur ses propres lectures et non d'après les racontars des autres”","Fondateur de la relativité");
        createPersonnage("Abraham Lincoln", "Homme", "États-Unis", "Amérique", "Dirigeant", 19, "“Le gouvernement du peuple, par le peuple, pour le peuple, ne disparaîtra pas de la Terre.”", "Président des États-Unis");
        createPersonnage("Alexandre Le Grand", "Homme", "Macédoine", "Europe", "Dirigeant Militaire", -4, "“Il n'y a rien d'impossible à celui qui essaie.”", "Conquérant de l'Antiquité");
        createPersonnage("Vercingétorix", "Homme", "France /Gaule", "Europe", "Dirigeant / Militaire", -1, "“Je suis ici pour défendre la liberté de la Gaule.”", "Chef des Gaulois");
        createPersonnage("Hô Chi Minh", "Homme", "Vietnam", "Asie", "Dirigeant", 20, "“Rien n'est plus précieux que l'indépendance et la liberté.”", "Leader de l'indépendance vietnamienne");
        createPersonnage("Catherine De Medicis", "Femme", "France / Italie", "Europe", "Dirigeant", 16, "“Il vaut mieux manquer de dignité que de manquer de sagesse.”", "Reine de France");
        createPersonnage("Cyrus Le Grand", "Homme", "Perse", "Asie", "Dirigeant", -6, "“Je ne crains qu'une seule chose, perdre mon honneur.”", "Fondateur de l'Empire perse");
        createPersonnage("Didon", "Femme", "Phénicie", "Afrique", "Dirigeant", -9, "“Un homme sans foi ne mérite pas mon amour.”", "Fondatrice de Carthage");
        createPersonnage("Alienor d'Aquitaine", "Femme", "France / Angleterre", "Europe", "Dirigeant", 12, "“Je suis née pour le pouvoir et la majesté.”", "Reine de France et d'Angleterre");
        createPersonnage("Élisabeth II", "Femme", "Angleterre", "Europe", "Dirigeant", 21, "“Je déclare devant vous tous que ma vie entière sera consacrée à votre service.”", "Reine du Royaume-Uni");
        createPersonnage("Otto von Bismarck", "Homme", "Allemagne", "Europe", "Dirigeant", 19, "“La politique n'est pas une science exacte.”", "Chancelier de l'Empire allemand");
        createPersonnage("Gandhi", "Homme", "Inde", "Asie", "Philosophe / Dirigeant / Religieux", 20, "“Soyez le changement que vous voulez voir dans le monde.”", "Père de la nation indienne");
        createPersonnage("Genghis Khan", "Homme", "Mongolie", "Asie", "Dirigeant / Militaire", 13, "“La conquête du monde repose sur le contrôle de soi.”", "Conquérant mongol");
        createPersonnage("Gilgamesh", "Homme", "Sumer", "Asie", "Dirigeant", -27, "“Ce que nous faisons dans la vie résonne pour l'éternité.”", "Héros de l'épopée sumérienne");
        createPersonnage("Nabuchodonosor", "Homme", "Babylone", "Asie", "Dirigeant", -6, "“Je suis le roi de Babylone, celui qui a reconstruit le temple des dieux.”", "Roi de Babylone");
        createPersonnage("Canute Le Grand", "Homme", "Angleterre / Danemark", "Europe", "Dirigeant", 11, "“La puissance d'un roi a ses limites.”", "Roi anglo-danois");
        createPersonnage("Oda Nobunaga", "Homme", "Japon", "Asie", "Dirigeant", 16, "“Si l'oiseau ne chante pas, tue-le.”", "Unificateur du Japon");
        createPersonnage("Marie Curie", "Femme", "France / Pologne", "Europe", "Scientifique", 20, "“Dans la vie, rien n'est à craindre, tout est à comprendre.”", "Pionnière de la radioactivité");
        createPersonnage("Jules Cesar", "Homme", "Rome", "Europe", "Dirigeant / Militaire", -1, "“Veni, vidi, vici.”", "Dictateur romain");
        createPersonnage("Louis II de Bavière", "Homme", "Allemagne", "Europe", "Dirigeant", 19, "“J'ai voulu rester un éternel mystère pour moi-même et pour les autres.”", "Roi bâtisseur");
        createPersonnage("Tim Berners-Lee", "Homme", "Angleterre", "Europe", "Scientifique", 21, "“Le Web est plus une création sociale que technique.”", "Inventeur du Web");
        createPersonnage("Brendan Eich", "Homme", "États-Unis", "Amérique", "Scientifique", 21, "“Si le code est ouvert, il est libre d’être modifié.”", "Créateur de JavaScript");
        createPersonnage("Moctezuma", "Homme", "Aztèque", "Amérique", "Dirigeant / Militaire", 16, "“Les dieux nous ont laissé un royaume magnifique.”", "Empereur aztèque");
        createPersonnage("Pachacutec", "Homme", "Inca", "Amérique", "Dirigeant / Militaire", 15, "“Nous vivons sous la protection de notre dieu Soleil.”", "Empereur inca");
        createPersonnage("Ramses II", "Homme", "Égypte", "Afrique", "Dirigeant / Militaire", -13, "“Je suis le pharaon du peuple.”", "Pharaon d'Égypte");
        createPersonnage("Saladin", "Homme", "Égypte / Ayyoubides", "Asie / Afrique", "Dirigeant / Militaire", 12, "“La paix est plus difficile à faire que la guerre.”", "Sultan d'Égypte et de Syrie");
        createPersonnage("Sejong Le Grand", "Homme", "Corée", "Asie", "Dirigeant", 15, "“L'éducation est la clé de la prospérité.”", "Roi de Corée");
        createPersonnage("Shaka Zulu", "Homme", "Afrique du Sud", "Afrique", "Dirigeant / Militaire", 19, "“Le zoulou est un guerrier invincible.”", "Roi des Zoulous");
        createPersonnage("Simon Bolivar", "Homme", "Venezuela / Colombie / Équateur / Pérou / Bolivie", "Amérique", "Dirigeant / Militaire", 19, "“La liberté est le seul objectif digne du sacrifice de la vie.”", "Libérateur de l'Amérique latine");
        createPersonnage("Soliman Le Magnifique", "Homme", "Empire ottoman", "Europe / Asie / Afrique", "Dirigeant / Militaire", 16, "“Mon empire ne connaît pas de frontières.”", "Sultan de l'Empire ottoman");
        createPersonnage("Theodore Roosevelt", "Homme", "États-Unis", "Amérique", "Dirigeant", 20, "“Parlez doucement et portez un gros bâton.”", "Président des États-Unis");
        createPersonnage("Newton", "Homme", "Angleterre", "Europe", "Scientifique", 18, "“Si j'ai vu plus loin, c'est en montant sur les épaules de géants.”", "Mathématicien et physicien");
        createPersonnage("Michel-Ange", "Homme", "Italie", "Europe", "Artiste", 16, "“La sculpture consiste à enlever ce qui est de trop.”", "Maître de la Renaissance");
        createPersonnage("Platon", "Homme", "Grèce", "Europe", "Philosophe", -4, "“Connais-toi toi-même.”", "Philosophe grec");
        createPersonnage("Socrate", "Homme", "Grèce", "Europe", "Philosophe", -5, "“Je sais que je ne sais rien.”", "Philosophe grec");
        createPersonnage("Bouddha", "Homme", "Inde", "Asie", "Religieux / Philosophe", -6, "“La paix vient de l'intérieur. Ne la cherchez pas à l'extérieur.”", "Fondateur du bouddhisme");
        createPersonnage("Confucius", "Homme", "Chine", "Asie", "Philosophe / Religieux", -5, "“Apprends à vivre comme si tu devais mourir demain.”", "Philosophe chinois");
        createPersonnage("Vincent Van Gogh", "Homme", "Pays-Bas", "Europe", "Artiste", 19, "“Je rêve mes tableaux, et je peins mes rêves.”", "Peintre post-impressionniste");
        createPersonnage("Aristote", "Homme", "Grèce", "Europe", "Philosophe / Scientifique", -4, "“L'ignorant affirme, le savant doute, le sage réfléchit.”", "Philosophe et scientifique");
        createPersonnage("Karl Marx", "Homme", "Allemagne", "Europe", "Philosophe", 19, "“Les philosophes n'ont fait qu'interpréter le monde de différentes manières ; ce qui importe, c'est de le transformer.”", "Théoricien politique");
        createPersonnage("Nikola Tesla", "Homme", "Serbie / États-Unis", "Europe / Amérique", "Scientifique", 20, "“Les scientifiques d’aujourd’hui pensent profondément au lieu de clairement. Pour penser clairement, il faut avoir de la clarté dans l’esprit.”", "Inventeur et ingénieur");
        createPersonnage("Sigmund Freud", "Homme", "Autriche", "Europe", "Scientifique / Philosophe", 20, "“Le moi n’est pas maître dans sa propre maison.”", "Fondateur de la psychanalyse");
        createPersonnage("Jean-Jacques Rousseau", "Homme", "Suisse / France", "Europe", "Philosophe", 18, "“L'homme est né libre, et partout il est dans les fers.”", "Philosophe des Lumières");
        createPersonnage("William Shakespeare", "Homme", "Angleterre", "Europe", "Artiste", 16, "“Être ou ne pas être, telle est la question.”", "Dramaturge et poète");
        createPersonnage("Charles Darwin", "Homme", "Angleterre", "Europe", "Scientifique", 19, "“Ce n’est pas le plus fort de l’espèce qui survit, ni le plus intelligent, mais celui qui s’adapte le mieux au changement.”", "Naturaliste et biologiste");
        createPersonnage("Thomas Edison", "Homme", "États-Unis", "Amérique", "Scientifique", 20, "“Le génie est un pour cent d’inspiration et quatre-vingt-dix-neuf pour cent de transpiration.”", "Inventeur et entrepreneur");
        createPersonnage("Victor Hugo", "Homme", "France", "Europe", "Artiste", 19, "“La liberté commence où l’ignorance finit.”", "Écrivain et poète");
        createPersonnage("Jeanne d'Arc", "Femme", "France", "Europe", "Militaire / Religieux", 15, "“Allez de l’avant et Dieu sera avec vous.”", "Héroïne et sainte de France");
        createPersonnage("Charles De Gaulle", "Homme", "France", "Europe", "Dirigeant / Militaire", 20, "“La grandeur d’un pays ne se mesure pas à son territoire, mais à sa volonté.”", "Président de la France");
        createPersonnage("Clovis", "Homme", "France", "Europe", "Dirigeant / Militaire", 6, "“Par ce signe, tu vaincras.”", "Premier roi des Francs");
        createPersonnage("Charlemagne", "Homme", "France", "Europe", "Dirigeant / Militaire", 9, "“J'aimerais savoir lire.”", "Empereur d'Occident");
        createPersonnage("Angela Merkel", "Femme", "Allemagne", "Europe", "Dirigeant", 21, "“Nous réussirons.”", "Chancelière d'Allemagne");
        createPersonnage("Frida Kahlo", "Femme", "Mexique", "Amérique", "Artiste", 20, "“Je peins des fleurs pour qu'elles ne meurent pas.”", "Artiste peintre mexicaine");

        personnageRepository.findAll().forEach(p -> System.out.println("Personnage : " + p.getNom()));

        //Afficher les réponses du jour
        Personnage reponseDevinette = reponseDevinetteController.getReponseDuJour();
        System.out.println("La réponse du jour est : " + reponseDevinette.getNom() + ", Domaine: " + reponseDevinette.getDomaine() + ", Genre: " + reponseDevinette.getGenre());
        Personnage reponseCitation = reponseCitationController.getReponseDuJour();
        System.out.println("La réponse du jour est : " + reponseCitation.getNom() + ", Domaine: " + reponseCitation.getDomaine() + ", Genre: " + reponseCitation.getGenre());
        Personnage reponsePortrait = reponsePortraitController.getReponseDuJour();
        System.out.println("La réponse du jour est : " + reponsePortrait.getNom() + ", Domaine: " + reponsePortrait.getDomaine() + ", Genre: " + reponsePortrait.getGenre());
    }

    private void createPersonnage(String nom, String genre, String pays, String continent, String domaine, int periode, String citation, String indiceDescription) {
    Personnage personnage = new Personnage(nom, genre, pays, continent, domaine, periode, citation);
    Indice indice = new Indice(personnage, indiceDescription);
    personnage.addIndice(indice);

    personnageRepository.save(personnage);
    indiceRepository.save(indice);

    logger.info("Personnage {} avec indice {} ajouté à la base de données", nom, indiceDescription);
    }
}

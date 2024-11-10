package com.historydle.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final PersonnageRepository personnageRepository;
    private final ReponseController reponseController;

    public DataInitializer(PersonnageRepository personnageRepository, ReponseController reponseController) {
        this.personnageRepository = personnageRepository;
        this.reponseController = reponseController;
    }

    @Override
    public void run(String... args) throws Exception {
        // Add characters to the database if empty
        if (personnageRepository.count() == 0) {
            personnageRepository.save(new Personnage("Napoléon Bonaparte", "Homme", "France", "Europe", "Politicien", "18e-19e siècle","“Les hommes de génie sont des météores destinés à brûler pour éclairer leur siècle”"));
            personnageRepository.save(new Personnage("Leonard de Vinci", "Homme", "Italie", "Europe", "Artiste", "15e-16e siècle","“Nul conseil n'est plus loyal que celui qui se donne sur un navire en péril”"));
            personnageRepository.save(new Personnage("Cléopâtre", "Femme", "Égypte", "Afrique", "Dirigeant", "1er siècle av. J.-C.","Je suis une personne qui ne possède pas citation connue"));
            personnageRepository.save(new Personnage("Galilée", "Homme", "Italie", "Europe", "Scientifique", "16e-17e siècle","“On ne peut rien apprendre aux gens. On peut seulement les aider à découvrir qu’ils possèdent déjà en eux tout ce qui est à apprendre”"));
            personnageRepository.save(new Personnage("Einstein", "Homme", "Allemagne", "Europe", "Scientifique", "19e-20e siècle","“Que chacun raisonne en son âme et conscience, qu'il se fasse une idée fondée sur ses propres lectures et non d'après les racontars des autres”"));
            personnageRepository.save(new Personnage("Abraham Lincoln", "Homme", "États-Unis", "Amérique", "Dirigeant", "XIXème", "“Le gouvernement du peuple, par le peuple, pour le peuple, ne disparaîtra pas de la Terre.”"));
            personnageRepository.save(new Personnage("Alexandre Le Grand", "Homme", "Macédoine", "Europe", "Dirigeant Militaire", "IVème AVJC", "“Il n'y a rien d'impossible à celui qui essaie.”"));
            personnageRepository.save(new Personnage("Vercingétorix", "Homme", "Gaule", "Europe", "Dirigeant/Militaire", "Ier AVJC", "“Je suis ici pour défendre la liberté de la Gaule.”"));
            personnageRepository.save(new Personnage("Hô Chi Minh", "Homme", "Vietnam", "Asie", "Dirigeant", "XXème", "“Rien n'est plus précieux que l'indépendance et la liberté.”"));
            personnageRepository.save(new Personnage("Catherine De Medicis", "Femme", "France/Italie", "Europe", "Dirigeant", "XVIème", "“Il vaut mieux manquer de dignité que de manquer de sagesse.”"));
            personnageRepository.save(new Personnage("Cyrus Le Grand", "Homme", "Perse", "Asie", "Dirigeant", "VIème AVJC", "“Je ne crains qu'une seule chose, perdre mon honneur.”"));
            personnageRepository.save(new Personnage("Didon", "Femme", "Phénicie", "Afrique", "Dirigeant", "IXème AVJC", "“Un homme sans foi ne mérite pas mon amour.”"));
            personnageRepository.save(new Personnage("Alienor d'Aquitaine", "Femme", "France/Angleterre", "Europe", "Dirigeant", "XIIème", "“Je suis née pour le pouvoir et la majesté.”"));
            personnageRepository.save(new Personnage("Elisabeth II", "Femme", "Angleterre", "Europe", "Dirigeant", "XXIème", "“Je déclare devant vous tous que ma vie entière sera consacrée à votre service.”"));
            personnageRepository.save(new Personnage("Otto von Bismarck", "Homme", "Allemagne", "Europe", "Dirigeant", "XIXème", "“La politique n'est pas une science exacte.”"));
            personnageRepository.save(new Personnage("Gandhi", "Homme", "Inde", "Asie", "Philosophe/Dirigeant/Religieux", "XXème", "“Soyez le changement que vous voulez voir dans le monde.”"));
            personnageRepository.save(new Personnage("Genghis Khan", "Homme", "Mongolie", "Asie", "Dirigeant/Militaire", "XIIIème", "“La conquête du monde repose sur le contrôle de soi.”"));
            personnageRepository.save(new Personnage("Gilgamesh", "Homme", "Sumer", "Asie", "Dirigeant", "XXVIIème AVJC", "“Ce que nous faisons dans la vie résonne pour l'éternité.”"));
            personnageRepository.save(new Personnage("Nabuchodonosor", "Homme", "Babylone", "Asie", "Dirigeant", "VIème AVJC", "“Je suis le roi de Babylone, celui qui a reconstruit le temple des dieux.”"));
            personnageRepository.save(new Personnage("Canute Le Grand", "Homme", "Angleterre/Danemark", "Europe", "Dirigeant", "XIème", "“La puissance d'un roi a ses limites.”"));
            personnageRepository.save(new Personnage("Oda Nobunaga", "Homme", "Japon", "Asie", "Dirigeant", "XVIème", "“Si l'oiseau ne chante pas, tue-le.”"));
            personnageRepository.save(new Personnage("Marie Curie", "Femme", "France/Pologne", "Europe", "Scientifique", "XXème", "“Dans la vie, rien n'est à craindre, tout est à comprendre.”"));
            personnageRepository.save(new Personnage("Jules Cesar", "Homme", "Rome", "Europe", "Dirigeant", "Ier siècle AVJC", "“Veni, vidi, vici.”"));
            personnageRepository.save(new Personnage("Louis II de Bavière", "Homme", "Allemagne", "Europe", "Dirigeant", "XIXème", "“J'ai voulu rester un éternel mystère pour moi-même et pour les autres.”"));
            personnageRepository.save(new Personnage("Tim Berners-Lee", "Homme", "Angleterre", "Europe", "Scientifique", "XXIème", "“Le Web est plus une création sociale que technique.”"));
            personnageRepository.save(new Personnage("Brendan Eich", "Homme", "États-Unis", "Amérique", "Scientifique", "XXIème", "“Si le code est ouvert, il est libre d’être modifié.”"));
            personnageRepository.save(new Personnage("Moctezuma", "Homme", "Aztèque", "Amérique", "Dirigeant", "XVIème", "“Les dieux nous ont laissé un royaume magnifique.”"));
            personnageRepository.save(new Personnage("Pachacutec", "Homme", "Inca", "Amérique", "Dirigeant", "XVème", "“Nous vivons sous la protection de notre dieu Soleil.”"));
            personnageRepository.save(new Personnage("Ramses II", "Homme", "Égypte", "Afrique", "Dirigeant", "XIIIème AVJC", "“Je suis le pharaon du peuple.”"));
            personnageRepository.save(new Personnage("Saladin", "Homme", "Égypte/Ayyoubides", "Asie/Afrique", "Dirigeant", "XIIème", "“La paix est plus difficile à faire que la guerre.”"));
            personnageRepository.save(new Personnage("Sejong Le Grand", "Homme", "Corée", "Asie", "Dirigeant", "XVème", "“L'éducation est la clé de la prospérité.”"));
            personnageRepository.save(new Personnage("Shaka Zulu", "Homme", "Afrique du Sud", "Afrique", "Dirigeant/Militaire", "XIXème", "“Le zoulou est un guerrier invincible.”"));
            personnageRepository.save(new Personnage("Simon Bolivar", "Homme", "Venezuela/Colombie/Équateur/Pérou/Bolivie", "Amérique", "Dirigeant/Militaire", "XIXème", "“La liberté est le seul objectif digne du sacrifice de la vie.”"));
            personnageRepository.save(new Personnage("Soliman Le Magnifique", "Homme", "Empire ottoman", "Europe/Asie", "Dirigeant/Militaire", "XVIème", "“Mon empire ne connaît pas de frontières.”"));
            personnageRepository.save(new Personnage("Theodore Roosevelt", "Homme", "États-Unis", "Amérique", "Dirigeant", "XXème", "“Parlez doucement et portez un gros bâton.”"));
            personnageRepository.save(new Personnage("Newton", "Homme", "Angleterre", "Europe", "Scientifique", "XVIIIème", "“Si j'ai vu plus loin, c'est en montant sur les épaules de géants.”"));
            personnageRepository.save(new Personnage("Michel-Ange", "Homme", "Italie", "Europe", "Artiste", "XVIème", "“La sculpture consiste à enlever ce qui est de trop.”"));
            personnageRepository.save(new Personnage("Platon", "Homme", "Grèce", "Europe", "Philosophe", "IVème AVJC", "“Connais-toi toi-même.”"));
            personnageRepository.save(new Personnage("Socrate", "Homme", "Grèce", "Europe", "Philosophe", "Vème AVJC", "“Je sais que je ne sais rien.”"));
            personnageRepository.save(new Personnage("Bouddha", "Homme", "Inde", "Asie", "Religieux/Philosophe", "VIème AVJC", "“La paix vient de l'intérieur. Ne la cherchez pas à l'extérieur.”"));
            personnageRepository.save(new Personnage("Confucius", "Homme", "Chine", "Asie", "Philosophe", "Vème AVJC", "“Apprends à vivre comme si tu devais mourir demain.”"));
            personnageRepository.save(new Personnage("Vincent Van Gogh", "Homme", "Pays-Bas", "Europe", "Artiste", "XIXème", "“Je rêve mes tableaux, et je peins mes rêves.”"));
            personnageRepository.save(new Personnage("Aristote", "Homme", "Grèce", "Europe", "Philosophe/Scientifique", "IVème AVJC", "“L'ignorant affirme, le savant doute, le sage réfléchit.”"));
            personnageRepository.save(new Personnage("Karl Marx", "Homme", "Allemagne", "Europe", "Philosophe", "XIXème", "“Les philosophes n'ont fait qu'interpréter le monde de différentes manières ; ce qui importe, c'est de le transformer.”"));
            personnageRepository.save(new Personnage("Nikola Tesla", "Homme", "Serbie/États-Unis", "Europe/Amérique", "Scientifique/Ingénieur", "XXème", "“Les scientifiques d’aujourd’hui pensent profondément au lieu de clairement. Pour penser clairement, il faut avoir de la clarté dans l’esprit.”"));
            personnageRepository.save(new Personnage("Sigmund Freud", "Homme", "Autriche", "Europe", "Scientifique/Philosophe", "XXème", "“Le moi n’est pas maître dans sa propre maison.”"));
            personnageRepository.save(new Personnage("Jean-Jacques Rousseau", "Homme", "Suisse/France", "Europe", "Philosophe", "XVIIIème", "“L'homme est né libre, et partout il est dans les fers.”"));
            personnageRepository.save(new Personnage("William Shakespeare", "Homme", "Angleterre", "Europe", "Artiste", "XVIème-XVIIème", "“Être ou ne pas être, telle est la question.”"));
            personnageRepository.save(new Personnage("Charles Darwin", "Homme", "Angleterre", "Europe", "Scientifique", "XIXème", "“Ce n’est pas le plus fort de l’espèce qui survit, ni le plus intelligent, mais celui qui s’adapte le mieux au changement.”"));
            personnageRepository.save(new Personnage("Thomas Edison", "Homme", "États-Unis", "Amérique", "Scientifique/Inventeur", "XXème", "“Le génie est un pour cent d’inspiration et quatre-vingt-dix-neuf pour cent de transpiration.”"));
            personnageRepository.save(new Personnage("Victor Hugo", "Homme", "France", "Europe", "Écrivain", "XIXème", "“La liberté commence où l’ignorance finit.”"));
            personnageRepository.save(new Personnage("Jeanne d'Arc", "Femme", "France", "Europe", "Militaire/Religieuse", "XVème", "“Allez de l’avant et Dieu sera avec vous.”"));
            personnageRepository.save(new Personnage("Charles De Gaulle", "Homme", "France", "Europe", "Dirigeant/Militaire", "XXème", "“La grandeur d’un pays ne se mesure pas à son territoire, mais à sa volonté.”"));
            personnageRepository.save(new Personnage("Clovis", "Homme", "France", "Europe", "Dirigeant", "VIème", "“Par ce signe, tu vaincras.”"));
            personnageRepository.save(new Personnage("Charlemagne", "Homme", "France", "Europe", "Dirigeant", "IXème", "“J'aimerais savoir lire.”"));
            personnageRepository.save(new Personnage("Angela Merkel", "Femme", "Allemagne", "Europe", "Dirigeante", "XXIème", "“Nous réussirons.”"));
            personnageRepository.save(new Personnage("Frida Kahlo", "Femme", "Mexique", "Amérique", "Artiste", "XXème", "“Je peins des fleurs pour qu'elles ne meurent pas.”"));


            
            logger.info("Les personnages ont été ajoutés à la base de données.");
        } else {
            logger.info("La base de données contient déjà des personnages.");
        }

        personnageRepository.findAll().forEach(p -> System.out.println("Personnage : " + p.getNom()));

        // Display today's answer in the console
        Personnage reponseDuJour = reponseController.getReponseDuJour();
        System.out.println("La réponse du jour est : " + reponseDuJour.getNom() + ", Domaine: " + reponseDuJour.getDomaine() + ", Genre: " + reponseDuJour.getGenre());
    }
}

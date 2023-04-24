package fr.simplon.sondageapiclient.controller;

import fr.simplon.sondageapiclient.entity.Sondage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collection;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 Cette classe est un contrôleur Spring qui gère les requêtes liées aux sondages.
 */
@Controller
public class SondageApiClientController {

    /**
     Constructeur de la classe qui initialise le RestTemplate.
     */
    private RestTemplate restTemplate;

    public SondageApiClientController(){
        this.restTemplate = new RestTemplate();
    }

    /**
     Méthode de la classe qui traite la requête pour afficher la page d'accueil.
     @param model Le modèle de données à envoyer à la vue.
     @return Le nom de la vue à afficher.
     */
    @GetMapping("/")
    public String index(Model model) {
        String url = "http://localhost:8080/sondages";
        ResponseEntity<Collection<Sondage>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Collection<Sondage>>() {});
        Collection<Sondage> sondages = response.getBody();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);

        // Parcours des sondages et formatage des dates
        for (Sondage sondage : sondages) {
            LocalDate dateCreation = sondage.getDate_creation();
            LocalDate dateCloture = sondage.getDate_cloture();
            String formattedDateCreation = dateCreation.format(formatter);
            String formattedDateCloture = dateCloture.format(formatter);
            sondage.setFormattedDateCreation(formattedDateCreation);
            sondage.setFormattedDateCloture(formattedDateCloture);
        }

        model.addAttribute("sondages", sondages);
        return "index";
    }


    /**
     Méthode de la classe qui traite la requête pour afficher le formulaire de création d'un nouveau sondage.
     @param model Le modèle de données à envoyer à la vue.
     @return Le nom de la vue à afficher.
     */
    @GetMapping("/sondages/formulaire/new")
    public String formSondage(Model model)
    {
        Sondage sondage = new Sondage();
        model.addAttribute("Sondage", sondage);
        model.addAttribute("titre", "Créer un nouveau sondage");
        model.addAttribute("soustitre", "Remplir");

        return "formulaire";
    }


    /**
     Méthode de la classe qui traite la requête pour afficher la page d'édition d'un sondage.
     @param model Le modèle de données à envoyer à la vue.
     @param id L'identifiant du sondage à éditer.
     @return Le nom de la vue à afficher.
     */
    @GetMapping("/sondages/edition/{id}")
    public String updateSondage(Model model, @PathVariable long id){

        String url = "http://localhost:8080/sondages/{id}";
        ResponseEntity<Sondage> response = restTemplate.getForEntity(url, Sondage.class, id);
        Sondage sondage = response.getBody();

        model.addAttribute("sondage", sondage);
        model.addAttribute("titre", "Edition du sondage");

        return "edition";
    }

    /**
     Méthode de la classe qui traite la requête pour supprimer un sondage.
     @param model Le modèle de données à envoyer à la vue.
     @param id L'identifiant du sondage à supprimer.
     @return Le nom de la vue à afficher.
     */
    @GetMapping("/sondages/adios/{id}")
    public String delEleve(Model model, @PathVariable long id)
    {
        String url = "http://localhost:8080/sondages/{id}";
        restTemplate.delete(url, id);

        return "redirect:/";
    }

    /**
     * Ajoute un nouveau sondage en envoyant une requête POST au serveur.
     * @param sondage Le sondage à ajouter.
     * @return Une chaîne de caractères représentant l'url de la vue à afficher après l'ajout du sondage.
     */
    @PostMapping("/sondages/formulaire/new")
    public String addSondage(@ModelAttribute("sondage") Sondage sondage){

        String url = "http://localhost:8080/sondages/formulaire";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Sondage> request = new HttpEntity<>(sondage, headers);
        ResponseEntity<Sondage> response = restTemplate.postForEntity(url, request, Sondage.class);

        return "redirect:/";
    }

    /**
     * Modifie un sondage existant en envoyant une requête PUT au serveur.
     * @param sondage Le sondage à modifier.
     * @param id L'identifiant du sondage à modifier.
     * @return Une chaîne de caractères représentant la vue à afficher après la modification du sondage.
     */
    @PostMapping("/sondages/edition/{id}")
    public String updateSondage(@ModelAttribute("sondage") Sondage sondage, @PathVariable long id){

        String url = "http://localhost:8080/sondages/formulaire/{id}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Sondage> request = new HttpEntity<>(sondage, headers);
        ResponseEntity<Sondage> response = restTemplate.exchange(url, HttpMethod.PUT, request, Sondage.class, id);

        return "redirect:/";
    }

}

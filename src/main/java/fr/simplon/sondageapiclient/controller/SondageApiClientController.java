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

@Controller
public class SondageApiClientController {

    private RestTemplate restTemplate;

    public SondageApiClientController(){
        this.restTemplate = new RestTemplate();
    }

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

    @GetMapping("/sondages/formulaire/new")
    public String formSondage(Model model)
    {
        Sondage sondage = new Sondage();
        model.addAttribute("Sondage", sondage);
        model.addAttribute("titre", "Créer un nouveau sondage");
        model.addAttribute("soustitre", "Remplir");

        return "formulaire";
    }

    @GetMapping("/sondages/edition/{id}")
    public String updateSondage(Model model, @PathVariable long id){

        String url = "http://localhost:8080/sondages/{id}";
        ResponseEntity<Sondage> response = restTemplate.getForEntity(url, Sondage.class, id);
        Sondage sondage = response.getBody();

        model.addAttribute("sondage", sondage);
        model.addAttribute("titre", "Edition du sondage");

        return "edition";
    }
    @GetMapping("/sondages/adios/{id}")
    public String delEleve(Model model, @PathVariable long id)
    {
        String url = "http://localhost:8080/sondages/{id}";
        restTemplate.delete(url, id);

        return "redirect:/";
    }

    @PostMapping("/sondages/formulaire/new")
    public String addSondage(@ModelAttribute("sondage") Sondage sondage){

        String url = "http://localhost:8080/sondages/formulaire";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Sondage> request = new HttpEntity<>(sondage, headers);
        ResponseEntity<Sondage> response = restTemplate.postForEntity(url, request, Sondage.class);

        return "redirect:/";
    }

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

package fr.simplon.sondageapiclient.entity;

import java.time.LocalDate;

/**
 * Entité représentant un sondage.
 */

public class Sondage {

    /**
     * Identifiant unique du sondage.
     */

    private Long id;


    /**
     * Description du sondage.
     */

    private String description;

    /**
     * Question posée dans le sondage.
     */
    private String question;

    /**
     * Date de création du sondage.
     */

    private LocalDate date_creation = LocalDate.now();

    private String formattedDateCreation;

    /**
     * Date de clôture du sondage.
     */

    private LocalDate date_cloture;


    private String formattedDateCloture;
    /**
     * Nom du créateur du sondage.
     */

    private String createur;



    /**
     * Retourne l'identifiant unique du sondage.
     * @return l'identifiant unique du sondage.
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifie l'identifiant unique du sondage.
     * @param id Le nouvel identifiant unique du sondage.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne la description du sondage.
     * @return La description du sondage.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifie la description du sondage.
     * @param description La nouvelle description du sondage.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retourne la question posée dans le sondage.
     * @return La question posée dans le sondage.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Modifie la question posée dans le sondage.
     * @param question La nouvelle question posée dans le sondage
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Retourne la date de création du sondage.
     * @return La date de création du sondage.
     */
    public LocalDate getDate_creation() {
        return date_creation;
    }

    /**
     * Modifie la date de création du sondage.
     * @param date_creation La date de création du sondage
     */
    public void setDate_creation(LocalDate date_creation) {
        this.date_creation = date_creation;
    }


    public void setFormattedDateCreation(String formattedDateCreation) {
        this.formattedDateCreation = formattedDateCreation;
    }
    public String getFormattedDateCreation() {
        return formattedDateCreation;
    }
    public void setFormattedDateCloture(String formattedDateCloture) {
        this.formattedDateCloture = formattedDateCloture;
    }
    public String getFormattedDateCloture() {
        return formattedDateCloture;
    }
    /**
     * Retourne la date de cloture du sondage.
     * @return La date de cloture du sondage.
     */
    public LocalDate getDate_cloture() {
        return date_cloture;
    }

    /**
     * Modifie la date de cloture du sondage.
     * @param date_cloture La date de cloture du sondage
     */
    public void setDate_cloture(LocalDate date_cloture) {
        this.date_cloture = date_cloture;
    }

    /**
     * Retourne le créateur du sondage.
     * @return Le créateur du sondage.
     */
    public String getCreateur() {
        return createur;
    }

    /**
     * Modifie le créateur du sondage.
     * @param  createur Le createur du sondage.
     */
    public void setCreateur(String createur) {
        this.createur = createur;
    }
}
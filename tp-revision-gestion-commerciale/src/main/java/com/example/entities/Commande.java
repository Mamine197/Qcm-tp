package com.example.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_commande")
    private LocalDate dateCommande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CommandeProduit> lignes = new ArrayList<>();

    public Commande() {
        this.dateCommande = LocalDate.now();
    }

    public Commande(Client client) {
        this.client = client;
        this.dateCommande = LocalDate.now();
    }

    public void addProduit(Produit p, int quantite) {
        CommandeProduit cp = new CommandeProduit(this, p, quantite);
        lignes.add(cp);
    }

    public void removeProduit(CommandeProduit cp) {
        lignes.remove(cp);
        cp.setCommande(null);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<CommandeProduit> getLignes() {
        return lignes;
    }

    public void setLignes(List<CommandeProduit> lignes) {
        this.lignes = lignes;
    }
}

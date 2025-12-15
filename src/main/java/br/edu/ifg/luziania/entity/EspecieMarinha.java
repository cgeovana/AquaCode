package br.edu.ifg.luziania.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "especies_marinhas")
public class EspecieMarinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeComum;

    @Column(nullable = false, unique = true, length = 150)
    private String nomeCientifico;

    @Column(length = 50)
    private String reino;

    @Column(length = 50)
    private String filo;

    @Column(length = 50)
    private String classe;

    @Column(length = 50)
    private String ordem;

    @Column(length = 50)
    private String familia;

    @Column(length = 50)
    private String genero;

    @Column(length = 2000)
    private String descricao;

    @Column(length = 200)
    private String habitat;

    @Column(length = 500)
    private String distribuicaoGeografica;

    @Column(length = 200)
    private String dieta;

    @Column(length = 50)
    private String statusConservacao; // LC, NT, VU, EN, CR, EW, EX

    @Column(length = 500)
    private String imagemUrl;

    @Column(length = 100)
    private String categoria; // Peixe, Mamífero, Invertebrado, Alga, Réptil, etc

    @Column(length = 1000)
    private String caracteristicasDistintivas;

    // Construtores
    public EspecieMarinha() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeComum() {
        return nomeComum;
    }

    public void setNomeComum(String nomeComum) {
        this.nomeComum = nomeComum;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public String getReino() {
        return reino;
    }

    public void setReino(String reino) {
        this.reino = reino;
    }

    public String getFilo() {
        return filo;
    }

    public void setFilo(String filo) {
        this.filo = filo;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getDistribuicaoGeografica() {
        return distribuicaoGeografica;
    }

    public void setDistribuicaoGeografica(String distribuicaoGeografica) {
        this.distribuicaoGeografica = distribuicaoGeografica;
    }

    public String getDieta() {
        return dieta;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    public String getStatusConservacao() {
        return statusConservacao;
    }

    public void setStatusConservacao(String statusConservacao) {
        this.statusConservacao = statusConservacao;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCaracteristicasDistintivas() {
        return caracteristicasDistintivas;
    }

    public void setCaracteristicasDistintivas(String caracteristicasDistintivas) {
        this.caracteristicasDistintivas = caracteristicasDistintivas;
    }
}

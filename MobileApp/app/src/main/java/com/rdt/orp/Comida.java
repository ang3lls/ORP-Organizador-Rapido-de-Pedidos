package com.rdt.orp;

public class Comida {
    private String nomePrato;
    private float precoPrato;
    private int tipoPrato;

    public Comida(String nomePrato, float precoPrato, int tipoPrato) {
        this.nomePrato = nomePrato;
        this.precoPrato = precoPrato;
        this.tipoPrato = tipoPrato;
    }

    public String getNomePrato() {
        return nomePrato;
    }

    public void setNomePrato(String nomePrato) {
        this.nomePrato = nomePrato;
    }

    public float getPrecoPrato() {
        return precoPrato;
    }

    public void setPrecoPrato(float precoPrato) {
        this.precoPrato = precoPrato;
    }

    public int getTipoPrato() {
        return tipoPrato;
    }

    public void setTipoPrato(int tipoPrato) {
        this.tipoPrato = tipoPrato;
    }
}

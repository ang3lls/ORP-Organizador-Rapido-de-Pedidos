package com.rdt.orp;

public class Comida {
    private String nomaPrato;
    private float precoPrato;
    private int tipoPrato;

    public Comida(String nomaPrato, float precoPrato, int tipoPrato) {
        this.nomaPrato = nomaPrato;
        this.precoPrato = precoPrato;
        this.tipoPrato = tipoPrato;
    }

    public String getNomaPrato() {
        return nomaPrato;
    }

    public void setNomaPrato(String nomaPrato) {
        this.nomaPrato = nomaPrato;
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

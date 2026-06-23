package com.damas.objetos;

//Representa uma célula individual (quadrante) da matriz do tabuleiro de damas.
 //Gerencia dinamicamente a presença de qualquer objeto que assine a interface Peca.
 
public class Casa {

    private final int x;
    private final int y;
    private Peca peca;

    public Casa(int x, int y) {
        this.x = x;
        this.y = y;
        this.peca = null;
    }
    
    
    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    //Limpa a ocupação atual da célula, podendo gerar novos movimentos.
    public void removerPeca() {
        this.peca = null;
    }
    
    
    public Peca getPeca() {
        return peca;
    }
    
    
    public boolean possuiPeca() {
        return peca != null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
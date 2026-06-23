package com.damas.objetos;

// Define as cores das peças e o vetor direcional de movimentação padrão no tabuleiro.
// BRANCA: Avança incrementando o eixo Y (de baixo para cima).
// VERMELHA: Avança decrementando o eixo Y (de cima para baixo).
public enum Cor {
    BRANCA(1),
    VERMELHA(-1);

    private int sentidoMovimento;

    Cor(int sentidoMovimento) {
        this.sentidoMovimento = sentidoMovimento;
    }

    public int getSentidoMovimento() {
        return sentidoMovimento;
    }
}

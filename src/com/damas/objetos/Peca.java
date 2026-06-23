package com.damas.objetos;

// Abstração de Contrato que rege os comportamentos obrigatórios de qualquer peça inserida no tabuleiro.
public interface Peca {
    // Aplica a transição física da peça para uma nova coordenada.
    void moverPara(Casa destino);

    // Analisa se as propriedades matemáticas da jogada respeitam a geometria do movimento da peça.
    boolean isMovimentoValido(Casa destino);

    // Valida movimentos simples de deslocamento (quando não há capturas envolvidas).
    boolean podeMoverSemCaptura(Casa destino);

    Cor getCor();

    Tipo getTipo();
}
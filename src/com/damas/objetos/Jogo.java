package com.damas.objetos;

import java.util.ArrayList;

// Máquina de estado principal da partida. 
// Aqui eu intermediei o fluxo de turnos, computação de pontuação, capturas em cadeia e promoção de peças.
public class Jogo {

    private Tabuleiro tabuleiro;
    private Jogador jogadorUm;
    private Jogador jogadorDois;
    private int vezAtual = 1;
    private int jogadas = 0;
    private int jogadasSemComerPeca = 0; // Utilizado para detectar possíveis empates por falta de atividade
    private Casa casaBloqueadaOrigem; // // Restringe a jogada caso haja uma captura consecutiva pendente (Double Jump)

    public Jogo() {
        tabuleiro = new Tabuleiro();
        jogadorUm = new Jogador("player branco", Cor.BRANCA);
        jogadorDois = new Jogador("player vermelho", Cor.VERMELHA);
        

        vezAtual = 1;
        jogadas = 0;
        jogadasSemComerPeca = 0;
        casaBloqueadaOrigem = null;

        tabuleiro.colocarPecas();
    }
    // Aqui se processa a tentativa de movimentação de uma peça no tabuleiro
    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Casa origem = tabuleiro.getCasa(origemX, origemY);
        Casa destino = tabuleiro.getCasa(destinoX, destinoY);
        Peca peca = origem.getPeca();

        if (casaBloqueadaOrigem == null) {
        // Valida o turno corrente em relação ao dono da peça
            if ((getVez() == 1 && jogadorUm.controla(peca)) ||
                (getVez() == 2 && jogadorDois.controla(peca))) {

                if (peca.isMovimentoValido(destino)) {
                    if (tabuleiro.simularMovimentoEValidar(origem, destino)) {
                        peca.moverPara(destino);

                        if (tabuleiro.getPecasAComer().size() > 0) {
                            comerPecas();
                            //Verifica se a peça atual possui capturas adicionais obrigatórias
                            if (tabuleiro.deveContinuarJogando(destino)) {
                                casaBloqueadaOrigem = destino;
                            } else {
                                trocarDeVez();
                            }
                        } else {
                            jogadasSemComerPeca++;
                            trocarDeVez();
                        }

                        jogadas++;
                        tabuleiro.transformarPedraParaDama(destino);
                    }
                }
            }
        } else {
            // Cláusula de proteção: força o jogador a continuar atacando utilizando a mesma peça bloqueada
            if (origem.equals(casaBloqueadaOrigem)) {
                if (tabuleiro.simularMovimentoEValidar(origem, destino)) {
                    if (tabuleiro.getPecasAComer().size() != 0) {
                        casaBloqueadaOrigem = null;
                        moverPeca(origemX, origemY, destinoX, destinoY);
                    }
                }
            }
        }
    }

    // Consolida a eliminação das peças capturadas, atualizando a pontuação do atacante.
    private void comerPecas() {
        ArrayList<Casa> pecasAComer = tabuleiro.getPecasAComer();
        int pecasComidas = pecasAComer.size();

        if (getVez() == 1) jogadorUm.addPonto(pecasComidas);
        if (getVez() == 2) jogadorDois.addPonto(pecasComidas);

        for (Casa casa : pecasAComer) {
            casa.removerPeca();
        }

        pecasAComer.clear();
        jogadasSemComerPeca = 0;
    }

    public void trocarDeVez() {
        if (vezAtual == 1) {
            vezAtual = 2;
        } else {
            vezAtual = 1;
        }
    }

    public int getGanhador() {
        if (jogadorUm.getPontos() == 12) return 1;
        if (jogadorDois.getPontos() == 12) return 2;
        return 0;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public void setJogadorUm(Jogador jogador) {
        jogadorUm = jogador;
    }

    public void setJogadorDois(Jogador jogador) {
        jogadorDois = jogador;
    }

    public Jogador getJogadorUm() {
        return jogadorUm;
    }

    public Jogador getJogadorDois() {
        return jogadorDois;
    }

    public int getVez() {
        return vezAtual;
    }

    public int getJogadasSemComerPecas() {
        return jogadasSemComerPeca;
    }

    public int getJogada() {
        return jogadas;
    }

    public Casa getCasaBloqueada() {
        return casaBloqueadaOrigem;
    }
}
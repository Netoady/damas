package com.damas.objetos;

import java.util.ArrayList;

/**
 * Gerenciador de malha matricial (8x8).
 * Responsável pelo mapeamento posicional físico, população inicial do Grid,
 * coroação de damas e validação física/espacial de trajetórias.
 */
public class Tabuleiro {

    private static final int MAX_LINHAS = 8;
    private static final int MAX_COLUNAS = 8;

    private Casa[][] casas;
    private ArrayList<Casa> pecasAComer; // Centralizado no Tabuleiro agora

    public Tabuleiro() {
        pecasAComer = new ArrayList<>();
        montarTabuleiro();
    }

    private void montarTabuleiro() {
        casas = new Casa[MAX_LINHAS][MAX_COLUNAS];

        for (int x = 0; x < MAX_LINHAS; x++) {
            for (int y = 0; y < MAX_COLUNAS; y++) {
                casas[x][y] = new Casa(x, y);
            }
        }
    }

    public void colocarPecas() {
        for (int x = 0; x < MAX_LINHAS; x++) {
            for (int y = 0; y < 3; y++) {
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
                    new Pedra(getCasa(x, y), Cor.BRANCA);
                }
            }
        }

        for (int x = 0; x < MAX_LINHAS; x++) {
            for (int y = 5; y < MAX_COLUNAS; y++) {
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
                    new Pedra(getCasa(x, y), Cor.VERMELHA);
                }
            }
        }
    }

    public void transformarPedraParaDama(Casa casa) {
        Peca peca = casa.getPeca();

        if (peca == null || peca.getTipo() != Tipo.PEDRA) return;

        boolean pedraBrancaNoLimite = peca.getCor() == Cor.BRANCA && casa.getY() == MAX_COLUNAS - 1;
        boolean pedraVermelhaNoLimite = peca.getCor() == Cor.VERMELHA && casa.getY() == 0;

        if (pedraBrancaNoLimite || pedraVermelhaNoLimite) {
            new Dama(casa, peca.getCor());
        }
    }

    /**
     * Valida a trajetória geométrica na matriz e calcula se há peças a serem capturadas.
     */
    public boolean simularMovimentoEValidar(Casa origem, Casa destino) {
        Peca peca = origem.getPeca();
        int casasComPecaSeguidas = 0;

        pecasAComer.clear();

        if (peca == null || destino.getPeca() != null) return false;
        if (!peca.isMovimentoValido(destino)) return false;//Olha o poli agindo!!

        int sentidoX = destino.getX() - origem.getX();
        int sentidoY = destino.getY() - origem.getY();
        int distanciaX = Math.abs(sentidoX);
        int distanciaY = Math.abs(sentidoY);

        if (distanciaX == 0 || distanciaY == 0 || distanciaX != distanciaY) return false;

        sentidoX = sentidoX / distanciaX;
        sentidoY = sentidoY / distanciaY;

        int i = origem.getX();
        int j = origem.getY();

        while (!((i == destino.getX()) || (j == destino.getY()))) {
            i += sentidoX;
            j += sentidoY;

            Casa alvo = getCasa(i, j);
            Peca pecaAlvo = alvo.getPeca();

            if (pecaAlvo != null) {
                casasComPecaSeguidas++;

                if (pecaAlvo.getCor() == peca.getCor()) {
                    pecasAComer.clear();
                    return false;
                }
            } else {
                if (casasComPecaSeguidas == 1) {
                    Casa casa = getCasa(alvo.getX() - sentidoX, alvo.getY() - sentidoY);
                    pecasAComer.add(casa);
                }
                casasComPecaSeguidas = 0;
            }

            if (casasComPecaSeguidas == 2) {
                pecasAComer.clear();
                return false;
            }
        }

        if (pecasAComer.isEmpty() && !peca.podeMoverSemCaptura(destino)) return false;

        return true;
    }

    private boolean percorrerEVerificar(Casa origem, int deltaX, int deltaY) {
        int x = origem.getX() + deltaX;
        int y = origem.getY() + deltaY;

        while (posicaoValida(x, y)) {
            Casa destino = getCasa(x, y);

            if (simularMovimentoEValidar(origem, destino) && !pecasAComer.isEmpty()) {
                pecasAComer.clear();
                return true;
            }

            pecasAComer.clear();
            x += deltaX;
            y += deltaY;
        }

        return false;
    }

    /**
     * Verifica as adjacências para determinar a existência de capturas consecutivas mandatórias.
     */
    public boolean deveContinuarJogando(Casa origem) {
        int[][] direcoesDiagonais = {
            {-1, 1}, {1, 1}, {1, -1}, {-1, -1}
        };

        for (int[] direcao : direcoesDiagonais) {
            if (percorrerEVerificar(origem, direcao[0], direcao[1])) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Casa> getPecasAComer() {
        return pecasAComer;
    }

    public boolean posicaoValida(int x, int y) {
        return x >= 0 && x < MAX_LINHAS && y >= 0 && y < MAX_COLUNAS;
    }

    public Casa getCasa(int x, int y) {
        return casas[x][y];
    }

    public int getMaxLinhas() {
        return MAX_LINHAS;
    }

    public int getMaxColunas() {
        return MAX_COLUNAS;
    }
}
package com.damas.objetos;


 //Sobrescreve as regras de movimentação para permitir deslocamentos sem restrição de distância vetorial.
 public class Dama extends Abstrata {

    public Dama(Casa casa, Cor cor) {
        super(casa, cor);
    }

    @Override
    public boolean isMovimentoValido(Casa destino) {
        // Valida se o deslocamento ocorre estritamente em uma diagonal perfeita
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = Math.abs(destino.getY() - casa.getY());

        return distanciaX == distanciaY;
    }

    @Override
    public boolean podeMoverSemCaptura(Casa destino) {
        return isMovimentoValido(destino);
    }

    @Override
    public Tipo getTipo() {
        return Tipo.DAMA;
    }
}
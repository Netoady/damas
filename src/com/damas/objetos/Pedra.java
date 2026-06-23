package com.damas.objetos;

/**
 * Componente especializado que representa uma peça comum (Pedra).
 * Restringe suas ações a passos curtos bidirecionais e avanço simples unidirecional.
 */
public class Pedra extends Abstrata {

    public Pedra(Casa casa, Cor cor) {
        super(casa, cor);
    }

    @Override
    public boolean isMovimentoValido(Casa destino) {
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = Math.abs(destino.getY() - casa.getY());

        // Recusa movimentos que fiquem sobre os eixos ortogonais planos
        if (distanciaX == 0 || distanciaY == 0) 
            return false;

        return distanciaX <= 2 && distanciaX == distanciaY;
    }

    @Override
    public boolean podeMoverSemCaptura(Casa destino) {
        int distanciaX = Math.abs(destino.getX() - casa.getX());
        int distanciaY = destino.getY() - casa.getY();

        // Garante avanço de apenas 1 casa casando com o sinal do sentido da cor (+1 ou -1)
        return distanciaX == 1 && Math.abs(distanciaY) == 1
                && Integer.signum(distanciaY) == cor.getSentidoMovimento();
    }

    @Override
    public Tipo getTipo() {
        return Tipo.PEDRA;
    }
}
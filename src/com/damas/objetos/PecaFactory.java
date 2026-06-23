package com.damas.objetos;

public class PecaFactory {

    /**
    * Fábrica estática encarregada da criação e instanciação das peças.
    * Centraliza a construção de objetos complexos e isola o acoplamento direto com as subclasses.
    */
    public static Peca criarPedra(Casa casa, Cor cor) {
        return new Pedra(casa, cor);
    }

    
    public static Peca criarDama(Casa casa, Cor cor) {
        return new Dama(casa, cor);
    }

    /**
     * Mapeador utilitário de ID numérico para garantir retrocompatibilidade com 
     * renderizadores antigos da Camada de Interface Gráfica (GUI).
     */
    public static int getTipoNumerico(Peca peca) {
        if (peca == null) return -1; // Sem peça
        
        if (peca instanceof Dama) {
            return peca.getCor() == Cor.BRANCA ? 1 : 3; // 1 = Dama Branca, 3 = Dama Vermelha
        } else {
            return peca.getCor() == Cor.BRANCA ? 0 : 2; // 0 = Pedra Branca, 2 = Pedra Vermelha
        }
    }
}
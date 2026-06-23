package com.damas.objetos;


 //Estrutura base abstrata para compartilhamento de estado e lógica de movimentação das peças.
 //Garante a consistência do vínculo bidirecional entre a peça e sua respectiva casa.
 
public abstract class Abstrata implements Peca {

    protected Casa casa;
    protected Cor cor;

    public Abstrata(Casa casa, Cor cor) {
        this.casa = casa;
        this.cor = cor;
        casa.setPeca(this); // Registra a peça recém-criada na célula de destino
    }

    @Override
    public void moverPara(Casa destino) {
        casa.removerPeca(); // Desvincula da célula antiga
        destino.setPeca(this); // Associa à nova célula
        casa = destino; // Atualiza a referência interna da peça
    }

    @Override
    public Cor getCor() {
        return cor;
    }
}
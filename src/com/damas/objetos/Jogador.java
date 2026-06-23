package com.damas.objetos;

// Aqui eu monitoro as credenciais do competidor, sua respectiva pontuação e a posse das peças durante a partida.
public class Jogador {

    public static final String DEFAULT_NAME = "Anônimo";

    private String nome;
    private int pontos;
    private Cor cor;

    public Jogador(String nome, Cor cor) {
        if (validarNome(nome)) {
            this.nome = nome;
        } else {
            this.nome = DEFAULT_NAME;
        }

        this.cor = cor;
        pontos = 0;
    }
    // Pra garantir que o nome do jogador não extrapole o limite do layout visual (16 caracteres)
    private boolean validarNome(String nome) {
        if (nome.length() > 16) return false;
        return true;
    }
    // Pra verificar se a peça selecionada pertence à respectiva cor atribuída ao jogador.
    public boolean controla(Peca peca) {
        return peca.getCor() == cor;
    }

    public void addPonto() {
        pontos++;
    }

    public void addPonto(int pontos) {
        this.pontos += pontos;
    }

    public int getPontos() {
        return pontos;
    }

    public String getNome() {
        return nome;
    }

    public Cor getCor() {
        return cor;
    }

    public void setNome(String nome) {
        validarNome(nome);
    }
}
package com.claretiano.estoque.request;

public class MensagemRequestDTO {
    private String mensagem;

    public MensagemRequestDTO(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}

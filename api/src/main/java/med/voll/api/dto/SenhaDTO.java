package med.voll.api.dto;

public class SenhaDTO {

    private String senha;
    private String medico;

    public SenhaDTO(String senha, String medico) {
        this.senha = senha;
        this.medico = medico;
    }

    public String getSenha() { return senha; }
    public String getMedico() { return medico; }
}

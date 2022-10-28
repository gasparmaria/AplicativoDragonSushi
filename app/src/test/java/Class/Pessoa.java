package Class;

public class Pessoa {
    private Integer idPessoa, ocupacao;
    private String nomePessoa, telefone, cpf;


    public Integer getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Integer idUsuario) {
        this.idPessoa = idUsuario;
    }

    public Integer getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(Integer ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}

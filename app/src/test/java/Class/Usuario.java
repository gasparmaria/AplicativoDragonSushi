package Class;

public class Usuario {
    private  Integer idUsuario, fkPessoa;
    private String login, senha;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getFkPessoa() {
        return fkPessoa;
    }

    public void setFkPessoa(Integer fkPessoa) {
        this.fkPessoa = fkPessoa;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



}

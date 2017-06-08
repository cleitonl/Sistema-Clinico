package Fpb.SistemaClinico.Model;

public class Medico {
    private int id;
    private String nome;
    private String crm;
    private int Especialidade;
    private String nomeEspecialidade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public int getEspecialidade() {
        return Especialidade;
    }

    public void setEspecialidade(int nomeEspecialidade) {
        this.Especialidade = nomeEspecialidade;
    }

    public String getNomeEspecialidade() {
        return nomeEspecialidade;
    }

    public void setNomeEspecialidade(String nomeEspecialidade) {
        this.nomeEspecialidade = nomeEspecialidade;
    }

}


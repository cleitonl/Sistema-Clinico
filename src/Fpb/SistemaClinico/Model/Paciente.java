package Fpb.SistemaClinico.Model;

public class Paciente {
    private int id;
    private String nome;
    private String rg;
    private String cpf;
    private String endereco;
    private String telefone;
    private String sexo;
    private String nomeconvenio;
    private int conveniopaciente;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEnd() {
        return endereco;
    }

    public void setEnd(String end) {
        this.endereco = end;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConveniopaciente() {
        return conveniopaciente;
    }

    public void setConveniopaciente(int conveniopaciente) {
        this.conveniopaciente = conveniopaciente;
    }

    public String getNomeconvenio() {
        return nomeconvenio;
    }

    public void setNomeconvenio(String nomeconvenio) {
        this.nomeconvenio = nomeconvenio;
    }
}

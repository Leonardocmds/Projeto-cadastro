package br.com.caelum.livraria.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="cpf")
	private String cpf;
	
	@Column(name="endereco")
	private String endereco;
	
	@Column(name="idade")
	private int idade;
	

	
	
	public Pessoa(String nome, String cpf, String endereco, int idade) {
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.idade = idade;
	}

	
	
	public Pessoa(Integer id, String nome, String cpf, String endereco, int idade) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.idade = idade;
	}



	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Pessoa() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	@Override
	public String toString() {
		return "Nome: " + nome + ", CPF: " + cpf + ", END: " + endereco + ", Idade: " + idade;
	}

	@Override
	public boolean equals(Object ref) {
		Pessoa outra = (Pessoa) ref;
		return cpf.equals(outra.cpf);
	}

}

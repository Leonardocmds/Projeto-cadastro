package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.RollbackException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Pessoa;
import br.com.caelum.livraria.util.Util;

@ManagedBean
@SessionScoped
public class PessoaBean {

	private Pessoa pessoa;

	public PessoaBean() {
		pessoa = new Pessoa();
	}

	public List<Pessoa> getPessoas() {
		return new DAO<Pessoa>(Pessoa.class).listaTodos();
	}

	public void gravar() {

		Util util = new Util();
		DAO<Pessoa> dao = new DAO<Pessoa>(Pessoa.class);
		System.out.println("Gravando pessoa" + this.pessoa.getNome());

		if (util.validaNomeComNumero(pessoa.getNome())) {
			this.pessoa.setNome("");
			FacesContext.getCurrentInstance().addMessage("j_idt11:nome",
					new FacesMessage("O campo não pode conter números."));
			return;
		}

		if (util.validaNomeCom3Letras(pessoa.getNome())) {
			this.pessoa.setNome("");
			FacesContext.getCurrentInstance().addMessage("j_idt11:nome",
					new FacesMessage("O campo deve conter mais de 2 letras."));
			return;
		}

		if (!util.validaCpf(pessoa.getCpf())) {
			this.pessoa.setCpf("");
			FacesContext.getCurrentInstance().addMessage("j_idt11:cpf", new FacesMessage("O campo está incorreto"));
			return;
		}

		if (pessoa.getIdade() < 18) {
			this.pessoa.setIdade(0);
			FacesContext.getCurrentInstance().addMessage("j_idt11:idade",
					new FacesMessage("Idade permitida minima: 18 anos"));
			return;
		}

		if (pessoa.getId() == null) {
			List<Pessoa> listaDePessoas = dao.listaTodos();
			for (Pessoa item : listaDePessoas) {
				if (item.getCpf().equals(pessoa.getCpf())) {
					this.pessoa.setCpf("");
					FacesContext.getCurrentInstance().addMessage("j_idt11:cpf",
							new FacesMessage("Este CPF já está cadastrado no sistema."));
					return;
				}
			}

			new DAO<Pessoa>(Pessoa.class).adiciona(this.pessoa);
		} else {
			try {
				new DAO<Pessoa>(Pessoa.class).atualiza(this.pessoa);

			} catch (RollbackException ex) {
				this.pessoa.setCpf("");
				FacesContext.getCurrentInstance().addMessage("j_idt11:cpf",
						new FacesMessage("Este CPF já está cadastrado no sistema."));
				return;
			}

		}
		this.pessoa = new Pessoa();

	}

	public String redirecionar() {
		this.pessoa = new Pessoa();
		return "cadastro?faces-redirect=true";
	}

	public void remover(Pessoa pessoa) {
		System.out.println("Removendo pessoa");
		new DAO<Pessoa>(Pessoa.class).remove(pessoa);
	}

	public String carregar(Pessoa pessoa) {
		this.pessoa = pessoa;
		return "cadastro?faces-redirect=true";

	}

	public String buscarCliente() {
		return "dados?faces-redirect=true";
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}

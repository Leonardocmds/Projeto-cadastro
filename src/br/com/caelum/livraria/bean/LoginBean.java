package br.com.caelum.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@SessionScoped
public class LoginBean {

	private Usuario usuario;

	public LoginBean() {
		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String efetuaLogin() {
		System.out.println("entrando");
		FacesContext context = FacesContext.getCurrentInstance();

		boolean estaCadastrado = new UsuarioDao().estaCadastrado(this.usuario);

		if (estaCadastrado) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "cadastro?faces-redirect=true";
		}

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));

		return "login?faces-redirect=true";
	}

	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");

		this.usuario = new Usuario();

		return "login?faces-redirect=true";

	}
}

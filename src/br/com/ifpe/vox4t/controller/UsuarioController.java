package br.com.ifpe.vox4t.controller;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ifpe.vox4t.dao.UsuarioDAO;
import br.com.ifpe.vox4t.model.Usuario;

/**
 * @Author: henrique
 */

@Controller
public class UsuarioController {

	@RequestMapping("/usuario/cadastro")
	public String cadastroUsuario() {

		return "usuario/cadastro";
	}

	@RequestMapping("/usuario/save")
	public String save(Usuario usuario, Model attr) {

		UsuarioDAO dao = new UsuarioDAO();
		dao.salvar(usuario);

		attr.addAttribute("msgSucesso", "Usuario adcionado com sucesso."); // Envia string msg para o html.

		return "usuario/cadastro";
	}
	
	
	@RequestMapping("/usuario/login")
	public String loginUsuario() {

		return "usuario/loginModal";
	}

	@RequestMapping("loginCheck")
	public String loginCheck(@RequestParam("email") String emailUsuario,@RequestParam("senha") String senhaUsuario, Model attr, HttpSession session) {
		
		
		boolean result = false;
		UsuarioDAO dao = new UsuarioDAO();
		result = dao.logar(emailUsuario,senhaUsuario);
		
		if (result == true) {
			attr.addAttribute("msg", "Usuario Logado com sucesso."); // Envia string msg para o html.
			Usuario usuario = dao.buscarPorEmail(emailUsuario);
			session.setAttribute("usuarioLogado", usuario);
			return "logado";
		}
		else {
			attr.addAttribute("msg", "Usuario ou senha incorretos."); // Envia string msg para o html.
			return "logado";
		}
	}

}

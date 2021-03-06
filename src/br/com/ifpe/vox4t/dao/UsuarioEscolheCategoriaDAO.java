package br.com.ifpe.vox4t.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;


import br.com.ifpe.vox4t.model.Categoria;
import br.com.ifpe.vox4t.model.Usuario;
import br.com.ifpe.vox4t.model.UsuarioEscolheCategoria;

/**
	@Author: rique
*/

public class UsuarioEscolheCategoriaDAO {
	
	
	private static final String PERSISTENCE_UNIT = "vox4t";
	
	public boolean salvar(int idUsuario, int idCategoria) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager manager = factory.createEntityManager();
		
		UsuarioEscolheCategoria userchoose = new UsuarioEscolheCategoria();
		UsuarioDAO daoU = new UsuarioDAO();
		CategoriaDAO daoC = new CategoriaDAO();
		
		Usuario user = daoU.buscarPorId(idUsuario);
		Categoria cat = daoC.buscarPorId(idCategoria);
		
		userchoose.setIdCategoria(cat);
		userchoose.setIdUsuario(user);
		
		if(this.buscarPorids(idUsuario, idCategoria) == null){

			try {
				manager.getTransaction().begin();
				manager.persist(userchoose);
				manager.getTransaction().commit();
				
				manager.close();
				factory.close();
				
				return true;

			}catch(Exception e) {
				return false;
			}

		}
		
		return false;
					
	}
	
	
	/*public UsuarioEscolheCategoria buscarPorId(int idUsuario, int idCategoria) {
		
	}*/
	
	
	public Boolean remover(int idUsuario, int idCategoria) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager manager = factory.createEntityManager();
		UsuarioEscolheCategoria usuarioEscolheCategoria = this.buscarPorids(idUsuario, idCategoria);
		
		UsuarioEscolheCategoria usuarioEscolheCategoriRemover  = manager.find(UsuarioEscolheCategoria.class, usuarioEscolheCategoria.getId());
		
		try {
			manager.getTransaction().begin();
			manager.remove(usuarioEscolheCategoriRemover);
			manager.getTransaction().commit();
			
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		
	}
	
	
	
	public UsuarioEscolheCategoria buscarPorids(int idUsuario, int idCategoria) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager manager = factory.createEntityManager();
		
		UsuarioEscolheCategoria obj = null;
		
		
		Query query = null;
		query = manager.createQuery("FROM UsuarioEscolheCategoria WHERE idUsuario.id = :paramIdUsuario and idCategoria.id = :paramIdCategoria");
		query.setParameter("paramIdUsuario", idUsuario);
		query.setParameter("paramIdCategoria", idCategoria);
		
		try {
			obj = (UsuarioEscolheCategoria) query.getSingleResult();
		}catch(NoResultException nre) {
			return null;
		}
		
		return obj;

	   }
	
	public List<UsuarioEscolheCategoria> listar(int id) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		EntityManager manager = factory.createEntityManager();
		Query query = manager.createQuery("FROM UsuarioEscolheCategoria WHERE idUsuario.id LIKE :paramIdUsuario ORDER BY idCategoria");
		query.setParameter("paramIdUsuario", id);
		
		List<UsuarioEscolheCategoria> lista = query.getResultList();

		manager.close();
		factory.close();

		return lista;
	    }

}
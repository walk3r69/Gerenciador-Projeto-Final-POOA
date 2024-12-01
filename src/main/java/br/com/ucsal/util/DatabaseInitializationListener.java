package br.com.ucsal.util;

import br.com.ucsal.model.Produto;
import br.com.ucsal.persistencia.MemoriaProdutoRepository;
import br.com.ucsal.persistencia.ProdutoRepository;
import br.com.ucsal.service.ProdutoService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DatabaseInitializationListener implements ServletContextListener {

	// Método executado ao iniciar o contexto da aplicação
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Iniciando o banco de dados HSQLDB...");

		// Obtém a instância do repositório
		ProdutoRepository<Produto, Integer> produtoRepository = MemoriaProdutoRepository.getInstancia();

		// Passa o repositório para o serviço
		ProdutoService produtoService = new ProdutoService(produtoRepository);

		// Este método serve para adicionar um produto usando o serviço, neste caso estarei inicializando diversos produtos
		produtoService.adicionarProduto("Camisa do Bahia", 300.00);
		produtoService.adicionarProduto("Apple Watch Series 10", 5499.00);
		produtoService.adicionarProduto("Teclado Mecanico 60%", 183.09);
		produtoService.adicionarProduto("Camisa III do Vasco", 218.49);
		produtoService.adicionarProduto("Computador", 8999.99);
	}

	// Método executado ao finalizar o contexto da aplicação
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Aplicação sendo finalizada.");
	}
}
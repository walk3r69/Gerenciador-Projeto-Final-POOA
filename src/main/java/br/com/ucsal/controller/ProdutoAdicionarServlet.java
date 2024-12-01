package br.com.ucsal.controller;

import java.io.IOException;
import java.io.Serializable;

import br.com.ucsal.anotacoes.Inject;
import br.com.ucsal.anotacoes.Rota;
import br.com.ucsal.anotacoes.logic.DependencyInjector;
import br.com.ucsal.service.ProdutoService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet para adicionar produtos. Processa requisições HTTP GET e POST para
 * exibir o formulário e salvar o produto.
 */
@Rota(value = "/adicionarProdutos") // Define a rota para a URL de adicionar produtos
public class ProdutoAdicionarServlet implements Command, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject // Dependência injetada para manipulação de produtos
    private ProdutoService produtoService;

    //Construtor que executa a injeção de dependências. 
    public ProdutoAdicionarServlet() {
        DependencyInjector.injectDependencies(this);
    }

    // Método principal que decide qual método (GET ou POST) chamar com base no tipo de requisição
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getMethod();

        // Redireciona para o método adequado com base no verbo HTTP
        if ("GET".equalsIgnoreCase(method)) {
            // Se for GET, chama o método doGet para mostrar o formulário de adição de produto
            doGet(request, response);
        } else if ("POST".equalsIgnoreCase(method)) {
            // Se for POST, chama o método doPost para adicionar o produto
            doPost(request, response);
        }
    }

    //Exibe o formulário para adicionar um produto.
    private void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redireciona o usuário para a página do formulário de adição de produto
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/produtoformulario.jsp");
        dispatcher.forward(request, response);
    }

    //Processa a submissão do formulário de adição de produto.
    private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera os parâmetros enviados no formulário
        String nome = request.getParameter("nome");
        double preco = Double.parseDouble(request.getParameter("preco"));

        // Chama o serviço para adicionar o novo produto
        produtoService.adicionarProduto(nome, preco);

        // Exibe uma mensagem de sucesso no console com os detalhes do produto adicionado
        System.out.println("✨ Produto adicionado com sucesso: " + nome + " - R$" + preco);

        // Redireciona para a lista de produtos após a adição
        response.sendRedirect("listarProdutos");
    }
}

package br.com.ucsal.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import br.com.ucsal.anotacoes.Inject;
import br.com.ucsal.anotacoes.Rota;
import br.com.ucsal.anotacoes.logic.DependencyInjector;
import br.com.ucsal.model.Produto;
import br.com.ucsal.service.ProdutoService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet para listar produtos. Recupera os produtos do serviço e os exibe em
 * uma página JSP.
 */
@Rota(value = "/listarProdutos")
public class ProdutoListarServlet implements Command, Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private ProdutoService produtoService;

    public ProdutoListarServlet() {
        // Realiza a injeção de dependências no serviço
        DependencyInjector.injectDependencies(this);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtém a lista de produtos
        List<Produto> produtos = produtoService.listarProdutos();
        
        // Define a lista de produtos como atributo da requisição
        request.setAttribute("produtos", produtos);
        
        // Encaminha para a página JSP que exibe a lista de produtos
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/produtolista.jsp");
        dispatcher.forward(request, response); // Exibe a lista de produtos
    }
}

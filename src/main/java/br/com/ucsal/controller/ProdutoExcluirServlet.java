package br.com.ucsal.controller;

import java.io.IOException;
import java.io.Serializable;

import br.com.ucsal.anotacoes.Inject;
import br.com.ucsal.anotacoes.Rota;
import br.com.ucsal.anotacoes.logic.DependencyInjector;
import br.com.ucsal.service.ProdutoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet para excluir produtos.
 * Processa requisições para remover produtos do sistema.
 */
@Rota(value = "/excluirProdutos")
public class ProdutoExcluirServlet implements Command, Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private ProdutoService produtoService;

    public ProdutoExcluirServlet() {
        // Realiza a injeção de dependências no serviço
        DependencyInjector.injectDependencies(this);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtém o ID do produto a ser excluído
        Integer id = Integer.parseInt(request.getParameter("id"));
        // Remove o produto usando o serviço
        produtoService.removerProduto(id);
        // Mensagem personalizada no console
        System.out.println("Produto de ID " + id + " excluído com sucesso!"); 
        // Redireciona para a listagem de produtos
        response.sendRedirect("listarProdutos");
    }
}

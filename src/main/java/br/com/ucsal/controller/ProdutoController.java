package br.com.ucsal.controller;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet controlador principal para gerenciar rotas e comandos.
 * É responsável por redirecionar as requisições para os comandos adequados com base na URL.
 */
@WebServlet(urlPatterns = {"/view/*", "/"}) // Mapeamento para capturar qualquer caminho dentro de "/view"
public class ProdutoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Mapa que contém os comandos registrados no contexto da aplicação
    private Map<String, Command> commands;

    // Método de inicialização, chamado uma vez quando o servlet é carregado
    @SuppressWarnings("unchecked")
    @Override
    public void init() throws ServletException {
        // Recupera o mapa de comandos do contexto da aplicação
        this.commands = (Map<String, Command>) getServletContext().getAttribute("commands");
        
        if (this.commands == null) {
            // Caso o mapa de comandos não esteja disponível, exibe um aviso
            System.out.println("😓 Erro: Mapa de comandos não encontrado no contexto da aplicação.");
        } else {
            // Caso o mapa de comandos seja encontrado, exibe uma mensagem de sucesso
            System.out.println("✅ Mapa de comandos carregado com sucesso.");
        }
    }

    // Método responsável por tratar as requisições HTTP (GET, POST, etc.)
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Pega o caminho completo da requisição (ex: /seu-app/view/adicionarProdutos)
        String requestURI = request.getRequestURI();
        
        // Remove o contexto da aplicação para obter apenas o caminho específico por exemplo: /seu-app
        String contextPath = request.getContextPath();

        // Remove o contexto da URL para obter apenas o caminho da requisição após o contexto
        String path = requestURI.substring(contextPath.length());

        // Se o caminho estiver vazio ou for apenas "/"
        if (path == null || path.equals("/")) {
            path = "/listarProdutos"; // Redireciona para a página de listagem de produtos
        }

        System.out.println("🔍 Caminho solicitado: " + path);

        // Verifica se o mapa de comandos foi carregado corretamente
        if (this.commands == null) {
            // Caso o mapa de comandos seja nulo, gera um erro e envia uma resposta de falha
            System.out.println("❌ Erro: Mapa de comandos não encontrado no contexto durante a requisição.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no servidor ao processar a requisição.");
            return;
        }

        // Obtém o comando associado ao caminho
        Command command = commands.get(path);

        // Executa o comando, se encontrado, ou retorna erro 404
        if (command != null) {
            // Se o comando for encontrado, executa-o
            System.out.println("🚀 Executando comando para o caminho: " + path);
            command.execute(request, response);
        } else {
            // Caso o comando não seja encontrado, retorna um erro 404
            System.out.println("⚠️ Comando não encontrado para o caminho: " + path);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Página não encontrada" + path);
        }
    }
}

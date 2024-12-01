package br.com.ucsal.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interface para encapsular comandos em requisições HTTP...s Elas irão aparecer pelo código. Cada implementação
 * define uma lógica específica para processar a solicitação.
 */

public interface Command {
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}

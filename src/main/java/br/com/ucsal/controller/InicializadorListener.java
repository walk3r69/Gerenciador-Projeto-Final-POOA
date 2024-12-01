package br.com.ucsal.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import br.com.ucsal.anotacoes.Rota;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

//Listener responsável por inicializar e registrar rotas associadas a comandos.
@WebListener
public class InicializadorListener implements ServletContextListener {

	// Mapa para armazenar as associações de rotas a comandos
	private Map<String, Command> commands = new HashMap<>(); 

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("⚙️ Preparando a máquina: inicializando rotas...");

		try {
			// Usando Reflections para buscar classes no pacote br.com.ucsal.controller
			// LEMBRAR: O Reflections vai "varrer" o pacote e encontrar todas as classes que possuem
			// a anotação @Rota.
			// Basicamente, ele faz uma busca dinâmica pelas classes no pacote especificado,
			// sem precisar declarar uma lista manualmente.
			Reflections reflections = new Reflections("br.com.ucsal.controller");
			Set<Class<?>> classesAnotadas = reflections.getTypesAnnotatedWith(Rota.class);

			// Verifica se foram encontradas classes anotadas
			if (classesAnotadas.isEmpty()) {
				System.out.println("❌ Ops! Nenhuma classe anotada com @Rota foi encontrada.");
			}

			// Processa cada classe anotada encontrada com a anotação @Rota
			for (Class<?> classe : classesAnotadas) {
				System.out.println("🔍 Classe anotada localizada: " + classe.getName());
				Rota rota = classe.getAnnotation(Rota.class); // Pega a anotação @Rota da classe

				// Valida se a classe implementa a interface Command
				if (!Command.class.isAssignableFrom(classe)) {
					throw new IllegalArgumentException(
							"❌ A classe " + classe.getName() + " precisa implementar Command!");
				}

				// Cria uma instância do comando e registra no mapa de rotas
				Command commandInstance = (Command) classe.getDeclaredConstructor().newInstance();
				commands.put(rota.value(), commandInstance); // Adiciona a rota e o comando ao mapa
				System.out.println("✅ Rota registrada: " + rota.value() + " -> " + classe.getName());
			}

			// Armazena o mapa de comandos no contexto da aplicação
			sce.getServletContext().setAttribute("commands", commands);
			System.out.println("🚀 Rotas prontas para ação!");

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("🔥 Erro fatal ao inicializar rotas: " + e.getMessage(), e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		commands.clear(); // Limpa o mapa de comandos ao encerrar a aplicação
		System.out.println("🧹 Limpeza completa: rotas foram encerradas.");
	}
}

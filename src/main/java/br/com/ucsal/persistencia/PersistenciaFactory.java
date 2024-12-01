package br.com.ucsal.persistencia;

import br.com.ucsal.anotacoes.logic.SingletonLoader;

/**
 * Fábrica para criação de instâncias de repositórios de produtos.
 * Permite alternar entre diferentes implementações de persistência.
 */
public class PersistenciaFactory {

    public static final int TIPO_MEMORIA = 0;
    public static final int TIPO_HSQL = 1;

    // Retorna a implementação de repositório com base no tipo especificado.
    public static ProdutoRepository<?, ?> getProductRepository(int repoType) {
        switch (repoType) {
            case TIPO_MEMORIA:
                System.out.println("Fábrica: Criando repositório em memória.");
                return SingletonLoader.carregarSingleton(MemoriaProdutoRepository.class);
            case TIPO_HSQL:
                System.out.println("Fábrica: Criando repositório HSQLDB.");
                return new HSQLProdutoRepository();
            default:
                throw new IllegalArgumentException("Valor inesperado: " + repoType);
        }
    }
}

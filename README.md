# Backend de Apoio a Desabrigados de Enchentes

Este projeto consiste no desenvolvimento de uma aplicação backend para auxiliar desabrigados em situações de enchentes. A aplicação foi desenvolvida utilizando Java 17 e JPA (Java Persistence API), sem o uso do Spring Boot, conforme os requisitos do desafio proposto.

## Funcionalidades Principais

- **Registro de Doações**
  - Cadastro, leitura de itens doados (alimentos, higiene, roupas).
  - Associação de cada item doado a um centro de distribuição específico.
  - Suporte para cadastro de doações via arquivo CSV.
  - Validação para garantir que nenhum centro de distribuição exceda sua capacidade máxima de armazenamento.

- **Registro de Abrigos**
  - Cadastro, leitura, edição e exclusão de abrigos.
  - Armazenamento detalhado de informações sobre cada abrigo, incluindo capacidade e ocupação atual.

- **Ordem de Pedido**
  - Abrigos podem listar suas necessidades de itens específicos.
  - A aplicação indica quais centros de distribuição possuem os itens solicitados, ordenados pela disponibilidade mais próxima da quantidade requerida.
  - Facilidade para abrigos enviarem pedidos para um ou mais centros de distribuição conforme suas necessidades.

- **Checkout de Itens**
  - Centros de distribuição podem aceitar ou recusar pedidos recebidos.
  - Em caso de aceitação, a quantidade de itens é deduzida do estoque disponível no centro.
  - Motivos de recusa são registrados e comunicados ao abrigo solicitante.

- **Transferência de Doações**
  - Restrição de quantidade máxima de itens por centro de distribuição e por abrigo para garantir equilíbrio no estoque.
    
## Repositório e Contribuições

O código-fonte está disponível no GitHub. Sinta-se à vontade para explorar, fazer fork e contribuir para o desenvolvimento deste projeto

## Modelagem projeto 
https://drive.google.com/file/d/1oFTJnVfTqvbu7PAtjGXK8VQ7cQ2V6fzm/view?usp=sharing

Este README fornece uma visão geral do projeto, suas funcionalidades principais, estrutura de implementação e informações sobre como contribuir. Em caso de dúvidas ou sugestões, sinta-se à vontade para entrar em contato ou abrir uma issue no repositório.

# Helpdesk / GLPI - Sistema de Gestão de Chamados Técnicos

Projeto Full-Stack containerizado com Spring Boot, PostgreSQL, React e autenticação JWT/RBAC.

## Stack

- Java 17
- Spring Boot 3.3.5
- Spring Security
- JWT (JJWT 0.12.6)
- BCryptPasswordEncoder
- Spring Data JPA / Hibernate
- Bean Validation
- PostgreSQL 16
- React 18 + Vite
- Vanilla CSS por módulo
- Docker / Docker Compose
- Nginx

## Módulo de autenticação e RBAC

Perfis disponíveis:

- `USUARIO`: cria chamados e visualiza apenas os próprios chamados.
- `TECNICO`: visualiza todos os chamados, assume, atualiza histórico e conduz o fluxo técnico.
- `ADMINISTRADOR`: acesso global, gestão de usuários, relatórios, exclusão de chamados e reatribuição de técnicos.

> O cadastro público permite selecionar qualquer um dos três perfis porque esse comportamento foi solicitado para o projeto. Em ambiente produtivo real, recomenda-se impedir auto cadastro como `TECNICO` ou `ADMINISTRADOR` e delegar essas promoções a administradores existentes.

## Executar com Docker

```bash
docker compose up --build
```

Acessos:

- Frontend: http://localhost:5173
- Backend: http://localhost:8080
- PostgreSQL: localhost:5432

A primeira tela do frontend será o Login. Para criar a primeira conta, use `Cadastre-se`.

## Endpoints públicos

### Cadastro

`POST /auth/register`

```json
{
  "nome": "Administrador",
  "email": "admin@helpdesk.local",
  "senha": "123456",
  "perfil": "ADMINISTRADOR"
}
```

### Login

`POST /auth/login`

```json
{
  "email": "admin@helpdesk.local",
  "senha": "123456"
}
```

Resposta:

```json
{
  "token": "JWT_AQUI",
  "id": 1,
  "nome": "Administrador",
  "email": "admin@helpdesk.local",
  "perfil": "ADMINISTRADOR"
}
```

Os demais endpoints exigem:

```text
Authorization: Bearer <token>
```

## Regras RBAC

### USUARIO

- `GET /chamados`: backend devolve somente os chamados pertencentes ao usuário autenticado.
- `GET /chamados/{id}`: somente se o chamado pertencer ao usuário.
- `POST /chamados`: permitido.
- atualização técnica, alteração de fluxo e exclusão: negadas.

### TECNICO

- consulta global de chamados e atendimentos;
- atualização de histórico;
- movimentação do fluxo;
- atribuição automática como técnico responsável quando o chamado ainda não possui técnico;
- não pode excluir chamados nem administrar usuários.

### ADMINISTRADOR

Além das permissões técnicas:

- `GET /admin/usuarios`
- `GET /admin/tecnicos`
- `PUT /admin/usuarios/{id}/perfil`
- `DELETE /admin/usuarios/{id}`
- `GET /admin/relatorios/resumo`
- `DELETE /chamados/{id}`
- reatribuição do técnico responsável pelo frontend.

## Fluxo

```text
ABERTO
   ↓
EM_TRIAGEM (N1)
   ↓
EM_ATENDIMENTO (N2/N3)
   ↓
PENDENTE_EVIDENCIA
   ↓
RESOLVIDO
   ↓
FECHADO
```

Também são aceitos:

```text
PENDENTE_EVIDENCIA -> EM_ATENDIMENTO
RESOLVIDO -> EM_ATENDIMENTO
```

## Propriedade do chamado

Para preservar a fidelidade do `ChamadoModel.java` original, o vínculo com o solicitante não foi inserido diretamente em `tab_chamados`. O proprietário é armazenado em `AtendimentoModel` através de:

```java
@ManyToOne(optional = false)
private UsuarioModel solicitante;
```

Assim, `ChamadoModel` continua com seus campos originais:

```text
id
tituloChamado
ocorrenciaChamado
descricaoChamado
prioridadeChamado
```

## Segurança

As senhas nunca são persistidas em texto puro. O backend usa `BCryptPasswordEncoder`.

O JWT contém `usuarioId`, `nome`, `perfil`, `subject=email`, data de emissão e expiração. A sessão do backend é stateless.

O frontend mantém o token em `localStorage`, conforme o requisito do projeto. Para produção com maior exigência de segurança contra XSS, avalie cookies HttpOnly/Secure/SameSite e uma política CSP rigorosa.

### JWT secret

O `docker-compose.yml` possui uma chave de desenvolvimento. Em produção, substitua `JWT_SECRET` por um segredo forte e mantido fora do repositório.

## Teste rápido

1. Execute `docker compose up --build`.
2. Abra http://localhost:5173.
3. Clique em `Cadastre-se`.
4. Crie uma conta `USUARIO`.
5. Faça login e abra um chamado.
6. Saia e crie uma conta `TECNICO`.
7. Faça login como técnico e veja a lista global.
8. Abra o chamado, avance o fluxo e registre histórico.
9. Crie uma conta `ADMINISTRADOR`.
10. Acesse `Administração`, altere perfis e visualize o resumo.
11. Como administrador, abra um chamado e reatribua o técnico responsável.

## Encerrar

```bash
docker compose down
```

Para remover também o volume do banco:

```bash
docker compose down -v
```

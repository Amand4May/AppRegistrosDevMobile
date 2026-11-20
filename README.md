# 📋 Registro de Produção

Aplicativo Android para organizar as informações do dia a dia da produção industrial: **registros**, **listas de controle** e **anotações operacionais**, tudo em um só lugar.

Projeto da disciplina **Desenvolvimento Mobile** (Prof. Rafael Soleiman Franco).

---

## 🎯 Contexto

Na empresa, essas informações ficam espalhadas em anotações à mão, planilhas e arquivos soltos, o que dificulta consultar e atualizar os dados. O app reúne tudo no celular, com uma interface simples e direta.

Como o ambiente de produção **não tem acesso à rede local nem à internet**, o app funciona **100% offline**: não usa internet, serviços externos nem banco de dados remoto.

## ✨ Funcionalidades

- **Cadastrar** registros com título, categoria, setor/linha, responsável, prioridade e descrição
- **Visualizar** todos os registros em lista, com a cor da prioridade
- **Ver os detalhes** de cada registro
- **Editar** registros existentes
- **Excluir** registros, com confirmação antes de apagar
- **Feedback visual** com mensagens claras após cada ação (Toast e diálogos)

## 🛠️ Tecnologias

| | |
|---|---|
| IDE | Android Studio |
| Linguagem | Java |
| Interface | Layouts em XML (Material Components) |
| Armazenamento | Em memória, com `ArrayList` (sem banco de dados) |
| SDK mínimo | Android 7.0 (API 24) |

## 📱 Telas

| Tela | Arquivo | O que faz |
|---|---|---|
| Início | `activity_main.xml` | Total de registros e acesso rápido a cadastrar e listar |
| Lista | `activity_lista.xml` + `item_registro.xml` | Todos os registros, com aviso quando a lista está vazia |
| Formulário | `activity_formulario.xml` | Cadastro e edição (a mesma tela serve para os dois) |
| Detalhes | `activity_detalhes.xml` | Dados completos do registro, com os botões Editar e Excluir |

**Fluxo de navegação:**

```
Início ──► Novo registro ──► Formulário ──► (salva e volta)
   │
   └─────► Ver registros ──► Lista ──► Detalhes ──► Editar ──► Formulário
                               │            └────► Excluir (confirma)
                               └──► Novo registro ──► Formulário
```

### Design

Interface pensada para o chão de fábrica:
- **Botões grandes** (60dp de altura), fáceis de tocar até de luva
- **Alto contraste**: azul escuro como cor principal e laranja de segurança como destaque
- **Cores de prioridade**: 🟢 Baixa · 🟡 Média · 🔴 Alta

## 🗂️ Estrutura do projeto

```
app/src/main/
├── java/com/exemplo/registros/
│   ├── Registro.java              # Modelo de dados
│   ├── RegistroRepositorio.java   # Armazenamento em memória (ArrayList)
│   ├── BarrasSistema.java         # Ajuste da barra de status (Android 15+)
│   ├── MainActivity.java          # Tela inicial
│   ├── ListaActivity.java         # Lista de registros
│   ├── FormularioActivity.java    # Cadastro e edição
│   └── DetalhesActivity.java      # Detalhes do registro
├── res/
│   ├── layout/      # Telas em XML
│   ├── values/      # Cores, textos, tamanhos, estilos e categorias
│   └── drawable/    # Fundos e ícones
└── AndroidManifest.xml
```

## 💾 Como os dados são guardados

Os registros ficam em um `ArrayList` estático dentro de `RegistroRepositorio`, compartilhado por todas as telas enquanto o app estiver aberto:

```java
RegistroRepositorio.adicionar(registro);   // cadastrar
RegistroRepositorio.listar();              // listar
RegistroRepositorio.buscarPorId(id);       // buscar
RegistroRepositorio.atualizar(id, ...);    // editar
RegistroRepositorio.remover(id);           // excluir
RegistroRepositorio.total();               // contar
```

> Como o armazenamento é em memória, os dados são apagados quando o app é fechado. Isso atende ao escopo do projeto.

## ▶️ Como rodar

1. Clone o repositório:
   ```bash
   git clone <link-do-repositorio>
   ```
2. No Android Studio: **File → Open** e selecione a pasta do projeto.
3. Se aparecer o aviso *"Please Select Gradle JVM"*, clique em **Use JVM 21**.
4. Espere o Gradle sincronizar.
5. Escolha um emulador (ex.: Pixel 7) ou um celular com depuração USB ativada e clique em **▶ Run**.

## 👥 Equipe

| Integrante | Responsabilidade |
|---|---|
| Amanda Mayumi | Interface (telas em XML), base de dados em memória |
| _Nome_ | Lista de registros (adapter e tela de lista) |
| _Nome_ | Formulário de cadastro e edição |
| Gabriel Pereira | Tela inicial, tela de detalhes, Manifest e teste final |

> Mais detalhes técnicos para a equipe (IDs de cada tela, como usar o repositório) estão no [GUIA.md](GUIA.md).

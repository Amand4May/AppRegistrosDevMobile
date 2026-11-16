# Telas do app (parte visual)

## O que tem aqui
- `res/layout/`: as 4 telas (`activity_main`, `activity_lista`, `activity_formulario`, `activity_detalhes`) e o item da lista (`item_registro`)
- `res/values/`: cores, textos (incluindo as mensagens de feedback), tamanhos, categorias e estilos
- `res/drawable/`: fundos (cards, campos, badges) e ícones
- `MainActivity`, `ListaActivity`, `FormularioActivity`, `DetalhesActivity`: PROVISÓRIAS só para navegar entre as telas. O grupo substitui pelas versões finais.

## IDs para o grupo usar no Java
| Tela | IDs |
|---|---|
| activity_main | tvTotalRegistros, btnNovoRegistro, btnVerRegistros |
| activity_lista | btnVoltarLista, tvQuantidade, listRegistros, layoutVazio (usar `setEmptyView`), btnAdicionar |
| item_registro | viewPrioridade, tvItemTitulo, tvItemCategoria, tvItemDescricao, tvItemSetor |
| activity_formulario | btnVoltarForm, tvTituloFormulario, etTitulo, spCategoria, etSetor, etResponsavel, rgPrioridade (rbBaixa/rbMedia/rbAlta), etDescricao, btnCancelar, btnSalvar |
| activity_detalhes | btnVoltarDetalhes, tvDetalheCategoria, tvDetalhePrioridade, tvDetalheTitulo, tvDetalheSetor, tvDetalheResponsavel, tvDetalheData, tvDetalheDescricao, btnExcluir, btnEditar |

Mensagens de feedback em `strings.xml`: msg_salvo, msg_atualizado, msg_excluido, msg_erro_titulo, dialogo_excluir_*.
Cores de prioridade: `@color/prioridade_alta`, `prioridade_media`, `prioridade_baixa`.

## Manifest (dentro de `<application>`)
```xml
<activity android:name=".ListaActivity" />
<activity android:name=".FormularioActivity" />
<activity android:name=".DetalhesActivity" />
```
E no `<application>`: `android:theme="@style/Theme.AppRegistros"`

## Base de dados em memória (`java_base/`) — já pronta
Já estão em `app/src/main/java/com/exemplo/registros/`.

Como usar (tudo é estático, não precisa dar `new` no repositório):
```java
// Cadastrar
int id = RegistroRepositorio.adicionar(new Registro(titulo, categoria, setor, responsavel, prioridade, descricao));
// Listar / contar
List<Registro> lista = RegistroRepositorio.listar();
int total = RegistroRepositorio.total();
// Buscar / atualizar / remover
Registro r = RegistroRepositorio.buscarPorId(id);
RegistroRepositorio.atualizar(id, titulo, categoria, setor, responsavel, prioridade, descricao);
RegistroRepositorio.remover(id);

// Passar o id entre telas (SEMPRE essa chave)
intent.putExtra(RegistroRepositorio.EXTRA_ID, r.getId());
int id = getIntent().getIntExtra(RegistroRepositorio.EXTRA_ID, -1); // -1 = cadastro novo
```
Prioridade: usar `Registro.PRIORIDADE_BAIXA`, `PRIORIDADE_MEDIA`, `PRIORIDADE_ALTA` (mesmos textos da tela).
Datas: `r.getDataFormatada()` (detalhes) e `r.getDataCurta()` (item da lista).

## Divisão do restante
- **Pessoa A**: `RegistroAdapter` + `ListaActivity` (atualizar no `onResume`, `setEmptyView`, clique → Detalhes com o id)
- **Pessoa B**: `FormularioActivity` (cadastro e edição pelo id, validação do título com `setError`, Toasts `msg_salvo`/`msg_atualizado`)
- **Pessoa C**: `MainActivity` (contador no `onResume`) + `DetalhesActivity` (preencher, editar, excluir com `AlertDialog` + Toast) + Manifest + teste final

## Barra de status (Android 15+)
Toda activity precisa chamar `BarrasSistema.ajustar(this);` logo depois do `setContentView(...)`. Sem isso, o cabeçalho fica embaixo do relógio.

# Treasy desafio backend

Aplicação para representar uma estrutura de arvore. A aplicação contém métodos REST para exibir a estrutura, é possível adicionar, editar ou excluir os nós da arvore.

#### Importante

>
> O ambiente de desenvolvimento foi configurado em sistema com base Unix, Mac e Linux, ou seja, pode haver divergência caso seja utilizado o Windows para desenvovlimento, dessa forma, caso seja extremamente necessário que se desenvolva nessa plataforma, as configurações podem variar, porém, não pude fazer os testes e nem tenho a pretenção de fazê-los para desenvolvimento em Windows.
>
> Dessa forma, todas as configurações foram realizadas utilizando a linha de comando.
>

## Setup

Para preparar o ambiente de desenvolvimento, é necessário gerar as variáveis de ambiente para a conexão com o banco de dados e baixar as dependências do projeto

##### Variáveis de ambiente

```text
- DATABASE_URL=localhost:5432/treasy
- DATABASE_USER=postgres
- DATABASE_PASSWORD=123456

Para testes

- DATABASE_TEST_URL=localhost:5432/treasy_test
```

##### Dependências do projeto

```text
./gradle build
```

Para gerar as variáveis, basta apenas executar o comando:

##### Mac

```shell
./setup.sh
```

##### Linux

```shell
. ./setup.sh
```

> Ps.: As variáveis de sessão serão criadas apenas para a sessão do terminal em questão.

### Iniciar para desenvolvimento

Executar o projeto com suporte para auto-detecção da classe principal e recarregando recursos estáticos

```shell
./gradle bootRun
```

> Para desenvolvimento utilizando o [Visual Studio Code](https://code.visualstudio.com/) o debug está configurado, basta apenas executá-lo.

### Build

Compila e testa o projeto

```shell
./gradlew build
```

Executar apenas os testes

```shell
./gradlew test --debug
```

#### Opcional

> Para visualizar os resultados dos testes e a documentação (javadoc). Instale os pacotes com o npm, (nesse caso, é necessário que o node esteja instalado e configurado no computador).
>
> Rode o comando ```npm install``` para instalar as dependências node
>
> Após realizar o build para o teste ```./gradlew test``` o relatório pode ser visualizada com o comando ```npm run test```
>
> Após realizar o build para a documentação ```./gradlew javadoc``` o site pode ser visualizada com o comando ```npm run doc```
>

##### Output

![alt text][test_output]

## Available Tasks

Todas as tarefas devem ser executadas à partir da base do projeto ```./```

### Build tasks

- ```assemble``` - Assembles the outputs of this project.
- ```bootRepackage``` - Repackage existing JAR and WAR archives so that they can be executed from the command line using 'java -jar'
- ```buildDependents``` - Assembles and tests this project and all projects that depend on it.
- ```buildNeeded``` - Assembles and tests this project and all projects it depends on.
- ```classes``` - Assembles main classes.
- ```clean``` - Deletes the build directory.
- ```jar``` - Assembles a jar archive containing the main classes.
- ```testClasses``` - Assembles test classes.
- ```war``` - Generates a war archive with all the compiled classes, the web-app content and the libraries.

### Build Setup tasks

- ```init``` - Initializes a new Gradle build.
- ```wrapper``` - Generates Gradle wrapper files.

### Documentation tasks

- ```javadoc``` - Generates Javadoc API documentation for the main source code.

### Help tasks

- ```buildEnvironment``` - Displays all buildscript dependencies declared in root project 'treasyapi'.
- ```components``` - Displays the components produced by root project 'treasyapi'. [incubating]
- ```dependencies``` - Displays all dependencies declared in root project 'treasyapi'.
- ```dependencyInsight``` - Displays the insight into a specific dependency in root project 'treasyapi'.
- ```dependencyManagement``` - Displays the dependency management declared in root project 'treasyapi'.
- ```dependentComponents``` - Displays the dependent components of components in root project 'treasyapi'. [incubating]
- ```help``` - Displays a help message.
- ```model``` - Displays the configuration model of root project 'treasyapi'. [incubating]
- ```projects``` - Displays the sub-projects of root project 'treasyapi'.
- ```properties``` - Displays the properties of root project 'treasyapi'.
- ```tasks``` - Displays the tasks runnable from root project 'treasyapi'.

### Verification tasks

- ```check``` - Runs all checks.
- ```test``` - Runs the unit tests.

### Rules

- ```Pattern: clean<TaskName>```: Cleans the output files of a task.
- ```Pattern: build<ConfigurationName>```: Assembles the artifacts of a configuration.
- ```Pattern: upload<ConfigurationName>```: Assembles and uploads the artifacts belonging to a configuration.

[test_output]: http://res.cloudinary.com/nogsantos/image/upload/v1516807486/test-output_ssk0cm.png "Amostra do resultado do teste"

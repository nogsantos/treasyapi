# Roadmap

## Requisitos técnicos

- [x] A aplicação deve ser construída utilizando Java 8;
- [x] Pode ser utilizada qualquer biblioteca ou componente do Java;
- [x] A persistência dos dados deve ser feita utilizando Hibernate e MySQL (Irei utilizar o postgres);
- [x] O Hibernate que deve gerar as tabelas e relacionamentos necessários no banco de dados ao subir a aplicação;
- [x] A comunicação entre o backend e o frontend deve ser feita através de métodos REST utilizando JSON;
- [] No final o projeto deve gerar um WAR que será testado no Tomcat 9;

## Endpoints

### POST - /node

- Request
    - code: string
    - description: string
    - parent_id: number
    - detail: string
- Response
    - id: number

### PUT - /node

- Request
    - id: number
    - code: string
    - description: string
    - parent_id: number
    - detail: string
- Response
    - id: number

### GET - /node

- Response
    - id: number
    - code: string
    - description: string
    - parent_id: number
    - detail: string
    - children: array
        - id: number
        - code: string
        - description: string
        - parent_id: number
        - detail: string
        - children: array
        - ...
- O atributo children é um array que contém todos os nós filhos daquele nó, ou seja, irá conter toda a hierarquia até o ultimo nível;

### GET - /node/{id}

- Response
    - array
        - id: number
        - code: string
        - description: string
        - parent_id: number
        - detail: string
        - has_children: boolean
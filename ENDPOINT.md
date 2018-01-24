# Endpoints

Métodos disponíveis para requisição:

## GET

Método para buscar toda a estrutura

Endpoint: ```/nodes```

### Return

Status: 200

```json
[
    {
        "id": 10,
        "code": "NODE",
        "description": "Parent node",
        "detail": "Nothing to say",
        "childrens": [
            {
                "id": 8,
                "code": "NODE-8",
                "description": "Parent node",
                "detail": "Nothing to say",
                "childrens": [
                    {
                        "id": 7,
                        "code": "NODE",
                        "description": "Parent node",
                        "detail": "Nothing to say",
                        "childrens": [
                            {
                                "id": 6,
                                "code": "NODE",
                                "description": "Parent node",
                                "detail": "Nothing to say",
                                "createdAt": 1516705044789,
                                "updatedAt": 1516733487934
                            }
                        ],
                        "createdAt": 1516713308656,
                        "updatedAt": 1516733504059
                    }
                ],
                "createdAt": 1516713364541,
                "updatedAt": 1516733412849
            }
        ],
        "createdAt": 1516733217304,
        "updatedAt": 1516734207651
    },
    {
        "id": 8,
        "code": "NODE-8",
        "description": "Parent node",
        "detail": "Nothing to say",
        "childrens": [
            {
                "id": 7,
                "code": "NODE",
                "description": "Parent node",
                "detail": "Nothing to say",
                "childrens": [
                    {
                        "id": 6,
                        "code": "NODE",
                        "description": "Parent node",
                        "detail": "Nothing to say",
                        "createdAt": 1516705044789,
                        "updatedAt": 1516733487934
                    }
                ],
                "createdAt": 1516713308656,
                "updatedAt": 1516733504059
            }
        ],
        "createdAt": 1516713364541,
        "updatedAt": 1516733412849
    },
    {
        "id": 7,
        "code": "NODE",
        "description": "Parent node",
        "detail": "Nothing to say",
        "childrens": [
            {
                "id": 6,
                "code": "NODE",
                "description": "Parent node",
                "detail": "Nothing to say",
                "createdAt": 1516705044789,
                "updatedAt": 1516733487934
            }
        ],
        "createdAt": 1516713308656,
        "updatedAt": 1516733504059
    },
    {
        "id": 6,
        "code": "NODE",
        "description": "Parent node",
        "detail": "Nothing to say",
        "createdAt": 1516705044789,
        "updatedAt": 1516733487934
    }
]
```

## GET/{id}

Método para buscar todos os nós abaixo de um nó específico

Endpoint: ```/node/{id}```

### Params

- Long id código identificador do nó.

### Return

Status: 201

```json
{
    "id": 10,
    "code": "NODE",
    "description": "Parent node",
    "detail": "Nothing to say",
    "childrens": [
        {
            "id": 8,
            "code": "NODE-8",
            "description": "Parent node",
            "detail": "Nothing to say",
            "childrens": [
                {
                    "id": 7,
                    "code": "NODE",
                    "description": "Parent node",
                    "detail": "Nothing to say",
                    "childrens": [
                        {
                            "id": 6,
                            "code": "NODE",
                            "description": "Parent node",
                            "detail": "Nothing to say",
                            "createdAt": 1516705044789,
                            "updatedAt": 1516733487934
                        }
                    ],
                    "createdAt": 1516713308656,
                    "updatedAt": 1516733504059
                }
            ],
            "createdAt": 1516713364541,
            "updatedAt": 1516733412849
        }
    ],
    "createdAt": 1516733217304,
    "updatedAt": 1516734207651
}
```

## POST

Método para cadastrar nó

Endpoint: ```/node```

### Params

- Body json/data

### Campos

Obrigatórios:

- code
- description
- details

Opcional

- parent

### Send

Status: 201

```json
{
  "code": "NODE",
  "description":"Parent node",
  "detail":"Nothing to say"
}
```

Para cadastro informado o nó pai, o corpo da requisição deve ser:

```json
{
  "code": "NODE",
  "description":"Parent node",
  "detail":"Nothing to say",
  "parent":{
	"id": {id}
  }
}
```

### Return

```json
{
    "id": {id}
}
```

## PUT/{id}

Método para atualizar nó

Endpoint: ```/node/{id}```

### Params

- Long id código identificador do nó.
- Body json/data

### Campos

Obrigatórios

- code
- description
- details

Opcional

- parent

### Send

Status: 201

```json
{
  "code": "NODE",
  "description":"Parent node",
  "detail":"Nothing to say"
}
```

Para edição informado o nó pai, o corpo da requisição deve ser:

```json
{
  "code": "NODE",
  "description":"Parent node",
  "detail":"Nothing to say",
  "parent":{
	"id": {id}
  }
}
```

### Return

```json
{
    "id": {id}
}
```

## DELET/{id}

Método para excluir um nó e seus filhos

Endpoint: ```/node/{id}```

### Params

- Long id código identificador do nó.

### Send

Status: 201

### Return

```json
{
    "Success": "Node deleted successfully"
}
```

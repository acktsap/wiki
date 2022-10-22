# Open API Spec

- [Concept](#concept)
- [History](#history)
- [Swagger](#swagger)
- [Why use it?](#why-use-it)
- [Basic Structure](#basic-structure)
- [See also](#see-also)

## Concept

- OpenAPI Specification (OAS)
- A specification for machine-readable interface files for describing, producing, consuming, and visualizing RESTful web services.
- REST API를 위한 API Spec. 다음을 정의.
  - Available endpoints (/users) and operations on each endpoint (GET /users, POST /users)
  - Operation parameters Input and output for each operation
  - Authentication methods
  - Contact information, license, terms of use and other information.
- JSON이나 YAML로 읽을 수 있음.

## History

- Swagger development began in early 2010 by Tony Tam, who was working at online dictionary company Wordnik.
- In March 2015, SmartBear Software acquired the open-source Swagger API specification from Reverb Technologies, Wordnik's parent company.
- In November 2015, SmartBear announced that it was creating a new organization called the OpenAPI Initiative under the sponsorship of the Linux Foundation. Other founding member companies included 3scale, Apigee, Capital One, Google, IBM, Intuit, Microsoft, PayPal, and Restlet. SmartBear donated the Swagger specification to the new group. RAML and API Blueprint were also under consideration by the group.
- ...

> 그냥 REST API로 잘 쓰는데 어떻게 해야 하는지 규약이 애매해서 규약 만들고 관련된 도구도 미리 제공하고 이걸 쓰자! 이런거같음

## Swagger

- REST API를 OpenAPI에 맞게 설계할 수 있게 도와주는 오픈소스 도구의 모음.

## Why use it?

- API Spec이 일단 잘 정의되어 있음.
- Swagger CodeGen, Server Mock 같은거 도구 많음. Commertial tools도 있음.

## Basic Structure

- [Basic Structure](https://swagger.io/docs/specification/basic-structure/)

## See also

- [OpenAPI Specification (wiki)](https://en.wikipedia.org/wiki/OpenAPI_Specification)
- [OpenAPI spec](https://swagger.io/specification/)
- [OpenAPI 3.0: How to Design and Document APIs with the Latest OpenAPI Specification 3.0](https://www.youtube.com/watch?v=6kwmW_p_Tig)
- [Official training guide](https://swagger.io/docs/specification/about/)
- [Open API Live Editor](https://editor.swagger.io/)
- [OpenAPI-Specification (Github)](https://github.com/OAI/OpenAPI-Specification)
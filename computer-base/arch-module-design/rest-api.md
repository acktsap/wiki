# Restful api design

## Design guide

Restful: Representational State Trasfer

Define resources with URI(Uniform Resource Identifier) & use POST, GET, PUT, DELETE for CRUD on resources

Use plural on resources

Use `/api/some_resources/id1` on specific item

Use HTTP status codes for handling error

- Everything worked - success (code: 20x)
- Go away - redirection (code: 30x)
- The application did something wrong – client error (code: 40x)
- The API did something wrong – server error (code: 50x)

다음의 특징이 있다

- Server-Client : 자원을 가진 쪽이 서버, 자원을 요청하는 쪽이 클라이언트가 된다.
- Stateless : 각 요청 간 클라이언트의 콘텍스트가 서버에 저장되어서는 안 된다. HTTP Protocol을 따름. 구현이 단순해짐.
- Cacheable : 클라이언트는 응답을 캐싱할 수 있어야 한다. HTTP 프로토콜을 그대로 사용하므로 기존의 인프라를 그대로 활용할 수 있다.
- Self-descriptiveness : REST API 메시지만 보고도 쉽게 이해할 수 있도록 JSON을 이용해 직관적으로 이해가 가능한 자체 표현 구조로 되어있음.
- 계층형 구조가 가능 : 계층 구조를 통해 보안, LB, 암호화 계층, Proxy, Gateway등을 둘 수 있음. 구조상의 유연성을 높임.

Use base domain with integer version (no v1.2)

- /api/v1/dogs/
- /api/v1/dogs/:id

If you wanna split api, use like

- /api/v1/dogs/
- /api/v1/dogs/:id
- /stream/v1/dogs/
- /stream/v1/dogs/:id

### POST (Create)

```text
POST /api/v1/dogs
name=Al&furColor=brown

Response
200 OK
{
  "dog":{
    "id": "1234",
    "name": "Al",
    "furColor": "brown"
  }
}
```

### GET (Read)

Basic

```text
GET /dogs/1234

Response
200 OK
{
  "dog":{
    "id":"1234",
    "name": "Rover",
    "furColor": "brown"
  }
}
```

Associations

```text
GET /dogs?furColor=white&location=korea

Response
200 OK
{
  "dogs":
    [
      {
        "dog": {
           "id":"1233",
           "name": "Fido",
           "furColor": "white",
           "location": "korea"
         }
      },
      {
        "dog":{
           "id":"1234",
           "name": "Rover",
           "furColor": "white",
           "location": "korea"
         }
      },
      ...
    ]

  "_metadata":
  [
    {
      "totalCount":327,
    }
  ]
}
```

Filtering

```text
GET /dogs?fields=id,name

Response
200 OK
{
  "dogs":
    [
      {
        "dog": {
           "id":"1233",
           "name": "Fido"
         }
      },
      {
        "dog":{
           "id":"1234",
           "name": "Rover"
         }
      },
      ...
    ]

  "_metadata":
  [
    {
      "totalCount":327,
    }
  ]
}
```

Pagination

```text
GET /dogs?limit=25&offset=100

Response
200 OK
{
  "dogs":
    [
      {
        "dog": {
           "id":"1233",
           "name": "Fido",
           "furColor": "white"
         }
      },
      {
        "dog":{
           "id":"1234",
           "name": "Rover",
           "furColor": "brown"
         }
      },
      ... // 25 items
    ]

  "_metadata":
  [
    {
      "totalCount":327,
      "limit":25,
      "offset":100
    }
  ]
}
```

Filtering & Associations

```text
GET /dogs?furColor=white&location=korea&fields=id,name,furColor

Response
200 OK
{
  "dogs":
    [
      {
        "dog": {
           "id":"1233",
           "name": "Fido",
           "furColor": "white"
         }
      },
      {
        "dog":{
           "id":"1234",
           "name": "Rover",
           "furColor": "white"
         }
      },
      ...
    ]

  "_metadata":
  [
    {
      "totalCount":327,
    }
  ]
}
```

### PUT (Update)

```text
PUT /dogs/1234
name=Rover

Response
200 OK
{
  "dog":{
    "id":"1234",
    "name": "Rover",
    "furColor": "brown"
  }
}
```

### DELETE (Delete)

```text
DELETE /dogs/1234

Response
200 OK
```

## Reference

- [some page](/gmlwjd9405.github.io/2018/09/21/rest-and-restful.html)
- [apigee-web-api-design](https://pages.apigee.com/rs/apigee/images/api-design-ebook-2012-03.pdf)
- [wiki HTTP status codes](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)
- [신입개발자 면접 질문들](https://www.notion.so/54d624628a634c879cc93d94f54cd2d1#64a64b82f730455388cbb6332dc5b383)

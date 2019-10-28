# Restful api design

## Design guide

Restful: Representational State Trasfer

Define resources with URI(Uniform Resource Identifier) & use POST, GET, PUT, DELETE for CRUD on resources

Keep it simple stupid

Write it extendablly

Use HTTP status codes for handling error\
[wiki HTTP status codes](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)

- Everything worked - success (code: 20x)
- Go away - redirection (code: 30x)
- The application did something wrong – client error (code: 40x)
- The API did something wrong – server error (code: 50x)

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

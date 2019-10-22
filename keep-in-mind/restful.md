# Restful api design

## Reference

[apigee-web-api-design](https://pages.apigee.com/rs/apigee/images/api-design-ebook-2012-03.pdf)

I deliberately missed some statements against my thought

p. 3

The API’s job is to make the developer as successful as possible. The orientation for APIs is
to think about design choices from the application developer’s point of view

### Nouns are good; verbs are bad

p. 5

2 base URLs per resource. (/dogs: a collection, /dogs/1234: a specific element)

p. 6

Use HTTP verbs to operate on the collections and elements (POST, GET, PUT, and DELETE)\
We think of them as mapping to the acronym, CRUD (Create-Read-Update-Delete)\
(`/dogs`, `/dogs/:id`) * (POST, GET, PUT, DELETE) => 8 combinations

### Plural nouns and concrete names

p. 8

Use plural nouns (`/dog`: x, `/dogs`: o)\
Use concrete (`/items`: x, `/users`: o)

### Simplify associations - sweep complexity under the ‘?’

p. 9

Once you have the primary key for one level, you usually don't need to include the\
levels above because you've already got your specific object. In other words, you shouldn't\
need too many cases where a URL is deeper than what we have above /resource/identifier/resource.

GET `/dogs?color=red&state=running&location=park`

### Handling errors

p. 11

Use HTTP status codes.

[commonly used one](https://www.smartlabsoftware.com/ref/http-status-codes.htm)\
[wiki HTTP status codes](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes)

Handle following errors

- Everything worked - success (code: 20x)
- The application did something wrong – client error (code: 40x)
- The API did something wrong – server error (code: 50x)

### Tips for versioning

p. 13

Never release an API without a version and make the version mandatory

`/v1/dogs`: o, `/v1.2/dogs`: x

### Pagination and partial response

p. 16

Support partial response to cut down bandwidth issue

`/dogs?fields=name,color,location`

p. 17

Make it easy for developers to paginate objects in a database\
Use limit and offset

`/dogs?limit=25&offset=50` (records 50 ~ 75)

Use default data size (eg. limit=10 with offset=0)

`/dogs` == `/dogs?limit=10&offset=0`

### What about responses that don’t involve resources

p. 19.

Use verbs not nouns

`/convert?from=EUR&to=CNY&amount=10`

-> Should be no such request

### Supporting multiple formats

-> Use just json. No multiple formats

### What about attribute names

-> Use just json (camelcase, eg. "createdAt": 1320296464)

### Tips for search

p. 22

`/search?q=fluffy+fur` q: query

### Consolidate API requests in one subdomain

api.twitter.com\
stream.twitter.com\
search.twitter.com

-> In a small field, place domain in an endpoint like `localhost:xxx/api/v1/` or `localhost:xxx/stream/v1/`

### Tips for handling exceptional behavior

-> Just throw a pass

### Authentication

-> Beyond the api design scope

### Making requests on your API

p.27 - 29

Create a brown dog named Al

```text
POST /dogs
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

Rename Al to Rover - Update

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

Tell me about a particular dog

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

Tell me about all the dogs

```text
GET /dogs

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
      }
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

Delete Rover

```text
DELETE /dogs/1234

Response
200 OK
```

### Chatty APIs

-> what?

### Complement with an SDK

-> what?

### The API Facade Pattern

p. 34

We recommend you implement an API façade pattern. This pattern gives you a buffer or\
virtual layer between the interface on top and the API implementation on the bottom

p. 35

"Use the façade pattern when you want to provide a simple interface to a complex subsystem.\
Subsystems often get more complex as they evolve."

- Design Patterns – Elements of Reusable Object-Oriented Software

## Design guide

Keep it simple stupid

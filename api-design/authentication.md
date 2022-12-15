# Authentication

- [Proxy Authentication](#proxy-authentication)
- [Digest Authentication](#digest-authentication)
- [Session vs Cookie](#session-vs-cookie)
- [Token Based Authentication](#token-based-authentication)
- [SAML](#saml)
- [SWT](#swt)
- [Kerberos](#kerberos)
- [See also](#see-also)

## Proxy Authentication

## Digest Authentication

## Session vs Cookie

- Cookie
  - 브라우저에서 저장해두고 요청시마다 쓰는 값.
- Session
  - 인증 정보를 서버에서 관리하는 방식.
  - 그래서 Session을 쓸 때 보통 Cookie를 써서 Session Id를 보냄. 근데 꼭 cookie를 쓰지 않아도 됨. 핵심은 서버에서 관리하는 것.
 
## Token Based Authentication

- 서버에 인증정보를 가지고 싶지 않다. 그래서 인증 정보를 기간에 있는 token자체에 넣는 방식. Server가 Stateless함.
- authentication에 one level을 추가한 것.
- 보통 이렇게 사용 : `Authorization: Barrier ${token_value}`

Pros & Cons

- Pros
  - Server가 Stateless함.
  - usename과 password를 매번 제공 안해도 됨.
- Cons
  - 인증 정보가 token 자체에 있고 거기 기간이 있기 때문에 logout 시킬 수 없다.

## SAML

- Security Assertion Markup Language.
- xml 형식으로 인증하는 표준.

> 사실 요즘은 json 쓰잖아?

## SWT

- Simple Web Token.
- 딱히 표준이 없는듯? 그냥 토큰인데 단순한 형식인거 다 말하는 듯?
## Kerberos

## See also

- Authentication
  - [Create a Session Using Basic Authentication (vmware)](https://vdc-download.vmware.com/vmwb-repository/dcr-public/94b8bd8d-74ff-4fe3-b7a4-41ae31516ed7/1b42f3b5-8b31-4279-8b3f-547f6c7c5aa8/doc/GUID-536ED934-ECE3-4B17-B7E5-F8D0765C9ECB.html)
- Authorization
  - [Authorization (wiki)](https://en.wikipedia.org/wiki/Authorization)
- Common
  - [Cookie-based vs Session vs Token-based vs Claims-based authentications (softwareenginerring)](https://softwareengineering.stackexchange.com/questions/350092/cookie-based-vs-session-vs-token-based-vs-claims-based-authentications)
- Token
  - [What is token-based authentication? (stackoverflow)](https://stackoverflow.com/questions/1592534/what-is-token-based-authentication)
  - [Simple Web Token (SWT) (Microsoft)](https://docs.microsoft.com/en-us/previous-versions/azure/azure-services/hh781551(v=azure.100)?redirectedfrom=MSDN)
- OAuth
- [Kerberos (wiki)](https://en.wikipedia.org/wiki/Kerberos_(protocol))

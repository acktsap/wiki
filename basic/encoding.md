# Encoding

- [What is Encoding](#what-is-encoding)
- [Base64](#base64)
- [See also](#see-also)

## What is Encoding

## Base64

- 8-bit bytes를 6-bit로 쪼개서 encoding하는 것.
- RFC 4648 구성 문자열 : [A-Z] + [a-z] + [0-9] + `+` + `/` = 64 (26 + 26 + 10 + 2)
- 전송할 때 특수문자열 (eg. `:` in http request header)같은게 문제가 발생할 수 있으므로 전송에 문제 없는 character로 보내기 위해 encoding함.

## See also

- [Base64 (wiki)](https://en.wikipedia.org/wiki/Base64)

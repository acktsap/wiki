# URI

- [URI](#uri)
  - [History](#history)
  - [Syntax](#syntax)
  - [URI, URL, and URN](#uri-url-and-urn)
  - [References](#references)

## History

A Uniform Resource Identifier (URI) provides a simple and extensible
means for identifying a resource.

This document obsoletes [RFC2396], which merged "Uniform Resource
Locators" [RFC1738] and "Relative Uniform Resource Locators"
[RFC1808] in order to define a single, generic syntax for all URIs.
It obsoletes [RFC2732], which introduced syntax for an IPv6 address.

> Resource Identifier에 대한 표준을 제시하려고 만들어짐. URL(Uniform Resource Locators)같은 이전의 것들을 포함함.

## Syntax

Uniform

Uniformity provides several benefits.  It allows different types
of resource identifiers to be used in the same context, even when
the mechanisms used to access those resources may differ.  It
allows uniform semantic interpretation of common syntactic
conventions across different types of resource identifiers.  It
allows introduction of new types of resource identifiers without
interfering with the way that existing identifiers are used.

Resource

This specification does not limit the scope of what might be a
resource; rather, the term "resource" is used in a general sense
for whatever might be identified by a URI.  Familiar examples
include an electronic document, an image, a source of information
with a consistent purpose (e.g., "today's weather report for Los
Angeles"), a service (e.g., an HTTP-to-SMS gateway), and a
collection of other resources.  A resource is not necessarily
accessible via the Internet; e.g., human beings, corporations, and
bound books in a library can also be resources.

Identifier

An identifier embodies the information required to distinguish
what is being identified from all other things within its scope of
identification.  Our use of the terms "identify" and "identifying"
refer to this purpose of distinguishing one resource from all
other resources, regardless of how that purpose is accomplished
(e.g., by name, address, or context).

> Uniform (General한 형태), Resource (자원), Identifier (유일하게 가리키기 위한)

The scheme and path components are required, though the path may be
empty (no characters).  When authority is present, the path must
either be empty or begin with a slash ("/") character.  When
authority is not present, the path cannot begin with two slash
characters ("//").

The following are two example URIs and their component parts:

      foo://example.com:8042/over/there?name=ferret#nose
      \_/   \______________/\_________/ \_________/ \__/
      |           |            |            |        |
    scheme     authority       path        query   fragment
      |   _____________________|__
      / \ /                        \
      urn:example:animal:ferret:nose

> scheme:(//authority/path/query)  
> scheme:path:subpath

## URI, URL, and URN

A URI can be further classified as a locator, a name, or both.  The
term "Uniform Resource Locator" (URL) refers to the subset of URIs
that, in addition to identifying a resource, provide a means of
locating the resource by describing its primary access mechanism
(e.g., its network "location").  The term "Uniform Resource Name"
(URN) has been used historically to refer to both URIs under the
"urn" scheme [RFC2141], which are required to remain globally unique
and persistent even when the resource ceases to exist or becomes
unavailable, and to any other URI with the properties of a name.

> URL는 Locator, name 둘다 지칭하고 URL은 Locator만 지칭, URN은 resource가 사라지더라도 남아 있는 이름을 지칭.

## References

2005

https://tools.ietf.org/html/rfc3986
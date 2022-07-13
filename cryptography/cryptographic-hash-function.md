# Cryptographic hash function

- [What?](#what)
- [Good cryptographic hash function](#good-cryptographic-hash-function)
- [Degree of difficulty](#degree-of-difficulty)
- [Collision attack](#collision-attack)
  - [Classical collision attack](#classical-collision-attack)
  - [Chosen-prefix collision attack](#chosen-prefix-collision-attack)
  - [Attack (Digital signature)](#attack-digital-signature)
  - [Attack (Hash flooding)](#attack-hash-flooding)
- [Dictionary Attack](#dictionary-attack)
- [Salt](#salt)
- [MD5](#md5)
- [SHA family](#sha-family)
- [Key derivation function](#key-derivation-function)
  - [Scrypt](#scrypt)
  - [Bcrypt](#bcrypt)
  - [Argon2](#argon2)
- [See also](#see-also)

## What?

- 데이터의 무결성 확인을 위해 hash function인데 digest 로부터 역으로 가는게 거의 실행 불가능하게 만든것.
- digest를 가지고 key를 찾는 방법은 brute force밖에 답이 없는데 그게 너무 오래걸려서 거의 불가능한 수준으로.
- hash된 값을 digest라고도 부름.

## Good cryptographic hash function

- fast to compute.
- infeasible to generate a message that yields a given hash value.
- infeasible to find two different message with the same hash value.
- a small change to a message should change the hash value so extensively that a new hash value appears uncorrelated with the old hash value (avalanche effect).

## Degree of difficulty

"difficult" generally means "almost certainly beyond the reach of any adversary who must be prevented from breaking the system for as long as the security of the system is deemed important".

> ???

## Collision attack

- 서로 다른 message 값으로 같은 hash 값이 되는 message값을 찾는 공격.

### Classical collision attack

- Find two different messages m1 and m2 such that hash(m1) = hash(m2).

### Chosen-prefix collision attack

- Given two different prefixes p1 and p2, find two appendages m1 and m2 such that hash(p1 ∥ m1) = hash(p2 ∥ m2), where ∥ denotes the concatenation operation.

### Attack (Digital signature)

- digital signature algorithm이 원본의 큰 데이터를 대상으로는 처리를 못하기 때문에 큰 데이터에다 hash를 한 결과에다가 서명함.
- collision있는 경우 원본 데이터와 동일한 hash값이 계산되는 원본 데이터를 들이밀어서 이게 sign된 데이터라고 말할 수 있음. 

### Attack (Hash flooding)

- attacker가 같은 hash value를 가지는 데이터를 계속 보내서 서버에서 slow lookup을 하게 만듬. 

## Dictionary Attack

- 미리 잘 알려진 password (eg. qwer1234) 같은거에 대한 hash값을 만들어두고 그걸 이용해서 공격하는 것.

## Salt

- random data that is used as an additional input to a one-way function that hashes data, a password or passphrase.
- hash값에 대한 보안을 강화하기 위해 사용.

## MD5

- 1992년에 나옴.
- result : 128 bits.
- cryptographic hash function 목적으로 만들었으나 취약점이 발견되어서 그 용도로는 미사용. 그래도 SHA보다 계산은 빨라서 일반 hash function으로 사용.
- problem
  - 일반 컴퓨터로도 collision attack이 가능.

## SHA family

SHA-1

- Secure Hash Algorithm 1
- 1995년에 나옴.
- result : 160 bits.
- problem
  - collision attack이 가능.
- Cryptographic hash function 로는 쓰지 않는 것을 권고.
> git commit 값 계산 할 때 사용

SHA-2

- Secure Hash Algorithm 2
- 2001년에 나옴.
- result : 6개의 hash function, 결과는 224, 256, 384, 512 bits.
- SHA-224, SHA-256, SHA-384, SHA-512, SHA-512/224, SHA-512/256.
- Usage
  - bitcoin : verifying transaction, calculating proof of work.
  - ...

SHA-3

- Secure Hash Algorithm 3
- 2016년에 나옴.
> 아직 많이 안쓰는 듯.

## Key derivation function

- secret value를 가지고 새로운 key 값을 만드는 것.
- key를 가지고 기존의 secret value에 대한 정보를 얻지 못하게 하기 위해 사용.
- brute-force attack, dictionary attack을 방지하기 위해 느린게 좋음.
- password hashing에 많이 사용.

### Scrypt

### Bcrypt

### Argon2

## See also

- [Cryptographic hash function (wiki)](https://en.wikipedia.org/wiki/Cryptographic_hash_function)
- [Collision attack (wiki)](https://en.wikipedia.org/wiki/Collision_attack)
- [Salt (cryptography) (wiki)](https://en.wikipedia.org/wiki/Salt_(cryptography))
- [MD5 (wiki)](https://en.wikipedia.org/wiki/MD5)
- [SHA-1 (wiki)](https://en.wikipedia.org/wiki/SHA-1)
- [SHA-2 (wiki)](https://en.wikipedia.org/wiki/SHA-2)
- [SHA-3 (wiki)](https://en.wikipedia.org/wiki/SHA-3)
- [Key derivation function (wiki)](https://en.wikipedia.org/wiki/Key_derivation_function)
- [Scrypt (wiki)](https://en.wikipedia.org/wiki/Scrypt)
- [Bcrypt (wiki)](https://en.wikipedia.org/wiki/Bcrypt)
- [Bcrypt & Password Security - An Introduction)](https://www.youtube.com/watch?v=O6cmuiTBZVs)
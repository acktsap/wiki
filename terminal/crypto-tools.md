# Crypto tools

- [Openssl](#openssl)
- [gpg](#gpg)

## Openssl

```sh
# sha256 digest input.txt file
openssl dgst -sha256 input.txt

# encrypt input.txt file with aes-256-cbc
openssl aes-256-cbc -a -in input.txt

# encrypt input.txt file with aes-256-cbc with salt
openssl aes-256-cbc -a -salt -in input.txt

# decrypt input.txt file with aes-256-cbc
openssl aes-256-cbc -a -d -in input.txt
```

## gpg

- GnuPG Privacy Guard is complete and free implementation of the OpenPGP standard.

https://central.sonatype.org/publish/requirements/gpg

```sh
# generate key
gpg --gen-key

# check keys
gpg --list-keys

# send pub key to key server
gpg --keyserver keyserver.ubuntu.com --send-keys ${PUB_KEY}

# export keyrings
gpg --keyring secring.gpg --export-secret-keys > ~/.gnupg/secring.gpg
```
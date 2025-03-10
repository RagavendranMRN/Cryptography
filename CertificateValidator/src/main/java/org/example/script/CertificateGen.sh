#!/bin/sh

#Generate Private root Key.
openssl genpkey -algorithm RSA -out rootCA.key -pkeyopt rsa_keygen_bits:4096

#Generate Self-signed Root CA.
openssl req -x509 -new -nodes -key rootCA.key -sha256 -days 3650 -out rootCA.pem -subj "/C=US/ST=State/L=City/O=MyOrg/CN=MyCustomRootCA"

#!/bin/sh

#Generate Private root Key.
openssl genpkey -algorithm RSA -out rootCA.key -pkeyopt rsa_keygen_bits:4096
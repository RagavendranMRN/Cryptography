#!/bin/sh

#Generate Private root Key.
openssl genpkey -algorithm RSA -out rootCA.key -pkeyopt rsa_keygen_bits:4096

#Generate Self-signed Root CA.
openssl req -x509 -new -nodes -key rootCA.key -sha256 -days 3650 -out rootCA.pem -subj "/C=US/ST=State/L=City/O=MyOrg/CN=MyCustomRootCA"

#Add the Root CA in the trust Store
keytool -import -trustcacerts -file rootCA.pem -alias myCustomCA -keystore truststore.jks -storepass changeit

#Generate Private Key for the Server/Client
openssl genpkey -algorithm RSA -out server.key -pkeyopt rsa_keygen_bits:2048

#Generate Certificate Signing Request.
openssl req -new -key server.key -out server.csr -subj "/C=US/ST=State/L=City/O=MyOrg/CN=mywebsite.com"

#Generate Certificate
openssl x509 -req -in server.csr -CA rootCA.pem -CAkey rootCA.key -CAcreateserial -out certificate.pem -days 365 -sha256

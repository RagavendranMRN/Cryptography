## OpenSSL Commands

## Generating Private Key for the Root CA.
```openssl genpkey -algorithm RSA -out rootCA.key -pkeyopt rsa_keygen_bits:4096```

| Command | Option | Description |
|---------|--------|-------------|
| `openssl genpkey` | `-algorithm RSA` | Generates a private key using the RSA algorithm |
|  | `-out rootCA.key` | Specifies the output file for the generated private key |
|  | `-pkeyopt rsa_keygen_bits:4096` | Sets the key size to 4096 bits |

## Generate Self-signed Root CA.
```openssl req -x509 -new -nodes -key rootCA.key -sha256 -days 3650 -out rootCA.pem -subj "/C=US/ST=State/L=City/O=MyOrg/CN=MyCustomRootCA"```

| Command | Option | Description |
|---------|--------|-------------|
| `openssl req` | `-x509` | Creates a self-signed certificate |
|  | `-new` | Generates a new certificate request |
|  | `-nodes` | Does not encrypt the private key |
|  | `-key rootCA.key` | Specifies the private key to use for signing |
|  | `-sha256` | Uses SHA-256 for hashing |
|  | `-days 3650` | Sets the certificate validity period to 3650 days (10 years) |
|  | `-out rootCA.pem` | Specifies the output file for the root CA certificate |
|  | `-subj "/C=US/ST=State/L=City/O=MyOrg/CN=MyCustomRootCA"` | Defines the subject details of the certificate |

|Subject|Description|
|----------|----------|
|/C|Country|
|/ST|State|
|/O|Organization Name|
|/CN| Common Name (usually the Root CA name)|

## Add the Root CA in the trust Store
```keytool -import -trustcacerts -file rootCA.pem -alias myCustomCA -keystore truststore.jks -storepass changeit```

| Command | Option | Description |
|---------|--------|-------------|
| `keytool` | `-import` | Imports a certificate into a keystore |
|  | `-trustcacerts` | Specifies that the certificate is a trusted CA |
|  | `-file rootCA.pem` | Specifies the certificate file to import |
|  | `-alias myCustomCA` | Sets an alias name for the imported certificate |
|  | `-keystore truststore.jks` | Specifies the keystore file |
|  | `-storepass changeit` | Defines the password for the keystore |

## Generate Private Key for the Server/Client
```openssl genpkey -algorithm RSA -out server.key -pkeyopt rsa_keygen_bits:2048```

| Command | Option | Description |
|---------|--------|-------------|
| `openssl genpkey` | `-algorithm RSA` | Generates a private key for the server/client |
|  | `-out server.key` | Specifies the output file for the private key |
|  | `-pkeyopt rsa_keygen_bits:2048` | Sets the key size to 2048 bits |

## Request Certificate Signing.
```openssl req -new -key server.key -out server.csr -subj "/C=US/ST=State/L=City/O=MyOrg/CN=mywebsite.com"```

| Command | Option | Description |
|---------|--------|-------------|
| `openssl req` | `-new` | Creates a new certificate signing request (CSR) |
|  | `-key server.key` | Specifies the private key for the CSR |
|  | `-out server.csr` | Specifies the output file for the CSR |
|  | `-subj "/C=US/ST=State/L=City/O=MyOrg/CN=mywebsite.com"` | Defines the subject details for the CSR |

## Generate Certificate
```openssl x509 -req -in server.csr -CA rootCA.pem -CAkey rootCA.key -CAcreateserial -out certificate.pem -days 365 -sha256```

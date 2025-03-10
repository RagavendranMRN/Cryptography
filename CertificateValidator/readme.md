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
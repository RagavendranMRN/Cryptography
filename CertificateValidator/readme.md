## OpenSSL Commands

## Generating Private Key for the Root CA.
```openssl genpkey -algorithm RSA -out rootCA.key -pkeyopt rsa_keygen_bits:4096```

| Command | Option | Description |
|---------|--------|-------------|
| `openssl genpkey` | `-algorithm RSA` | Generates a private key using the RSA algorithm |
|  | `-out rootCA.key` | Specifies the output file for the generated private key |
|  | `-pkeyopt rsa_keygen_bits:4096` | Sets the key size to 4096 bits |

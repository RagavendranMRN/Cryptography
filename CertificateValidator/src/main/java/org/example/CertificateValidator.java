package org.example;

import javax.security.auth.x500.X500Principal;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;

public class CertificateValidator {
    public void ValidateCertificate() throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        /*  Load the Server Certificate. */
        FileInputStream fileInputStream = new FileInputStream("src/main/java/org/example/script/certificate.pem");
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(fileInputStream);

        /* Get the Certificate Issue for this Server Certificate */
        X500Principal issuer = certificate.getIssuerX500Principal();

        /* Load the TrustStore */
        FileInputStream fis = new FileInputStream("src/main/java/org/example/script/truststore.jks"); // Java Keystore
        KeyStore keystore = KeyStore.getInstance("JKS");
        keystore.load(fis, "changeit".toCharArray());

        /* Get the RootCA certificate from the TrustStore. */
        X509Certificate caCert = null;
        Enumeration<String> aliases = keystore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            X509Certificate candidate = (X509Certificate) keystore.getCertificate(alias);
            if (candidate != null && candidate.getSubjectX500Principal().equals(issuer)) {
                caCert = candidate;
                break;
            }
        }

        /*
            Get the public key from the Certificate of the RootCA which will be used for the Hashing
            To verify the Server's certificate authentication.
        */

        PublicKey caPublicKey = caCert.getPublicKey();

        /* Get the signature bytes from the Server Certificate */
        byte[] signatureBytes = certificate.getSignature();

        /*
            Get the "signed" part of the certificate (TBS = To Be Signed)
            This excludes the signature!

            This will be hashed again with the RootCA Public key and verified with the existing SignatureByte.
        */

        byte[] tbsCertificate = certificate.getTBSCertificate();

        /* Get signature algorithm */
        String sigAlgorithm = certificate.getSigAlgName(); // e.g., SHA256withRSA

        /* Verify the signature */
        Signature sig = Signature.getInstance(sigAlgorithm);
        sig.initVerify(caPublicKey);
        sig.update(tbsCertificate);

        /* Verify the hashed TBS value with the Signature which was already part of Certificate */
        if (sig.verify(signatureBytes)) {
            System.out.println("✅ Signature is VALID");
        } else {
            System.out.println("❌ Signature is INVALID");
        }
    }
}

/*
 * @copyright defined in LICENSE.txt
 */

package api;

import static java.util.UUID.randomUUID;
import hera.api.model.Aer;
import hera.api.model.BytesValue;
import hera.api.model.ChainIdHash;
import hera.api.model.RawTransaction;
import hera.api.model.Signature;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import hera.key.AergoKey;
import hera.spec.resolver.SignatureResolver;
import hera.spec.resolver.TransactionHashResolver;
import hera.util.HexUtils;
import hera.util.Sha256Utils;
import hera.util.pki.ECDSAKey;
import hera.util.pki.ECDSAKeyGenerator;
import hera.util.pki.ECDSASignature;
import java.math.BigInteger;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;

public class Test {

  public static void main(String[] args) throws Exception {
    AergoClient client = new AergoClientBuilder().build();
    ChainIdHash chainIdHash = client.getBlockchainOperation().getChainIdHash();
    client.cacheChainIdHash(chainIdHash);

    ECKeyPair ecKeyPair = Keys.createEcKeyPair();
    BigInteger d = ecKeyPair.getPrivateKey();

    ECDSAKey ecdsaKey = new ECDSAKeyGenerator().create(d);
    AergoKey key = new AergoKey(ecdsaKey);

    RawTransaction rawTransaction = RawTransaction.newBuilder(chainIdHash)
        .from(key.getAddress())
        .to(key.getAddress())
        .amount(Aer.AERGO_ONE)
        .nonce(3L)
        .build();
    TxHash withoutSignature = TransactionHashResolver.calculateHash(rawTransaction);
    byte[] message = withoutSignature.getBytesValue().getValue();

    Sign.SignatureData ethSignature = Sign.signMessage(message, ecKeyPair, false);

    BigInteger r = new BigInteger(1, ethSignature.getR());
    BigInteger s = new BigInteger(1, ethSignature.getS());
    ECDSASignature aergoSignature = new ECDSASignature(r, s);

    Signature signature = SignatureResolver.serialize(aergoSignature, ecdsaKey.getParams().getN());
    TxHash withSignature = TransactionHashResolver.calculateHash(rawTransaction, signature);
    Transaction transaction = Transaction.newBuilder()
        .rawTransaction(rawTransaction)
        .signature(signature)
        .hash(withSignature)
        .build();
    
    client.getTransactionOperation().commit(transaction);


    // System.out.println("Eth R: " + r);
    // System.out.println("Eth S: " + s);
    //
    // System.out.println(ecdsaKey.verify(message, new ECDSASignature(r, s)));
    // System.out.println(ecdsaKey.verify(message, aergoSignature));
    // System.out.println(aergoSignature);
    // System.out.println(HexUtils.dump(ethSignature.getR()));
    // System.out.println(HexUtils.dump(aergoSignature.getR().toByteArray()));
  }

}

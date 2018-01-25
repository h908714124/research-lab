package how.monero.hodl.bulletproof;

import static how.monero.hodl.bulletproof.OptimizedLogBulletproof.G;
import static how.monero.hodl.bulletproof.OptimizedLogBulletproof.Gi;
import static how.monero.hodl.bulletproof.OptimizedLogBulletproof.H;
import static how.monero.hodl.bulletproof.OptimizedLogBulletproof.Hi;
import static how.monero.hodl.bulletproof.OptimizedLogBulletproof.N;
import static how.monero.hodl.bulletproof.OptimizedLogBulletproof.PROVE;
import static how.monero.hodl.bulletproof.OptimizedLogBulletproof.VERIFY;
import static how.monero.hodl.bulletproof.OptimizedLogBulletproof.logN;
import static how.monero.hodl.crypto.CryptoUtil.getHpnGLookup;
import static how.monero.hodl.crypto.Scalar.randomScalar;

import how.monero.hodl.crypto.Curve25519Point;
import how.monero.hodl.crypto.Scalar;
import java.math.BigInteger;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OptimizedLogBulletproofTest {

  @Test
  void main() {
    // Number of bits in the range
    N = 64;
    logN = 6; // its log, manually

    // Set the curve base points
    G = Curve25519Point.G;
    H = Curve25519Point.hashToPoint(G);
    Gi = new Curve25519Point[N];
    Hi = new Curve25519Point[N];
    for (int i = 0; i < N; i++) {
      Gi[i] = getHpnGLookup(2 * i);
      Hi[i] = getHpnGLookup(2 * i + 1);
    }

    // Run a bunch of randomized trials
    Random rando = new Random();
    int TRIALS = 250;
    int count = 0;

    while (count < TRIALS) {
      long amount = rando.nextLong();
      if (amount > Math.pow(2, N) - 1 || amount < 0)
        continue;

      OptimizedLogBulletproof.ProofTuple proof = PROVE(new Scalar(BigInteger.valueOf(amount)), randomScalar());
      if (!VERIFY(proof))
        Assertions.fail("Test failed");

      count += 1;
    }
  }


}

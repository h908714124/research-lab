package how.monero.hodl.bulletproof;

import static how.monero.hodl.bulletproof.MultiBulletproof.G;
import static how.monero.hodl.bulletproof.MultiBulletproof.M;
import static how.monero.hodl.bulletproof.MultiBulletproof.N;
import static how.monero.hodl.bulletproof.MultiBulletproof.PROVE;
import static how.monero.hodl.bulletproof.MultiBulletproof.VERIFY;
import static how.monero.hodl.bulletproof.MultiBulletproof.logMN;
import static how.monero.hodl.crypto.CryptoUtil.getHpnGLookup;
import static how.monero.hodl.crypto.Scalar.randomScalar;

import how.monero.hodl.crypto.Curve25519Point;
import how.monero.hodl.crypto.Scalar;
import java.math.BigInteger;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MultiBulletproofTest {

  @Test
  void main() {
    // Test parameters
    N = 64; // number of bits in amount range (so amounts are 0..2^(N-1))
    M = 4; // number of outputs (must be a power of 2)
    logMN = 8; // must be manually set to log_2(MN)
    int TRIALS = 25; // number of randomized trials to run

    // Set the curve base points
    G = Curve25519Point.G;
    MultiBulletproof.H = Curve25519Point.hashToPoint(G);
    MultiBulletproof.Gi = new Curve25519Point[M * N];
    MultiBulletproof.Hi = new Curve25519Point[M * N];
    for (int i = 0; i < M * N; i++) {
      MultiBulletproof.Gi[i] = getHpnGLookup(2 * i);
      MultiBulletproof.Hi[i] = getHpnGLookup(2 * i + 1);
    }

    // Run a bunch of randomized trials
    Random rando = new Random();
    int count = 0;

    Scalar[] amounts = new Scalar[M];
    Scalar[] masks = new Scalar[M];
    while (count < TRIALS) {
      for (int j = 0; j < M; j++) {
        long amount = -1L;
        while (amount > Math.pow(2, N) - 1 || amount < 0L)
          amount = rando.nextLong();
        amounts[j] = new Scalar(BigInteger.valueOf(amount));
        masks[j] = randomScalar();
      }

      MultiBulletproof.ProofTuple proof = PROVE(amounts, masks);
      if (!VERIFY(proof))
        Assertions.fail("Test failed");

      count += 1;
    }
  }
}

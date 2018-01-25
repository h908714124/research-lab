package how.monero.hodl.bulletproof;

import static how.monero.hodl.bulletproof.LogBulletproof.G;
import static how.monero.hodl.bulletproof.LogBulletproof.Gi;
import static how.monero.hodl.bulletproof.LogBulletproof.H;
import static how.monero.hodl.bulletproof.LogBulletproof.Hi;
import static how.monero.hodl.bulletproof.LogBulletproof.N;
import static how.monero.hodl.bulletproof.LogBulletproof.PROVE;
import static how.monero.hodl.bulletproof.LogBulletproof.VERIFY;
import static how.monero.hodl.bulletproof.LogBulletproof.logN;
import static how.monero.hodl.crypto.CryptoUtil.getHpnGLookup;
import static how.monero.hodl.crypto.Scalar.randomScalar;

import how.monero.hodl.crypto.Curve25519Point;
import how.monero.hodl.crypto.Scalar;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LogBulletproofTest {

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

      LogBulletproof.ProofTuple proof = PROVE(new Scalar(BigInteger.valueOf(amount)), randomScalar());
      if (!VERIFY(proof))
        Assertions.fail("Test failed");

      count += 1;
    }
  }

  @Test
  void test2() {
    Scalar[] ar = Scalar.bigIntegerArrayToScalarArray(new BigInteger[]{BigInteger.ZERO});
    BigInteger[] bigIntegers = Scalar.scalarArrayToBigIntegerArray(ar);
    System.out.println(Arrays.toString(bigIntegers));
  }

  @Test
  void test3() {
    Scalar[] z = Scalar.bigIntegerArrayToScalarArray(new BigInteger[]{BigInteger.ZERO});
    Scalar[] o = Scalar.bigIntegerArrayToScalarArray(new BigInteger[]{BigInteger.ONE});
    Scalar diff = o[0].sub(z[0]);
    Scalar.printScalarArray(new Scalar[]{diff});
  }
}

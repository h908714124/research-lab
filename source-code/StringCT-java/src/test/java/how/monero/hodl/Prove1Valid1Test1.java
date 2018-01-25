package how.monero.hodl;

import how.monero.hodl.crypto.Curve25519Point;
import how.monero.hodl.crypto.Scalar;
import org.junit.jupiter.api.Test;

import static how.monero.hodl.crypto.CryptoUtil.COMb;
import static how.monero.hodl.ringSignature.StringCT.*;

class Prove1Valid1Test1 {

  @Test
  void test() {

    Scalar[][] b = new Scalar[][]{
      new Scalar[]{Scalar.ONE, Scalar.ZERO},
      new Scalar[]{Scalar.ZERO, Scalar.ONE}
    };

    Scalar r = Scalar.ONE;

    Proof1 P = PROVE1(b, r);

    Curve25519Point B = COMb(b, r);

    System.out.println("VALID1 returns " + VALID1(B, P));

  }
}

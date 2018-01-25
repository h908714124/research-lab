package how.monero.hodl;

import static how.monero.hodl.crypto.CryptoUtil.COMeg;
import static how.monero.hodl.ringSignature.StringCT.PROVE2;
import static how.monero.hodl.ringSignature.StringCT.Proof2;
import static how.monero.hodl.ringSignature.StringCT.VALID2;

import how.monero.hodl.crypto.Curve25519PointPair;
import how.monero.hodl.crypto.Scalar;
import org.junit.jupiter.api.Test;

class Prove2Valid2Test1a {

  @Test
  void test() {

    System.out.println("Test: " + new Object() {
    }.getClass().getEnclosingClass().getName());

    Scalar r = Scalar.ONE;

    Curve25519PointPair[] co = new Curve25519PointPair[]{
      COMeg(Scalar.ONE, Scalar.ONE),
      COMeg(Scalar.ONE, Scalar.ONE),
      COMeg(Scalar.ONE, Scalar.ONE),
      COMeg(Scalar.ONE, Scalar.ONE),
      COMeg(Scalar.ONE, Scalar.ONE),
      COMeg(Scalar.ZERO, Scalar.ONE),
      COMeg(Scalar.ONE, Scalar.ONE),
      COMeg(Scalar.ONE, Scalar.ONE),
      COMeg(Scalar.ONE, Scalar.ONE),
    };

    int iAsterisk = 5;

    int inputs = 1;
    int decompositionBase = 3;
    int decompositionExponent = 2;

    Proof2 P2 = PROVE2(co, iAsterisk, r, inputs, decompositionBase, decompositionExponent);

    System.out.println("VALID2 result: " + VALID2(decompositionBase, P2, co));

  }


}

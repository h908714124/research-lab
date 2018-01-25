package how.monero.hodl;

import static how.monero.hodl.crypto.CryptoUtil.COMeg;
import static how.monero.hodl.ringSignature.StringCT.PROVE2;
import static how.monero.hodl.ringSignature.StringCT.Proof2;
import static how.monero.hodl.ringSignature.StringCT.VALID2;

import how.monero.hodl.crypto.Curve25519Point;
import how.monero.hodl.crypto.Curve25519PointPair;
import how.monero.hodl.crypto.Scalar;
import java.util.Date;
import org.junit.jupiter.api.Test;

class Prove2Valid2Test1b {

  @Test
  void test() {

    System.out.println("Test: " + new Object() {
    }.getClass().getEnclosingClass().getName());

    Scalar r = Scalar.ONE;

    for (int k = 1; k <= 12 /* k > 12 may take a long time */; k++) {

      System.out.println("---------------------------------------------------------------------------");
      int len = (int) Math.pow(2, k);
      Curve25519PointPair[] co = new Curve25519PointPair[len];
      for (int i = 0; i < len; i++) co[i] = COMeg(Scalar.intToScalar(i), Scalar.ONE);

      int iAsterisk = 0;

      int inputs = 1;
      int decompositionBase = 2;
      int decompositionExponent = k;

      System.out.println("k: " + k);
      System.out.println("decompositionBase: " + decompositionBase);
      System.out.println("decompositionExponent: " + decompositionExponent);

      Curve25519Point.scalarMults = 0;
      Curve25519Point.scalarBaseMults = 0;
      long startMs = new Date().getTime();

      Proof2 P2 = PROVE2(co, iAsterisk, r, inputs, decompositionBase, decompositionExponent);

      System.out.println("PROVE2 duration: " + (new Date().getTime() - startMs) + " ms");
      System.out.println("PROVE2 ScalarMults: " + Curve25519Point.scalarMults);
      System.out.println("PROVE2 BaseScalarMults: " + Curve25519Point.scalarBaseMults);
      Curve25519Point.scalarMults = 0;
      Curve25519Point.scalarBaseMults = 0;
      startMs = new Date().getTime();

      System.out.println("VALID2 result: " + VALID2(decompositionBase, P2, co));

      System.out.println("VALID2 ScalarMults: " + Curve25519Point.scalarMults);
      System.out.println("VALID2 BaseScalarMults: " + Curve25519Point.scalarBaseMults);

      System.out.println("VALID2 duration: " + (new Date().getTime() - startMs) + " ms");
    }
  }
}

package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * All-DU-pairs tests for gcd(). Line numbers are lines of DataFlowTargets.java.
 * Notation: x(13>16) = def of x at line 13 reaches use at line 16.
 * y(13>15T) / y(13>15F) = p-use at line 15, true / false branch.
 */
class GcdTest {

    // G1: zero loop iterations (y == 0 at once)
    @Test
    @DisplayName("G1 gcd(7,0)=7 | x(13>20) y(13>15F)")
    void g1_zeroIterations_gcd7and0_is7() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    // G2: two iterations
    @Test
    @DisplayName("G2 gcd(12,8)=4 | x(13>16) x(17>16) x(17>20) y(13>15T) y(13>16) y(13>17) y(18>15T) y(18>15F) y(18>16) y(18>17) tmp(16>18)")
    void g2_twoIterations_gcd12and8_is4() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }

    // G3: exactly one iteration (x = 0)
    @Test
    @DisplayName("G3 gcd(0,5)=5 | x(13>16) x(17>20) y(13>15T) y(13>16) y(13>17) y(18>15F) tmp(16>18)")
    void g3_oneIteration_gcd0and5_is5() {
        assertEquals(5, DataFlowTargets.gcd(0, 5));
    }
}

package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for bonus(). Line numbers are lines of DataFlowTargets.java.
 * Notation: var(def>use) e.g. score(49>54) = def of score at line 49 reaches the use at line 54.
 */
class BonusTest {

    // ===== Stage 1: ALL-DEFS (2 tests are the minimum, see README) =====

    // B1: present < threshold, streak of 5, a gap, then one more day.
    // score(49>54), score(54>56), present(35>40), present(40>40), present(40>50F),
    // streak(36>41), streak(41>41), streak(46>41), streak(41>42), streak(41>43),
    // best(37>42T), best(43>42T/F), best(43>53T), a(38>39T/F)
    @Test
    @DisplayName("B1 bonus({1,1,1,1,1,0,1},10)=3: present<threshold, best>=5")
    void b1_allDefs_presentBelowThreshold_streak5_score3() {
        assertEquals(3, DataFlowTargets.bonus(new int[]{1, 1, 1, 1, 1, 0, 1}, 10));
    }

    // B2: present >= threshold, short streak.
    // score(51>56), present(40>50T), threshold(31>50T)
    @Test
    @DisplayName("B2 bonus({1,1},1)=5: present>=threshold, best<5")
    void b2_allDefs_presentAtThreshold_shortStreak_score5() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{1, 1}, 1));
    }
}

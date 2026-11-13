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

    // ===== Stage 2: exception paths (Branch 100%) =====

    // B3: attendance(31>32T)
    @Test
    @DisplayName("B3 bonus(null,3) throws: attendance(31>32T)")
    void b3_nullAttendance_throws() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(null, 3));
    }

    // B4: threshold(31>32T)
    @Test
    @DisplayName("B4 bonus({1,1},-1) throws: threshold(31>32T)")
    void b4_negativeThreshold_throws() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(new int[]{1, 1}, -1));
    }

    // ===== Stage 3: ALL-DU-PAIRS (remaining pairs) =====

    // B5: no loop iterations, threshold 0: present(35>50T), best(37>53F), score(51>56)
    @Test
    @DisplayName("B5 bonus({},0)=5: present(35>50T) best(37>53F)")
    void b5_emptyArray_thresholdZero_score5() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{}, 0));
    }

    // B6: no loop iterations, threshold 1: present(35>50F), score(49>56)
    @Test
    @DisplayName("B6 bonus({},1)=0: present(35>50F) score(49>56)")
    void b6_emptyArray_thresholdOne_score0() {
        assertEquals(0, DataFlowTargets.bonus(new int[]{}, 1));
    }

    // B7: run of 2, gap, run of 1: best(43>42F), best(43>53F), streak(41>42F)
    @Test
    @DisplayName("B7 bonus({1,1,0,1},1)=5: shorter run after longer run")
    void b7_gapThenShorterRun_score5() {
        assertEquals(5, DataFlowTargets.bonus(new int[]{1, 1, 0, 1}, 1));
    }

    // B8: present >= threshold and streak 5: score(51>54), score(54>56), best(43>53T)
    @Test
    @DisplayName("B8 bonus({1,1,1,1,1},3)=8: score(51>54)")
    void b8_streak5_thresholdMet_score8() {
        assertEquals(8, DataFlowTargets.bonus(new int[]{1, 1, 1, 1, 1}, 3));
    }
}

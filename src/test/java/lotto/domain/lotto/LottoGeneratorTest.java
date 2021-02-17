package lotto.domain.lotto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoGeneratorTest {

    @Test
    @DisplayName("로또 그룹 생성")
    void generateLottoGroup() {
        LottoGenerator lottoGenerator = new LottoGenerator();
        assertThat(lottoGenerator.generateLottos(3).getCount()).isEqualTo(3);
    }
}
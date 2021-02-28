package lotto.domain.lotto;

import lotto.domain.number.LottoNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class LottoGroupTest {
    @Test
    @DisplayName("로또 그룹의 merge를 수행한다.")
    void add_addLottosViaMergingWithOtherLottoGroup() {
        LottoNumbers lottoNumbers1 = new LottoNumbers(Arrays.asList(
                LottoNumber.from(1),
                LottoNumber.from(2),
                LottoNumber.from(3),
                LottoNumber.from(4),
                LottoNumber.from(5),
                LottoNumber.from(6)
        ));

        LottoNumbers lottoNumbers2 = new LottoNumbers(Arrays.asList(
                LottoNumber.from(4),
                LottoNumber.from(5),
                LottoNumber.from(6),
                LottoNumber.from(7),
                LottoNumber.from(8),
                LottoNumber.from(9)
        ));

        LottoGroup lottoGroup1 = new LottoGroup(Arrays.asList(
                lottoNumbers1
        ));

        LottoGroup lottoGroup2 = new LottoGroup(Arrays.asList(
                lottoNumbers2
        ));

        LottoGroup addedLottoGroup = lottoGroup1.add(lottoGroup2);

        assertThat(addedLottoGroup.getLottos().get(0).getMatchCount(lottoNumbers1)).isEqualTo(6);
        assertThat(addedLottoGroup.getLottos().get(1).getMatchCount(lottoNumbers2)).isEqualTo(6);
    }
}

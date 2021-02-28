package lotto.domain.lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.Arrays;
import lotto.domain.number.LottoNumber;
import lotto.domain.number.Number;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoNumbersTest {

    @Test
    @DisplayName("로또 넘버에 중복이 있으면 예외")
    public void duplicateLottoNumber() {
        assertThatIllegalArgumentException().isThrownBy(
            () -> new LottoNumbers(
                Arrays.asList(
                    LottoNumber.from(1),
                    LottoNumber.from(1),
                    LottoNumber.from(2),
                    LottoNumber.from(3),
                    LottoNumber.from(4),
                    LottoNumber.from(5)
                ))).withMessage("로또 넘버에 중복이 있습니다.");
    }

    @Test
    @DisplayName("로또넘버가 6개가 아니면 예외")
    public void lottoNumbersMustHaveSixLottoNumbers() {
        assertThatIllegalArgumentException().isThrownBy(
            () -> new LottoNumbers(
                Arrays.asList(
                    LottoNumber.from(1),
                    LottoNumber.from(45),
                    LottoNumber.from(2),
                    LottoNumber.from(3),
                    LottoNumber.from(4)
                ))).withMessage("로또 넘버가 6개가 아닙니다.");
    }
    
    @Test
    @DisplayName("로또번호들이 String으로 들어오면 LottoNumbers 반환")
    public void crateLottoNumbersUsingStringInput() {
        LottoNumbers lottoNumbers = LottoNumbers.valueOf("1 ,2 ,3 ,4 ,5, 6");
        assertThat(lottoNumbers.getMatchCount(new LottoNumbers(
                Arrays.asList(
                        LottoNumber.from(1),
                        LottoNumber.from(2),
                        LottoNumber.from(3),
                        LottoNumber.from(4),
                        LottoNumber.from(5),
                        LottoNumber.from(6)
                )
        ))).isEqualTo(6);
    }
}
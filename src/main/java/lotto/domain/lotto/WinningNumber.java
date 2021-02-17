package lotto.domain.lotto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lotto.domain.number.LottoNumber;
import lotto.domain.number.Number;
import lotto.domain.number.PayOut;
import lotto.domain.rank.RankFactory;

public class WinningNumber {

    private final LottoNumbers lottoNumbers;
    private final LottoNumber bonusNumber;

    public WinningNumber(String lottoNumber, String bonusNumber) {
        LottoNumbers extractedLottoNumbers =
            getLottoNumbersFromStringLottoNumberList(getSplitLottoNumber(lottoNumber));

        validateBonusNumberFormat(bonusNumber);
        LottoNumber extractedBonusNumber = new LottoNumber(new Number(bonusNumber));
        validateDuplicateBonusNumberWithLottoNumbers(extractedLottoNumbers, extractedBonusNumber);

        this.lottoNumbers = extractedLottoNumbers;
        this.bonusNumber = extractedBonusNumber;
    }

    private LottoNumbers getLottoNumbersFromStringLottoNumberList(List<String> lottoNumbers) {
        return new LottoNumbers(lottoNumbers.stream()
            .map(lottoNumber -> new LottoNumber(new Number(lottoNumber.trim())))
            .collect(Collectors.toList()));
    }

    private List<String> getSplitLottoNumber(String lottoNumber) {
        return Arrays.asList(lottoNumber.split(",", -1));
    }

    private void validateBonusNumberFormat(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("보너스 볼의 하나의 숫자로 이루어져야 합니다.");
        }
    }

    private void validateDuplicateBonusNumberWithLottoNumbers(LottoNumbers lottoNumbers,
        LottoNumber bonusNumber) {
        if (lottoNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("보너스 번호는 로또 번호와 달라야 합니다.");
        }
    }

    public LottoNumbers getLottoNumbers() {
        return lottoNumbers;
    }

    public WinningStatistics getResult(LottoGroup lottoGroup, PayOut payOut) {
        Map<Integer, Long> result = lottoGroup.getLotties().stream()
            .map(this::getRank)
            .filter(rank -> RankFactory.FAIL.getRank() != rank)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        IntStream.range(1, RankFactory.values().length)
            .filter(rank -> !result.containsKey(rank))
            .forEach(rank -> result.put(rank, 0L));

        return new WinningStatistics(result, payOut);
    }

    private int getRank(LottoNumbers lottoNumbers) {
        return RankFactory.getRank(
            this.lottoNumbers.getMatchCount(lottoNumbers),
            lottoNumbers.contains(bonusNumber)
        ).getRank();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WinningNumber that = (WinningNumber) o;
        return Objects.equals(lottoNumbers, that.lottoNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lottoNumbers);
    }
}
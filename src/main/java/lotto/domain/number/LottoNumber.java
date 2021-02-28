package lotto.domain.number;

import java.util.Objects;
import java.util.stream.IntStream;

public class LottoNumber {

    private static final int NUMBER_MINIMUM = 1;
    private static final int NUMBER_MAXIMUM = 45;

    private static final LottoNumber[] caches;

    static {
        caches = new LottoNumber[NUMBER_MAXIMUM + 1];
        IntStream.rangeClosed(NUMBER_MINIMUM, NUMBER_MAXIMUM)
                .forEach(index -> {
                    System.out.println(index);
                    caches[index] = new LottoNumber(index);
                });
    }

    private final Number number;

    public LottoNumber(Number number) {
        validateRange(number);
        this.number = number;
    }

    public LottoNumber(int number) {
        this(new Number(number));
    }

    public LottoNumber(String number) {
        this(new Number(number));
    }

    private void validateRange(Number number) {
        if (number.getValueAsInt() < NUMBER_MINIMUM || number.getValueAsInt() > NUMBER_MAXIMUM) {
            throw new IllegalArgumentException("범위 밖의 로또 번호 입니다.");
        }
    }

    public static LottoNumber from(int number) {
        if (NUMBER_MINIMUM <= number && number < +NUMBER_MAXIMUM) {
            return caches[number];
        }

        return new LottoNumber(number);
    }

    public static LottoNumber from(String number) {
        Number castedNumber = new Number(number);
        int intNumber = castedNumber.getValueAsInt();

        return from(intNumber);
    }

    public int getValueAsInt() {
        return number.getValueAsInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LottoNumber that = (LottoNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return String.valueOf(number.getValueAsInt());
    }
}
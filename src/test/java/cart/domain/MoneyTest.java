package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.IllegalMoneyException;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class MoneyTest {

    @Test
    @DisplayName("금액은 음수일 수 없다.")
    void createMoney_negative_fail() {
        // when, then
        assertThatThrownBy(() -> new Money(BigDecimal.valueOf(-1)))
            .isInstanceOf(IllegalMoneyException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @DisplayName("0보다 큰 금액을 생성할 수 있다.")
    void createMoney_success(int value) {
        // when, then
        assertDoesNotThrow(() -> new Money(BigDecimal.valueOf(value)));
    }

    @Test
    @DisplayName("금액을 더할 수 있다.")
    void add_success() {
        // given
        Money standard = new Money(BigDecimal.valueOf(1000));
        Money other = new Money(BigDecimal.valueOf(500));

        // when
        Money result = standard.add(other);

        // then
        assertThat(result).isEqualTo(new Money(BigDecimal.valueOf(1500)));
    }

    @Test
    @DisplayName("금액을 차감할 수 있다.")
    void subtract_success() {
        // given
        Money standard = new Money(BigDecimal.valueOf(1000));
        Money other = new Money(BigDecimal.valueOf(500));

        // when
        Money result = standard.subtract(other);

        // then
        assertThat(result).isEqualTo(new Money(BigDecimal.valueOf(500)));
    }

    @Test
    @DisplayName("원액보다 큰 금액을 차감할 수 없다.")
    void subtract_moreThanStandard_fail() {
        // given
        Money standard = new Money(BigDecimal.valueOf(500));
        Money other = new Money(BigDecimal.valueOf(550));

        // when, then
        assertThatThrownBy(() -> standard.subtract(other))
            .isInstanceOf(IllegalMoneyException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"230:6", "0:0"}, delimiter = ':')
    @DisplayName("원액에 비율을 곱해서 반올림 된 금액을 계산할 수 있다.")
    void multiplyRateAndRound(int standardValue, int resultValue) {
        Money money = new Money(BigDecimal.valueOf(standardValue));
        double rate = 2.5 / 100;

        // when
        Money result = money.multiplyRateAndRound(rate);

        // then
        Money expected = new Money(BigDecimal.valueOf(resultValue));
        assertThat(result).isEqualTo(expected);
    }
}

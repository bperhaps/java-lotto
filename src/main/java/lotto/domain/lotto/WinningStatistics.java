package lotto.domain.lotto;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import lotto.domain.number.PayOut;
import lotto.domain.rank.RankFactory;
import lotto.domain.rank.Ranking;

public class WinningStatistics {

    private final List<Ranking> rankings;
    private final double yield;

    public WinningStatistics(Map<Integer, Long> gameResult, PayOut payOut) {
        this.rankings = IntStream.range(1, RankFactory.values().length)
            .boxed()
            .map(key -> RankFactory.createRanking(key, gameResult.getOrDefault(key, 0L)))
            .sorted(comparingInt(Ranking::getRank))
            .collect(toList());

        this.yield = rankings.stream()
            .mapToDouble(r -> r.getWinnings() * r.getCount())
            .sum() / payOut.toInt();
    }

    public List<Ranking> getRankings() {
        return Collections.unmodifiableList(rankings);
    }

    public double getYield() {
        return yield;
    }
}
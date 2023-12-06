package bib.aoc.day4;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.pow;
import static java.util.function.Predicate.not;

@Getter
@RequiredArgsConstructor
@Slf4j
public class Card implements Comparable<Card> {

    private final Set<Integer> winningNumbers;
    private final List<Integer> numbersYouHave;
    private final int id;
    private int nbInstances=1;

    private static final Pattern pattern= Pattern.compile("Card +(?<id>\\d+):(?<winnings>[ \\d]*)\\|(?<yours>[ \\d]*)");

    public static Card of(@NonNull String line) {
        var matcher=pattern.matcher(line);
        if(!matcher.matches()){
            log.error(STR."no match for \{line}");
            return null;
        };
        var winningNumbers=toStream(matcher.group("winnings")).collect(Collectors.toSet());
        var yours=toStream(matcher.group("yours")).toList();
        var id=Integer.parseInt(matcher.group("id"));
        return new Card(winningNumbers,yours,id);
    }

    private static Stream<Integer> toStream(String numbers) {
        return Arrays.stream(numbers.split(" "))
                .filter(not(String::isEmpty))
                .map(Integer::valueOf);
    }

    public int points() {
        var nbMatchingNumbers= nbMatchingNumbers();
        return nbMatchingNumbers > 0 ? calcPoints(nbMatchingNumbers) : 0;
    }

    private long nbMatchingNumbers() {
        return numbersYouHave.stream().filter(winningNumbers::contains).count();
    }

    private static int calcPoints(long nbMatch) {
        var points=pow(2,nbMatch-1);
        int result=(int)points;
        if (result==points){
            return result;
        }else{
            throw new ArithmeticException(STR."\{points} cannot be cast to int (find \{result})");
        }
    }

    @Override
    public int compareTo(Card card) {
        return id-card.id;
    }

    public void computeNumberOfMatchesAndUpdateCopies(@NonNull List<Card> cards) {
        int nbMatchingNumbers= (int) nbMatchingNumbers();
        updateFurtherCards(cards, nbMatchingNumbers);
    }
    private void updateFurtherCards(List<Card> cards, int nbMatchingNumbers) {
        for(int i=id+1;i<=id+nbMatchingNumbers;i++){
            cards.get(i-1).addCopies(nbInstances);
        }
    }

    private void addCopies(int i) {
        nbInstances+=i;
    }

}

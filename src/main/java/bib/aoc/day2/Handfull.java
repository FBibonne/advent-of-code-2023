package bib.aoc.day2;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Integer.parseInt;

@Slf4j
public record Handfull(int green, int red, int blue) {

    public static Handfull of(String source) {
        // 12 green, 4 blue, 7 red
        int green=0, red=0, blue =0;
        for(String part:source.split(",")){
            var colAndNum=part.trim().split(" ");

            switch (colAndNum[1]){
                case "green" : green= parseInt(colAndNum[0]); break;
                case "blue" : blue= parseInt(colAndNum[0]); break;
                case "red" : red= parseInt(colAndNum[0]); break;
                default:
                    log.error(STR."Erreur \{colAndNum[1]}");
            }
        }
        return new Handfull(green, red, blue);
    }

    public boolean isPermitted() {
        return red()<= 12 && green <= 13 && blue <= 14;
    }
}

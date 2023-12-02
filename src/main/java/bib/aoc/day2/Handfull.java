package bib.aoc.day2;

public record Handfull(int green, int red, int blue) {
    public static Handfull of(String source) {
        // 12 green, 4 blue, 7 red
        int green=0, red=0, blue =0;
        for(String part:source.split(",")){
            var colAndNum=part.trim().split(" ");

            switch (colAndNum[1]){
                case "green" : green=Integer.valueOf(colAndNum[0]); break;
                case "blue" : blue=Integer.valueOf(colAndNum[0]); break;
                case "red" : red=Integer.valueOf(colAndNum[0]); break;
                default:
                    System.out.println(STR."Erreur \{colAndNum[1]}");
            }
        }
        return new Handfull(green, red, blue);
    }

    public boolean isPermitted() {
        return red()<= 12 && green <= 13 && blue <= 14;
    }
}

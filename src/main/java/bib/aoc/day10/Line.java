package bib.aoc.day10;

import lombok.NonNull;

import java.util.Set;

public record Line(int x1, int y1, int x2, int y2) {
    public Line(Point previousPosition, Point position) {
        this(previousPosition.getX(), previousPosition.getY(), position.getX(), position.getY());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Line(int x1, int y1, int x2, int y2) && (
                (x1 == this.x1 && y1 == this.y1 && x2 == this.x2 && y2 == this.y2) || (x2 == this.x1 && y2 == this.y1 && x1 == this.x2 && y1 == this.y2)
        );
    }

    public boolean isHorizontalLessThan(@NonNull Line southLine) {
        return ( (southLine.y1==this.y1 && southLine.y2==this.y2) || (southLine.y1==this.y2 && southLine.y2==this.y1))
                && southLine.x1==southLine.x2 && this.x1==this.x2
                && southLine.x1>=this.x1;
    }

    public boolean isHorizontalMoreThan(@NonNull Line northLine) {
        return ( (northLine.y1==this.y1 && northLine.y2==this.y2) || (northLine.y1==this.y2 && northLine.y2==this.y1))
                && northLine.x1==northLine.x2 && this.x1==this.x2
                && this.x1>=northLine.x1;
    }

    public boolean isVerticalLessThan(Line eastLine) {
        return ( (eastLine.x1==this.x1 && eastLine.x2==this.x2) || (eastLine.x1==this.x2 && eastLine.x2==this.x1))
                && eastLine.y1==eastLine.y2 && this.y1==this.y2
                && this.y1<=eastLine.y1;
    }

    public boolean isVerticalMoreThan(Line westLine) {
        return ( (westLine.x1==this.x1 && westLine.x2==this.x2) || (westLine.x1==this.x2 && westLine.x2==this.x1))
                && westLine.y1==westLine.y2 && this.y1==this.y2
                && this.y1>=westLine.y1;
    }
}

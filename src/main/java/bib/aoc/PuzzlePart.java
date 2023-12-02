package bib.aoc;

import bib.utils.PathToFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface PuzzlePart {

    ClassLoader classLoader=PuzzlePart.class.getClassLoader();

    String solve(Stream<String> input);

    default String solve(PathToFile pathToFile) throws IOException, URISyntaxException {
        return solve(Files.lines(pathForInput(pathToFile)));
    }

    private Path pathForInput(PathToFile pathToFile) throws URISyntaxException {
        return Path.of(classLoader.getResource(pathToFile.filename()).toURI());
    }

}

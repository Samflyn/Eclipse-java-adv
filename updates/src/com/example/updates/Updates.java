package com.example.updates;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Updates {
    //    JAVA 9


    //    try-with-resources
    //    the resources will be closed before exiting the try block
    //    the resource should be auto closable
    //    the order in which it is closed is reversed of that is passed to it
    //    the order in which it closes is file2 then file1
    public void tryWithResources() throws IOException {
        FileInputStream file1 = new FileInputStream("");
        FileInputStream file2 = new FileInputStream("");
        try (file1; file2) {
        }
        //    the file1 cannot be used here again
    }

    //    Immutable Set
    //    Set.of() – creates an immutable set of a given elements
    public void createImmutableSet() throws UnsupportedOperationException {
        Set<Integer> integers = Set.of(1, 2, 3, 4, 5);
        //    trying to add or remove from the set will throw an UnsupportedOperationException
        integers.remove(2);
    }


    //    JAVA 10

    //    local variable type inference
    //    compiler infers the type of data, this is available only for local variables
    public void localVariableTypeInference() {
        var something = "";
    }

    //    unmodifiable collections
    //    List, Map and Set each got a new static method copyOf(Collection).
    //    It returns the unmodifiable copy of the given Collection:
    public void collectionsCopyOf() throws UnsupportedOperationException {
        List<Integer> eList = List.copyOf(List.of(1, 2, 3));
        List<Integer> eList1 = List.of(1, 2, 3).stream().collect(Collectors.toUnmodifiableList());
    }


    //    JAVA 11

    //    String Methods
    public void newStringMethods() {
        String repeating = "K".repeat(2);
//        output would be "KK"

        String stripLeadingTailing = "\n\t  hello   \u2005".strip();
//        output would be "hello"
//        there are also stripLeading() and stripTrailing()
//        the difference between strip and trim is that strip also considers the unicode characters

        Boolean isItBlank = "\n\t\u2005  ".isBlank();
//        checks if the input is whitespaces, it also considers unicode characters
//        output would be true or false

        String fewMultiLines = "This is\n \n a multiline\n string.";
        long lineCount = fewMultiLines.lines()
                .filter(String::isBlank)
                .count();
//        returns stream of lines after removing the line separators like "\n”, “\r”, or “\r\n”
//        better performance over split()
    }

    //    New File Methods
    public void newFileMethods() throws IOException {
        Path filePath = Files.writeString(Files.createTempFile(Path.of(""), "demo", ".txt"), "Sample text");
        String fileContent = Files.readString(filePath);
//        to directly read and write strings to files
    }

    //    Local variable in lambda expressions
    public void localVariable() {
        List<String> sampleList = Arrays.asList("Java", "Kotlin");
        String resultString = sampleList.stream()
                .map((var x) -> x.toUpperCase())
                .collect(Collectors.joining(", "));
    }
}

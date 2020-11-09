package com.example.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExExample {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(".xx.");
        Matcher matcher = pattern.matcher("1xxe");
        System.out.println(matcher.matches());
        System.out.println(Pattern.matches("[abc]", "a"));
        System.out.println(Pattern.matches("[abc]", "aaa"));
        System.out.println(Pattern.matches("[abc]", "a"));
        System.out.println(Pattern.matches("[^abc]", "a"));// ^ -> not
        System.out.println(Pattern.matches("[a-c[x-z]]", "x"));// union
        System.out.println(Pattern.matches("[a-c&&[ayz]]", "a"));// intersection
        // quantifiers -> no of times
        System.out.println(Pattern.matches("[afe]?", "a"));// ? -> only once
        System.out.println(Pattern.matches("[afe]+", "aae"));// ? -> at least once
        System.out.println(Pattern.matches("[afe]*", ""));// * -> may or may not occur
        // meta characters \\-> to escape \ in java
        System.out.println(Pattern.matches(".", "*"));// . -> any char
        System.out.println(Pattern.matches("\\d", "7"));// \d -> any digit
        System.out.println(Pattern.matches("\\D", "4"));// \D -> non digit
        System.out.println(Pattern.matches("\\D*", "essay"));// \D -> non digit more than once
        System.out.println(Pattern.matches("\\s", " "));// \s -> whitespace
        System.out.println(Pattern.matches("\\S", "_"));// \S -> anything but whitespace
        System.out.println(Pattern.matches("\\w", "a"));// \w -> alphanumeric
        System.out.println(Pattern.matches("\\W", "!"));// \W -> symbols
        // modifiers
        /*
        g 	Perform a global match (find all matches rather than stopping after the first match)
        i 	Perform case-insensitive matching
        m 	Perform multiline matching
         */

        // brackets
        /*
        [abc] 	Find any character between the brackets
        [^abc] 	Find any character NOT between the brackets
        [0-9] 	Find any character between the brackets (any digit)
        [^0-9] 	Find any character NOT between the brackets (any non-digit)
        (x|y) 	Find any of the alternatives specified
         */

        // Metacharacters
        /*
        . 	Find a single character, except newline or line terminator
        \w 	Find a word character
        \W 	Find a non-word character
        \d 	Find a digit
        \D 	Find a non-digit character
        \s 	Find a whitespace character
        \S 	Find a non-whitespace character
        \b 	Find a match at the beginning/end of a word, beginning like this: \bHI, end like this: HI\b
        \B 	Find a match, but not at the beginning/end of a word
        \0 	Find a NULL character
        \n 	Find a new line character
        \f 	Find a form feed character
        \r 	Find a carriage return character
        \t 	Find a tab character
        \v 	Find a vertical tab character
        \xxx 	Find the character specified by an octal number xxx
        \xdd 	Find the character specified by a hexadecimal number dd
        \udddd 	Find the Unicode character specified by a hexadecimal number dddd
         */

        // Quantifiers
        /*
        n+ 	Matches any string that contains at least one n
        n* 	Matches any string that contains zero or more occurrences of n
        n? 	Matches any string that contains zero or one occurrences of n
        n{X} 	Matches any string that contains a sequence of X n's
        n{X,Y} 	Matches any string that contains a sequence of X to Y n's
        n{X,} 	Matches any string that contains a sequence of at least X n's
        n$ 	Matches any string with n at the end of it
        ^n 	Matches any string with n at the beginning of it
        ?=n 	Matches any string that is followed by a specific string n
        ?!n 	Matches any string that is not followed by a specific string n
         */


        /*
        Character classes

        .	any character except newline
        \w\d\s	word, digit, whitespace
        \W\D\S	not word, digit, whitespace
        [abc]	any of a, b, or c
        [^abc]	not a, b, or c
        [a-g]	character between a & g


        Anchors

        ^abc$	start / end of the string
        \b\B	word, not-word boundary
        Escaped characters
        \.\*\\	escaped special characters
        \t\n\r	tab, linefeed, carriage return


        Groups & Lookaround

        (abc)	capture group
        \1	backreference to group #1
        (?:abc)	non-capturing group
        (?=abc)	positive lookahead
        (?!abc)	negative lookahead

        Quantifiers & Alternation

        a*a+a?	0 or more, 1 or more, 0 or 1
        a{5}a{2,}	exactly five, two or more
        a{1,3}	between one & three
        a+?a{2,}?	match as few as possible
        ab|cd	match ab or cd
         */
    }
}

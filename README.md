# NumberEncoding

Task description



The following mapping from letters to digits is given:
- E e - 0
- J N Q j n q - 1
- R W X r w x - 2
- D S Y d s y - 3
- F T f t - 4
- A M a m - 5
- C I V c i v - 6
- B K U b k u - 7
- L O P l o p - 8
- G H Z g h z - 9


We want to use this mapping for encoding telephone numbers by words, so
that it becomes easier to remember the numbers.

Functional requirements

Your task is writing a program that finds, for a given phone number, all
possible encodings by words, and prints them. A phone number is an
arbitrary(!) string of dashes - , slashes / and digits. The dashes and
slashes will not be encoded. The words are taken from a dictionary which
is given as an alphabetically sorted ASCII file (one word per line).

[NOTE: The dictionary is in German and contains umlaut characters
encoded as double-quotes.  The double-quotes should be ignored.  EG.]

Only exactly each encoding that is possible from this dictionary and
that matches the phone number exactly shall be printed. Thus, possibly
nothing is printed at all. The words in the dictionary contain letters
(capital or small, but the difference is ignored in the sorting), dashes
- and double quotes " . For the encoding only the letters are used, but
the words must be printed in exactly the form given in the dictionary.
Leading non-letters do not occur in the dictionary.

Encodings of phone numbers can consist of a single word or of multiple
words separated by spaces. The encodings are built word by word from
left to right. If and only if at a particular point no word at all from
the dictionary can be inserted, a single digit from the phone number can
be copied to the encoding instead. Two subsequent digits are never
allowed, though. To put it differently: In a partial encoding that
currently covers k digits, digit k+1 is encoded by itself if and only if,
first, digit k was not encoded by a digit and, second, there is no word
in the dictionary that can be used in the encoding starting at digit k+1.

Your program must work on a series of phone numbers; for each encoding
that it finds, it must print the phone number followed by a colon, a
single(!) space, and the encoding on one line; trailing spaces are not
allowed. All remaining ambiguities in this specification will be
resolved by the following example. (Still remaining ambiguities are
intended degrees of freedom.)

Sample dictionary:
````
an
blau
Bo"
Boot
bo"s
da
Fee
fern
Fest
fort
je
jemand
mir
Mix
Mixer
Name
neu
o"d
Ort
so
Tor
Torf
Wasser
```

Sample phone number list:
```
112
5624-82
4824
0721/608-4067
10/783--5
1078-913-5
381482
04824
```
Corresponding correct program output (on screen):
```
5624-82: mir Tor
5624-82: Mix Tor
4824: Torf
4824: fort
4824: Tor 4
10/783--5: neu o"d 5
10/783--5: je bo"s 5
10/783--5: je Bo" da
381482: so 1 Tor
04824: 0 Torf
04824: 0 fort
04824: 0 Tor 4
```
Any other output would be wrong (except for different ordering of the
lines).

Wrong outputs for the above example would be e.g.

562482: Mix Tor, because the formatting of the phone number is
incorrect,

10/783--5: je bos 5, because the formatting of the second word is
incorrect,

4824: 4 Ort, because in place of the first digit the words Torf, fort,
Tor could be used,

1078-913-5: je Bo" 9 1 da , since there are two subsequent digits in the
encoding,

04824: 0 Tor , because the encoding does not cover the whole phone
number, and

5624-82: mir Torf , because the encoding is longer than the phone number.


Quantitative requirements

- Length of the individual words in the dictionary: 50 characters maximum.
- Number of words in the dictionary: 75000 maximum
- Length of the phone numbers: 50 characters maximum.
- Number of entries in the phone number file: unlimited.


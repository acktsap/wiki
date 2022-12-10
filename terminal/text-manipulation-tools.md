# Text Manipulation Tool

- [grep](#grep)
- [awk](#awk)
- [sed](#sed)
- [cut](#cut)
- [uniq](#uniq)
- [cat](#cat)
- [echo](#echo)
- [printf](#printf)
- [head](#head)
- [tail](#tail)
- [fmt](#fmt)
- [tr](#tr)
- [nl](#nl)
- [wc](#wc)
- [sort](#sort)
- [egrep](#egrep)
- [fgrep](#fgrep)
- [See also](#see-also)

## grep

Case insentive search.

```sh
grep -i hello sample.txt
```

Case sensitive search.

```sh
grep hello sample.txt
```

Get two line of context after match.

```sh
grep -A 2 Hello sample.txt
```

Get two line of context before match.

```sh
grep -B 2 Hello sample.txt
```

Find all lines that don't match.

```sh
grep -v Hello sample.txt
```

Show only the filenames of the files that matched

```sh
grep -l Hello *.txt
```

Recursive search.

```sh
grep -r Hello */*.txt
```

Print matching part only.

```sh
grep -o Hello sample.txt
```

Using regex.

```sh
# prints any string including one or more "H"
grep -E "H+" sample.txt
```

```sh
# prints any string including one or more 'H' | By
grep -E "H+|By" sample.txt
```

## awk

## sed

- stream editor.

Replace all 'Hello' to 'No'

```sh
sed 's/Hello/No/g' sample.txt
```

Replace 7th line of 'Hello' to 'No'

```sh
sed '7 s/Hello/No/' sample.txt
```

Search for a 'Hello' and only print the matched lines.

```sh
sed -n '/Hello/p' sample.txt
```

Remove "10" from the end of each line

```sh
sed 's/10$//' sample.txt
```

## cut

Cut with delim ' ' and print field 3.
```sh
cut -d ' ' -f3 sample.txt
```

## uniq

## cat

Creating a file with content. `Ctrl + D` to save and exit.

```sh
cat > file1.txt
```

Displaying file contents.

```sh
cat sample.txt
```

Displaying multiple files.

```sh
cat sample.txt file1.txt
```

Displaying file contents with a line number.

```sh
cat -n sample.txt
```

Redirection to an new file.

```sh
cat file1.txt > file2.txt
```

## echo

## printf

Just line c printf function.

```sh
printf "%d %f %s\n" 10 10 message
```

## head

- display first lines of a file.

Print first 3 lines of file.

```sh
head -n 3 sample.txt 
```

Print first 10 lines of file.

```sh
head sample.txt 
```

## tail

- display the last part of a file.

Display last 2 lines of file.

```sh
tail -n 2 sample.txt
```

Display last 10 lines of file.

```sh
tail sample.txt
```

Monitor files for changes.

```sh
tail -f sample.txt
```

## fmt

## tr

## nl

- Line numbering.

Number files.

```sh
nl sample.txt
```

Number files with start number 4.

```sh
nl -v 4 sample.txt
```

Number files with increment 3.

```sh
nl -i 3 sample.txt
```

Add a string literal after line numbers.

```sh
nl -s "..." sample.txt
```

## wc

- Word Count

Count count of entries in a current directory .

```sh
ls | wc -l
```

Print lines count of a file.

```sh
wc -l sample.txt #=>  10 readme.txt
```

Print words count of a file.

```sh
wc -w sample.txt #=> 30 readme.txt
```

Print character count of a file.

```sh
wc -c sample.txt #=> 191 readme.txt
```

## sort

Sort file in an ascending order.

```sh
sort sample.txt
```

Sort file in a descending order.

```sh
sort --reverse sample.txt
```

Sort a file in case-insensitive way.

```sh
sort --ignore-case sample.txt
```

## egrep

## fgrep

## See also

- [grep cheat sheet](https://vishnuch.tech/grep-cheatsheet)
- [sed cheat sheet](https://quickref.me/sed)
- [cat cheat sheet](https://onecompiler.com/cheatsheets/cat)
- [cut cheat sheet](https://bencane.com/2012/10/22/cheat-sheet-cutting-text-with-cut/)
- [lc cheat sheet](https://www.geeksforgeeks.org/nl-command-in-linux-with-examples/)
- [wc cheat sheet](https://onecompiler.com/cheatsheets/wc)
- [nl cheat sheet](https://www.geeksforgeeks.org/nl-command-in-linux-with-examples/)
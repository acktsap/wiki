#include <stdio.h>
#include <stdlib.h>

/*

  Assume that a system has a 32-bit virtual address with a 4-KB page size.
  Write a C program that is passed a virtual address (in decimal) on the
  command line and have it output the page number and offset for the
  given address. As an example, your program would run as follows:

    ./a.out 19986

  Your program would output:

    The address 19986 contains:
    page number = 4
    offset = 3602

 */
int main(int argc, char *argv[]) {
  if (argc != 2) {
      fprintf(stderr, "usage: a.out <address size>\n");
      return -1;
  }

  unsigned int address = atoi(argv[1]);

  // 4kb : 2^12 bytes
  unsigned int page = address >> 12; // address / 4kb
  unsigned int offset = address & 0xfff; // offset & 4kb

  printf("The address %d contains: \n", address);
  printf("page number = %d\n", page);
  printf("offset = %d\n", offset);

  return 0;
}
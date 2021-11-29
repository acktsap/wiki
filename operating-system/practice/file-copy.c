#include <sys/types.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char* argv[]) {
  char* input_file = argv[1];
  if (input_file == NULL) {
    fprintf(stderr, "Input file must be provided\n");
    exit(-1);
  }

  char* output_file = argv[2];
  if (output_file == NULL) {
    fprintf(stderr, "Output file must be provided\n");
    exit(-1);
  }

  int input_fd, output_fd;

  // O_RDONLY : read only
  if ((input_fd = open(input_file, O_RDONLY)) == -1) {
    fprintf(stderr, "Input file open failed\n");
    exit(-1);
  }

  // O_RDWR : read & write, O_TRUNC : truncate to zero length if exists
  output_fd = open(output_file, O_RDWR, O_TRUNC);
  if ((output_fd) == -1) {
    // r/w permission from owner
    output_fd = creat(output_file, S_IRUSR | S_IWUSR); 
  }

  char buffer[64];
  int length;
  while ((length = read(input_fd, buffer, 64)) != 0) {
    write(output_fd, buffer, length);
    length = read(input_fd, buffer, 64);
  }

  close(input_fd);
  close(output_fd);

  exit(0);
}
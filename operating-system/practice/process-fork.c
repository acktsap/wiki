#include <sys/types.h>
#include <stdio.h>
#include <unistd.h>

int main() {
  pid_t pid;

  pid = fork();

  if (pid < 0) { // if error
    fprintf(stderr, "Fork Failed");
    exit(-1);
  }
  
  if (pid == 0) {
    execlp("/bin/ls", "ls", NULL); // child process
  } else {
    printf("Wait\n");
    wait(NULL); // parent will wait
    printf("Child Complete\n");
    exit(0);
  }
}
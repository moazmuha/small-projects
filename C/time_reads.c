#include <stdio.h>
#include <stdlib.h>

#include <unistd.h>
#include <signal.h>
#include <sys/time.h>


// Message to print in the signal handling function. 
#define MESSAGE "%ld reads were done in %ld seconds.\n"


/* Global variables to store number of read operations and seconds elapsed.
 * These have to be global so that signal handler to be written will have
 * access.
 */
long num_reads = 0, seconds;

void handler(int code){
  fprintf(stderr, MESSAGE, num_reads, seconds);
  exit(1);
}


int main(int argc, char ** argv) {
    if (argc != 3) {
        fprintf(stderr, "Usage: time_reads s filename\n");
        exit(1);
    }
    seconds = strtol(argv[1], NULL, 10);

    struct itimerval timer;
    struct itimerval timer2;
    struct timeval time;
    time.tv_sec = seconds;
    time.tv_usec = 0; 
    timer.it_value = time;
    if (setitimer(ITIMER_PROF, &timer, &timer2) == -1) {
        perror("error calling getitimer()");
        exit(1);
    }


    struct sigaction action;
    action.sa_handler = handler;
    action.sa_flags = 0;
    sigemptyset(&action.sa_mask);
    sigaction(SIGPROF, &action, NULL);


    FILE *fp;
    if ((fp = fopen(argv[2], "r+")) == NULL) {    // Read+Write for later ...
      perror("fopen");
      exit(1);
    }

    /* In an infinite loop, read an int from a random location in the file
     * and print it to stderr.
     */
    int size;
    fseek(fp, -sizeof(int), SEEK_END);
    size = ftell(fp) + 1;
    int m;
    for (;;) {
      int seek = rand() % size;
      while (seek%sizeof(int)!=0){
        seek++;
      }
      if (seek == 400){
        seek = 396;
      }
      fseek(fp,seek,SEEK_SET);
      fread(&m, sizeof(int), 1, fp);
      fprintf(stderr, "%d\n", m);
      num_reads++;
    }

    return 1;  //something is wrong if we ever get here!
}


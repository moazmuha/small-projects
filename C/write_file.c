#include <stdio.h>
#include <stdlib.h>


int main(int argc, char ** argv) {
    FILE *file = NULL;

    file = fopen(argv[1], "wb");

    if (file == NULL) {
        fprintf(stderr, "Cannot open file\n");
        exit(1);
    }

    int i = 0;
    while (i<100){
        int m = rand() % 100;
        //printf("%d\n", m);
        if(fwrite(&m, sizeof(int), 1, file) != 1) {
            fprintf(stderr, "Error: write failed\n");
            fclose(file);
            exit(1);
        }
        i++;
    }

    if (fclose(file) != 0){
		fprintf(stderr, "failed to close fail file\n");
		return 0;
	}
}

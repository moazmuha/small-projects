#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char **argv) {
    if (argc != 3) {
        fprintf(stderr, "Usage: ./greeting 'greeting message' 'name'\n");
        exit(1);
    }
    char greeting[20];
    char *name = argv[2];
    if(strlen(argv[1]) >18){
        strncpy(greeting, argv[1], 19);
        greeting[19] = '\0';
	}else{
        strncpy(greeting, argv[1], 19);
        if (strlen(argv[1]) <19){
        greeting[strlen(argv[1])]= ' ';
        if ((19 - strlen(greeting)) > 0){
            strncat(greeting, name, 19 - strlen(greeting));
        }
        greeting[strlen(greeting)] = '\0';
        }else{greeting[strlen(greeting)-1] = '\0';}
    }


    printf("%s\n", greeting);
    return 0;
}
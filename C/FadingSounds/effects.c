#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char **argv){
if (argc != 3 || !(strcmp(argv[1],"-fin")==0 || strcmp(argv[1],"-pan")==0 || strcmp(argv[1],"-fout")==0)||(*argv[2] != '0' &&strtol(argv[2], NULL, 10)==0)){
	printf("Error: Invalid command-line arguments.\n");
	
	return 0;
}


char line[BUFSIZ];
int line_num = 0;
int sample_rate_dec = 0;
int data_size_dec= 0;
int file_size_dec= 0;
int samples = 0;
int sample_numerator=0; 
int samples_fout = 0;
int samples_notdo = 0;
int numerator = 0;
int channel_dec = 1;
int samples_in_file = data_size_dec/2;
int samples_pan = 0;
int numerator_first = 0;
int numerator_second = 0;
int samples_seen = 0;

while (fgets(line, BUFSIZ, stdin) != NULL){
	char *token_list[30];
	char *token = strtok(line, " ");
	token_list[0] = token;
	int count = 1;
	


	int i = 1;
	while(token != NULL){
		token = strtok(NULL, " ");
		token_list[i] = token;
		i++;
		count++;
	}
	//printf("Count: %d\n", count);
	
	/*printf("[");
	for(i = 0; i < 18; i++){
      		printf("%s", token_list[i]);
		if(i!=17) printf(",");
	}
	printf("]");
	printf("\n");*/
	if (line_num == 0){
		char file_size[10];
		strcpy(file_size, token_list[8]);
		strcat(file_size, token_list[7]);
		strcat(file_size, token_list[6]);
		strcat(file_size, token_list[5]);
		file_size_dec = (int) strtol(file_size, NULL, 16);
		//printf("My line: ");
		for(i = 0; i < count-3; i++){printf("%s ", token_list[i]);}
		printf(" %s\n", token_list[i++]);
		//printf("count: %d\n:", count);
	}
	else if (line_num == 1){
		char sampling_rate[10];
		strcpy(sampling_rate, token_list[12]);
		strcat(sampling_rate, token_list[11]);
		strcat(sampling_rate, token_list[10]);
		strcat(sampling_rate, token_list[9]);
		sample_rate_dec = (int) strtol(sampling_rate, NULL, 16);
		char channel[10];
		strcpy(channel, token_list[8]);
		strcat(channel, token_list[7]);
		channel_dec = (int) strtol(channel, NULL, 16);
		if (samples == 0 && argc > 2 && strcmp(argv[1],"-fin")==0){
			int milli_secs = strtol(argv[2], NULL, 10);
			float secs = ((float) milli_secs)/1000;
			samples = sample_rate_dec*secs;
			if (samples <0) {samples = 1;}
		}
		//printf("My line: ");
		for(i = 0; i < count-2; i++){printf("%s ", token_list[i]);}
		printf(" %s", token_list[i++]);
		//printf("count: %d\n:", count);
		//for(i = 0; i < 17; i++){printf("%s ", token_list[i]);}
		//printf("%s", token_list[i++]);
	}

	else if (line_num == 2){
		char data_size[10];
		strcpy(data_size, token_list[12]);
		strcat(data_size, token_list[11]);
		strcat(data_size, token_list[10]);
		strcat(data_size, token_list[9]);
		int data_size_dec = (int) strtol(data_size, NULL, 16);
		samples_in_file = data_size_dec/2;
		if (samples_fout == 0 && argc > 2 && strcmp(argv[1],"-fout")==0){
			int milli_secs = strtol(argv[2], NULL, 10);
			float secs = ((float) milli_secs)/1000;
			samples_fout = sample_rate_dec*secs;
			samples_notdo = samples_in_file - samples_fout;
			if (samples_notdo < 0){samples_notdo = 0;}
			numerator = samples_fout-1;
		}
		if (argc > 2 && strcmp(argv[1],"-pan")==0){
			int milli_secs = strtol(argv[2], NULL, 10);
			float secs = ((float) milli_secs)/1000;
			samples_pan = sample_rate_dec*secs;
			numerator_first = samples_pan-1;
			numerator_second = 0;
		}
		
		//printf("Samples left: %d Samples: %d\n", samples_left, samples_fout);
		char two_bytes[5];
		strcpy(two_bytes, token_list[14]);
		strcat(two_bytes, token_list[13]);
		short two_bytes_dec = (short) strtol(two_bytes, NULL, 16);
		if (samples > 0 && argc > 2 && strcmp(argv[1],"-fin")==0 && sample_numerator <= samples){
			//printf("my bytes: %d\n", two_bytes_dec);
			double two_bytes_dec_d = ((double) two_bytes_dec) * (((double)sample_numerator)/((double)samples));
			//printf("converted bytes: %f\n", two_bytes_dec_d);
			two_bytes_dec = (short) two_bytes_dec_d;
			if ((samples_seen%channel_dec)==0){sample_numerator++;}
			samples_seen++;
		}if (samples_fout > 0 && samples_notdo <= 0 && argc > 2 && strcmp(argv[1],"-fout")==0){
			//printf("my bytes: %d\n", two_bytes_dec);
			//printf("SAMPLES NOT: %d NUMERTOR: %d SAMPLES: %d\n", samples_notdo, numerator, samples_fout);
			double two_bytes_dec_d = ((double) two_bytes_dec) * (((double) numerator)/((double) samples_fout));
			//printf("converted bytes: %f\n", two_bytes_dec_d);
			if (numerator>0 && (samples_seen%channel_dec)==0){numerator--;}
			samples_seen++;
			two_bytes_dec = (short) two_bytes_dec_d;
			//printf("entered\n");
		}samples_notdo--;
		if (samples_pan > 0 && argc > 2 && strcmp(argv[1],"-pan")==0 && numerator_first >= 0){
			//printf("my bytes: %d\n", two_bytes_dec);
			double two_bytes_dec_d = ((double) two_bytes_dec) * (((double)numerator_first)/((double)samples_pan));
			//printf("converted bytes: %f\n", two_bytes_dec_d);
			two_bytes_dec = (short) two_bytes_dec_d;
			//printf("Numerator: %d Samples: %d Looking at: %s changes to: %04x\n", numerator_first, samples_pan, two_bytes, two_bytes_dec);
            //printf("Numerator fout: %d Samples: %d Looking at: %s at [%d] [%d]  changes to: %04x\n", numerator_first, samples_pan, two_bytes,i-1,i, two_bytes_dec);
			if (numerator_first>0){numerator_first--;}
		}
		//printf("Samples left: %d Samples: %d\n", samples_left, samples_fout);
		//printf("Samples left: %d Samples: %d\n", samples_left, samples_fout);
		
		char hex_str[20];
    		sprintf(hex_str, "%04x", two_bytes_dec);
		char first[3] = {hex_str[strlen(hex_str)-2], hex_str[strlen(hex_str)-1], '\0'};
		char second[3] = {hex_str[strlen(hex_str)-4], hex_str[strlen(hex_str)-3], '\0'};
		strncpy(token_list[14], second, 2);
		strncpy(token_list[13], first, 2);

		char nxt_two_bytes[5];
		strcpy(nxt_two_bytes, token_list[16]);
		strcat(nxt_two_bytes, token_list[15]);
		//printf("%s\n", nxt_two_bytes);
		short nxt_two_bytes_dec = (short) strtol(nxt_two_bytes, NULL, 16);
		if (samples > 0 && argc > 2 && strcmp(argv[1],"-fin")==0 && sample_numerator <= samples){
			//printf("my bytes: %d\n", nxt_two_bytes_dec);
			double nxt_two_bytes_dec_d = ((double) nxt_two_bytes_dec) * (((double)sample_numerator)/((double)samples));
			//printf("converted bytes: %f\n", nxt_two_bytes_dec_d);
			nxt_two_bytes_dec = (short) nxt_two_bytes_dec_d;
			if ((samples_seen%channel_dec)==0){sample_numerator++;}
			samples_seen++;
		}if (samples_fout > 0 && samples_notdo <= 0 && argc > 2 && strcmp(argv[1],"-fout")==0){
			//printf("my bytes: %d\n", two_bytes_dec);
			//printf("SAMPLES NOT: %d NUMERTOR: %d SAMPLES: %d\n", samples_notdo, numerator, samples_fout);
			double nxt_two_bytes_dec_d = ((double) nxt_two_bytes_dec) * (((double) numerator)/((double) samples_fout));
			//printf("converted bytes: %f\n", two_bytes_dec_d);
			if(numerator>0 && (samples_seen%channel_dec)==0){numerator--;}
			samples_seen++;
			nxt_two_bytes_dec = (short) nxt_two_bytes_dec_d;
			//printf("entered\n");
		}samples_notdo--;
		if (samples_pan > 0 && argc > 2 && strcmp(argv[1],"-pan")==0 && numerator_second <= samples_pan){
			//printf("my bytes: %d\n", two_bytes_dec);
			//printf("SAMPLES NOT: %d NUMERTOR: %d SAMPLES: %d\n", samples_notdo, numerator, samples_fout);
			double nxt_two_bytes_dec_d = ((double) nxt_two_bytes_dec) * (((double) numerator_second)/((double) samples_pan));
			//printf("converted bytes: %f\n", two_bytes_dec_d);
			if (numerator_second<samples_pan){ numerator_second++;}
			nxt_two_bytes_dec = (short) nxt_two_bytes_dec_d;
			//printf("Numerator: %d Samples: %d Looking at: %s changes to: %04x\n", numerator_second, samples_pan, nxt_two_bytes, two_bytes_dec);
			//printf("entered\n");
		}
		
		//printf("Samples left: %d Samples: %d\n", samples_left, samples_fout);
		char nxt_hex_str[20];
    		sprintf(nxt_hex_str, "%04x", nxt_two_bytes_dec);
		//printf("hex_str: %s\n", nxt_hex_str);
		char nxt_first[3] = {nxt_hex_str[strlen(nxt_hex_str)-2], nxt_hex_str[strlen(nxt_hex_str)-1], '\0'};
		char nxt_second[3] = {nxt_hex_str[strlen(nxt_hex_str)-4], nxt_hex_str[strlen(nxt_hex_str)-3], '\0'};
		strncpy(token_list[16], nxt_second, 2);
		strncpy(token_list[15], nxt_first, 2);
		
		//token_list[16] = nxt_second;
		//token_list[15] = nxt_first;
		//printf("My line: ");
		for(i = 0; i < count-2; i++){printf("%s ", token_list[i]);}
		printf(" %s", token_list[i++]);
		//printf("count: %d\n:", count);
		//printf("%s,%s\n", nxt_first, nxt_second);
		//for(i = 0; i < 17; i++){printf("%s ", token_list[i]);}
		//printf("%s", token_list[i++]);
	}
	else{	
		int flag = 0;
		if (file_size_dec >= 16){
		//for(int i = 2; i <= 16; i+=2)
		int i=2;
		while(i<=16){
			char two_bytes[5];
			strcpy(two_bytes, token_list[i]);
			strcat(two_bytes, token_list[i-1]);
			//printf("before: %s\n", two_bytes);
			short two_bytes_dec = (short) strtol(two_bytes, NULL, 16);
				if (samples > 0 && argc > 2 && strcmp(argv[1],"-fin")==0 && sample_numerator <= samples){
					//printf("my bytes: %d\n", two_bytes_dec);
					double converted_bytes = ((double) two_bytes_dec) * ((double)sample_numerator/(double)samples);
					//printf("converted bytes: %f\n", converted_bytes);
					two_bytes_dec = (short) converted_bytes;
					if ((samples_seen%channel_dec)==0){sample_numerator++;}
					samples_seen++;
					//printf("my number: %d\n", two_bytes_dec);
					//printf("hex form: %04x\n", two_bytes_dec);
				}
			//printf("SAMPLES NOT: %d NUMERTOR: %d SAMPLES: %d\n", samples_notdo, numerator, samples_fout);
			if (samples_fout > 0 && samples_notdo <= 0 && argc > 2 && strcmp(argv[1],"-fout")==0){
				//printf("my bytes: %d\n", two_bytes_dec);
				//printf("SAMPLES NOT: %d NUMERTOR: %d SAMPLES: %d\n", samples_notdo, numerator, samples_fout);
				double two_bytes_dec_d = ((double) two_bytes_dec) * (((double) numerator)/((double) samples_fout));
				//printf("converted bytes: %f\n", two_bytes_dec_d);
				if(numerator>0 && (samples_seen%channel_dec)==0){numerator--;}
			samples_seen++;
			two_bytes_dec = (short) two_bytes_dec_d;
			//printf("entered\n");
			}samples_notdo--;

			if (flag == 0 && samples_pan > 0 && argc > 2 && strcmp(argv[1],"-pan")==0){
				//printf("my bytes: %d\n", two_bytes_dec);
				double two_bytes_dec_d = ((double) two_bytes_dec) * (((double)numerator_first)/((double)samples_pan));
				//printf("converted bytes: %f\n", two_bytes_dec_d);
				two_bytes_dec = (short) two_bytes_dec_d;
				//printf("Numerator fout: %d Samples: %d Looking at: %s changes to: %04x\n", numerator_first, samples_pan, two_bytes, two_bytes_dec);
                //printf("Numerator fout: %d Samples: %d Looking at: %s at [%d] [%d]  changes to: %04x\n", numerator_first, samples_pan, two_bytes,i-1,i, two_bytes_dec);
				if (numerator_first>0){numerator_first--;}
				flag = 1;
			}else if (flag == 1 && samples_pan > 0 && argc > 2 && strcmp(argv[1],"-pan")==0 && numerator_second <= samples_pan){
				//printf("my bytes: %d\n", two_bytes_dec);
				//printf("SAMPLES NOT: %d NUMERTOR: %d SAMPLES: %d\n", samples_notdo, numerator, samples_fout);
				double two_bytes_dec_d = ((double) two_bytes_dec) * (((double) numerator_second)/((double) samples_pan));
				//printf("converted bytes: %f\n", two_bytes_dec_d);
				if (numerator_second<samples_pan){ numerator_second++;}
				two_bytes_dec = (short) two_bytes_dec_d;
				//printf("Numerator fin: %d Samples: %d Looking at: %s changes to: %04x\n", numerator_second, samples_pan, two_bytes, two_bytes_dec);
				flag = 0;
				//printf("entered\n");
			}
			//printf("Samples left: %d Samples: %d\n", samples_left, samples_fout);
			//printf("%s\n", argv[1]);
			char hex_str[20];
	    		sprintf(hex_str, "%04x", two_bytes_dec);
			//printf("after: %s\n", hex_str);
			char first[3] = {hex_str[strlen(hex_str)-2], hex_str[strlen(hex_str)-1], '\0'};
			char second[3] = {hex_str[strlen(hex_str)-4], hex_str[strlen(hex_str)-3], '\0'};
			//printf("i: %d\n", i);
			//printf("first: %s, seconed: %s\n", first, second);
			
			strncpy(token_list[i], second, 2);
			strncpy(token_list[i-1], first, 2);
			//token_list[i] = second;
			//token_list[i-1] = first;
			//printf("tokenlist[%d]: %s, tokenlist[%d-1]: %s\n", i, token_list[i], i, token_list[i-1]); 
			//printf("line num: %d\n", line_num);
			//if(line_num ==9){for(i = 0; i < count-2; i++){printf("%s ", token_list[i]);} printf("\n");}
			//for(i = 0; i < count-2; i++){printf("%s ", token_list[i]);}
			//printf("%s", token_list[i++]);}
			//for(i = 0; i < 17; i++){printf("%s ", token_list[i]);}
			//printf("%s", token_list[i++]);
			//printf("My line: ");
			i+=2;
		}
		if (count>19){
			//printf("\n");
			for(i = 0; i < count-2; i++){
				printf("%s ", token_list[i]);
				if (i==16){printf(" ");}
			}
			printf("%s", token_list[i++]);
			
		}else{
			for(i = 0; i < count-2; i++){printf("%s ", token_list[i]);}
			printf(" %s", token_list[i++]);
		}
		//printf("count: %d\n:", count);		
		}
		else{
		//printf("Count: %d my loop: %d\n", count, 16-file_size_dec);
		for(i = 2; i <= count-2; i+=2){
		char two_bytes[5];
		//printf("index: %d\n", i);
		strcpy(two_bytes, token_list[i]);
		strcat(two_bytes, token_list[i-1]);
		//printf("before: %s\n", two_bytes);
		short two_bytes_dec = (short) strtol(two_bytes, NULL, 16);
		if (samples > 0 && argc > 2 && strcmp(argv[1],"-fin")==0 && sample_numerator <= samples){
					//printf("my bytes: %d\n", two_bytes_dec);
					double converted_bytes = ((double) two_bytes_dec) * ((double)sample_numerator/(double)samples);
					//printf("converted bytes: %f\n", converted_bytes);
					two_bytes_dec = (short) converted_bytes;
					if ((samples_seen%channel_dec)==0){sample_numerator++;}
					samples_seen++;
					//printf("my number: %d\n", two_bytes_dec);
					//printf("hex form: %04x\n", two_bytes_dec);
		}
		if (samples_fout > 0 && samples_notdo <= 0 && argc > 2 && strcmp(argv[1],"-fout")==0){
				//printf("my bytes: %d\n", two_bytes_dec);
				//printf("SAMPLES NOT: %d NUMERTOR: %d SAMPLES: %d\n", samples_notdo, numerator, samples_fout);
				double two_bytes_dec_d = ((double) two_bytes_dec) * (((double) numerator)/((double) samples_fout));
				//printf("converted bytes: %f\n", two_bytes_dec_d);
				two_bytes_dec = (short) two_bytes_dec_d;
				//printf("Numerator fout: %d Samples: %d Looking at: %s at [%d] [%d]  changes to: %04x\n", numerator, samples_fout, two_bytes,i-1,i, two_bytes_dec);
				if(numerator>0 && (samples_seen%channel_dec)==0){numerator--;}
				samples_seen++;
			
			//printf("entered\n");
		}samples_notdo--;
		if (flag == 0 && samples_pan > 0 && argc > 2 && strcmp(argv[1],"-pan")==0 && numerator_first >= 0){
				//printf("last line: entered\n");
				//printf("my bytes: %d\n", two_bytes_dec);
				double two_bytes_dec_d = ((double) two_bytes_dec) * (((double)numerator_first)/((double)samples_pan));
				//printf("converted bytes: %f\n", two_bytes_dec_d);
				two_bytes_dec = (short) two_bytes_dec_d;
                //printf("Numerator fout: %d Samples: %d Looking at: %s at [%d] [%d]  changes to: %04x\n", numerator_first, samples_pan, two_bytes,i-1,i, two_bytes_dec);
				if (numerator_first>0){numerator_first--;}
				flag = 1;
			}else if (flag == 1 && samples_pan > 0 && argc > 2 && strcmp(argv[1],"-pan")==0 && numerator_second <= samples_pan){
				//printf("last line: entered\n");
				//printf("my bytes: %d\n", two_bytes_dec);
				//printf("SAMPLES NOT: %d NUMERTOR: %d SAMPLES: %d\n", samples_notdo, numerator, samples_fout);
				double two_bytes_dec_d = ((double) two_bytes_dec) * (((double) numerator_second)/((double) samples_pan));
				//printf("converted bytes: %f\n", two_bytes_dec_d);
				two_bytes_dec = (short) two_bytes_dec_d;
                //printf("Numerator fin: %d Samples: %d Looking at: %s at [%d] [%d]  changes to: %04x\n", numerator_second, samples_pan, two_bytes,i-1,i, two_bytes_dec);
				if (numerator_second<samples_pan){ numerator_second++;}
				flag = 0;
				//printf("entered\n");
			}
			//printf("Samples left: %d Samples: %d\n", samples_left, samples_fout);
		char hex_str[20];
    	    sprintf(hex_str, "%04x", two_bytes_dec);
		//printf("after: %s\n", hex_str);
		char first[3] = {hex_str[strlen(hex_str)-2], hex_str[strlen(hex_str)-1], '\0'};
		char second[3] = {hex_str[strlen(hex_str)-4], hex_str[strlen(hex_str)-3], '\0'};
		strncpy(token_list[i], second, 2);
		strncpy(token_list[i-1], first, 2);
		//token_list[i] = second;
		//token_list[i-1] = first;
		}
		for(i = 0; i < count-2; i++){
			printf("%s ", token_list[i]);
		}
		int m =0;
		while (m < ((18-count))*2+2+(19-count)){
			printf(" ");
			m++;
		}
		printf(" %s", token_list[i++]);
		
		//printf("first: %s, seconed: %s\n", first, second);
		//for(i = 0; i < 16-file_size_dec; i++){printf("%s ", token_list[i]);}
		//printf("%s", token_list[i++]);	
		//check if line ends with \n
		}
	}
	
	
	
	
	line_num ++;
	file_size_dec = file_size_dec-16;
	//printf("\n");
	//printf("end of line: %c%c\n", line(strlen(line)-2), line(strlen(line)-1))
	//printf("file_size: %d\n", file_size_dec);

	//printf("%s\n",token_list[count-1]);

	}
    return 0;
}

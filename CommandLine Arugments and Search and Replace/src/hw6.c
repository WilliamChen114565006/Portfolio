#include "hw6.h"

int main(int argc, char *argv[]) {


   int option=0;
   int store1, store2;
   extern char *optarg;
   extern int optind;
   char *sname="default", *rname="default2", *lname="default3";
   FILE *in1, *out1;

   //checks for if the mandatory ones are only used once
   int sflag=0, rflag=0, lflag=0, wflag=0;
  
   if(argc<7)
   {
       return MISSING_ARGUMENT;
   }

   while((option=getopt(argc, argv, "ws:r:l:"))!=-1)
   {
       switch(option){
           case 'w':
               if(wflag==1)
               {
                   return DUPLICATE_ARGUMENT;
               }
               wflag=1;
               break;


           case 'r':
               if(rflag==1)
               {
                   return DUPLICATE_ARGUMENT;
               }
               rflag=1;
               rname= optarg;
               break;


           case 's':
               if(sflag==1)
               {
                   return DUPLICATE_ARGUMENT;
               }
               sflag=1;
               sname= optarg;
               break;
              
              
           case 'l':
               if(lflag==1)
               {
                   return DUPLICATE_ARGUMENT;
               }
               lflag=1;
               lname=optarg;
               break;
              
       }
    }
   //we see if the flags have been triggered and then see what to do after
  
   //SECTION 2: ERRORS (so that it can be read in a certain order)
   if((in1 = fopen(argv[argc-2], "r"))==NULL)
   {
       return INPUT_FILE_MISSING;
   }
   fclose(in1);


   if((out1 = fopen(argv[argc-1], "w"))==NULL) //maybe needs changing
   {
       return OUTPUT_FILE_UNWRITABLE;
   }
   fclose(out1);


   //Right here, we check for if the files at the end are readable or writeable before the missing arguments


   if(sflag==0 || sname[0]=='-')
   {
       return S_ARGUMENT_MISSING;
   }
  
   if(rflag==0 || rname[0]=='-')
   {
       return R_ARGUMENT_MISSING;
   }


    if(lflag==1) //if there is a L flag
    {
        const char com[]=",";
        char *token1, *token2;
        char *ptr1, *ptr2;
         
        token1= strtok(lname, com);
        token2= strtok(NULL, com);
   


        if(token2==NULL) //for if the endline is missing and maybe if the comma is missing
        {
            return L_ARGUMENT_INVALID;
        }

        store1=strtol(token1, &ptr1, 10);
        store2=strtol(token2, &ptr2, 10);

        if(store1<1 || store2<1 || store1>store2)
        {
            return L_ARGUMENT_INVALID;
        }
    }
   
   size_t lengthOfS=strlen(sname);

   if(wflag==1 && ((sname[0]!='*' && sname[lengthOfS-1]!='*') || (sname[0]=='*' && sname[0]=='*')) )
   {
       return WILDCARD_INVALID;
   }

   //SECTION 3: Once all errors have been checked out, we can then read and write
    char bufferSize[MAX_LINE];
    char *track;
    char storage[MAX_LINE];


    in1= fopen(argv[argc-2], "r");
    out1= fopen(argv[argc-1], "w");


    if(wflag==1) //case for wflag
    {
        int starto=0;
        int endo=0;
        char replacement[lengthOfS]; //for making sure that sname doesn't have a star anymore
        char *reptr;
        size_t lengthOfRepl;

        //first, detects if the sname begins with a * or ends with a star
        if(sname[0]=='*')
        {
            endo=1;
            for(int j=0;j<(int)lengthOfS-1;j++)
            {
                replacement[j]=sname[j+1];
            }
            replacement[lengthOfS-1]='\0'; //insurance
            lengthOfRepl=strlen(replacement);
            reptr=replacement;
        }
        else if(sname[lengthOfS-1]=='*')
        {
            starto=1;
            for(int j=0;j<(int)lengthOfS-1;j++)
            {
                replacement[j]=sname[j];
            }
            replacement[lengthOfS-1]='\0';
            lengthOfRepl=strlen(replacement);
            reptr=replacement;
        }

        //after, we start checkings if it triggered an lflag or not
        if (lflag==1)
        {
            if(starto==1)
            {
                int counter=0; //for line count
                while((fgets(bufferSize, MAX_LINE, in1))!=NULL)
                {
                    counter++;
                    if((counter>=store1) && (counter<=store2)) //it reaches the line 
                    {
                        char *track1=bufferSize; //gets the line or pointer to the line
                        while((track1=strstr(track1, reptr))!=NULL)
                        {
                            int tempCount=0;
                
                            if(track1==bufferSize || isspace(*(track1-1)) || ispunct(*(track1-1))) //checks if the character behind is a punc or not
                            {   
                                char *tempTrack=track1;
                                while( !isspace(*(tempTrack)) && !ispunct(*(tempTrack)) && *(tempTrack)!='\0')
                                {
                                    tempCount++;
                                    tempTrack++;
                                }
                                strcpy(storage, bufferSize);
                                int i=track1-bufferSize;
                                bufferSize[i]='\0'; //sets the index of the string there to nothing

                                strcat(bufferSize, rname); //concats the part where sname would be and sets it to be rname with the concatanation
                                strcat(bufferSize, (storage+i+tempCount)); //concats the rest of the parts back after the string plus past the word to be replaced
                            }
                        
                            track1+=lengthOfRepl; //moves it past that occurance  
                        }
                    }
                    fputs(bufferSize, out1);
                }
            }
            else if(endo==1)
            {
                int counter=0; //for line count
                while((fgets(bufferSize, MAX_LINE, in1))!=NULL)
                {
                    counter++;
                    if((counter>=store1) && (counter<=store2)) //it reaches the line 
                    {
                       char *track1=bufferSize;
                        while((track1=strstr(track1, reptr))!=NULL)
                        {
                            int tempCount=0;
                            if(isspace(*(track1+lengthOfRepl)) || ispunct(*(track1+lengthOfRepl)) || *(track1+lengthOfRepl)=='\0') //finds if char after substring is a space or puncutuation, making it a word
                            {
                                char *tempTrack=track1; //starts at the end of the word
                                while(!isspace(*(tempTrack)) && !ispunct(*(tempTrack)) && (tempTrack)!=bufferSize) //counts the word with the substring
                                {
                                    tempCount++;
                                    tempTrack--;
                                }
                                tempCount+=(lengthOfRepl-1); 
                                tempTrack++; //moves it up once so it starts at the start of the word with the substring

                                strcpy(storage, bufferSize);
                                int i=(tempTrack)-bufferSize; //the index should be right after the whitespace in the word containing the substring
                                bufferSize[i]='\0'; 

                                strcat(bufferSize, rname);  //connects the replacement word
                                strcat(bufferSize, (storage+i+tempCount)); //connects everything after the word
                            }
                            track1+=lengthOfRepl;
                        }
                    }
                    fputs(bufferSize, out1);
                }
            }
        }
        else
        {
           if(starto==1) //if we are looking for the substring at the start
           {
                 while((fgets(bufferSize, MAX_LINE, in1))!=NULL) //case when l does not trigger, so that we have to replace everyline
                 {
                    //copy the line and look through that copy instead 
                    char *track1=bufferSize; //gets the line or pointer to the line //**The reason for diff name for track was because I was testing some pointer stuff**//
                    
                    while((track1=strstr(track1, reptr))!=NULL)
                    {
                        //for counting the word which contains the substring
                        int tempCount=0;
                
                        if(track1==bufferSize || isspace(*(track1-1)) || ispunct(*(track1-1))) //checks if the character behind is a punc or not
                        {   
                            char *tempTrack=track1;
                            while( !isspace(*(tempTrack)) && !ispunct(*(tempTrack)) && *(tempTrack)!='\0') //counts the word with the substring
                            {
                                tempCount++;
                                tempTrack++;
                            }
                            strcpy(storage, bufferSize);
                            int i=track1-bufferSize;
                            bufferSize[i]='\0'; //sets the index of the string there to nothing

                            strcat(bufferSize, rname); //concats the part where sname would be and sets it to be rname with the concatanation
                            strcat(bufferSize, (storage+i+tempCount)); //concats the rest of the parts back after the string plus past the word to be replaced
                        }
                        
                        track1+=lengthOfRepl; //moves it past that occurance  
                    }
                    fputs(bufferSize, out1);
                 }
           }
           else if(endo==1)//else, we are looking at the end
           {
                while((fgets(bufferSize, MAX_LINE, in1))!=NULL) 
                 {
                    char *track1=bufferSize;
                    while((track1=strstr(track1, reptr))!=NULL)
                    {
                        int tempCount=0;
                        if(isspace(*(track1+lengthOfRepl)) || ispunct(*(track1+lengthOfRepl)) || *(track1+lengthOfRepl)=='\0') //finds if char after substring is a space or puncutuation, making it a word
                        {
                            char *tempTrack=track1; //starts at the end of the word
                            while(!isspace(*(tempTrack)) && !ispunct(*(tempTrack)) && (tempTrack)!=bufferSize) //counts the word with the substring
                            {
                                tempCount++;
                                tempTrack--;
                            }
                            tempCount+=(lengthOfRepl-1); //adds back the substring part that was missing from the counting since we started at the index of the occurance of the substring
                            tempTrack+=2; //moves it up once so it starts at the start of the word with the substring

                            strcpy(storage, bufferSize);
                            int i=(tempTrack)-bufferSize; //the index should be right after the whitespace in the word containing the substring
                            bufferSize[i]='\0'; 

                            strcat(bufferSize, rname);  //connects the replacement word
                            strcat(bufferSize, (storage+i+tempCount)); //connects everything after the word
                        }
                        track1+=lengthOfRepl;
                    }
                    fputs(bufferSize, out1);
                 }
           }
        }
           
    }

    else if(lflag==1) //case for when there are lines to detect and no wildcard
    {
        int counter=0; //for line count
        while((fgets(bufferSize, MAX_LINE, in1))!=NULL)
        {
            counter++;
            if((counter>=store1) && (counter<=store2)) //it reaches the line 
            {
               while((track=strstr(bufferSize, sname))!=NULL)
                {
                    strcpy(storage, bufferSize);
                    int i=track-bufferSize; //the location index of where it is
                    bufferSize[i]='\0';

                    strcat(bufferSize, rname);
                    strcat(bufferSize, (storage+i+lengthOfS));
                }
            }
            fputs(bufferSize, out1);
        }
    }
    else
    {
        while((fgets(bufferSize, MAX_LINE, in1))!=NULL) //case when l does not trigger, so that we have to replace everyline
        {
            while((track=strstr(bufferSize, sname))!=NULL)
            {
                strcpy(storage, bufferSize);
                int i=track-bufferSize;
                bufferSize[i]='\0'; //sets the index of the string there to nothing

                strcat(bufferSize, rname); //concats the part where sname would be and sets it to be rname with the concatanation
                strcat(bufferSize, (storage+i+lengthOfS)); //concats the rest of the parts back after the string
            }
            fputs(bufferSize, out1);

        }
    }

    fclose(in1);
    fclose(out1);

   return 0;
}




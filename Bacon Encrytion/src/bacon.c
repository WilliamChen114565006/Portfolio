#include "bacon.h"

// Add other #includes here if you want.
int powerFunc(int num, int ex)//Used for decryption when adding the values together
{
    int result;
    result=1;

    for(int i=0;i<ex;i++)
    {
        result*=num;
    }
    return result;
}


int encrypt(const char *plaintext, char *ciphertext) {

    int count, countReturn;
    size_t strLength;
    strLength= strlen(plaintext);

    //1: stores the binary form of each letter in to letters array
    char letters[strLength+1];
    letters[0]=0;

    int i;
    i=0;
    while(plaintext[i]!='\0') //converts plaintext characters (ASCII) to bacon code
    {
        if(islower(plaintext[i])>0) //for lower case characters
        {
            letters[i]=(plaintext[i]-97);
        }
        else if(isupper(plaintext[i])>0) //for upper
        {
            letters[i]=(plaintext[i]-65);
        }
        else if(isdigit(plaintext[i])>0) //for numbers
        {
            letters[i]=(plaintext[i]-8);
        }
        else if(plaintext[i]>31 && plaintext[i]<42) //for space to )
        {
            letters[i]=(plaintext[i]-6);
        }
        else if(plaintext[i]>43 && plaintext[i]<48) //for comma and on
        {
            letters[i]=(plaintext[i]-8);
        }
        else if(plaintext[i]==63) //for ?
        {
            letters[i]=(plaintext[i]-11);
        }
        else if(plaintext[i]>57 && plaintext[i]<60) //for : and ;
        {
            letters[i]=(plaintext[i]-8);
        }
        i++;
    }
    letters[i]='\0';

    //2: Loops through to see how many characters in cipher are actual storable
    int countCipher;
    countCipher=0;
    int j;
    j=0;
    while(ciphertext[j]!='\0')
    {
        if(isalpha(ciphertext[j])>0)
        {
            countCipher++;
        }
        j++;
    }

    if(countCipher<6)
    {
        count=-1;
        return count;
    }

    //2:Checks to see how many characters can fit
    int multSixty;
    multSixty=(6*strLength)+6;

    if(countCipher<multSixty)
    {
        count=(countCipher/6);
        countReturn=((countCipher/6)-1);
    }
    else
    {
        count=strLength+1;
        countReturn=strLength;
    }

    //3:Loops to then adjust characters in ciphertext
    int m, countSix, lT;
    m=0;
    lT=0;
    countSix=5;

    while(ciphertext[m]!='\0' && count!=0)
    {
        if(isalpha(ciphertext[m])>0) //checks if it is a valid character to store it in
        {
            if (countSix==-1) //after every 6 bits, moves on to the next char
            {
                countSix=5; //resets bits to go by
                lT++; //moves up to the next letter to go
                count--; //one character has been encrypted
            }

            if(count==1)
            {
                ciphertext[m]=toupper(ciphertext[m]);
            }

            else if(count!=0){
                unsigned int p= (letters[lT]>>countSix) & 0x00000001; //obtains each bit
                if(p==1)
                {
                    ciphertext[m]=toupper(ciphertext[m]);
                }
                else
                {
                    ciphertext[m]=tolower(ciphertext[m]);
                }
            }
            countSix--;
        }

        m++;
    }


    return countReturn;
}

int decrypt(const char *ciphertext, char *plaintext) {
    size_t strLength2;
    int count, countReturn;
    strLength2=strlen(plaintext);
    count=0;
    countReturn=0;

    char letters2[strLength2+1]; //will store the bacon code into
    letters2[0]=0;

    //1: Testing to see if plaintext is empty (Error: -1)
    if(strLength2==0)
    {
        return -1;
    }

    //2: Go through ciphertext to grab the binary values and look for EOM value as well (Error: -2)
    int q, foundEOM, tL, countSix2;
    unsigned int storage;
    q=0;
    tL=0;
    storage=0;
    countSix2=5;
    foundEOM=0;
    
    while(ciphertext[q]!='\0' && (foundEOM==0))
    {
        if(isalpha(ciphertext[q])>0) //checks if the character is a valid to take a value out of (meaning if they alphabetical)
        {
            if(countSix2==-1)
            {
                storage=0;
                countSix2=5;
                count++;
                if(letters2[tL]==63)
                {
                    foundEOM+=1; //EOM has been found
                }
                tL++;
            }

            if(foundEOM==0){
                if(isupper(ciphertext[q])>0)
                {
                    unsigned int temp= (1 << countSix2);
                    storage+=temp;
                    letters2[tL]=storage;
                }
            }
            countSix2--;
        }

        q++;
    }
    letters2[tL]='\0';

    if(foundEOM==0) //2: detects for error -2
    {
        return -2;
    }
    

    //3: Going through letters array to then make it into ASCII (detects for error -3)
    int o;
    o=0;
    while(letters2[o]!='\0')
    {
        if(letters2[o]==52) //checks if it is a question mark
       {
           letters2[o]+=11;
       }
       else if(letters2[o]>49 && letters2[o]<52) //checks if it is : or ;
       {
           letters2[o]+=8;
       }
       else if(letters2[o]>39 && letters2[o]<50) //checks if it is a numerical value
       {
           letters2[o]+=8;
       }
       else if(letters2[o]>35 && letters2[o]<40) //checks for , and more
       {
           letters2[o]+=8;
       }
       else if(letters2[o]>25 && letters2[o]<36) //checks for space to before comma in bacon code
       {
           letters2[o]+=6;
       }
       else if(letters2[o]>(-1) && letters2[o]<26) //checks for the alphetical values
       {
           letters2[o]+=65;
       }
       else if (letters2[o]==63) //checks for EOM marker
       {
           //nothing happens since it is at the EOM marker already
       }
       else
       {
           return -3; //there was no EOM marker before it reached the end
       }      
        o++;
    }

    //4: Store everything into the plaintext array
    int e, breakEr;
    e=0;
    breakEr=0;
    while(plaintext[e]!='\0' && breakEr==0)
    {
        if(letters2[e]==63)
        {
            plaintext[e]='\0';
            breakEr+=1;
        }
        else
        {
            plaintext[e]=letters2[e];
        }

        e++;
    }


    countReturn=count-1;
    return countReturn;
}

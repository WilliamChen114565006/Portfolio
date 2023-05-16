#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

// FILL IN THE BODY OF THIS FUNCTION.
// Feel free to create any other functions you like; just include them in this file.
int smallPower (int num, int exp)
{
    //creates an exponent function
    int count=1;

    for(int i=0; i<exp;i++)
    {
        count*=num;
    }

    return(count);
}


void repr_convert(char source_repr, char target_repr, unsigned int repr) {
    //starts editting it using source_repr
    if (source_repr=='1')
    {
        if(repr & 1 << 31)
        {
            repr=~repr;
            repr |= 0x80000000;
        }
        
    }
    else if(source_repr=='2')
    {   
        if(repr & 1 << 31)
        {
            repr=~repr;
            repr |= 0x80000000;
            repr+=1;
            if ((repr % 2^33) >0)
            {
                printf("undefined\n");
                return;
            }
        }
        
    }
    else if (source_repr=='S')
    {
        
            repr |= 0x80000000;
        
    }
    else if(source_repr=='D')
    {
        //grabs every 5 bits for 6 times to get the first 30 bits. Then for every 5 bits, grabs the int representation of it and multiplies it by 10^i, with i being each loop in order to get the BCD 
        unsigned int temp=repr;
        unsigned int store=0;

        if( (repr >> 31 & 0x00000001) != (repr >> 30 & 0x00000001)  )
        {
            printf("error\n");
            return;
        }
        
        for(int i=0;i<6;i++)
        {
            if( (temp & 0x0000001f)>9)
            {
                printf("error\n");
                return;
            }
            unsigned int tempAdd=(( temp & 0x0000001f));
            tempAdd*= (smallPower(10,i));
            store = store + tempAdd;
            temp >>= 5;
        }
        
        store+=(repr & 0x20000000);
        repr=store;
        
    }
    else
    {
        printf("error\n");
        return;
    }
    
    //starts editting it using target_repr
    if (target_repr=='1')
    {
        if(repr & 1 << 31)
        {
            repr= ~repr;
            repr |= 0x80000000;
        }

    }
    else if(target_repr=='2')
    {
        if(repr & 1 << 31)
        {
            repr=~repr;
            repr |= 0x80000000;
            repr+=1;
        }
       
    }
    else if(target_repr=='S')
    {   
            repr |= 0x80000000;
    }
    else if(target_repr=='D')
    {
        //grabs each digit in the number by modulo 10 each one to get the digit and dividing by 10 to decrement the number to get each single digit. Then converts the digit to 5 bit binary representation and moves it according to the loop so that it finishes at 30 bits, creating the BCD
        unsigned int store2=0x00000000;
        if (repr & 1 <<31)
        {
            store2 |= 0xc0000000;
            repr &= ~0x80000000;
        }

        if ( (repr / 1000000) > 0)
        {
            printf("undefined\n");
            return;
        }

        unsigned int temp1=repr;
        for (int i=0;i<6;i++)
        {
            unsigned int temp2=  temp1 %10;
            store2= store2 + (temp2 << (5*i));
            temp1 = temp1/10;
        }
        repr=store2;    
    }
    else
    {
        printf("error\n");
        return;
    }

    printf("%08x\n", repr);
}

// DO NOT CHANGE ANY CODE BELOW THIS LINE
int main(int argc, char *argv[]) {
    (void)argc; // Suppress compiler warning
    repr_convert(argv[1][0], argv[2][0], (int)strtol(argv[3], NULL, 16));
    return 0;
}

#include "hw7.h"
// #include <assert.h>
// #include <stdlib.h>
// #include <stdio.h>
// #include <string.h>
// #include <ctype.h>
// #include <unistd.h>

// #define MAX_LINE_LEN 1000

// #ifndef __MATRIX_SF
// #define __MATRIX_SF

// typedef struct {
//     char name;
//     unsigned int num_rows;
//     unsigned int num_cols;
//     int values[]; 
// } matrix_sf;

// typedef struct bst_sf {
//     matrix_sf *mat;
//     struct bst_sf *left_child;
//     struct bst_sf *right_child;
// } bst_sf;

// bst_sf* insert_bst_sf(matrix_sf *mat, bst_sf *root); 
// matrix_sf* find_bst_sf(char name, bst_sf *root); 
// void free_bst_sf(bst_sf *root); 
// matrix_sf* add_mats_sf(const matrix_sf *mat1, const matrix_sf *mat2); 
// matrix_sf* mult_mats_sf(const matrix_sf *mat1, const matrix_sf *mat2); 
// matrix_sf* transpose_mat_sf(const matrix_sf *mat); 
// matrix_sf* create_matrix_sf(char name, const char *expr); 
// matrix_sf* execute_script_sf(char *filename); 
// matrix_sf* evaluate_expr_sf(char name, char *expr, bst_sf *root); 
// char* infix2postfix_sf(char *infix); 

// // This is a utility function you may use if you want. See hw7.c.
// matrix_sf *copy_matrix(unsigned int num_rows, unsigned int num_cols, int values[]);
// // Utility function used in testing. Don't mess with it.
// void print_matrix_sf(matrix_sf *mat);

// #endif // __MATRIX_SF


//support function to detect which operator it is
int isOp(char ch)
{
     return (ch == '+' || ch == '*' || ch == '\'');
}

//sets the order of value for the operations, with ' being the highest (support function)
int presc(char op)
{
    switch(op)
    {
        case '+':
            return 1;
        case '*':
            return 2;
        case '\'':
            return 3;
        default:
            return -1;
    }
}

bst_sf* insert_bst_sf(matrix_sf *mat, bst_sf *root) {
    if (root==NULL) //if there is no root in the beginning or we found the place to put it
    {
        bst_sf *ptr =malloc(sizeof(bst_sf)); //allocates memory to be used for a node in the search tree

        ptr->mat=mat;
        ptr->right_child=NULL;
        ptr->left_child=NULL;
        root=ptr;

        return ptr;
    }
    else if((mat->name)>((root->mat)->name) ) //if the namne of the mat is greater than that of the root, insert it to the right
    {
        root->right_child=insert_bst_sf(mat, root->right_child);
    }
    else
    {
        root->left_child=insert_bst_sf(mat, root->left_child);
    }

    return root;
}

matrix_sf* find_bst_sf(char name, bst_sf *root) {
    if(root==NULL || root->mat==NULL) //if the root in the beginning has no name or it was not found
    {
        return NULL;
    }
    else if(((root->mat)->name)==name) //if the root has the name already or when we reached the name
    {
        return (root->mat);
    }
    else if(name>((root->mat)->name)) //if the name is greater in size than the roots, then we move to the right to look
    {
        return find_bst_sf(name, root->right_child);
    }
    else if(name<((root->mat)->name))
    {
        return find_bst_sf(name, root->left_child); //last option is if the name is less than the roots, we go left
    }
    
}

void free_bst_sf(bst_sf *root) {
     if(root != NULL) {
        free_bst_sf(root->left_child);
        free_bst_sf(root->right_child);
        free(root->mat);
        free(root);
    }
    
    }
    
matrix_sf* add_mats_sf(const matrix_sf *mat1, const matrix_sf *mat2) {
    //1: need a double for loop
    unsigned int loopLength;
    matrix_sf *added;

    //allocates the memory for the matrix along with calculating the length
    loopLength=(mat1->num_cols)*(mat1->num_cols);
    added=malloc(sizeof(matrix_sf)+loopLength*sizeof(int));

    //2: sets the column and row size to be mat1's row and column since addition can only be made with the same size of row and columns
    added->num_cols=mat1->num_cols;
    added->num_rows=mat1->num_rows;
    added->name='?';

    //3: sets each of the arrays value to the added values between the matrixes to form the new added matrix by loopin it for the col*row times
    for(int i=0;i<(int)loopLength;i++)
    {
        (added->values)[i]=(mat1->values[i])+ (mat2->values[i]);
    }

    return added;
}

matrix_sf* mult_mats_sf(const matrix_sf *mat1, const matrix_sf *mat2) {
    //creates the size of matrix and allocates memory for it
    unsigned int truSize;
    matrix_sf *mult;

    truSize=(mat1->num_rows)*(mat2->num_cols);
    mult=malloc(sizeof(matrix_sf)+truSize*sizeof(int));

    int count1=0;
    int count2=0;
    int offset=0; //represents offset for each row in mat1 
    int offset2=0;
    int position=0;

    //1: set column and row size in new matrix
    mult->num_rows=mat1->num_rows;
    mult->num_cols=mat2->num_cols;
    mult->name='?';
    mult->values[0]=0;

    int tempMat1Col=mat1->num_cols;
    int tempMat2Col=mat2->num_cols;

    while(position<(int)truSize)
    {
        count1=0;
        offset2=0;

        while(count1 < (tempMat1Col))
        {
            int tempStore= ((mat1->values[(offset+count1)]) * (mat2->values[(offset2+count2)]));
            ((mult->values)[position]) += tempStore; 
            offset2+=((mat2->num_cols)); //fix this
            count1++;
        }

        position++;
        count2++; //moves onto the next column in mat2

        if(count2>=((tempMat2Col))) //moves to the next row in mat1 and next column in mat2
        {
            offset+= (mat1->num_cols);
            count2=0;
        }

    }

   return mult;
}

matrix_sf* transpose_mat_sf(const matrix_sf *mat) {
    unsigned int sizeTrans=(mat->num_cols)*(mat->num_rows);
    matrix_sf *trans= malloc(sizeof(matrix_sf)+sizeTrans*sizeof(int) );
    int position=0; //represent what column we are at
    int count=0;
    int offset=0;
    int offset2=0;
    int counter=0;

    //1: sets the trans row to the mat column and vise versa as the definition as transpose means
    trans->num_cols=mat->num_rows;
    trans->num_rows=mat->num_cols;
    trans->name='?';
    trans->values[0]=0;

    while(counter<(int)sizeTrans)
    {
        while(count< (int)(mat->num_cols))
        {
            (trans->values)[position+offset]= (mat->values[count+offset2]);

            offset+=mat->num_rows;
            counter++; //we did one position already
            count++;
        }

        count=0;
        offset=0;

        offset2+=(mat->num_cols); //moves onto the next row for mat
        position++; //moves onto the next column 
    }

    

    return trans;
}

matrix_sf* create_matrix_sf(char name, const char *expr) {
   unsigned int sizeMat=0;
   int rowSize=0;
   int columnSize=0;
   char storage[1000];
   long temp1;

   char *ptr;

   //step 1: parse the string and get necessary info to then store
   sscanf(expr, "%d %d [ %[;-0-9  ]]", &rowSize, &columnSize, storage);

   //step 2: create the array with the necessary size gathered from sizeMat
   sizeMat=rowSize*columnSize;
   matrix_sf *temp=malloc(sizeof(matrix_sf)+sizeMat*sizeof(int));

   int count=0;
   ptr=storage;


   while(*ptr!='\0')
   {
       if(isdigit(*ptr)  || ((*ptr=='-')&&(isdigit(*(ptr+1))))) //goes through each each element to determine if it is a number to be stored
       {
           temp1=strtol(ptr, &ptr, 10);
           (temp->values)[count]=temp1;
           count++;
       }
       else
       {
           ptr++;
       }
   }


   //step 3: allocate enough memory for the matrix and then add the values to there   
   temp->name= name;
   temp->num_cols= columnSize;
   temp->num_rows=rowSize;

   return temp;

}

char* infix2postfix_sf(char *infix) {
    int i;
    int j=0;
    int lengthOfStr=strlen(infix);
    char *ptr; //points to the character
    ptr=infix;

    char *postfixStr= (char*)malloc(sizeof(char)*(lengthOfStr+2));
    int top=-1;
    char stackTemp[1000];

    for(i=0; i<lengthOfStr;i++)
    {
        if(infix[i]== ' ' || infix[i] =='\t')
        {
            continue;
        }

        if(isalnum(infix[i]))
        {
            postfixStr[j]=infix[i];
            j++;
        }

        else if(isOp(infix[i]))
        {
            while (top>-1 && presc(stackTemp[top])>= presc(infix[i]))
            {
                postfixStr[j]=stackTemp[top];
                j++;
                top--;
            }
            top++;
            stackTemp[top]=infix[i];
        }

        else if(infix[i]=='(')
        {
            top++;
            stackTemp[top] = infix[i];
        }

        else if(infix[i]==')')
        {
            while(top>-1 && stackTemp[top]!= '(')
            {
                postfixStr[j]=stackTemp[top];
                j++;
                top--;
            }
            //checks if there was an open parentheses at the end, signifying that it was a completely closed parethesis
            if(top>-1 && stackTemp[top]!='(')
            {
                return "Invalid";
            }
            else
            {
                top--; //removes that parenthesis
            }
        }

    }

    while(top>-1) //pops all the elements that are left out
    {
        if(stackTemp[top]=='(')
        {
            return "Invalid";
        }
        postfixStr[j]=stackTemp[top];
        j++;
        top--;
    }
    postfixStr[j]='\0';

    return postfixStr;
}

matrix_sf* evaluate_expr_sf(char name, char *expr, bst_sf *root) {
    matrix_sf *stack[1000];
    matrix_sf *temp;
    matrix_sf *temp2;
    matrix_sf *temp3;
    matrix_sf *temp4;
    matrix_sf *temp5;

    int top=-1;
    
    //converts first the expr to postfix notation to then be tranversed
    char *converted=infix2postfix_sf(expr);
    size_t length=strlen(converted);

    for(int i=0;i<(int)length;i++)
    {
        if(converted[i]==' ' || converted[i]=='\t')
        {
            continue;
        }

        else if(isalpha(converted[i])) //if it is a letter
        {
            //search for the letter in the bst and store it in a temp to then be pushed 
            temp=find_bst_sf(converted[i], root);
            //push it to the stack
            top++;
            stack[top]=temp;
        }

        else if(converted[i]=='\'')//for when it is a transpose
        {
            //pop the stack and then perform the transpose
            temp2= (transpose_mat_sf(stack[top]));

            //push it to the stack
            stack[top]=temp2;
        }
        else if(converted[i]=='+')
        {
            //pops the top 2 elements for them to then be added
            temp3=stack[top];
            top--;
            temp4=stack[top];
            top--;

            //pushes the new added natrix onto the stack
            temp5=add_mats_sf(temp4, temp3);
            top++;
            stack[top]=temp5;

            //checks if they previous 2 don't got names; in which case, we will free them
            if(temp3->name=='?')
            {
                free(temp3);
            }
            if(temp4->name=='?')
            {
                free(temp4);
            }
        }
        else if(converted[i]=='*')
        {
            //pops top two
            temp3=stack[top];
            top--;
            temp4=stack[top];
            top--;

            //pushes the new mult matrix onto the stack
            temp5=mult_mats_sf(temp4, temp3);
            top++;
            stack[top]=temp5;

            //checks if they got no names so we can free them
            if(temp3->name=='?')
            {
                free(temp3);
            }
            if(temp4->name=='?')
            {
                free(temp4);
            }
        }

    }
    //once we reach the end, we pop the top of the stack and name it
    matrix_sf *valReturn=stack[top];
    top--;
    valReturn->name=name;

    //then free the bst once we done using it
    free_bst_sf(root);

    return valReturn;
    
}

matrix_sf *execute_script_sf(char *filename) {
    char *str=NULL;
    FILE *tempFile=fopen(filename, "r");
    if(tempFile==NULL)
    {
        printf("failed");
        exit(1);
    }

    size_t max_line_size= MAX_LINE_LEN;

    bst_sf *noder=NULL;
    matrix_sf *temper2;
    matrix_sf *temper;

    while(getline(&str, &max_line_size, tempFile)!=-1) //goes through each line
    {
        char str2[1000]; //to then be modified
        memset(str2, '\0', sizeof(str2));
        strcpy(str2, str);

        //seperates the two parts to be used
        char token[]="=";
        char *portion=strtok(str2, token); //gets the first half 
        char *portion2=strtok(NULL, token); //gets the second half to then be compared

        //checks what the name of the matrix will be called based on the first portion
        char tempName;
        sscanf(portion, " %c", &tempName);

        //checks if the first instance of a character is a number or a letter to then decide which one to call
        char tempCheck;
        sscanf(portion2, " %c", &tempCheck);
        
        //if the first char that it meets of the second portion is a number, we will assume this is a matrix to be stored
        if(tempCheck >= '0' || tempCheck <= '9')
        {
            temper=create_matrix_sf(tempName, portion2); //creates the matrix
            noder=insert_bst_sf(temper, noder); //inserts it into the bst
        }
        else if(isalpha(tempCheck)>0)//if the first char isnt a number, we are performing the evaluating the expression
        {
            temper2=evaluate_expr_sf(tempName, portion2, noder);
            noder=insert_bst_sf(temper2, noder);
        }
    }

    free(str);
    fclose(tempFile);

    return temper2;
}

// This is a utility function used during testing. Feel free to adapt the code to implement some of
// the assignment. Feel equally free to ignore it.
matrix_sf *copy_matrix(unsigned int num_rows, unsigned int num_cols, int values[]) {
    matrix_sf *m = malloc(sizeof(matrix_sf)+num_rows*num_cols*sizeof(int));
    m->name = '?';
    m->num_rows = num_rows;
    m->num_cols = num_cols;
    memcpy(m->values, values, num_rows*num_cols*sizeof(int));
    return m;
}

// Don't touch this function. It's used by the testing framework.
// It's been left here in case it helps you debug and test your code.
void print_matrix_sf(matrix_sf *mat) {
    assert(mat != NULL);
    assert(mat->num_rows <= 1000);
    assert(mat->num_cols <= 1000);
    printf("%d %d ", mat->num_rows, mat->num_cols);
    for (unsigned int i = 0; i < mat->num_rows*mat->num_cols; i++) {
        printf("%d", mat->values[i]);
        if (i < mat->num_rows*mat->num_cols-1)
            printf(" ");
    }
    printf("\n");
}

// int main()
// {
//     char *str1="A = 3 4[-4 18 6 7; 10 -14    29 8  ;21 -99 0 7;]";
//     char str[1000];

//     memset(str, '\0', sizeof(str));
//     strcpy(str, str1);
//     char token[]="=";
//     char *portion=strtok(str, token); //gets the first half 
//     char *portion2=strtok(NULL, token);

//     char tempName;
//         sscanf(portion, " %c", &tempName);

//         //checks if the first instance of a character is a number or a letter to then decide which one to call
//         char tempCheck;
//         sscanf(portion2, " %c", &tempCheck);
        

//     printf("%s \n", portion);
//     printf("%s \n", portion2);

//     printf("%c \n", tempName);
//     printf("%c \n", tempCheck);

//     int game=isalpha(tempCheck);
//     printf("%d \n", game);
//     if(tempCheck >= '0' && tempCheck <= '9')
//     {
//         printf("working 1 \n");
//     }
//     else if(isalpha(tempCheck)>0)
//     {
//         printf("working 2 \n");
//     }
    
//     return 0;

// }



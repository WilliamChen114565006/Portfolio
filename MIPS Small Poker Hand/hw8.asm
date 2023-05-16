.data
# Command-line arguments
num_args: .word 0
addr_arg0: .word 0
addr_arg1: .word 0
addr_arg2: .word 0
addr_arg3: .word 0
addr_arg4: .word 0
no_args: .asciiz "You must provide at least one command-line argument.\n"

# Output messages
straight_str: .asciiz "STRAIGHT_HAND"
four_str: .asciiz "FOUR_OF_A_KIND_HAND"
pair_str: .asciiz "TWO_PAIR_HAND"
unknown_hand_str: .asciiz "UNKNOWN_HAND"

# Error messages
invalid_operation_error: .asciiz "INVALID_OPERATION"
invalid_args_error: .asciiz "INVALID_ARGS"

# Put your additional .data declarations here, if any.


# Main program starts here
.text
.globl main
main:
    # Do not modify any of the code before the label named "start_coding_here"
    # Begin: save command-line arguments to main memory  
    sw $a0, num_args
    beqz $a0, zero_args
    li $t0, 1
    beq $a0, $t0, one_arg
    li $t0, 2
    beq $a0, $t0, two_args
    li $t0, 3
    beq $a0, $t0, three_args
    li $t0, 4
    beq $a0, $t0, four_args
five_args:
    lw $t0, 16($a1)
    sw $t0, addr_arg4
four_args:
    lw $t0, 12($a1)
    sw $t0, addr_arg3
three_args:
    lw $t0, 8($a1)
    sw $t0, addr_arg2
two_args:
    lw $t0, 4($a1)
    sw $t0, addr_arg1
one_arg:
    lw $t0, 0($a1)
    sw $t0, addr_arg0
    j start_coding_here

zero_args:
    la $a0, no_args
    li $v0, 4
    syscall
    j exit
    # End: save command-line arguments to main memory

start_coding_here:
	#first load in the first argument and then store it and check if it equals like e
	lw $s0, addr_arg0
	lbu $t0, 0($s0)
	lbu $t2, 1($s0)	#stores the second character in the fisrt argument, which should be nothing
	li $t1, 'E'

	#check if there is an addition character in the first argument
	bne $t2, $zero, exit_no_op

	#check if it is equal to each of the letters or if the beginning has more than one char
	beq $t0, $t1, e_checker
	li $t1, 'D'
	beq $t0, $t1, d_checker
	li $t1, 'P'
	beq $t0, $t1, p_checker
	#if none, then jump to exit_no_op
	j exit_no_op
			

    # Start the assignment by writing your code here
exit_no_op:
	la $a0, invalid_operation_error
	li $v0, 4
	syscall
	j exit

invalid_args_er:
	la $a0, invalid_args_error
	li $v0, 4
	syscall
	j exit

e_checker:
	#now check the num of args 
	lwr $t0, num_args #load num of args into s0
	li $t1, 5
	bne $t0, $t1, invalid_args_er
	
	#after its been checked, we can begin e's program by starting with checking each of the inputs
	#if its larger than the expected one, jump to invalid args
	lw $t0, addr_arg1
	lbu $s0, 0($t0)
	
	addi $s0, $s0, -48 #subtract 48 to convert it from ascii to integer, where s1 saves the total value of the first argument
	li $t1, 10
	mul $s0, $s0, $t1 #s1=s1*10
	
	lbu $t2, 1($t0)
	addi $t2, $t2, -48
	add $s0, $s0, $t2 #s0=s0+t2 ---> means that we got the first argument
	
	#check if the argument is less than or greater than 0 and 63 (FIRST ONE IS s0)
	li $t2, 63
	blt $s0, $zero, invalid_args_er #if s0 < 0, then we jump to the error
	bgt $s0, $t2, invalid_args_er #if s0 >63, we just to the error
	
	##ARGUMENT 2
	lw $t0, addr_arg2
	lbu $s1, 0($t0)
	
	addi $s1, $s1, -48
	mul $s1, $s1, $t1
	
	lbu $t2, 1($t0)
	addi $t2, $t2, -48
	add $s1, $s1, $t2 #s1=s1+t2  --> we got the second argument
	
	#check if argument is less than or greather than 0 or 63 (SECOND IS s1)
	li $t2, 31
	blt $s1, $zero, invalid_args_er #if s1 < 0, then we jump to the error
	bgt $s1, $t2, invalid_args_er #if s1 >63, we just to the error
	
	##ARGUMENT 3
	lw $t0, addr_arg3
	lbu $s2, 0($t0)
	
	addi $s2, $s2, -48
	mul $s2, $s2, $t1
	
	lbu $t2, 1($t0)#loads second char of the second argument
	addi $t2, $t2, -48
	add $s2, $s2, $t2 #gets the third argument
	
	#check for less than or great (TIHIRD IS s2)
	li $t2, 31
	blt $s2, $zero, invalid_args_er #if s2 < 0, then we jump to the error
	bgt $s2, $t2, invalid_args_er #if s2 >63, we just to the error
	
	##ARGUMENT 4
	lw $t0, addr_arg4
	lbu $s3, 0($t0)
	
	addi $s3, $s3, -48
	li $t1, 10000 #loads it to be 10000 to be multiplied to move it
	mul $s3, $s3, $t1 ##10000 place
	
	lbu $t2, 1($t0) ##1000 place
	addi $t2, $t2, -48
	li $t1, 1000
	mul $t2, $t2, $t1
	add $s3, $s3, $t2
	
	lbu $t2, 2($t0) ##100
	addi $t2, $t2, -48
	li $t1, 100
	mul $t2, $t2, $t1
	add $s3, $s3, $t2
	
	lbu $t2, 3($t0) ##10
	addi $t2, $t2, -48
	li $t1, 10
	mul $t2, $t2, $t1
	add $s3, $s3, $t2
	
	lbu $t2, 4($t0) ##1
	addi $t2, $t2, -48
	add $s3, $s3, $t2
	
	##CHECK FOR IF IT GOES OUT OF BOUND
	li $t2, 65535
	blt $s3, $zero, invalid_args_er
	bgt $s3, $t2, invalid_args_er
	
	#after getting everything, mask the bits for each of the arguments
	andi $s0, $s0, 0x0000003F#ARGUMENT ONE IS 6 BIT MASK, gets the first 6 bits
	andi $s1, $s1, 0x0000001F#ARGUMENT TWO IS 5 BIT MASK
	andi $s2, $s2, 0x0000001F#ARGUMENT THREE IS 5 BIT MASK
	andi $s3, $s3, 0x0000FFFF#ARGUMENT FOUR IS 16 BIT MASK
	
	##AFTER MASK, we then shift it around with bitwise operator and store it into s4
	li $s4, 0
	add $s4, $s4, $s3
	
	sll $s2, $s2, 16#shift argument 3 by 16 bits
	add $s4, $s4, $s2
	
	sll $s1, $s1, 21#shift argument 2 by 16+5 bits
	add $s4, $s4, $s1
	
	sll $s0, $s0, 26#shift argument 1 by 16+5+5 bits
	add $s4, $s4, $s0
	
	#print $s4 out
	move $a0, $s4
	li $v0, 34
	syscall
	j exit
	
d_checker:
	#stores the number arguments in and then checks if it equals to 2
	lwr $t0, num_args #load num of args into s0
	li $t1, 2
	bne $t0, $t1, invalid_args_er
	
	##CHECK FOR VALID 0x SIGN IN THE BEGINNING
	lw $t0, addr_arg1
	lbu $t1, 0($t0)
	li $t2, 48
	bne $t1, $t2, invalid_args_er #if t1 !=0, then we branch to error
	
	#next, load up and check if the next value is a lowercase x
	lbu $t1, 1($t0)
	li $t2, 'x'
	bne $t1, $t2, invalid_args_er #if t1 !=x (lowercase x), we branch to error
	
	
	##Check if it is less than 8 or greater than 8 characters
	lbu $t1, 9($t0)
	beq $t1, $zero, invalid_args_er #if t1==0, we know that there are less than 8 characters
	
	lbu $t1, 10($t0)
	bne $t1, $zero, invalid_args_er#if t1!=0, we know that there are more than 8 characters
	
	
	##After that, we loop through the array of strings to then identify each character 
	addi $t0, $t0, 2#move the index by 2 to get to the positon of where the char starts
	li $s0, 0
	j d_loop
	
d_loop:
	lbu $t1, 0($t0)
	beqz $t1, d_loop_exit #exits once it hits a null character
	
	#modify t1 
	li $t2, 102
	bgt $t1, $t2, invalid_args_er #we know it falls out of the boundary for letters
	
	li $t2, 9
	addi $t1, $t1, -48
	blt $t1, $zero, invalid_args_er #if its less than 0, we know its not a number
	bgt $t1, $t2, d_error_not_num #if it is greater than 9, we have to account that it is a letter
	
	j d_loop_2 #if it is a num, we jump straight to continuing the loop
	
	
d_error_not_num:
	addi $t1, $t1, -49 ##subtract it so that it becomes a lowercase letter
	blt $t1, $zero, invalid_args_er ##if it is less than any of the lowercase letters and is not a number, we call it an error
	
	addi $t1, $t1, 10 #convert the letter to the actual hexadecimal number
	j d_loop_2
	
d_loop_2:
	#assuming everything works then we need to store it into s0
	sll $s0, $s0, 4 #shift it by 4 bits
	add $s0, $s0, $t1 #add the number
	
	addi $t0, $t0, 1 #goes forward in the string
	j d_loop

d_loop_exit:
	##once we obtain everything in s0, we then split the bits through masks
	#get mask for 6 bit one then shift
	srl $t0, $s0, 26
	
	sll $t1, $s0, 6#andi $t1, $s0, 0x03E00000
	srl $t1, $t1, 27
	
	sll $t2, $s0, 11#andi $t2, $s0, 0x001F0000
	srl $t2, $t2, 27
	
	sll $t3, $s0, 16#li $t3, 0
	srl $t3, $t3, 16#andi $t3, $s0, 0x0000FFFF
	
	##after that, start printing it
	j d_argument_1
	
	
d_argument_1:
	li $t4, 10
	div $t0, $t4
	
	mflo $s1
	mfhi $s2
	
	move $a0, $s1 #prints the first digit
	li $v0, 1
	syscall
	
	move $a0, $s2 #prints the second digit
	li $v0, 1
	syscall
	
	li $a0, 32 ##prints space
	li $v0, 11
	syscall
	
	j d_argument_2

d_argument_2:
	li $t4, 10
	div $t1, $t4 ##for the second argument, we split it up to get values to then print
	
	mflo $s1
	mfhi $s2
	
	move $a0, $s1 #prints the first digit
	li $v0, 1
	syscall
	
	move $a0, $s2 #prints the second digit
	li $v0, 1
	syscall
	
	li $a0, 32 ##prints space
	li $v0, 11
	syscall
	
	j d_argument_3

d_argument_3:
	li $t4, 10
	div $t2, $t4 ##for the second argument, we split it up to get values to then print
	
	mflo $s1
	mfhi $s2
	
	move $a0, $s1 #prints the first digit
	li $v0, 1
	syscall
	
	move $a0, $s2 #prints the second digit
	li $v0, 1
	syscall
	
	li $a0, 32 ##prints space
	li $v0, 11
	syscall
	
	j d_argument_4

d_argument_4:
	#t3 is argument 4
	li $t4, 0 #digit 1
	li $t5, 0 #digit 2
	li $t6, 0 #digit 3
	li $t7, 0 #digit 4
	li $t8, 0 #digit 5
	
	
	li $s0, 10
	div $t3, $s0
	mfhi $t8 #moves remainder number to be stored in digit 5
	mflo $t3 #moves the new quiotent into t3
	
	div $t3, $s0
	mfhi $t7 #moves remainder to digit 4
	mflo $t3
	
	div $t3, $s0
	mfhi $t6 #digit 3
	mflo $t3
	
	div $t3, $s0
	mfhi $t5 #digit 2
	mflo $t3
	
	div $t3, $s0
	mfhi $t4 #digit 1
	mflo $t3
	
	#now we print it
	move $a0, $t4 #prints the first digit
	li $v0, 1
	syscall
	
	move $a0, $t5 
	li $v0, 1
	syscall
	
	move $a0, $t6 
	li $v0, 1
	syscall
	
	move $a0, $t7 
	li $v0, 1
	syscall
	
	move $a0, $t8 
	li $v0, 1
	syscall
	
	j exit


p_checker:
	#stores the number arguments in and then checks if it equals to 2
	lwr $t0, num_args #load num of args into s0
	li $t1, 2
	bne $t0, $t1, invalid_args_er
	
	#after that, store each of the characters
	lw $t0, addr_arg1
	
	lbu $s0, 0($t0) ##loads each of the 5 letters into addresses to then anaylze
	lbu $s1, 1($t0)
	lbu $s2, 2($t0)
	lbu $s3, 3($t0)
	lbu $s4, 4($t0)
	
	#get mask of the first bit of each of them
	andi $s0, $s0, 0x0000000F
	andi $s1, $s1, 0x0000000F
	andi $s2, $s2, 0x0000000F
	andi $s3, $s3, 0x0000000F
	andi $s4, $s4, 0x0000000F
	
	#counters for the cards 
	li $t2, 0
	li $t3, 0
	li $t4, 0
	li $t5, 0
	li $t6, 0
	
	
	lbu $t1, 0($t0) #load the first char of the string
	andi $t8, $t1, 0x0000000F #gets the rightmost bit of hexadecimal 
	
	li $s6, 0 #storage for checking straight
	li $s7, 0 #counter for straight
	
	j p_loop#start looping once everything is set up
		
	
	

p_loop:
	lbu $t1, 0($t0)
	beqz $t1, p_loop_exit #once we are done looking through the strings, we want to jump out to the exit to count

	#get the mask for the first 4 bits of the char to regonize the rank
	andi $t7, $t1, 0x0000000F
	
	j p_not_straight1


p_pair1:
	addi $t2, $t2, 1
	j p_go
	
p_pair2:
	addi $t3, $t3, 1
	j p_go
	
p_pair3:
	addi $t4, $t4, 1
	j p_go

p_pair4:
	addi $t5, $t5, 1
	j p_go

p_pair5:
	addi $t6, $t6, 1
	addi $s6, $s6, 1
	j p_go

p_not_straight1:
	move $s6, $t7
	addi $s6, $s6, 1
	
	beq $s6, $s0, p_not_straight2
	beq $s6, $s1, p_not_straight2
	beq $s6, $s2, p_not_straight2
	beq $s6, $s3, p_not_straight2
	beq $s6, $s4, p_not_straight2

	
	#if not a chance at straight, then we just move forward
	beq $t7, $s0, p_pair1 #if the rank is the same as the rank in the first char
	beq $t7, $s1, p_pair2
	beq $t7, $s2, p_pair3
	beq $t7, $s3, p_pair4
	beq $t7, $s4, p_pair5
	
	j p_go #if there are no pairs after a straight, we just go forward 

p_not_straight2:
	addi $s7, $s7, 1 #if there is a chance at a straight, we go up
	
	beq $t7, $s0, p_pair1 #if the rank is the same as the rank in the first char
	beq $t7, $s1, p_pair2
	beq $t7, $s2, p_pair3
	beq $t7, $s3, p_pair4
	beq $t7, $s4, p_pair5
	
	j p_go #if there are no pairs after a straight, we just go forward 

p_go:
	addi $t0, $t0, 1 #goes forward in the string
	j p_loop
	
p_loop_exit:
	li $s5, 3
	bgt $t2, $s5, p_four #if any of the pairs had more counts of cards that were greater than 3, it means a four pair was found
	bgt $t3, $s5, p_four
	bgt $t4, $s5, p_four
	bgt $t5, $s5, p_four
	bgt $t6, $s5, p_four
	
	li $s5, 1
	li $t9, 2 #number to keep pairs
	li $t1, 0 #counts pairs
	bgt $t2, $s5, p_paire1 #if any pairs had a count greater than one, it means it can be a pair
	bgt $t3, $s5, p_paire1
	bgt $t4, $s5, p_paire1
	bgt $t5, $s5, p_paire1
	bgt $t6, $s5, p_paire1
		
	#for straights	
	li $t7, 4
	beq $s7, $t7, p_straight #means that there was a straight found if t9 was at zero, meaning we never jumped away the straight
	
	
	#if there were no pairs or straights, then we go print unknown hand
	j p_unknown
	
	j exit

p_unknown:
	la $a0, unknown_hand_str
	li $v0, 4
	syscall
	j exit

p_four:
	la $a0, four_str
	li $v0, 4
	syscall
	j exit

p_two:

	#if confirmed pair
	la $a0, pair_str
	li $v0, 4
	syscall
	j exit
	
p_paire1:
	beq $t1, $t9, p_two #if pair count==4, we jump out of the print there is a pair
	bgt $t2, $s5, adder1#if 1 first card has more than 1, then it has a pair
	j p_paire2
	
adder1:
	addi $t1, $t1, 1 #we know that is one pair
	j p_paire2

p_paire2:
	
	beq $t1, $t9, p_two #if pair count==4, we jump out of the print there is a pair
	bgt $t3, $s5, adder2#if 1 first card has more than 1, then it has a pair
	j p_paire3
	
adder2:
	addi $t1, $t1,1
	j p_paire3
	

p_paire3:
	beq $t1, $t9, p_two #if pair count==4, we jump out of the print there is a pair
	bgt $t4, $s5, adder3#if 1 first card has more than 1, then it has a pair
	j p_paire4
	
adder3:
	addi $t1, $t1, 1
	j p_paire4
	
p_paire4:
	beq $t1, $t9, p_two #if pair count==4, we jump out of the print there is a pair
	bgt $t5, $s5, adder4#if 1 first card has more than 1, then it has a pair
	j p_paire5

adder4:
	addi $t1, $t1, 1
	j p_paire5
	
p_paire5:
	beq $t1, $t9, p_two #if pair count==4, we jump out of the print there is a pair
	
	#if there isnt by then, we print unknown hand
	j p_unknown
	
	

p_straight:
	la $a0, straight_str
	li $v0, 4
	syscall
	j exit

exit:
    li $v0, 10
    syscall


.text


#-----------
# s0 will be used to store the address of the cipher text
# $s1 will be used to store the new plaintext to be written over
# s2 will be used for indices address
# s3 will be used for the number of indices or hold the count for it, where it counts down to 0
# s4 will be used for storing the amount of characters for plaintext
#------
null_cipher_sf:
    addi $sp, $sp, -24
    sw $s0, 0($sp)
    sw $s2, 4($sp)
    sw $s3, 8($sp)
    sw $s1, 12($sp) #for the address of plaintext
    sw $s4, 16($sp)
    sw $ra, 20($sp)
    
    li $s4, 0 #start at 0 for the amount of letters in plaintext
    move $s1, $a0 #stores for plain text to be written over in the address
    move $s2, $a2 #copies the address of the indices to then traverse through
    move $s0, $a1 #copies the address of the cipher text to s0
    move $s3, $a3 #moves number of indices to there
    
    
    #once everything has been made and initiated, we start the loop
    j indicies_null_loop
    

    
indicies_null_loop:
	#we start with a loop through each time it reaches a character
	lbu $t0, 0($s2)
	beqz $s3, done_null #once we reach the end of the indicies, we can end the loop
	
	#else, if it is not, we get value in integer and store it and then jal to the cipher loop to loop through the cipher 
	addi $t0, $t0, -1 #one lower than the actual number to make it -1 if it was 0
	j cipher_null_loop #jump to cipher loop to then add it to the plaintext
	
	
	
#------
# t0 will have the stored value of the character to loop through to (the position of the number_)
# s3 will be the counter down 
# s1 is position of plain text
#----
cipher_null_loop:
	lb $t1, 0($s0) #load byte for cipher text
	
	#we will jump back when we reach a space or null terminated character
	li $t2, 32
	beq $t1, $t2, mover_up
	beqz $t1, mover_up

	#else, we want to loop till we reach the character by counting down to 0, in which if it 0, we would then replace the character
	#if it is -1. then it already doesnt replace anything so
	beq $t0, $zero, replace_char_plain
	
	#else if it isnt the position that we want, we will subtract one off the position then move forward  
	addi $t0, $t0, -1
	addi $s0, $s0, 1 #moves up in cipher text
	j cipher_null_loop #then jump back

replace_char_plain:
	
	sb $t1, 0($s1) #supposedly replaces the plaintext character with t1 in the first position
	li $t0, 60 #set $t0 to absurd number to make sure it cant go back to replacing plaintext till the next indicies
	
	addi $s4, $s4, 1 #adds up a character for plaintext used
	addi $s1, $s1, 1 #move up s1 to the next position of plaintext
	addi $s0, $s0, 1 #also moves up position for cipher text
	
	j cipher_null_loop

mover_up:
	addi $s3, $s3, -1 #even if it doesn't replace anything, we got past one of the indices
	addi $s2, $s2, 4 #moves up the indicies one to the next char for later
	addi $s0, $s0, 1 #moves it past the space in cipher text
	
	j indicies_null_loop #then jump back after reaching a space


done_null:
	sb $zero, 0($s1) #we want to store the null terminator at the end of plaintext
	move $v0, $s4 #gon use v0 for results
	
	#restores all the previous values used in there
	lw $s0, 0($sp)
    	lw $s2, 4($sp)
    	lw $s3, 8($sp)
    	lw $s1, 12($sp) 
    	lw $s4, 16($sp)
	lw $ra, 20($sp) 
	addi $sp, $sp, 24
	
	#then jumps back to the caller
	jr $ra
	
transposition_cipher_sf:
    #------
    # s0 will be plaintext
    # s1 will be ciphertext
    # s2 will be num rows
    # s3 will be num cols
    #------
    addi $sp, $sp, -20
    sw $s0, 0($sp)
    sw $s1, 4($sp)
    sw $s2, 8($sp)
    sw $s3, 12($sp)
    sw $ra, 16($sp)
    
    
    move $s0, $a0
    move $s1, $a1
    move $s2, $a2
    move $s3, $a3
    
    li $t2, 0 #row counter for letter i

rower:
	li $t3, 0 #column counter for letter j

#s2 is number of rows
#s3 is number of cols

coler:
	mul $t1, $t3, $s2 #j*row num
	add $t1, $t1, $t2 #j * num_rows + i
	
	add $t1, $t1, $s1 #address for cipher 
	
	
	#after that, we store it into the char of plaintext
	lb $t7, 0($t1)
	sb $t7, 0($s0) #stores the first char that obtained into plaintext
	addi $s0, $s0, 1 #moves up to the next element to be attached later
	
	
	#then we move on
	addi $t3, $t3, 1 #j++
	blt $t3, $s3, coler #j< col nums

coler_done:
	addi $t2, $t2, 1
	blt $t2, $s2, rower #i< num rows
		
rower_done:
	#we're done storing it into plaintext so then we loop back through plaintext to see where the first astrick is; else we just replace it 
	sb $zero, 0($s0) #stores a null terminator in case there is no astricks to be checked
	move $t6, $a0 #store address of starter in t6
	j remover_loop
	
	
remover_loop:
	lb $t0, 0($t6)
	li $t4, 42
	beq $t0, $t4, finisher #if it reaches an astrick, we know we have to place a null terminator
	beqz $t0, finisher #if it reaches my null terminator, we know we have to end it anyways
	
	#else if it does not reach anything, we just move up
	addi $t6, $t6, 1 
	j remover_loop	
	
finisher:
	sb $zero, 0($t6) #stores null terminator in address of the first astrick
	
	lw $s0, 0($sp)
    	lw $s1, 4($sp)
   	lw $s2, 8($sp)
    	lw $s3, 12($sp)
    	lw $ra, 16($sp)
    	addi $sp, $sp, 20
    	
	jr $ra
	
	 
	  
#------
    # s0 will be plaintext
    # s1 will be ciphertext
    # s2 will be num rows
    # s3 will be num cols
    #
    # s4 will be indicies
    # s5 will be number of indices
#------

#-----------
# s0 will be used to store the address of the cipher text
# $s1 will be used to store the new plaintext to be written over
# s2 will be used for indices address
# s3 will be used for the number of indices or hold the count for it, where it counts down to 0
# s4 will be used for storing the amount of characters for plaintext
#------

decrypt_sf:
	move $s5, $a0 #stores original address for a0 to then be picked up later for use in cipher text in null cipher
	lw $s6, 4($sp) #traversal for indicies
	lw $s7, 0($sp) 
	
	move $fp, $sp #copy the frame pointer
	addi $sp, $sp, 4
	sw $ra, 0($fp)
	
	addi $sp, $sp, -1000 #allocates space for the string
	addi $a0, $sp, 4 #offset
	jal transposition_cipher_sf
	
	#moves all the arguments together to then be used by null cipher by changing it up
	
	move $a1, $a0 #moves the plaintext to be the cipher text to be read
    	move $a0, $s5 #moves back our previously stored address for plaintext to be used in that section
    	move $a2, $s6 #move the arguments to then be used by null cipher
	move $a3, $s7 #move the arguments to then be used by null for amount in indicies
    	
    	jal null_cipher_sf
    	
    	lw $ra, 0($fp) #load back the register address
    	addi $sp, $sp, 1004
    	move $sp, $fp
    	
    	jr $ra
    	

	
	
    
    	

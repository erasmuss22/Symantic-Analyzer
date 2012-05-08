	.text
	b       main		#Branch so program starts at main
	.text
_printxyz:		# FUNCTION ENTRY
	sw      $ra, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $fp, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	addu    $fp, $sp, 20
	lw      $t0, 0($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L1:	.asciiz "\n"
	.text
	la      $t0, .L1
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -4($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L2:	.asciiz "\n"
	.text
	la      $t0, .L2
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -8($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L3:	.asciiz "\n"
	.text
	la      $t0, .L3
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, 0($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -4($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	bne     $t1, $t2, .L5	#check if equal
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	b       .L4
.L4:
				#WRITE STR
	.data
.L7:	.asciiz "x equals y\n"
	.text
	la      $t0, .L7
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, 0($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L8:	.asciiz "\n"
	.text
	la      $t0, .L8
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	b       .L6		#skip else statements
.L5:
				#WRITE STR
	.data
.L9:	.asciiz "x does not equal y\n"
	.text
	la      $t0, .L9
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, 0($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L10:	.asciiz "\n"
	.text
	la      $t0, .L10
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
.L6:
.L0:		# FUNCTION EXIT
	lw      $ra, -12($fp)	#load return address
	move    $t0, $fp	#save control link
	lw      $fp, -16($fp)	#restore FP
	move    $sp, $t0	#restore SP
	jr      $ra		#return
	.text
_printInt:		# FUNCTION ENTRY
	sw      $ra, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $fp, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	addu    $fp, $sp, 12
	subu    $sp, $sp, 8
	li      $t1, 3
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -12($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t1, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -16($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t0, 0($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L12:	.asciiz "\n"
	.text
	la      $t0, .L12
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, 0($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -12($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -16($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	jal     _printxyz		#jump to function
	sw      $v0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	li      $t1, 6
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -16($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, -16($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -12($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 0($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	jal     _printxyz		#jump to function
	sw      $v0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
.L11:		# FUNCTION EXIT
	lw      $ra, -4($fp)	#load return address
	move    $t0, $fp	#save control link
	lw      $fp, -8($fp)	#restore FP
	move    $sp, $t0	#restore SP
	jr      $ra		#return
	.text
	.globl main
main:		# FUNCTION ENTRY
	sw      $ra, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $fp, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	addu    $fp, $sp, 8
	subu    $sp, $sp, 4
	li      $t1, 7
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	jal     _printInt		#jump to function
	sw      $v0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
.L13:		# FUNCTION EXIT
	lw      $ra, 0($fp)	#load return address
	move    $t0, $fp	#save control link
	lw      $fp, -4($fp)	#restore FP
	move    $sp, $t0	#restore SP
	jr      $ra		#return

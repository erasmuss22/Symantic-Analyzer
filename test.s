	.text
	b       main		#Branch so program starts at main
	.text
	.globl main
main:		# FUNCTION ENTRY
	sw      $ra, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $fp, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	addu    $fp, $sp, 8
	subu    $sp, $sp, 64
	li      $t0, 3
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t2, -1
	mult    $t1, $t2
	mflo    $t1		#move from lo after multiplying
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	addi    $t1, $t2, 1	#increase by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -12($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L1:	.asciiz "y="
	.text
	la      $t0, .L1
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
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
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	not     $t1, $t1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -20($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L3:	.asciiz "z="
	.text
	la      $t0, .L3
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L4:	.asciiz "\n"
	.text
	la      $t0, .L4
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L5:	.asciiz "z="
	.text
	la      $t0, .L5
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L6:	.asciiz "\n"
	.text
	la      $t0, .L6
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L7:	.asciiz "x="
	.text
	la      $t0, .L7
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
.L8:	.asciiz "\n"
	.text
	la      $t0, .L8
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L9:	.asciiz "x="
	.text
	la      $t0, .L9
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
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
				#WRITE STR
	.data
.L11:	.asciiz "x="
	.text
	la      $t0, .L11
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
.L12:	.asciiz "\n"
	.text
	la      $t0, .L12
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L13:	.asciiz "x="
	.text
	la      $t0, .L13
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L14:	.asciiz "\n"
	.text
	la      $t0, .L14
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L15:	.asciiz "x="
	.text
	la      $t0, .L15
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
.L16:	.asciiz "\n"
	.text
	la      $t0, .L16
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L17:	.asciiz "/////// ++, --, and arithmetic ////////\n"
	.text
	la      $t0, .L17
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	li      $t0, 1
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 2
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -12($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 0
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -20($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L18:	.asciiz "x="
	.text
	la      $t0, .L18
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
.L19:	.asciiz ", y="
	.text
	la      $t0, .L19
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L20:	.asciiz ", z="
	.text
	la      $t0, .L20
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L21:	.asciiz "\n"
	.text
	la      $t0, .L21
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	addi    $t1, $t2, 1	#increase by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -12($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	add     $t1, $t1, $t2	#perform addition
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -20($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L22:	.asciiz "z = x++ + y;"
	.text
	la      $t0, .L22
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L23:	.asciiz "\n"
	.text
	la      $t0, .L23
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L24:	.asciiz "x="
	.text
	la      $t0, .L24
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
.L25:	.asciiz ", y="
	.text
	la      $t0, .L25
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L26:	.asciiz ", z="
	.text
	la      $t0, .L26
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L27:	.asciiz "\n"
	.text
	la      $t0, .L27
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	addi    $t1, $t2, 1	#increase by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -12($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sub     $t1, $t1, $t2	#generate subtraction
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -20($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L28:	.asciiz "z = x++ - y;"
	.text
	la      $t0, .L28
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L29:	.asciiz "\n"
	.text
	la      $t0, .L29
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L30:	.asciiz "x="
	.text
	la      $t0, .L30
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
.L31:	.asciiz ", y="
	.text
	la      $t0, .L31
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L32:	.asciiz ", z="
	.text
	la      $t0, .L32
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L33:	.asciiz "\n"
	.text
	la      $t0, .L33
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	addi    $t1, $t2, 1	#increase by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -12($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sub     $t1, $t1, 1	#decrease by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -12($fp)
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sub     $t1, $t1, $t2	#generate subtraction
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -20($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L34:	.asciiz "z = x++ - y--;"
	.text
	la      $t0, .L34
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L35:	.asciiz "\n"
	.text
	la      $t0, .L35
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L36:	.asciiz "x="
	.text
	la      $t0, .L36
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
.L37:	.asciiz ", y="
	.text
	la      $t0, .L37
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L38:	.asciiz ", z="
	.text
	la      $t0, .L38
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L39:	.asciiz "\n"
	.text
	la      $t0, .L39
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	addi    $t1, $t2, 1	#increase by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -12($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	addi    $t1, $t2, 1	#increase by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -12($fp)
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	li      $t0, 1
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	add     $t1, $t1, $t2	#perform addition
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	mult    $t1, $t2	#perform multiplication
	mflo    $t1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -20($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L40:	.asciiz "z = x++ * (y++ + 1);"
	.text
	la      $t0, .L40
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L41:	.asciiz "\n"
	.text
	la      $t0, .L41
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L42:	.asciiz "x="
	.text
	la      $t0, .L42
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
.L43:	.asciiz ", y="
	.text
	la      $t0, .L43
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L44:	.asciiz ", z="
	.text
	la      $t0, .L44
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L45:	.asciiz "\n"
	.text
	la      $t0, .L45
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	addi    $t1, $t2, 1	#increase by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -12($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	div     $t1, $t2	#perform division
	mflo    $t1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -20($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L46:	.asciiz "z = x++ / y;"
	.text
	la      $t0, .L46
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L47:	.asciiz "\n"
	.text
	la      $t0, .L47
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L48:	.asciiz "x="
	.text
	la      $t0, .L48
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
.L49:	.asciiz ", y="
	.text
	la      $t0, .L49
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L50:	.asciiz ", z="
	.text
	la      $t0, .L50
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L51:	.asciiz "\n"
	.text
	la      $t0, .L51
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L52:	.asciiz "////// comparison ops //////////\n"
	.text
	la      $t0, .L52
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	li      $t0, 1
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 2
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -12($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 1
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -20($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L53:	.asciiz "x="
	.text
	la      $t0, .L53
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
.L54:	.asciiz ", y="
	.text
	la      $t0, .L54
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L55:	.asciiz ", z="
	.text
	la      $t0, .L55
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L56:	.asciiz "\n"
	.text
	la      $t0, .L56
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L57:	.asciiz "x == y: "
	.text
	la      $t0, .L57
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L58:	.asciiz "\n"
	.text
	la      $t0, .L58
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L59:	.asciiz "x == z: "
	.text
	la      $t0, .L59
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L60:	.asciiz "\n"
	.text
	la      $t0, .L60
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L61:	.asciiz "x != y: "
	.text
	la      $t0, .L61
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L62:	.asciiz "\n"
	.text
	la      $t0, .L62
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L63:	.asciiz "x != z: "
	.text
	la      $t0, .L63
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L64:	.asciiz "\n"
	.text
	la      $t0, .L64
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L65:	.asciiz "!(x != z): "
	.text
	la      $t0, .L65
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L66:	.asciiz "\n"
	.text
	la      $t0, .L66
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L67:	.asciiz "x < y: "
	.text
	la      $t0, .L67
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L68:	.asciiz "\n"
	.text
	la      $t0, .L68
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L69:	.asciiz "x < z: "
	.text
	la      $t0, .L69
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L70:	.asciiz "\n"
	.text
	la      $t0, .L70
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L71:	.asciiz "y > x: "
	.text
	la      $t0, .L71
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L72:	.asciiz "\n"
	.text
	la      $t0, .L72
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L73:	.asciiz "x > z: "
	.text
	la      $t0, .L73
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L74:	.asciiz "\n"
	.text
	la      $t0, .L74
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L75:	.asciiz "x <= z: "
	.text
	la      $t0, .L75
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L76:	.asciiz "\n"
	.text
	la      $t0, .L76
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L77:	.asciiz "y <= x: "
	.text
	la      $t0, .L77
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L78:	.asciiz "\n"
	.text
	la      $t0, .L78
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L79:	.asciiz "z >= x: "
	.text
	la      $t0, .L79
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L80:	.asciiz "\n"
	.text
	la      $t0, .L80
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L81:	.asciiz "x >= y: "
	.text
	la      $t0, .L81
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L82:	.asciiz "\n"
	.text
	la      $t0, .L82
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L83:	.asciiz "////// stmts other than call/return //////////\n"
	.text
	la      $t0, .L83
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	li      $t0, 2
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L84:	.asciiz "x="
	.text
	la      $t0, .L84
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
.L85:	.asciiz "\n"
	.text
	la      $t0, .L85
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	addi    $t1, $t2, 1	#increase by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
				#WRITE STR
	.data
.L86:	.asciiz "x++; x="
	.text
	la      $t0, .L86
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
.L87:	.asciiz "\n"
	.text
	la      $t0, .L87
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sub     $t1, $t1, 1	#decrease by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
				#WRITE STR
	.data
.L88:	.asciiz "x--; x="
	.text
	la      $t0, .L88
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
.L89:	.asciiz "\n"
	.text
	la      $t0, .L89
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	addi    $t1, $t2, 1	#increase by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
				#WRITE STR
	.data
.L90:	.asciiz "++x; x="
	.text
	la      $t0, .L90
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
.L91:	.asciiz "\n"
	.text
	la      $t0, .L91
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sub     $t1, $t1, 1	#decrease by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
				#WRITE STR
	.data
.L92:	.asciiz "--x; x="
	.text
	la      $t0, .L92
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
.L93:	.asciiz "\n"
	.text
	la      $t0, .L93
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	li      $v0, 5
	syscall			#read in Integer
	sw      $v0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
				#WRITE STR
	.data
.L94:	.asciiz "scanf(\"%d\", &x); x="
	.text
	la      $t0, .L94
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
.L95:	.asciiz "\n"
	.text
	la      $t0, .L95
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L96:	.asciiz "scanf(\"%f\", &foo); foo="
	.text
	la      $t0, .L96
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	l.d     $f0, -36($fp)
	l.d     $f12, 4($sp)	#POP
	addu    $sp, $sp, 8
	li      $v0, 3
	syscall
				#WRITE STR
	.data
.L97:	.asciiz "\n"
	.text
	la      $t0, .L97
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L98:	.asciiz "////// And and OR logical operators //////////\n"
	.text
	la      $t0, .L98
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	li      $t0, 1
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 1
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -12($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 0
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -20($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L99:	.asciiz "x="
	.text
	la      $t0, .L99
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
.L100:	.asciiz ", y="
	.text
	la      $t0, .L100
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L101:	.asciiz ", z="
	.text
	la      $t0, .L101
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L102:	.asciiz "\n"
	.text
	la      $t0, .L102
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L103:	.asciiz "x && y: "
	.text
	la      $t0, .L103
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L104:	.asciiz "\n"
	.text
	la      $t0, .L104
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L105:	.asciiz "z && x++: "
	.text
	la      $t0, .L105
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L106:	.asciiz ", x="
	.text
	la      $t0, .L106
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
.L107:	.asciiz "\n"
	.text
	la      $t0, .L107
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L108:	.asciiz "x && z++: "
	.text
	la      $t0, .L108
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L109:	.asciiz ", z="
	.text
	la      $t0, .L109
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L110:	.asciiz "\n"
	.text
	la      $t0, .L110
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	li      $t0, 0
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 0
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -12($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 1
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -20($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L111:	.asciiz "x="
	.text
	la      $t0, .L111
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
.L112:	.asciiz ", y="
	.text
	la      $t0, .L112
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L113:	.asciiz ", z="
	.text
	la      $t0, .L113
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L114:	.asciiz "\n"
	.text
	la      $t0, .L114
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L115:	.asciiz "x || y: "
	.text
	la      $t0, .L115
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L116:	.asciiz "\n"
	.text
	la      $t0, .L116
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L117:	.asciiz "z || x++: "
	.text
	la      $t0, .L117
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L118:	.asciiz ", x="
	.text
	la      $t0, .L118
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
.L119:	.asciiz "\n"
	.text
	la      $t0, .L119
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L120:	.asciiz "x || z++: "
	.text
	la      $t0, .L120
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L121:	.asciiz ", z="
	.text
	la      $t0, .L121
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L122:	.asciiz "\n"
	.text
	la      $t0, .L122
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L123:	.asciiz "////// If statements //////////\n"
	.text
	la      $t0, .L123
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	li      $t0, 0
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 0
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -12($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 1
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -20($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L124:	.asciiz "x="
	.text
	la      $t0, .L124
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
.L125:	.asciiz ", y="
	.text
	la      $t0, .L125
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L126:	.asciiz ", z="
	.text
	la      $t0, .L126
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L127:	.asciiz "\n"
	.text
	la      $t0, .L127
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -12($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	bne     $t1, $t2, .L129	#check if equal
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	b       .L128
.L128:
				#WRITE STR
	.data
.L130:	.asciiz "yes: x == y"
	.text
	la      $t0, .L130
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L131:	.asciiz "\n"
	.text
	la      $t0, .L131
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
.L129:
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -20($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	bne     $t1, $t2, .L133	#check if equal
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	b       .L132
.L132:
				#WRITE STR
	.data
.L134:	.asciiz "this test failed!!!!!!!!!!!"
	.text
	la      $t0, .L134
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
				#WRITE STR
	.data
.L135:	.asciiz "\n"
	.text
	la      $t0, .L135
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
.L133:
				#WRITE STR
	.data
.L136:	.asciiz "////// If-Else statements //////////\n"
	.text
	la      $t0, .L136
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	li      $t0, 0
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 0
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -12($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 1
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -20($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L137:	.asciiz "x="
	.text
	la      $t0, .L137
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
.L138:	.asciiz ", y="
	.text
	la      $t0, .L138
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L139:	.asciiz ", z="
	.text
	la      $t0, .L139
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -20($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L140:	.asciiz "\n"
	.text
	la      $t0, .L140
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -12($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	bne     $t1, $t2, .L142	#check if equal
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	b       .L141
.L141:
				#WRITE STR
	.data
.L144:	.asciiz "inside if block: x == y\n"
	.text
	la      $t0, .L144
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	b       .L143		#skip else statements
.L142:
				#WRITE STR
	.data
.L145:	.asciiz "this test failed!!!!!!!!!!!\n"
	.text
	la      $t0, .L145
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
.L143:
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -20($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	bne     $t1, $t2, .L147	#check if equal
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	b       .L146
.L146:
				#WRITE STR
	.data
.L149:	.asciiz "this test failed!!!!!!!!!!!\n"
	.text
	la      $t0, .L149
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	b       .L148		#skip else statements
.L147:
				#WRITE STR
	.data
.L150:	.asciiz "inside else block: x != z\n"
	.text
	la      $t0, .L150
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
.L148:
				#WRITE STR
	.data
.L151:	.asciiz "////// While loops /////////\n"
	.text
	la      $t0, .L151
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	li      $t0, 0
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $t0, 4
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -12($fp)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
				#WRITE STR
	.data
.L152:	.asciiz "x="
	.text
	la      $t0, .L152
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
.L153:	.asciiz ", y="
	.text
	la      $t0, .L153
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	lw      $t0, -12($fp)
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall			#write an Integer
				#WRITE STR
	.data
.L154:	.asciiz "\n"
	.text
	la      $t0, .L154
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
.L155:
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, -12($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	bne     $t1, $t2, .L157	#check if equal
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	b       .L156
.L156:
				#WRITE STR
	.data
.L158:	.asciiz "this test failed!!!!!!!!!!!\n"
	.text
	la      $t0, .L158
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	b       .L155		#check while condition
.L157:
				#WRITE STR
	.data
.L159:	.asciiz "while x < 7; x++\n"
	.text
	la      $t0, .L159
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
.L160:
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	li      $t0, 7
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	bge     $t1, $t2, .L162	#check if less than
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	b       .L161
.L161:
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	addi    $t1, $t2, 1	#increase by 1
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $t1, -8($fp)
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
				#WRITE STR
	.data
.L163:	.asciiz "x="
	.text
	la      $t0, .L163
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
.L164:	.asciiz "\n"
	.text
	la      $t0, .L164
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall			#write a string
	b       .L160		#check while condition
.L162:
.L0:		# FUNCTION EXIT
	lw      $ra, 0($fp)	#load return address
	move    $t0, $fp	#save control link
	lw      $fp, -4($fp)	#restore FP
	move    $sp, $t0	#restore SP
	jr      $ra		#return

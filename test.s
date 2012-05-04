	.text
	.globl main
main:		# FUNCTION ENTRY
	sw      $ra, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $fp, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	addu    $fp, $sp, 8
	subu    $sp, $sp, 4
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
	lw      $t0, -8($fp)
	beqz    $t0, .L2
	b       .L1
.L1:
				#WRITE STR
	.data
.L4:	.asciiz "then"
	.text
	la      $t0, .L4
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall
	b       .L3
.L2:
				#WRITE STR
	.data
.L5:	.asciiz "else"
	.text
	la      $t0, .L5
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall
	lw      $t1, -8($fp)
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t2, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	li      $t0, 0
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t2, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	bne     $t1, $t2, .L7
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	b       .L6
.L6:
				#WRITE STR
	.data
.L8:	.asciiz "then2"
	.text
	la      $t0, .L8
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 4
	syscall
.L7:
.L3:
	li      $t0, 12
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall
.L0:		# FUNCTION EXIT
	lw      $ra, 0($fp)	#load return address
	move    $t0, $fp	#save control link
	lw      $fp, -4($fp)	#restore FP
	move    $sp, $t0	#restore SP
	jr      $ra		#return

	.data
	.align 2
_x:	.space 4	# GLOBAL
	.text
	.globl main
main:		# FUNCTION ENTRY
	sw      $ra, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $fp, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	addu    $fp, $sp, 8
	subu    $sp, $sp, 4
	li      $t0, 10
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	la      $t0, _x
	sw      $t1, 0($t0)
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t1, _x
	sw      $t1, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t1, 4($sp)	#POP
	addu    $sp, $sp, 4
	sub     $t1, $t1, 1
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
	lw      $t0, _x
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $a0, 4($sp)	#POP
	addu    $sp, $sp, 4
	li      $v0, 1
	syscall
	lw      $t0, -8($fp)
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

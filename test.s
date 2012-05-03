	.text
	.globl main
main:		# FUNCTION ENTRY
	sw      $ra, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	sw      $fp, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	addu    $fp, $sp, 8
	li      $t0, 3
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	sw      $t0, 0($sp)	#PUSH
	subu    $sp, $sp, 4
	lw      $t0, 4($sp)	#POP
	addu    $sp, $sp, 4
	lw      $t0, 0($fp)
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

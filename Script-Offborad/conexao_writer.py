import serial
import os
PORTA_SERIAL ="COM6"
BAUD_RATE = 9600

	
def write():
	conexao = serial.Serial(PORTA_SERIAL, BAUD_RATE)
	while True:
		print("Digite:")
		mensage = raw_input()
		conexao.write(mensage)
		os.system("cls")
				
write()
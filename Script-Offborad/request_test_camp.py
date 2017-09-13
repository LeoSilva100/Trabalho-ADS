
#coding: UTF8
import sys
import serial
import os
from datetime import datetime

PORTA_SERIAL ="COM6"
BAUD_RATE = 9600
now = datetime.now()
# Retrieve the CSRF token first
def get_string():
	conexao = serial.Serial(PORTA_SERIAL, BAUD_RATE)
	while True:
		now = datetime.now()
		time =  str(now)
		message = conexao.readline()
		print(message)
		arq = open('./log.txt', 'r')
		conteudo = arq.readlines()
		conteudo.append(time+ "|==|" +message)
		arq = open('./log.txt', 'w')
		arq.writelines(conteudo) 
		arq.close()

get_string()
OSDEP=../aircrack-ng/src/osdep/
CC=gcc
CFLAGS=-I${OSDEP}
LFLAGS=

OUTPUT=wifi_dump
CFILES=wifi_dump.c
OFILES=${CFILES:.c=.o}

all: ${OFILES}
	${CC} ${LFLAGS} ${OFILES} ${OSDEP}/libosdep.a -o ${OUTPUT} 

%.o: %.c
	${CC} ${CFLAGS} -c $< -o ${<:.c=.o}
	
clean:
	rm -rf *.o
	rm -rf ${OUTPUT}
	

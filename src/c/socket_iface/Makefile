OSDEP=../aircrack-ng/src/osdep/
CC=gcc
CFLAGS=-I${OSDEP} -DLinux -DAIRCRACK_NG_BYTE_ORDER_DEFINED -fPIC -DLinux
LFLAGS=
NAME=socket_test

CFILES=main.c
OFILES=${CFILES:.c=.o}

all: ${OFILES}
	${CC} ${LFLAGS} ${OFILES} ${OSDEP}/libosdep.a -o ${NAME}

%.o: %.c
	${CC} ${CFLAGS} -c $< -o ${<:.c=.o}
	
clean:
	rm -rf ${OFILES}
	rm -rf ${NAME}
	

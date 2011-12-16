JDKDIR=/usr/lib/jvm/java-1.6.0-openjdk
AIRCRACKNGDIR=/home/niels/src/aircrack-ng/

CC=gcc
NAME=aircrack-ng-jni
CFLAGS=-I${JDKDIR}/include -I${JDKDIR}/include/linux -I${AIRCRACKNGDIR}/src/osdep -fPIC
LFLAGS=-L$(JDKDIR)/jre/bin/classic -L$(JDKDIR)/jre/bin -fPIC -ldl -shared -D_FILE_OFFSET_BITS=64
CFILES=src/c/jni/org_tudelft_aircrack_Interface.c
OFILES=${CFILES:.c=.o}
LIBS=${AIRCRACKNGDIR}/src/osdep/libosdep.a

all: ${OFILES}
	${CC} ${LFLAGS} ${OFILES} ${LIBS} -o lib${NAME}.so
	
%o: %.c
	${CC} ${CFLAGS}

clean:
	rm -f lib${NAME}.so
	rm -f ${OFILES}

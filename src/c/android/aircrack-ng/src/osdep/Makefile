NDK=/home/niels/android-ndk-r7
TOOLCHAIN=${NDK}/toolchains/arm-linux-androideabi-4.4.3/prebuilt/linux-x86/bin
CC=${TOOLCHAIN}/arm-linux-androideabi-gcc --sysroot ${NDK}/platforms/android-4/arch-arm/ -fPIC -mandroid -DANDROID -DOS_ANDROID
CFLAGS=-I.. -DLinux

CFILES=osdep.c network.c file.c linux.c linux_tap.c radiotap/radiotap-parser.c common.c
OFILES=${CFILES:.c=.o}

all: ${OFILES}
	ar cru libosdep.a  osdep.o network.o file.o linux.o linux_tap.o radiotap/radiotap-parser.o common.o
	ranlib libosdep.a
	
%.o: %.c
	${CC} ${CFLAGS} -c $< -o ${<:.c=.o}
	
clean:
	rm -rf *.o
	rm -rf radiotap/*.o



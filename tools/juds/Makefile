SHELL = /bin/sh
VERSION = 0.94
PACKAGE = com.etsy.net
PACKAGE_DIR = com/etsy/net
TEST_SOCKET_FILE = JUDS_TEST_SOCKET_FILE
CC = gcc
JAVA_HOME = /usr/lib/jvm/java-6-openjdk/
JAVAC = /usr/bin/javac
JAR = $(JAVA_HOME)/bin/jar
JAVAH = $(JAVA_HOME)/bin/javah
PLATFORM=linux
PREFIX = /usr
CFLAGS = -g -O2 -shared
JAVA_FLAGS = -g:none -deprecation -target 1.6
NATIVELIB=libunixdomainsocket
LIBEXT=so
INCLUDEPATH = -I $(JAVA_HOME)/include -I $(JAVA_HOME)/include/$(PLATFORM)

LIB32=$(NATIVELIB)-$(PLATFORM)-i386.$(LIBEXT)
LIB64=$(NATIVELIB)-$(PLATFORM)-x86_64.$(LIBEXT)
M32=-m32
M64=-m64
JARFILE=juds-$(VERSION).jar
UNIJARFILE=juds-$(VERSION)-universal.jar

all: jar 

jar: $(JARFILE)

# requires that the binaries from other platforms are copied here. 
universal: jar
	cp $(JARFILE) $(UNIJARFILE)
	$(JAR) uf $(UNIJARFILE) $(NATIVELIB)-darwin-i386.dylib $(NATIVELIB)-darwin-x86_64.dylib $(NATIVELIB)-linux-x86_64.so $(NATIVELIB)-linux-i386.so

package: universal
	mkdir juds-$(VERSION)-universal
	cp $(UNIJARFILE) juds-$(VERSION)-universal
	cp README.md juds-$(VERSION)-universal
	cp COPYRIGHT juds-$(VERSION)-universal
	tar pczf juds-$(VERSION)-universal.tar.gz juds-$(VERSION)-universal
	rm -rf juds-$(VERSION)-universal

juds-$(VERSION).jar: $(PACKAGE_DIR)/UnixDomainSocket.class $(PACKAGE_DIR)/UnixDomainSocketClient.class $(PACKAGE_DIR)/UnixDomainSocketServer.class nativelib $(PACKAGE_DIR)/JUDS.class
	$(JAR) cf $@ $(PACKAGE_DIR)/*.class $(LIB64) $(LIB32)

nativelib: $(PACKAGE_DIR)/UnixDomainSocket.c $(PACKAGE_DIR)/UnixDomainSocket.h
	$(CC) $(CFLAGS) -fPIC $(INCLUDEPATH) $(M32) -o $(LIB32) $< 
	$(CC) $(CFLAGS) -fPIC $(INCLUDEPATH) $(M64) -o $(LIB64) $< 

$(PACKAGE_DIR)/UnixDomainSocket.h: $(PACKAGE).UnixDomainSocket
	$(JAVAH) -o $@ $<

$(PACKAGE).UnixDomainSocket: $(PACKAGE_DIR)/UnixDomainSocket.class

$(PACKAGE_DIR)/JUDS.class: $(PACKAGE_DIR)/JUDS.java
	$(JAVAC) $(JAVA_FLAGS) $?

$(PACKAGE_DIR)/UnixDomainSocket.class: $(PACKAGE_DIR)/UnixDomainSocket.java
	$(JAVAC) $(JAVA_FLAGS) $?

$(PACKAGE_DIR)/UnixDomainSocketClient.class: $(PACKAGE_DIR)/UnixDomainSocketClient.java
	$(JAVAC) $(JAVA_FLAGS) $?

$(PACKAGE_DIR)/UnixDomainSocketServer.class: $(PACKAGE_DIR)/UnixDomainSocketServer.java
	$(JAVAC) $(JAVA_FLAGS) $?

install: nativelib
	cp $(NATIVELIB) $(PREFIX)/lib

uninstall:
	rm -f $(PREFIX)/lib/$(NATIVELIB)

test: test/TestUnixDomainSocket.class test/TestUnixDomainSocketServer.class
	python test/TestUnixDomainSocket.py $(TEST_SOCKET_FILE) &
	@sleep 2
	java -cp $(JARFILE):test TestUnixDomainSocket $(TEST_SOCKET_FILE)
	rm -f $(TEST_SOCKET_FILE)
	java -cp $(JARFILE):test TestUnixDomainSocketServer $(TEST_SOCKET_FILE)
	rm -f $(TEST_SOCKET_FILE)

test/TestUnixDomainSocket.class: test/TestUnixDomainSocket.java jar
	$(JAVAC) -cp $(JARFILE) $(JAVA_FLAGS) $<


test/TestUnixDomainSocketServer.class: test/TestUnixDomainSocketServer.java jar
	$(JAVAC) -cp $(JARFILE) $(JAVA_FLAGS) $<

clean:
	rm -f $(PACKAGE_DIR)/*.class $(PACKAGE_DIR)/test/*.class $(PACKAGE_DIR)/*.h *.so *.jar $(TEST_SOCKET_FILE) test/*class

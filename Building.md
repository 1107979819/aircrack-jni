**This project is a work in progress, do not use for anything.**

# Compile the .so #

First you must build aircrack-ng itself. Specifically we need `libosdep.a`. Download aircrack-ng [source](http://www.aircrack-ng.org/) and unpack somewhere. Make sure to satisfy these [requirements](http://www.aircrack-ng.org/doku.php?id=install_aircrack#installing_aircrack-ng_from_source). Go into the directory where you unpacked it and type `make`.

Edit the Makefile in the root of aircrack-jni and change the following lines:

```
JDKDIR=[path to your JDK installation]
AIRCRACKNGDIR=[path to where you unpacked the aircrack-ng source]
```

Now you should be able to build `aircrack-jni.so` file by simply typing `make`.

# Running aircrack-jni #

You can build the code by typing `ant`. Type `sudo ./run` to run.
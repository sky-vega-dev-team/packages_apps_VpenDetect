#include <jni.h>
#include <stdio.h>
#include <sys/ioctl.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <unistd.h>

extern "C" JNIEXPORT jint JNICALL
Java_com_monitor_vpen_VPenDetect_setTouchMode(JNIEnv* env __unused, jobject thiz __unused, jint state) {

    int dev = 0;

    dev = open("/dev/touch_fops", O_RDWR);
    if (dev < 0) {
        printf("device file open error !!\n");
        return 0;
    }

    ioctl(dev,6001, state);

    close(dev);

    return 0;
}

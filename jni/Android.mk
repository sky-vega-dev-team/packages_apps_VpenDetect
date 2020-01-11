LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libtm_switch
LOCAL_SRC_FILES := tm_switch.cpp
LOCAL_MODULE_TAGS := optional

include $(BUILD_SHARED_LIBRARY)

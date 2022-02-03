#!/bin/bash

sleep 40

exec java \
    -showversion -server -Dfile.encoding=UTF-8 \
    -cp "/opt/messenger/lib/*" \
    -Xms$JAVA_XMS -Xmx$JAVA_XMX \
    -XX:ActiveProcessorCount=$CPU_CORES \
    $ADDITIONAL_JAVA_OPTOINS \
    -XX:+AlwaysPreTouch -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:MetaspaceSize=32m \
    -XX:+ParallelRefProcEnabled -XX:+ExplicitGCInvokesConcurrent \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:+ExitOnOutOfMemoryError \
    -XX:InitialCodeCacheSize=16m -XX:ReservedCodeCacheSize=32m \
    ru.yandex.sbd.messenger.MessengerApplication

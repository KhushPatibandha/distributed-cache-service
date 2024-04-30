#!/bin/bash

CPU_THRESHOLD_1=50
CPU_THRESHOLD_2=55
CPU_THRESHOLD_3=60
CPU_THRESHOLD_4=70
CPU_THRESHOLD_5=80
CPU_THRESHOLD_6=85
CPU_THRESHOLD_7=90

# 50% -> 4 replicas
# 55% -> 5 replicas
# 60% -> 6 replicas
# 70% -> 7 replicas
# 80% -> 8 replicas
# 85% -> 9 replicas
# 90% -> 10 replicas

while true; do
    CPU_USAGE=$(kubectl top pod -l app=cache-client --no-headers | awk '{print $2}' | sed 's/m//')
    CPU_USAGE_PERCENT=$((CPU_USAGE * 100 / 500))

    if(( CPU_USAGE_PERCENT > CPU_THRESHOLD_7 && CPU_USAGE_PERCENT < 100 )); then
        kubectl scale deployment cache-server --replicas=10
    elif(( CPU_USAGE_PERCENT > CPU_THRESHOLD_6  && CPU_USAGE_PERCENT < CPU_THRESHOLD_7 )); then
        kubectl scale deployment cache-server --replicas=9
    elif(( CPU_USAGE_PERCENT > CPU_THRESHOLD_5 && CPU_USAGE_PERCENT < CPU_THRESHOLD_6)); then
        kubectl scale deployment cache-server --replicas=8
    elif(( CPU_USAGE_PERCENT > CPU_THRESHOLD_4 && CPU_USAGE_PERCENT < CPU_THRESHOLD_5 )); then
        kubectl scale deployment cache-server --replicas=7
    elif(( CPU_USAGE_PERCENT > CPU_THRESHOLD_3 && CPU_USAGE_PERCENT < CPU_THRESHOLD_4 )); then
        kubectl scale deployment cache-server --replicas=6
    elif(( CPU_USAGE_PERCENT > CPU_THRESHOLD_2 && CPU_USAGE_PERCENT < CPU_THRESHOLD_3 )); then
        kubectl scale deployment cache-server --replicas=5
    elif(( CPU_USAGE_PERCENT > CPU_THRESHOLD_1 && CPU_USAGE_PERCENT < CPU_THRESHOLD_2 )); then
        kubectl scale deployment cache-server --replicas=4
    elif(( CPU_USAGE_PERCENT < CPU_THRESHOLD_1 )); then
        kubectl scale deployment cache-server --replicas=3
    fi

    sleep 10
done
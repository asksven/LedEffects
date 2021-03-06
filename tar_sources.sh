#!/bin/bash

if [ -d ./LedEffects ]; then 
  VERSION=`cat LedEffects/AndroidManifest.xml | grep "versionName" | sed 's/"/ /g' | awk {'print $2'}`
  tar -czf LedEffects_src_${VERSION}.tar.gz ./LedEffects/ --exclude=./LedEffects/bin/*
  tar -czf LedEffects_${VERSION}.tar.gz ./LedEffects/bin/LedEffects.apk
else
  echo "you must be in your workspace dir to tar sources"
fi

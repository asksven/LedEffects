#!/bin/bash

if [ -d ./LedEffects ]; then 
  tar -czf LedEffects.tar.gz ./LedEffects/ --exclude=./LedEffects/bin/*
else
  echo "you must be in your workspace dir to tar sources"
fi

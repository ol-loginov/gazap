#!/bin/bash

# make css
mkdir -p ./dist
lessc --verbose ./less/theme.less > ./dist/theme.css
lessc --compress --verbose ./less/theme.less > ./dist/theme.min.css

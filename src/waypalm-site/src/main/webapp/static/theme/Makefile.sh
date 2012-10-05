#!/bin/bash

# make css
mkdir -p ./dist
lessc --verbose ./less/layout.less > ./dist/theme.css
lessc --compress --verbose ./less/layout.less > ./dist/theme.min.css

#!/bin/bash

# make css
mkdir -p ./dist/css
lessc --verbose ./less/layout.less > ./dist/gazap.css
lessc --compress --verbose ./less/layout.less > ./dist/gazap.min.css

# make javascripts
for file in `find ./scripts -name *.js`
do
  filefolder=${file%/*.js}
  filefolder=${filefolder#./}
  filename=${file##*/}
  mkdir -p dist/$filefolder

  uglifyjs --output dist/$filefolder/$filename $file
done


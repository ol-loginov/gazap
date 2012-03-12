LESS_COMPRESSOR ?= `which lessc`
LESS_COMPRESSOR = ~/bin/lessc
WATCHR ?= `which watchr`

SOURCE = ./less/layout.less
OUTPUT = ./dist

#
# BUILD SIMPLE BOOTSTRAP DIRECTORY
# lessc & uglifyjs are required
#
gazap:
	mkdir -p ${OUTPUT}
	${LESS_COMPRESSOR} --verbose ${SOURCE} > ${OUTPUT}/gazap.css
	${LESS_COMPRESSOR} --verbose --compress ${SOURCE} > ${OUTPUT}/gazap.min.css


.PHONY: gazap

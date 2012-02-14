BOOTSTRAP = ./docs/assets/css/bootstrap.css
BOOTSTRAP_LESS = ./less/bootstrap.less
BOOTSTRAP_RESPONSIVE = ./docs/assets/css/bootstrap-responsive.css
BOOTSTRAP_RESPONSIVE_LESS = ./less/responsive.less
LESS_COMPRESSOR ?= `which lessc`
LESS_COMPRESSOR = ~/bin/lessc
WATCHR ?= `which watchr`

BOOTSTRAP_DIST = ./dist

#
# BUILD DOCS
#

docs: bootstrap
	rm docs/assets/bootstrap.zip
	zip -r docs/assets/bootstrap.zip bootstrap
	rm -r bootstrap
	${LESS_COMPRESSOR} --verbose ${BOOTSTRAP_LESS} > ${BOOTSTRAP}
	${LESS_COMPRESSOR} --verbose ${BOOTSTRAP_RESPONSIVE_LESS} > ${BOOTSTRAP_RESPONSIVE}
	node docs/build
	cp img/* docs/assets/img/
	cp js/*.js docs/assets/js/
	cp js/tests/vendor/jquery.js docs/assets/js/
	cp js/tests/vendor/jquery.js docs/assets/js/

#
# BUILD SIMPLE BOOTSTRAP DIRECTORY
# lessc & uglifyjs are required
#

bootstrap:
	mkdir -p ${BOOTSTRAP_DIST}/img
	mkdir -p ${BOOTSTRAP_DIST}/css
	mkdir -p ${BOOTSTRAP_DIST}/js
	cp img/* ${BOOTSTRAP_DIST}/img/
	${LESS_COMPRESSOR} --verbose ${BOOTSTRAP_LESS} > ${BOOTSTRAP_DIST}/css/bootstrap.css
	${LESS_COMPRESSOR} --verbose --compress ${BOOTSTRAP_LESS} > ${BOOTSTRAP_DIST}/css/bootstrap.min.css
	${LESS_COMPRESSOR} --verbose ${BOOTSTRAP_RESPONSIVE_LESS} > ${BOOTSTRAP_DIST}/css/bootstrap-responsive.css
	${LESS_COMPRESSOR} --verbose --compress ${BOOTSTRAP_RESPONSIVE_LESS} > ${BOOTSTRAP_DIST}/css/bootstrap-responsive.min.css
	cat js/bootstrap-transition.js js/bootstrap-alert.js js/bootstrap-button.js js/bootstrap-carousel.js js/bootstrap-collapse.js js/bootstrap-dropdown.js js/bootstrap-modal.js js/bootstrap-tooltip.js js/bootstrap-popover.js js/bootstrap-scrollspy.js js/bootstrap-tab.js js/bootstrap-typeahead.js > ${BOOTSTRAP_DIST}/js/bootstrap.js
	uglifyjs -nc ${BOOTSTRAP_DIST}/js/bootstrap.js > ${BOOTSTRAP_DIST}/js/bootstrap.min.js

#
# MAKE FOR GH-PAGES 4 FAT & MDO ONLY (O_O  )
#

gh-pages: docs
	rm -f ../bootstrap-gh-pages/assets/bootstrap.zip
	node docs/build production
	cp -r docs/* ../bootstrap-gh-pages

#
# WATCH LESS FILES
#

watch:
	echo "Watching less files..."; \
	watchr -e "watch('less/.*\.less') { system 'make' }"


.PHONY: dist bootstrap

cd www

wget \
     --recursive \
     --no-clobber \
     --page-requisites \
     --html-extension \
     --convert-links \
     --restrict-file-names=windows \
     --domains liris.cnrs.fr \
     --no-parent \
     --no-host-directories \
http://liris.cnrs.fr/ideal/mooc/lesson.php

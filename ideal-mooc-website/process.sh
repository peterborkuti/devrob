#!/bin/bash

indir="ideal/mooc"
outdir="mooc"

mkdir $outdir

mv $indir/lesson.php.html $outdir/index.html

for i in $indir/lesson.*.html; do
    fn=`basename $i`
    cat $i|sed -e '/Lessons:/,/class="page"/d' |sed -e '/Share/,/\/ul/d'| sed -e 's/pre class="lesson"/pre/'> $outdir/$fn
done
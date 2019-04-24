for f in *.png*
do
	mv "$f" "${f: -7}"
done

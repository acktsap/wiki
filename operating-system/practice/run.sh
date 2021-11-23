readonly FILE=$1

if [ -z ${FILE} ]; then
  echo "no file selected"
  exit 0
fi

gcc ${FILE} -o a.out > /dev/null 2>&1
./a.out
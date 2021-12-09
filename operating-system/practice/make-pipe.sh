# make an named pipe
mkfifo my_pipe
gzip -9 -c < my_pipe > out.gz &

### run following command
# use pipe
# cat input.txt > my_pipe

# remove pipe
# rm my_pipe


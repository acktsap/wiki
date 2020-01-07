direnv allow . > /dev/null 2>&1
find . -type d -maxdepth 2 ! -name ".git" -exec direnv allow {} > /dev/null 2>&1 \; 

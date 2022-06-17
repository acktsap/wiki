# Setting Instruction

1. guide 따라 위에서 아래로 설치
    - [osx](./osx.md)
    - [windows](./windows.md)
2. ssh 설정
    - [ssh 공개키만들기](https://git-scm.com/book/ko/v2/Git-서버-SSH-공개키-만들기)
    - `~/.ssh/config`
      ```sh
      # make sure IdentityFile is in 400 mode
      
      Host bitbucket.org
        IdentityFile ~/.ssh/sibera21@gmail.com
        StrictHostKeyChecking no
      
      Host github.com
        IdentityFile ~/.ssh/sibera21@gmail.com
        StrictHostKeyChecking no
      
      Host *
        IdentityFile ~/.ssh/sibera21@gmail.com
        StrictHostKeyChecking no
      ```
3. terminal setting
   - `git@github.com:acktsap/terminal-setting.git`